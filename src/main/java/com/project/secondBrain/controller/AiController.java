package com.project.secondBrain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.project.secondBrain.dto.AiSearchResponse;
import com.project.secondBrain.dto.AiSearchRequest;
import com.project.secondBrain.service.AiSearchService;

@RestController
@RequestMapping("api/ai")
public class AiController {

    private final AiSearchService aiService;
    
    public AiController(AiSearchService aiService)
    {
        this.aiService = aiService;
    }

    @PostMapping("/search")
    public AiSearchResponse search(@RequestBody AiSearchRequest req)
    {
        String ans = aiService.askQuestion(req.getQuestion());
        return new AiSearchResponse(ans);
    }
}
