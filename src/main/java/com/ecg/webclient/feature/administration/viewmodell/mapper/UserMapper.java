package com.ecg.webclient.feature.administration.viewmodell.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

public class UserMapper
{
    public static UserDto mapToDto(User user)
    {
        UserDto dto = new UserDto();
        dto.setLogin(user.getLogin());
        dto.setType(user.isType());
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        dto.setEnabled(user.isEnabled());
        dto.setDelete(false);
        dto.setRid(user.getRid());

        return dto;
    }

    public static List<UserDto> mapToDtos(List<User> users)
    {
        List<UserDto> result = new AutoPopulatingList<UserDto>(UserDto.class);

        for (User user : users)
        {
            UserDto dto = new UserDto();
            dto.setLogin(user.getLogin());
            dto.setType(user.isType());
            dto.setLastname(user.getLastname());
            dto.setFirstname(user.getFirstname());
            dto.setEnabled(user.isEnabled());
            dto.setDelete(false);
            dto.setRid(user.getRid());

            result.add(dto);
        }

        return result;
    }

    public static List<User> mapToEntities(List<UserDto> dtos)
    {
        List<User> result = new ArrayList<User>();

        for (UserDto dto : dtos)
        {
            User entity = new User();
            entity.setLogin(dto.getLogin());
            entity.setType(dto.getType());
            entity.setLastname(dto.getLastname());
            entity.setFirstname(dto.getFirstname());
            entity.setEnabled(dto.isEnabled());
            entity.setRid(dto.getRid());

            result.add(entity);
        }

        return result;
    }

    public static User mapToEntity(UserDto dto)
    {
        User entity = new User();
        entity.setLogin(dto.getLogin());
        entity.setType(dto.getType());
        entity.setLastname(dto.getLastname());
        entity.setFirstname(dto.getFirstname());
        entity.setEnabled(dto.isEnabled());
        entity.setRid(dto.getRid());

        return entity;
    }
}
