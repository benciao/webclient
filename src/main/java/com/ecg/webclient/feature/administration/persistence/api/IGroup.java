package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für persistente Entitäten vom Typ Benutzergruppe.
 * 
 * @author arndtmar
 */
public interface IGroup extends IBaseObject
{
    /**
     * Bindet die Eigenschaften des Parameters an dieses Objekt
     * 
     * @param newRole
     *            zu persistierende Benutzergruppe
     */
    void bind(IGroup newGroup);

    /**
     * @return Den der Gruppe zugeordneten Mandanten
     */
    IClient getClient();

    /**
     * @return Die Beschreibung der Gruppe
     */
    String getDescription();

    /**
     * @return Den namen der Gruppe
     */
    String getName();

    /**
     * @return Die der Gruppe zugeordneten Rollen IDs
     */
    List<Object> getRoleRids();

    /**
     * @return Die der Gruppe zugeordneten Rollen
     */
    List<IRole> getRoles();

    /**
     * @return true, wenn die Gruppe aktiviert ist, sonst false
     */
    boolean isEnabled();

    /**
     * Setzt den Mandanten, zu welchem die Gruppe gehört
     * 
     * @param client
     *            Mandanten
     */
    void setClient(IClient client);

    /**
     * Setzt die Beschreibung der Gruppe
     * 
     * @param description
     *            Beschreibung der Gruppe
     */
    void setDescription(String description);

    /**
     * Aktiviert oder Deaktiviert die Gruppe
     * 
     * @param enabled
     *            true, wenn Gruppe aktiviert werden soll, sonst false
     */
    void setEnabled(boolean enabled);

    /**
     * Setzt den Namen der Gruppe
     * 
     * @param name
     *            Name der Gruppe
     */
    void setName(String name);

    /**
     * Setzt die IDs der der Gruppe zugeordneten Rollen
     * 
     * @param roleRids
     *            IDs der Rollen
     */
    void setRoleRids(List<Object> roleRids);

    /**
     * Setzt der Gruppe zugeoprdnete Rollen
     * 
     * @param roles
     *            Der Gruppe zugeordnete Rollen
     */
    void setRoles(List<IRole> roles);
}
