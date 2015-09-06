package com.ecg.webclient.feature.administration.persistence.api;


/**
 * Schnittstelle für persistente Entitäten vom Typ Benutzerrolle.
 * 
 * @author arndtmar
 */
public interface IRole extends IBaseObject
{
    /**
     * Bindet die Eigenschaften des Parameters an dieses Objekt
     * 
     * @param newRole
     *            zu persistierende Benutzerrolle
     */
    void bind(IRole newRole);

    /**
     * @return Beschreibung der Benutzerrolle
     */
    String getDescription();

    /**
     * @return Name der Benutzerrolle
     */
    String getName();

    /**
     * @return true, wenn aktiv, sonst false
     */
    boolean isEnabled();

    /**
     * Setzt die Beschreibung einer Benutzerrolle
     * 
     * @param description
     *            Beschreibung
     */
    void setDescription(String description);

    /**
     * Aktiviert oder deaktiviert eine Benutzerrolle
     * 
     * @param enabled
     *            true, wenn Rolle aktiv sein soll, sonst false
     */
    void setEnabled(boolean enabled);

    /**
     * Setzt den Namen der Benutzerrolle
     * 
     * @param name
     *            Name der Benutzerrolle
     */
    void setName(String name);
}
