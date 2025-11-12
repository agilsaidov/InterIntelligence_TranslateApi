package com.project.translate.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_id", unique = true, nullable = false, length = 10)
    private String publicId;

    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @Column(unique = true, nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 300)
    private String password;

    @Column(name = "name", length = 150)
    private String name;

    @Column(name = "surname", length = 150)
    private String surname;

    @Column(name = "native_language", length = 10)
    private String nativeLang;

    @Column(name = "preferred_language", length = 10)
    private String preferredLang;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    private Role role = Role.USER;

    @Column(name = "account_status", length = 50)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "login_count", nullable = false)
    @ColumnDefault("0")
    private Integer loginCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "profile_picture_url",length = 500)
    private String ppUrl;


    //Helper method
    public void recordLogin(){
        this.loginCount++;
        this.lastLogin = LocalDateTime.now();
    }

    public String getDisplayName(){
        if(name != null && surname != null){
            return name + " " + surname;
        }
        return this.username;
    }
}
