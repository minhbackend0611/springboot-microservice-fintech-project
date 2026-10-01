package minh.project.infrastructure.auth.jpa;

import minh.project.infrastructure.auth.model.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserJPARepository extends JpaRepository<UserJpaEntity, UUID> {

    boolean existsByEmail(String email);

    Optional<UserJpaEntity> findByEmail(String email);

    UserJpaEntity save(UserJpaEntity userJpaEntity);

    Optional<UserJpaEntity> findById(UUID userId);
}
