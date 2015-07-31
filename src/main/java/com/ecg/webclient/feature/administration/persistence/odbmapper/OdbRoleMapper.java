package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IRole;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbRole;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine detachted Rolle oder umgekehrt.
 * 
 * @author arndtmar
 */
public class OdbRoleMapper
{
    /**
     * Wandelt eine persistente Rolle in eine detachte um
     * 
     * @param role
     *            persistente Rolle
     * @return Detachete Rolle
     */
    public static IRoleDto mapToDto(IRole attachedRole)
    {
        RoleDto detachedRole = new RoleDto();
        detachedRole.setDescription(attachedRole.getDescription());
        detachedRole.setName(attachedRole.getName());
        detachedRole.setEnabled(attachedRole.isEnabled());
        detachedRole.setDelete(false);
        detachedRole.setRid(attachedRole.getRid());

        return detachedRole;
    }

    /**
     * Wandelt eine Liste von persistenten Rollen in eine Liste von detachten Rollen um
     * 
     * @param roles
     *            Liste von Persistenten Rollen
     * @return Liste von detachten Rollen
     */
    public static List<IRoleDto> mapToDtos(List<IRole> attachedRoles)
    {
        List<IRoleDto> result = new AutoPopulatingList<IRoleDto>(IRoleDto.class);

        for (IRole attachedRole : attachedRoles)
        {
            RoleDto detachedRole = new RoleDto();
            detachedRole.setDescription(attachedRole.getDescription());
            detachedRole.setName(attachedRole.getName());
            detachedRole.setEnabled(attachedRole.isEnabled());
            detachedRole.setDelete(false);
            detachedRole.setRid(attachedRole.getRid());

            result.add(detachedRole);
        }

        return result;
    }

    /**
     * Wandelt eine Liste von detachten Rollen in eine Liste von persistenten Rollen um
     * 
     * @param dtos
     *            Liste von detachten Rollen
     * @return Liste von persistenten Rollen
     */
    public static List<IRole> mapToEntities(List<IRoleDto> detachedRoles)
    {
        List<IRole> result = new ArrayList<IRole>();

        for (IRoleDto detachedRole : detachedRoles)
        {
            OdbRole entity = new OdbRole();
            entity.setDescription(detachedRole.getDescription());
            entity.setName(detachedRole.getName());
            entity.setEnabled(detachedRole.isEnabled());
            entity.setRid(detachedRole.getRid());

            result.add(entity);
        }

        return result;
    }

    /**
     * Wandelt eine detachte Rolle in eine persistente um
     * 
     * @param dto
     *            Detachte Rolle
     * @return Persistente Rolle
     */
    public static IRole mapToEntity(IRoleDto detachedRole)
    {
        OdbRole entity = new OdbRole();
        entity.setDescription(detachedRole.getDescription());
        entity.setName(detachedRole.getName());
        entity.setEnabled(detachedRole.isEnabled());
        entity.setRid(detachedRole.getRid());

        return entity;
    }
}
