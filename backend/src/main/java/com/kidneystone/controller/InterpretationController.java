package com.kidneystone.controller;

import com.kidneystone.dto.AiInterpretationResponse;
import com.kidneystone.model.User;
import com.kidneystone.service.InterpretationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyses")
public class InterpretationController {

    private final InterpretationService interpretationService;

    public InterpretationController(InterpretationService interpretationService) {
        this.interpretationService = interpretationService;
    }

    @PostMapping("/{id}/interpret")
    public ResponseEntity<AiInterpretationResponse> interpretAnalysis(
            @PathVariable Long id,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        AiInterpretationResponse response = interpretationService.interpretAnalysis(id, authenticatedUser);
        return ResponseEntity.ok(response);
    }
}