package com.convit.autosuggestion.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.convit.autosuggestion.util.Constants.Business.OFFERINGS_RAW;
import static com.convit.autosuggestion.util.Constants.Business.STATE;
import static com.convit.autosuggestion.util.Constants.Business.RATING;
import static com.convit.autosuggestion.util.Constants.Business.LOCATION;
import static com.convit.autosuggestion.util.Constants.Business.CATEGORY_RAW;
import static com.convit.autosuggestion.util.Constants.Business.NAME;
import static com.convit.autosuggestion.util.Constants.Business.CATEGORY;
import static com.convit.autosuggestion.util.Constants.Business.OFFERINGS;
import static com.convit.autosuggestion.util.Constants.Business.ADDRESS;
import static com.convit.autosuggestion.util.Constants.Business.DESCRIPTION;
import static com.convit.autosuggestion.util.ElasticsearchUtil.buildGeoDistanceQuery;
import static com.convit.autosuggestion.util.ElasticsearchUtil.buildRangeQuery;
import static com.convit.autosuggestion.util.ElasticsearchUtil.buildTermQuery;
import static com.convit.autosuggestion.util.ElasticsearchUtil.buildMultiMatchQuery;


public class QueryRules {

    private static final String BOOST_FIELD_FORMAT = "%s^%f";

    public static final QueryRule STATE_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.state()),
            srp -> buildTermQuery(STATE, srp.state(), 1.0f)
    );

    public static final QueryRule OFFERINGS_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.offerings()),
            srp -> buildTermQuery(OFFERINGS_RAW, srp.offerings(), 1.0f)
    );

    public static final QueryRule RATING_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.rating()),
            srp -> buildRangeQuery(RATING, builder -> builder.gte(srp.rating()))
    );

    public static final QueryRule DISTANCE_QUERY = QueryRule.of(
            srp -> Stream.of(srp.distance(), srp.longitude(), srp.latitude()).allMatch(Objects::nonNull),
            srp -> buildGeoDistanceQuery(LOCATION, srp.distance(), srp.latitude(), srp.longitude())
    );

    public static final QueryRule CATEGORY_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.query()),  // can also use Predicates.isTrue() if it is true always
            srp -> buildTermQuery(CATEGORY_RAW, srp.query(), 5.0f)
    );

    private static final List<String> SEARCH_BOOST_FIELDS = List.of(
            boostField(NAME, 2.0f),
            boostField(CATEGORY, 1.5f),
            boostField(OFFERINGS, 1.5f),
            boostField(ADDRESS, 1.2f),
            DESCRIPTION
    );

    public static final QueryRule SEARCH_QUERY = QueryRule.of(
            srp -> Objects.nonNull(srp.query()),  // can also use Predicates.isTrue() if it is true always
            srp -> buildMultiMatchQuery(SEARCH_BOOST_FIELDS, srp.query())
    );

    private static String boostField(String field, float boost){
        return BOOST_FIELD_FORMAT.formatted(field, boost);
    }
}
