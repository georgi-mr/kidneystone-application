package com.kidneystone.service;

import com.kidneystone.dto.AiInterpretationResponse;
import com.kidneystone.model.Analysis;
import com.kidneystone.model.User;
import com.kidneystone.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

@Service
public class InterpretationService {

    private final AnalysisRepository analysisRepository;
    private final AiService aiService;

    public InterpretationService(AnalysisRepository analysisRepository, AiService aiService) {
        this.analysisRepository = analysisRepository;
        this.aiService = aiService;
    }

    public AiInterpretationResponse interpretAnalysis(Long analysisId, User authenticatedUser) {
        Analysis analysis = analysisRepository.findById(analysisId)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        if (!analysis.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("You are not allowed to access this analysis");
        }

        String interpretation = aiService.interpretAnalysis(analysis);

        analysis.setAiInterpretation(interpretation);
        analysisRepository.save(analysis);

        return new AiInterpretationResponse(
                analysis.getId(),
                interpretation,
                "Această interpretare este informativă și nu înlocuiește consultul medical.");
    }
}