package com.kidneystone.controller;

import com.kidneystone.dto.AnalysisRequest;
import com.kidneystone.dto.AnalysisResponse;
import com.kidneystone.model.User;
import com.kidneystone.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analyses")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping
    public ResponseEntity<AnalysisResponse> createAnalysis(
            @AuthenticationPrincipal User authenticatedUser,
            @Valid @RequestBody AnalysisRequest request) {
        AnalysisResponse response = analysisService.createAnalysis(authenticatedUser, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<AnalysisResponse> uploadFile(
            @PathVariable Long id,
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam("file") MultipartFile file) {
        AnalysisResponse response = analysisService.attachFile(id, authenticatedUser, file);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AnalysisResponse>> getAnalyses(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestParam(required = false) String analysisType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate collectionDate) {
        List<AnalysisResponse> analyses = analysisService.getAnalyses(
                authenticatedUser,
                analysisType,
                collectionDate);

        return ResponseEntity.ok(analyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponse> getAnalysisById(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable Long id) {
        AnalysisResponse response = analysisService.getAnalysisById(id, authenticatedUser);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnalysisResponse> updateAnalysis(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable Long id,
            @Valid @RequestBody AnalysisRequest request) {
        AnalysisResponse response = analysisService.updateAnalysis(id, authenticatedUser, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnalysis(
            @AuthenticationPrincipal User authenticatedUser,
            @PathVariable Long id) {
        analysisService.deleteAnalysis(id, authenticatedUser);
        return ResponseEntity.noContent().build();
    }
}