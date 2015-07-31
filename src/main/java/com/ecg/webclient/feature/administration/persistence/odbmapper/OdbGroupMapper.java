package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IGroup;
import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IRole;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbGroup;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine detachted Gruppe oder umgekehrt.
 * 
 * @author arndtmar
 */
public class OdbGroupMapper
{
    /**
     * Wandelt eine persistente Gruppe in eine detachte um
     * 
     * @param group
     *            persistente Gruppe
     * @return Detachete Gruppe
     */
    public static IGroupDto mapToDto(IGroup group)
    {
        IGroupDto dto = new GroupDto();
        dto.setDescription(group.getDescription());
        dto.setName(group.getName());
        dto.setEnabled(group.isEnabled());
        dto.setDelete(false);
        dto.setRid(group.getRid());

        if (group.getRoles() != null)
        {
            String roles = "";
            for (IRole role : group.getRoles())
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

    /**
     * Wandelt eine Liste von persistenten Gruppen in eine Liste von detachten Gruppen um
     * 
     * @param groups
     *            Liste von Persistenten Gruppen
     * @return Liste von detachten Gruppen
     */
    public static List<IGroupDto> mapToDtos(List<IGroup> groups)
    {
        List<IGroupDto> result = new AutoPopulatingList<IGroupDto>(IGroupDto.class);

        for (IGroup group : groups)
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
                for (IRole role : group.getRoles())
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

    /**
     * Wandelt eine Liste von detachten Gruppen in eine Liste von persistenten Gruppen um
     * 
     * @param dtos
     *            Liste von detachten Gruppen
     * @return Liste von persistenten Gruppen
     */
    public static List<IGroup> mapToEntities(List<IGroupDto> dtos)
    {
        List<IGroup> result = new ArrayList<IGroup>();

        for (IGroupDto dto : dtos)
        {
            IGroup entity = new OdbGroup();
            entity.setDescription(dto.getDescription());
            entity.setName(dto.getName());
            entity.setEnabled(dto.isEnabled());
            entity.setRid(dto.getRid());
            entity.setRoleRids(getRoleRids(dto.getRoleRids()));

            result.add(entity);
        }

        return result;
    }

    /**
     * Wandelt eine detachte Gruppen in eine persistente um
     * 
     * @param dto
     *            Detachte Gruppen
     * @return Persistente Gruppen
     */
    public static IGroup mapToEntity(IGroupDto dto)
    {
        IGroup entity = new OdbGroup();
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
