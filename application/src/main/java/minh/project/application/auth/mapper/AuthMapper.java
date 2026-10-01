package minh.project.application.auth.mapper;

import minh.project.application.auth.dto.UserInfoDTO;
import minh.project.domain.auth.model.User;
import minh.project.domain.auth.vo.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    public UserInfoDTO toDTO(User user);

    default String map(Email email) {
        return email.value();
    }

}
