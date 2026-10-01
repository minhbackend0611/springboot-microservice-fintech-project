package minh.project.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.repository.UserRepository;
import minh.project.infrastructure.auth.jpa.UserJPARepository;
import minh.project.infrastructure.auth.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceMapper userPersistenceMapper;

    private final UserJPARepository userJPARepository;

    @Override
    public boolean existsByEmail(String email) {
        return userJPARepository
                .existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJPARepository
                .findByEmail(email)
                .map(userPersistenceMapper::toDomain);
    }

    @Override
    public User save(User user) {
        return userPersistenceMapper.toDomain(
                userJPARepository.save(
                        userPersistenceMapper.toInfrastructure(user)
                )
        );
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return userJPARepository
                .findById(userId)
                .map(userPersistenceMapper::toDomain);
    }
}
