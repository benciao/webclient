package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für Implementierungen von von der Persistenz detachte Mandanten.
 * 
 * @author arndtmar
 */
public interface IClientDto extends IBaseObjectDto
{
    /**
     * @return Die Farbe des Mandanten
     */
    String getColor();

    /**
     * @return Die Beschreibung des Mandanten
     */
    String getDescription();

    /**
     * @return den Namen des Mandanten
     */
    String getName();

    /**
     * @return Die Mandanteneigenschaften
     */
    List<IPropertyDto> getProperties();

    /**
     * @return true, wenn Benutzerrolle aktiviert ist, sonst false
     */
    boolean isEnabled();

    /**
     * @return true, wenn Mandant ausgewählt ist, sonst false
     */
    boolean isSelected();

    /**
     * Setzt die Farbe des Mandanten
     * 
     * @param color
     *            Farbe in Form von #123456
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
     *            true, wenn Mandant aktiviert werden soll, sonst false
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
    void setProperties(List<IPropertyDto> properties);

    /**
     * Wählt den Mandanten aus oder nicht
     * 
     * @param selected
     *            true, wenn Mandant ausgewählt werden soll, sonst false
     */
    void setSelected(boolean selected);
}
