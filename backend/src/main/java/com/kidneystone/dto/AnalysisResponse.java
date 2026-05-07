package com.kidneystone.dto;

import com.kidneystone.model.Analysis;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AnalysisResponse {

    private Long id;
    private String analysisType;
    private Double value;
    private String unit;
    private LocalDate collectionDate;
    private LocalDateTime createdAt;
    private Long userId;
    private String aiInterpretation;
    private String fileName;
    private String filePath;
    private String fileType;

    public AnalysisResponse(Analysis analysis) {
        this.id = analysis.getId();
        this.analysisType = analysis.getAnalysisType();
        this.value = analysis.getValue();
        this.unit = analysis.getUnit();
        this.collectionDate = analysis.getCollectionDate();
        this.createdAt = analysis.getCreatedAt();
        this.userId = analysis.getUser().getId();
        this.aiInterpretation = analysis.getAiInterpretation();
        this.fileName = analysis.getFileName();
        this.filePath = analysis.getFilePath();
        this.fileType = analysis.getFileType();
    }

    public Long getId() {
        return id;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAiInterpretation() {
        return aiInterpretation;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }
}