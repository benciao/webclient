package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für Persistenzimplementierung von Benutzerrollen.
 * 
 * @author arndtmar
 */
public interface IRoleRepository
{
    /**
     * Löscht alle in der Liste enthaltenen Benutzerrollen
     * 
     * @param roles
     *            Liste von Benutzerrollen
     */
    void deleteRoles(List<IRoleDto> detachedRoles);

    /**
     * @param onlyEnabledRoles
     *            true, wenn nur aktivierte Benutzerrollen zurückgegeben werden sollen, sonst false
     * @return Alle aktivierten Benutzerrollen, wenn Parameter == true, sonst alle
     */
    List<IRoleDto> getAllRoles(boolean onlyEnabledRoles);

    /**
     * @param roleRidObjects
     *            Liste von Rollen IDs
     * @return Alle Rollen mit den im Parameter enhaltenen IDs
     */
    List<IRoleDto> getRolesForIds(List<Object> roleRidObjects);

    /**
     * Speichert eine Benutzerrolle in der DB
     * 
     * @param role
     *            zu speichernde Rolle
     * @return Gespeicherte Rolle
     */
    IRoleDto saveRole(IRoleDto detachedRole);

    /**
     * Speichert alle in der Liste enthaltenen Benutzerrollen
     * 
     * @param roles
     *            Liste von Benutzerrollen
     */
    void saveRoles(List<IRoleDto> detachedRoles);

}
