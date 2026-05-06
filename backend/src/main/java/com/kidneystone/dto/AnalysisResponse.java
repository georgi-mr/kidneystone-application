package com.kidneystone.dto;

import java.time.LocalDateTime;

import com.kidneystone.model.Analysis;

public class AnalysisResponse {
    
    private Long id;
    private String analysisType;
    private Double value;
    private  String unit;
    private String collectionDate;
    private LocalDateTime createdAt;
    private Long userId;

  public AnalysisResponse(Analysis analysis) {
        this.id = analysis.getId();
        this.analysisType = analysis.getTipeAnalysis();
        this.value = analysis.getValue();
        this.unit = analysis.getUnit();
        this.collectionDate = analysis.getDate().toString();
        this.createdAt = analysis.getCreatedAt();
        this.userId = analysis.getUser().getId();
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
    public String getCollectionDate() {
        return collectionDate;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Long getUserId() {
        return userId;
    }


}
