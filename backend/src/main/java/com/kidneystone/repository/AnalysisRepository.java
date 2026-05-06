package com.kidneystone.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kidneystone.model.Analysis;
import com.kidneystone.model.User;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

    List<Analysis>findByUser(User user);
    List<Analysis>findByUserAndAnalysisTypeContainingIgnoreCase(User user, String analysisType);
    List<Analysis>findByUserAndCollectionDate(User user, LocalDate collectionDate);
    List<Analysis>findByUserAndAnalysisTypeContainingIgnoreCaseAndCollectionDate(User user,String analysisType,LocalDate collectionDate);   
}
