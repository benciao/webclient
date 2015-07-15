package com.ecg.webclient.feature.administration.viewmodell.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;

public class RoleMapper
{
    public static RoleDto mapToDto(Role role)
    {
        RoleDto dto = new RoleDto();
        dto.setDescription(role.getDescription());
        dto.setName(role.getName());
        dto.setEnabled(role.isEnabled());
        dto.setDelete(false);
        dto.setRid(role.getRid());

        return dto;
    }

    public static List<RoleDto> mapToDtos(List<Role> roles)
    {
        List<RoleDto> result = new AutoPopulatingList<RoleDto>(RoleDto.class);

        for (Role role : roles)
        {
            RoleDto dto = new RoleDto();
            dto.setDescription(role.getDescription());
            dto.setName(role.getName());
            dto.setEnabled(role.isEnabled());
            dto.setDelete(false);
            dto.setRid(role.getRid());

            result.add(dto);
        }

        return result;
    }

    public static List<Role> mapToEntities(List<RoleDto> dtos)
    {
        List<Role> result = new ArrayList<Role>();

        for (RoleDto dto : dtos)
        {
            Role entity = new Role();
            entity.setDescription(dto.getDescription());
            entity.setName(dto.getName());
            entity.setEnabled(dto.isEnabled());
            entity.setRid(dto.getRid());

            result.add(entity);
        }

        return result;
    }

    public static Role mapToEntity(RoleDto dto)
    {
        Role entity = new Role();
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setEnabled(dto.isEnabled());
        entity.setRid(dto.getRid());

        return entity;
    }
}
