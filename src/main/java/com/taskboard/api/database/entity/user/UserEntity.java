package com.taskboard.api.database.entity.user;

import com.taskboard.api.database.BaseEntity;
import com.taskboard.api.database.entity.project.ProjectEntity;
import com.taskboard.api.database.entity.task.TaskEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserEntity extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Builder.Default
    private boolean isVerified = false;

    @Builder.Default
    private String token = UUID.randomUUID().toString();

    @Builder.Default
    private LocalDateTime tokenExpiration = LocalDateTime.now().plusMinutes(30);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @OneToMany(mappedBy = "projectOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectEntity> projects = new ArrayList<>();
}
