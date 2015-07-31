package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für persistente Entitäten vom Typ Mandant.
 * 
 * @author arndtmar
 */
public interface IClient extends IBaseObject
{
    /**
     * Bindet die Eigenschaften des Parameters an dieses Objekt
     * 
     * @param newRole
     *            zu persistierender Mandant
     */
    void bind(IClient newClient);

    /**
     * @return Die Farbe des Mandanten
     */
    String getColor();

    /**
     * @return Die Beschreibung des Mandanten
     */
    String getDescription();

    /**
     * @return Den Namen des Mandanten
     */
    String getName();

    /**
     * @return Die zugehörigen Mandanteneigenschaften
     */
    List<IProperty> getProperties();

    /**
     * @return true, wenn der Mandant aktiviert ist, sonst false
     */
    boolean isEnabled();

    /**
     * Setzt die Mandantenfarbe
     * 
     * @param color
     *            Farbe des Mandanten in der Form #123456
     */
    void setColor(String color);

    /**
     * Setzt die Beschreibung des Mandanten
     * 
     * @param description
     *            Beschreibung
     */
    void setDescription(String description);

    /**
     * Aktiviert oder deaktiviert den Mandanten
     * 
     * @param enabled
     *            true, wenn der Mandant aktiviert werden soll, sonst false
     */
    void setEnabled(boolean enabled);

    /**
     * Setzt den Namen des Mandanten
     * 
     * @param name
     *            Name
     */
    void setName(String name);

    /**
     * Setzt die zum Mandanten gehörenden Eigenschaften
     * 
     * @param properties
     *            Mandanteneigenschaften
     */
    void setProperties(List<IProperty> properties);
}
