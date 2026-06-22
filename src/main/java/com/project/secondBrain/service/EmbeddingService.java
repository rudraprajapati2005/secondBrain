package com.project.secondBrain.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class EmbeddingService {

    @Value("${baai-bge-3-read}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // ✅ Embedding endpoint (feature-extraction pipeline)
    private static final String EMBEDDING_URL =
        "https://router.huggingface.co/hf-inference/models/BAAI/bge-m3/pipeline/feature-extraction";

    // ✅ Sentence similarity endpoint (testing only)
    private static final String SIMILARITY_URL =
        "https://router.huggingface.co/hf-inference/models/BAAI/bge-m3/pipeline/sentence-similarity";

    /**
     * Generate an embedding vector for a given text.
     * Returns double[] suitable for pgvector storage.
     */
    public double[] generateEmbedding(String text) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));

    String body = String.format("{\"inputs\":\"%s\"}", text);

    HttpEntity<String> request = new HttpEntity<>(body, headers);
    ResponseEntity<String> response =
        restTemplate.postForEntity(EMBEDDING_URL, request, String.class);

    try {
        Object parsed = mapper.readValue(response.getBody(), Object.class);

        if (parsed instanceof List) {
            List<?> outer = (List<?>) parsed;

            // Case 1: nested list [[...]]
            if (!outer.isEmpty() && outer.get(0) instanceof List) {
                List<?> inner = (List<?>) outer.get(0);
                double[] vector = new double[inner.size()];
                for (int i = 0; i < inner.size(); i++) {
                    vector[i] = ((Number) inner.get(i)).doubleValue();
                }
                return vector;
            }

            // Case 2: flat list [...]
            double[] vector = new double[outer.size()];
            for (int i = 0; i < outer.size(); i++) {
                vector[i] = ((Number) outer.get(i)).doubleValue();
            }
            return vector;
        }

        throw new RuntimeException("Unexpected embedding response: " + response.getBody());
    } catch (Exception e) {
        throw new RuntimeException("Failed to parse embedding response: " + response.getBody(), e);
    }
}

    /**
     * Compare a source sentence against candidate sentences.
     * Returns similarity scores as double[].
     */
    public double[] sentenceSimilarity(String source, List<String> sentences) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        Map<String, Object> inputs = Map.of(
            "source_sentence", source,
            "sentences", sentences
        );
        Map<String, Object> body = Map.of("inputs", inputs);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response =
            restTemplate.postForEntity(SIMILARITY_URL, request, String.class);

        try {
            // Hugging Face returns a flat list of floats
            List<Double> scores = mapper.readValue(
                response.getBody(),
                new TypeReference<List<Double>>() {}
            );
            return scores.stream().mapToDouble(Double::doubleValue).toArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse similarity response", e);
        }
    }
}
