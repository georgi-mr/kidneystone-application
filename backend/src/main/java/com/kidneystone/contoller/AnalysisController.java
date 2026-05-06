package com.kidneystone.contoller;

import java.time.LocalDate;
import java.util.List;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kidneystone.dto.AnalysisRequest;
import com.kidneystone.dto.AnalysisResponse;
import com.kidneystone.service.AnalysisService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;
    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping
    public ResponseEntity<AnalysisResponse> createAnalysis(@Valid @RequestBody AnalysisRequest request){
        AnalysisResponse response=analysisService.createAnalysis(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<AnalysisResponse>> getAnalyses(
        @RequestParam Long userId, 
        @RequestParam(required = false) String analysisType, 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate collectionDate)
        {
        List<AnalysisResponse> responses=analysisService.getAnalysisResponses(userId, analysisType, collectionDate);
        return ResponseEntity.ok(responses);
    }
      @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponse> getAnalysisById(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        AnalysisResponse response = analysisService.getAnalysisById(id, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResponse> updateAnalysis(
            @PathVariable Long id,
            @Valid @RequestBody AnalysisRequest request
    ) {
        AnalysisResponse response = analysisService.updateAnalysis(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        analysisService.deleteAnalysis(id, userId);
        return ResponseEntity.noContent().build();
    }
    
}
