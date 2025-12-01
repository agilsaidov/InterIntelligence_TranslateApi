package com.project.translate.repository;

import com.project.translate.model.AppUser;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends CrudRepository<AppUser,Long> {
    boolean existsAppUserByEmail(String email);

    boolean existsAppUserByUsername(@NotNull(message = "Username is mandatory") @Size(min=6, max = 30, message = "Username must be between 6 and 30 characters") String username);

    Optional<AppUser> findAppUserByEmail(String email);

    Optional<AppUser> getAppUserByPublicId(String publicId);

}
