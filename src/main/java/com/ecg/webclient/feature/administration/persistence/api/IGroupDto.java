package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für Implementierungen von von der Persistenz detachten Benutzergruppen.
 * 
 * @author arndtmar
 */
public interface IGroupDto extends IBaseObjectDto
{
    /**
     * @return Die Beschreibung der Gruppe
     */
    String getDescription();

    /**
     * @return Den Namen der Gruppe
     */
    String getName();

    /**
     * @return Liste mit ID-Objekten
     */
    List<Object> getRoleRidObjects();

    /**
     * @return Die IDs der Gruppe zugeordneten Rollen
     */
    String getRoleRids();

    /**
     * @return true, wenn Gruppe aktiv ist, sonst false
     */
    boolean isEnabled();

    /**
     * Setzt die Beschreibung der Gruppe
     * 
     * @param description
     *            Beschreibung der Gruppe
     */
    void setDescription(String description);

    /**
     * Aktiviert oder deaktiviert die Gruppe
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
     * Setzt die IDs der Gruppe zugeordnete Rollen
     * 
     * @param roleRids
     *            IDs der Gruppe zugeordneten Rollen
     */
    void setRoleRids(String roleRids);
}
