package com.kidneystone.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class AnalysisRequest {

    @NotBlank(message = "Analysis type is required")
    private String analysisType;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private Double value;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Collection date is required")
    private LocalDate collectionDate;

    public AnalysisRequest() {
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

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }
}