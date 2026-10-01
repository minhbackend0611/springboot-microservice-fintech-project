package minh.project.infrastructure.auth.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import minh.project.domain.auth.vo.Role;
import minh.project.domain.auth.vo.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users", schema = "auth")
@Getter
@Setter
@DynamicInsert
public class UserJpaEntity implements UserDetails {

    @Id
    @Column(columnDefinition = "UUID", name = "user_id", nullable = false, unique = true)
    UUID userId;

    @Column(columnDefinition = "VARCHAR(256)", name = "email", nullable = false, unique = true)
    String email;

    @Column(columnDefinition = "VARCHAR(255)", name = "password_hash", nullable = false)
    String passwordHash;

    @Column(columnDefinition = "VARCHAR(255)", name = "pin_hash", nullable = true)
    String pinHash;

    @Column(columnDefinition = "UUID", name = "customer_id", nullable = true)
    UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", name = "role", nullable = false)
    Role role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", name = "status", nullable = false)
    Status status;

    @Column(columnDefinition = "BOOLEAN", name = "is_verified", nullable = false)
    Boolean isVerified;

    @Column(columnDefinition = "INT", name = "failed_attempt_count", nullable = false)
    Integer failedAttemptCount;

    @Column(columnDefinition = "INT", name = "failed_cycle_count", nullable = false)
    Integer failedCycleCount;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMPTZ", name = "created_at", nullable = false)
    Instant createdAt;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMPTZ", name = "updated_at", nullable = true)
    Instant updatedAt;

    @Column(columnDefinition = "TIMESTAMPTZ", name = "locked_at", nullable = true)
    Instant lockedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
