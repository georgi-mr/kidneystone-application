package com.kidneystone.dto;

public class AiInterpretationResponse {

    private Long analysisId;
    private String interpretation;
    private String disclaimer;

    public AiInterpretationResponse(Long analysisId, String interpretation, String disclaimer) {
        this.analysisId = analysisId;
        this.interpretation = interpretation;
        this.disclaimer = disclaimer;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public String getDisclaimer() {
        return disclaimer;
    }
}