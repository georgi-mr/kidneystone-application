package com.kidneystone.service;

import com.kidneystone.dto.AnalysisRequest;
import com.kidneystone.dto.AnalysisResponse;
import com.kidneystone.model.Analysis;
import com.kidneystone.model.User;
import com.kidneystone.repository.AnalysisRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalysisService {

    private final AnalysisRepository analysisRepository;

    public AnalysisService(AnalysisRepository analysisRepository) {
        this.analysisRepository = analysisRepository;
    }

    public AnalysisResponse createAnalysis(User authenticatedUser, AnalysisRequest request) {
        Analysis analysis = new Analysis(
                request.getAnalysisType(),
                request.getValue(),
                request.getUnit(),
                request.getCollectionDate(),
                authenticatedUser
        );

        Analysis savedAnalysis = analysisRepository.save(analysis);

        return new AnalysisResponse(savedAnalysis);
    }

    public List<AnalysisResponse> getAnalyses(
            User authenticatedUser,
            String analysisType,
            LocalDate collectionDate
    ) {
        List<Analysis> analyses;

        if (analysisType != null && !analysisType.isBlank() && collectionDate != null) {
            analyses = analysisRepository.findByUserAndAnalysisTypeContainingIgnoreCaseAndCollectionDate(
                    authenticatedUser,
                    analysisType,
                    collectionDate
            );
        } else if (analysisType != null && !analysisType.isBlank()) {
            analyses = analysisRepository.findByUserAndAnalysisTypeContainingIgnoreCase(
                    authenticatedUser,
                    analysisType
            );
        } else if (collectionDate != null) {
            analyses = analysisRepository.findByUserAndCollectionDate(
                    authenticatedUser,
                    collectionDate
            );
        } else {
            analyses = analysisRepository.findByUser(authenticatedUser);
        }

        return analyses.stream()
                .map(AnalysisResponse::new)
                .toList();
    }

    public AnalysisResponse getAnalysisById(Long id, User authenticatedUser) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        validateAnalysisOwner(analysis, authenticatedUser);

        return new AnalysisResponse(analysis);
    }

    public AnalysisResponse updateAnalysis(
            Long id,
            User authenticatedUser,
            AnalysisRequest request
    ) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        validateAnalysisOwner(analysis, authenticatedUser);

        analysis.setAnalysisType(request.getAnalysisType());
        analysis.setValue(request.getValue());
        analysis.setUnit(request.getUnit());
        analysis.setCollectionDate(request.getCollectionDate());

        Analysis updatedAnalysis = analysisRepository.save(analysis);

        return new AnalysisResponse(updatedAnalysis);
    }

    public void deleteAnalysis(Long id, User authenticatedUser) {
        Analysis analysis = analysisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Analysis not found"));

        validateAnalysisOwner(analysis, authenticatedUser);

        analysisRepository.delete(analysis);
    }

    private void validateAnalysisOwner(Analysis analysis, User authenticatedUser) {
        if (!analysis.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("You are not allowed to access this analysis");
        }
    }
}