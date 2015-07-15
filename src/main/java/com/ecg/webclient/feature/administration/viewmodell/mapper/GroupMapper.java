package com.ecg.webclient.feature.administration.viewmodell.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;

public class GroupMapper
{
    public static GroupDto mapToDto(Group group)
    {
        GroupDto dto = new GroupDto();
        dto.setDescription(group.getDescription());
        dto.setName(group.getName());
        dto.setEnabled(group.isEnabled());
        dto.setDelete(false);
        dto.setRid(group.getRid());

        if (group.getRoles() != null)
        {
            String roles = "";
            for (Role role : group.getRoles())
            {
                if (roles.length() == 0)
                {
                    roles = role.getRid().toString();
                }
                else
                {
                    roles = roles + "," + role.getRid().toString();
                }
            }
            dto.setRoleRids(roles);
        }

        return dto;
    }

    public static List<GroupDto> mapToDtos(List<Group> groups)
    {
        List<GroupDto> result = new AutoPopulatingList<GroupDto>(GroupDto.class);

        for (Group group : groups)
        {
            GroupDto dto = new GroupDto();
            dto.setDescription(group.getDescription());
            dto.setName(group.getName());
            dto.setEnabled(group.isEnabled());
            dto.setDelete(false);
            dto.setRid(group.getRid());

            if (group.getRoles() != null)
            {
                String roles = "";
                for (Role role : group.getRoles())
                {
                    if (roles.length() == 0)
                    {
                        roles = role.getRid().toString();
                    }
                    else
                    {
                        roles = roles + "," + role.getRid().toString();
                    }
                }
                dto.setRoleRids(roles);
            }

            result.add(dto);
        }

        return result;
    }

    public static List<Group> mapToEntities(List<GroupDto> dtos)
    {
        List<Group> result = new ArrayList<Group>();

        for (GroupDto dto : dtos)
        {
            Group entity = new Group();
            entity.setDescription(dto.getDescription());
            entity.setName(dto.getName());
            entity.setEnabled(dto.isEnabled());
            entity.setRid(dto.getRid());
            entity.setRoleRids(getRoleRids(dto.getRoleRids()));

            result.add(entity);
        }

        return result;
    }

    public static Group mapToEntity(GroupDto dto)
    {
        Group entity = new Group();
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setEnabled(dto.isEnabled());
        entity.setRid(dto.getRid());
        entity.setRoleRids(getRoleRids(dto.getRoleRids()));

        return entity;
    }

    private static List<Object> getRoleRids(String roleRids)
    {
        List<Object> result = new ArrayList<Object>();

        List<String> rids = Arrays.asList(roleRids.split(","));

        for (String rid : rids)
        {
            result.add(rid);
        }

        return result.size() != 0 ? result : null;
    }
}
