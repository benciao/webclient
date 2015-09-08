package com.ecg.webclient.feature.administration.persistence.odbmapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Role;
import com.ecg.webclient.feature.administration.viewmodell.RoleDto;

/**
 * Mapped die Eigenschaften einer in OrientDb bekannten Entität auf eine detachted Rolle oder umgekehrt.
 * 
 * @author arndtmar
 */
public class RoleMapper
{
    /**
     * Wandelt eine persistente Rolle in eine detachte um
     * 
     * @param role
     *            persistente Rolle
     * @return Detachete Rolle
     */
    public static RoleDto mapToDto(Role attachedRole)
    {
        RoleDto detachedRole = new RoleDto();
        detachedRole.setDescription(attachedRole.getDescription());
        detachedRole.setName(attachedRole.getName());
        detachedRole.setEnabled(attachedRole.isEnabled());
        detachedRole.setDelete(false);
        detachedRole.setId(attachedRole.getId());

        return detachedRole;
    }

    /**
     * Wandelt eine Liste von persistenten Rollen in eine Liste von detachten Rollen um
     * 
     * @param roles
     *            Liste von Persistenten Rollen
     * @return Liste von detachten Rollen
     */
    public static List<RoleDto> mapToDtos(List<Role> attachedRoles)
    {
        List<RoleDto> result = new AutoPopulatingList<RoleDto>(RoleDto.class);

        for (Role attachedRole : attachedRoles)
        {
            RoleDto detachedRole = new RoleDto();
            detachedRole.setDescription(attachedRole.getDescription());
            detachedRole.setName(attachedRole.getName());
            detachedRole.setEnabled(attachedRole.isEnabled());
            detachedRole.setDelete(false);
            detachedRole.setId(attachedRole.getId());

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
    public static List<Role> mapToEntities(List<RoleDto> detachedRoles)
    {
        List<Role> result = new ArrayList<Role>();

        for (RoleDto detachedRole : detachedRoles)
        {
            Role entity = new Role();
            entity.setDescription(detachedRole.getDescription());
            entity.setName(detachedRole.getName());
            entity.setEnabled(detachedRole.isEnabled());

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
    public static Role mapToEntity(RoleDto detachedRole)
    {
        Role entity = new Role();
        entity.setDescription(detachedRole.getDescription());
        entity.setName(detachedRole.getName());
        entity.setEnabled(detachedRole.isEnabled());

        return entity;
    }
}
