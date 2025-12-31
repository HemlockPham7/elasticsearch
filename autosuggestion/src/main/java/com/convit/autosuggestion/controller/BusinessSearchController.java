package com.convit.autosuggestion.controller;

import com.convit.autosuggestion.dto.SuggestionRequestParameters;
import com.convit.autosuggestion.service.SuggestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BusinessSearchController {

    private final SuggestionService suggestionService;

    @GetMapping("api/suggestions")
    public List<String> suggest(SuggestionRequestParameters parameters) {
        return suggestionService.fetchSuggestions(parameters);
    }

}
