package com.kidneystone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AnalysisRequest {
    
    @NotNull(message="User ID is required")
    private Long userId;

    @NotBlank(message="Analysis type is required")
    private String analysisType;

    @NotNull(message="Value is required")
    @Positive(message="Value must be positive")
    private Double value;
    
    @NotBlank(message="Unit is required")
    private String unit;

    @NotBlank(message="Collection date is required")
    private String collectionDate;
    public AnalysisRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnalysisType() {
        return analysisType;
    }   

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }
    


}
