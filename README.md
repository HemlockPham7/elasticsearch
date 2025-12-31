# Auto Suggestion For 5 Millions Data

## Get data in Kibana devtools

```
GET /suggestions/_search

POST /businesses/_search
{
    "query": {
        "match_all": {}
    },
    "track_total_hits": true
}
```