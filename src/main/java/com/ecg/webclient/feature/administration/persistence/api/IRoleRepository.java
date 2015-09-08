package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.viewmodell.RoleDto;

/**
 * Schnittstelle für Persistenzimplementierung von Benutzerrollen.
 * 
 * @author arndtmar
 *
 */
public interface IRoleRepository
{
    /**
     * Löscht alle in der Liste enthaltenen Benutzerrollen
     * 
     * @param roles
     *            Liste von Benutzerrollen
     */
    void deleteRoles(List<RoleDto> roles);

    /**
     * @param onlyEnabledRoles
     *            true, wenn nur aktivierte Benutzerrollen zurückgegeben werden sollen, sonst false
     * @return Alle aktivierten Benutzerrollen, wenn Parameter == true, sonst alle
     */
    List<RoleDto> getAllRoles(boolean onlyEnabledRoles);

    /**
     * @param roleIdObjects
     *            Liste von Gruppen IDs
     * @return Alle Rollen mit den im Parameter enhaltenen IDs
     */
    List<RoleDto> getRolesForIds(List<Long> roleIdObjects);

    /**
     * Speichert eine Rolle in der DB
     * 
     * @param setupRole
     *            zu Speichernde Rolle
     * @return Gespeicherte Rolle
     */
    RoleDto saveRole(RoleDto detachedRole);

    /**
     * Speichert alle in der Liste enthaltenen Benutzerrollen
     * 
     * @param detachedRoles
     *            Liste von Benutzerrollen
     */
    void saveRoles(List<RoleDto> detachedRoles);

}
