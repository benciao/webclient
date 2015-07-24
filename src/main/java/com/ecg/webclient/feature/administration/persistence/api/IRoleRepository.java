package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;

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
    void deleteRoles(List<Role> roles);

    /**
     * @return alle Benutzerrollen
     */
    List<Role> getAllRoles();

    /**
     * Speichert eine Rolle in der DB
     * 
     * @param setupRole
     *            zu Speichernde Rolle
     * @return Gespeicherte Rolle
     */
    Role saveRole(Role setupRole);

    /**
     * Speichert alle in der Liste enthaltenen Benutzerrollen
     * 
     * @param roles
     *            Liste von Benutzerrollen
     */
    void saveRoles(List<Role> roles);

}
