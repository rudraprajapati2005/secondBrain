package com.project.secondBrain.service;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.project.secondBrain.entity.Archive;
import com.project.secondBrain.entity.User;
import com.project.secondBrain.repository.ArchiveRepository;
import com.project.secondBrain.repository.UserRepository;

@Service
public class AiSearchService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ArchiveRepository archiveRepository;
    private final GroqService groqService;
    private final EmbeddingService embService;
    public AiSearchService(
            UserService userService,
            UserRepository userRepository,
            ArchiveRepository archiveRepository,
            GroqService groqService,
            EmbeddingService embService
        ) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.archiveRepository = archiveRepository;
        this.groqService = groqService;
        this.embService = embService;
    }

    public String askQuestion(String question) {

        String email =
                userService.getCurrentUserEmail();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));
        List<Archive> archives = archiveRepository.findByUser(user).stream()
            .sorted(Comparator.comparingInt((Archive archive) -> calculateScore(question, archive)).reversed())
            .limit(5)
            .toList();
    if (archives.isEmpty()) {
    return "I couldn't find any related archive.";
        }
        archives.forEach(a ->
    System.out.println(
            a.getTitle() +
            " score = " +
            calculateScore(question, a)
            )
        );
        StringBuilder context =
                new StringBuilder();

        for (Archive archive : archives) {

            context.append("Title: ")
                    .append(archive.getTitle())
                    .append("\n");

            context.append("Description: ")
                    .append(
                        archive.getDescription() == null
                        ? ""
                        : archive.getDescription()
                    )
                    .append("\n");

            context.append("URL: ")
                    .append(archive.getUrl())
                    .append("\n\n");
        }

        String prompt =
                """
                You are helping a user search their Second Brain.

                Archives:
                %s

                User Question:
                %s

                Rules:
                1. Answer only using the archives above.
                2. If the answer cannot be found, say so.
                3. Mention the archive title when possible.
                """
                .formatted(context.toString(), question);

        return groqService.askGroq(prompt);
    }

    private int calculateScore(String question, Archive archive) {
   
    String text =
            (
                archive.getTitle() + " " +
                archive.getDescription() + " " +
                archive.getUrl()
            ).toLowerCase();

    String[] keywords =
            question.toLowerCase().split("\\s+");

    int score = 0;

    for (String word : keywords) {

        if (word.length() < 3) {
            continue;
        }

        if (text.contains(word)) {
            score++;
        }
    }

    return score;
}
}