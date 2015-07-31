package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IGroup;
import com.ecg.webclient.feature.administration.persistence.api.IUser;
import com.ecg.webclient.feature.administration.persistence.api.IUserDto;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbUser;
import com.ecg.webclient.feature.administration.viewmodell.UserDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf einen detachten Benutzer oder umgekehrt.
 * 
 * @author arndtmar
 */
public class OdbUserMapper
{
    /**
     * Wandelt einen persistenten Benutzer in einen detachten um
     * 
     * @param user
     *            persistenter Benutzer
     * @return Detacheter Benutzer
     */
    public static IUserDto mapToDto(IUser user)
    {
        IUserDto dto = new UserDto();
        dto.setLogin(user.getLogin());
        dto.setType(user.isType());
        dto.setLastname(user.getLastname());
        dto.setFirstname(user.getFirstname());
        dto.setEnabled(user.isEnabled());
        dto.setDelete(false);
        dto.setRid(user.getRid());
        dto.setEmail(user.getEmail());
        dto.setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());

        if (user.getDefaultClient() != null)
        {
            dto.setDefaultClient(user.getDefaultClient().getRid().toString());
        }

        if (user.getGroups() != null)
        {
            String groups = "";
            for (IGroup group : user.getGroups())
            {
                if (groups.length() == 0)
                {
                    groups = group.getRid().toString();
                }
                else
                {
                    groups = groups + "," + group.getRid().toString();
                }
            }
            dto.setGroupRids(groups);
        }

        return dto;
    }

    /**
     * Wandelt eine Liste von persistenten Benutzern in eine Liste von detachten Benutzern um
     * 
     * @param users
     *            Liste von persistenten Benutzern
     * @return Liste von detachten Benutzern
     */
    public static List<IUserDto> mapToDtos(List<IUser> users)
    {
        List<IUserDto> result = new AutoPopulatingList<IUserDto>(IUserDto.class);

        for (IUser user : users)
        {
            IUserDto dto = new UserDto();
            dto.setLogin(user.getLogin());
            dto.setType(user.isType());
            dto.setLastname(user.getLastname());
            dto.setFirstname(user.getFirstname());
            dto.setEnabled(user.isEnabled());
            dto.setDelete(false);
            dto.setRid(user.getRid());
            dto.setEmail(user.getEmail());
            dto.setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());

            if (user.getDefaultClient() != null)
            {
                dto.setDefaultClient(user.getDefaultClient().getRid().toString());
            }

            if (user.getGroups() != null)
            {
                String groups = "";
                for (IGroup group : user.getGroups())
                {
                    if (groups.length() == 0)
                    {
                        groups = group.getRid().toString();
                    }
                    else
                    {
                        groups = groups + "," + group.getRid().toString();
                    }
                }
                dto.setGroupRids(groups);
            }

            result.add(dto);
        }

        return result;
    }

    /**
     * Wandelt eine Liste von detachten Benutzern in eine Liste von persistenten Benutzern um
     * 
     * @param dtos
     *            Liste von detachten Benutzern
     * @return Liste von persistenten Benutzern
     */
    public static List<IUser> mapToEntities(List<IUserDto> dtos)
    {
        List<IUser> result = new ArrayList<IUser>();

        for (IUserDto dto : dtos)
        {
            IUser entity = new OdbUser();
            entity.setLogin(dto.getLogin());
            entity.setType(dto.isType());
            entity.setLastname(dto.getLastname());
            entity.setFirstname(dto.getFirstname());
            entity.setEnabled(dto.isEnabled());
            entity.setRid(dto.getRid());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setChangePasswordOnNextLogin(dto.isChangePasswordOnNextLogin());
            entity.setDefaultClientRid(dto.getDefaultClient());
            entity.setGroupRids(getGroupRids(dto.getGroupRids()));

            result.add(entity);
        }

        return result;
    }

    /**
     * Wandelt einen detachten Benutzer in einen persistenten um
     * 
     * @param dto
     *            Detachter Benutzer
     * @return Persistenter Benutzer
     */
    public static IUser mapToEntity(IUserDto dto)
    {
        IUser entity = new OdbUser();
        entity.setLogin(dto.getLogin());
        entity.setType(dto.isType());
        entity.setLastname(dto.getLastname());
        entity.setFirstname(dto.getFirstname());
        entity.setEnabled(dto.isEnabled());
        entity.setRid(dto.getRid());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setChangePasswordOnNextLogin(dto.isChangePasswordOnNextLogin());
        entity.setDefaultClientRid(dto.getDefaultClient());
        entity.setGroupRids(getGroupRids(dto.getGroupRids()));

        return entity;
    }

    private static List<Object> getGroupRids(String groupRids)
    {
        List<Object> result = new ArrayList<Object>();

        List<String> rids = Arrays.asList(groupRids.split(","));

        for (String rid : rids)
        {
            result.add(rid);
        }

        return result.size() != 0 ? result : null;
    }
}
