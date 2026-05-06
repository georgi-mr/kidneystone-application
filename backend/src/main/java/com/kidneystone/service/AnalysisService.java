package com.kidneystone.service;

import com.kidneystone.dto.AnalysisRequest;
import com.kidneystone.dto.AnalysisResponse;
import com.kidneystone.model.Analysis;
import com.kidneystone.model.User;
import com.kidneystone.repository.AnalysisRepository;
import com.kidneystone.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AnalysisService {
private final AnalysisRepository analysisRepository;
private final UserRepository userRepository;

public AnalysisService(AnalysisRepository analysisRepository, UserRepository userRepository) {
    this.analysisRepository = analysisRepository;
    this.userRepository = userRepository;
}

public  AnalysisResponse createAnalysis(AnalysisRequest request){
    User user=getUserById(request.getUserId());
    Analysis analysis=new Analysis(
        request.getAnalysisType(),
        request.getValue(),
        request.getUnit(),
        LocalDate.parse(request.getCollectionDate()),
        user
    );

    Analysis savedAnalysis=analysisRepository.save(analysis);
   return new AnalysisResponse(savedAnalysis);
}

public List<AnalysisResponse>getAnalysisResponses(Long userId,String analysisType, LocalDate collectionDate){
    User user=getUserById(userId);
    List<Analysis> analyses;

    if(analysisType!=null && collectionDate!=null && !analysisType.isBlank()){
        analyses=analysisRepository.findByUserAndAnalysisTypeContainingIgnoreCaseAndCollectionDate(user, analysisType, collectionDate);

    } else if( analysisType!=null && !analysisType.isBlank()){
        analyses=analysisRepository.findByUserAndAnalysisTypeContainingIgnoreCase(user, analysisType);

    }else if(collectionDate!=null){
        analyses=analysisRepository.findByUserAndCollectionDate(user, collectionDate);
    } else{
        analyses=analysisRepository.findByUser(user);
    }

    return analyses.stream().map(AnalysisResponse::new).toList();
    
}

public AnalysisResponse getAnalysisById(Long id, Long userId){
    User user=getUserById(userId);
    Analysis analysis=analysisRepository.findById(id)
    .orElseThrow(()->new RuntimeException("Analysis not found"));
    validateAnalysisOwner(analysis,user);
    return new AnalysisResponse(analysis);
    
}

public AnalysisResponse updateAnalysis(Long id, AnalysisRequest request){
    User user=getUserById(request.getUserId());
   Analysis analysis=analysisRepository.findById(id)
   .orElseThrow(()->new RuntimeException("Analysis not found"));
   validateAnalysisOwner(analysis,user);  
   analysis.setTipeAnalysis(request.getAnalysisType());
   analysis.setValue(request.getValue());
   analysis.setUnit(request.getUnit());
   analysis.setDate(LocalDate.parse(request.getCollectionDate()));
   Analysis updatedAnalysis=analysisRepository.save(analysis);
   return new AnalysisResponse(updatedAnalysis);  

}

public void deleteAnalysis(Long id, Long userId){
    User user=getUserById(userId);
    Analysis analysis=analysisRepository.findById(id)
    .orElseThrow(()->new RuntimeException("Analysis not found"));
    validateAnalysisOwner(analysis,user);
    analysisRepository.delete(analysis);
}

private void validateAnalysisOwner(Analysis analysis, User user) {
   if(!analysis.getUser().getId().equals(user.getId())){
    throw new RuntimeException("Unauthorized access to analysis");
   }
}

private User getUserById(Long userId) {
    return userRepository.findById(userId)
    .orElseThrow(()->new RuntimeException("User not found"));
}

}
