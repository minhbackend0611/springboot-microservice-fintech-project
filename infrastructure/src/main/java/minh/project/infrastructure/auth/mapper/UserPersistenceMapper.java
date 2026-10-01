package minh.project.infrastructure.auth.mapper;

import minh.project.domain.auth.model.User;
import minh.project.domain.auth.vo.Email;
import minh.project.infrastructure.auth.model.UserJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    User toDomain(UserJpaEntity userJpaEntity);

    UserJpaEntity toInfrastructure(User user);

    default Email map(String value) {
        return value.isEmpty() ? null : new Email(value);
    }

    default String map(Email email) {
        return email.value();
    }

}
