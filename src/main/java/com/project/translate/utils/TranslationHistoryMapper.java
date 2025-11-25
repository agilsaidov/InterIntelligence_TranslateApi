package com.project.translate.utils;

import com.project.translate.dto.response.TranslationHistoryResponse;
import com.project.translate.model.TranslationHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TranslationHistoryMapper {
    @Mapping(source = "id", target = "translationId")
    TranslationHistoryResponse toResponse(TranslationHistory translationHistory);

    List<TranslationHistoryResponse> toResponseList(List<TranslationHistory> translationHistories);
}
