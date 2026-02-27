package cl.previred.taskmanager.infrastructure.persistence.mapper;

import cl.previred.taskmanager.domain.model.User;
import cl.previred.taskmanager.infrastructure.persistence.entity.UserEntity;

import java.util.List;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword()
        );
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = new UserEntity(
                domain.id(),
                domain.username(),
                domain.password()
        );

        return entity;
    }

    public static List<User> toDomainList(List<UserEntity> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    public static List<UserEntity> toEntityList(List<User> domains) {
        if (domains == null) {
            return List.of();
        }

        return domains.stream()
                .map(UserMapper::toEntity)
                .toList();
    }

}