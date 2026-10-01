package minh.project.infrastructure.noti.mapper;

import minh.project.domain.noti.model.EmailHistory;
import minh.project.infrastructure.noti.model.EmailHistoryJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailPersistenceMapper {

    EmailHistoryJpaEntity toInfrastructure(EmailHistory emailHistoryDomain);

    EmailHistory toDomain(EmailHistoryJpaEntity smsHistoryJpaEntity);

}
