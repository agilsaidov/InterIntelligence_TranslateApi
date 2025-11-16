package com.project.translate.service;

import com.project.translate.dto.request.StarredTranslationRequest;
import com.project.translate.dto.response.StarredTranslationResponse;
import com.project.translate.exception.StarredTranslationNotFound;
import com.project.translate.model.StarredTranslation;
import com.project.translate.repository.StarredTranslationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarredTranslationService {

    private final StarredTranslationRepo repo;

    public void starTranslation(StarredTranslationRequest request) {
        StarredTranslation staredTranslation = new StarredTranslation();

        staredTranslation.setUserId(request.getUserId());
        staredTranslation.setTranslationId(request.getTranslationId());

        repo.save(staredTranslation);
    }

    public List<StarredTranslationResponse> getStarredTranslations(String userId) {
        return repo.getStarredTranslationsByUserId(userId);
    }


    public void unstarTranslation(StarredTranslationRequest request) {
        long affectedRows = repo.deleteByUserIdAndTranslationId(
                request.getUserId(),
                request.getTranslationId()
        );

        if(affectedRows == 0) {
            throw new StarredTranslationNotFound(
                    "Translation with id " +
                    request.getTranslationId() +
                    " not found for the user"
            );
        }
    }
}
