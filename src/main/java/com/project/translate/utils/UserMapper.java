package com.project.translate.utils;

import com.project.translate.dto.response.UserResponse;
import com.project.translate.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "publicId", target = "userId")
    @Mapping(source = "ppUrl", target = "profilePicture")
    UserResponse toUserResponse(AppUser user);
}
