package com.ecg.webclient.feature.administration.persistence.api;

/**
 * Schnittstelle für persistente Entitäten vom Typ Eigenschaft.
 * 
 * @author arndtmar
 */
public interface IProperty extends IBaseObject
{
    /**
     * Bindet die Eigenschaften des Parameters an dieses Objekt
     * 
     * @param newRole
     *            zu persistierende Eigenschaft
     */
    void bind(IProperty newProperty);

    /**
     * @return Den Schlüssel der Eigenschaft
     */
    String getKey();

    /**
     * @return Den Wert der Eigenschaft
     */
    String getValue();

    /**
     * Setzt den Schlüssel der Eigenschaft
     * 
     * @param key
     *            Schlüssel
     */
    void setKey(String key);

    /**
     * Setzt den Wert der Eigenschaft
     * 
     * @param value
     *            Wert
     */
    void setValue(String value);
}
