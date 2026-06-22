package com.project.secondBrain.service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestClient restClient =
            RestClient.create();

   public String askGroq(String prompt) {

    Map<String, Object> requestBody =
            Map.of(
                    "model", "llama-3.3-70b-versatile",
                    "messages", new Object[]{
                            Map.of(
                                    "role", "user",
                                    "content", prompt)
                    }
            );

    String response =
            restClient.post()
                    .uri("https://api.groq.com/openai/v1/chat/completions")
                    .header("Authorization",
                            "Bearer " + apiKey)
                    .header("Content-Type",
                            "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);
        try{
                ObjectMapper mapper = new ObjectMapper();

                JsonNode node = mapper.readTree(response);

                return node.path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText();
        }
        catch(Exception e)
        {
                throw new RuntimeException(
                        "Failed to parse Groq response" , e);
        }
    }
}
