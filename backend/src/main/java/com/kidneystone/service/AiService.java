package com.kidneystone.service;

import com.kidneystone.model.Analysis;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final OpenAIClient client;

    @Value("${openai.model}")
    private String model;

    public AiService(@Value("${openai.api-key}") String apiKey) {
        this.client = OpenAIOkHttpClient.builder()
                .apiKey(apiKey)
                .build();
    }

    public String interpretAnalysis(Analysis analysis) {
        String prompt = buildPrompt(analysis);

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model(model)
                .input(prompt)
                .build();

        Response response = client.responses().create(params);

        return response.output().stream()
        .flatMap(item -> item.message().stream())
        .flatMap(message -> message.content().stream())
        .flatMap(content -> content.outputText().stream())
        .map(outputText -> outputText.text())
        .findFirst()
        .orElse("No AI interpretation was generated.");
    }

    private String buildPrompt(Analysis analysis) {
        return """
                You are an assistant inside a kidney stone medical analysis management application.

                Interpret the following laboratory analysis in a clear, simple and responsible way.

                Important rules:
                - Do not give a diagnosis.
                - Do not prescribe treatment.
                - Mention that reference ranges may vary depending on laboratory and patient context.
                - Recommend consulting a doctor for medical decisions.
                - Keep the answer concise.
                - Answer in Romanian.

                Analysis data:
                Type: %s
                Value: %.2f
                Unit: %s
                Collection date: %s
                """.formatted(
                analysis.getAnalysisType(),
                analysis.getValue(),
                analysis.getUnit(),
                analysis.getCollectionDate()
        );
    }
}