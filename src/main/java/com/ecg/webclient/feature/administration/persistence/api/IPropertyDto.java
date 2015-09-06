package com.ecg.webclient.feature.administration.persistence.api;

/**
 * Schnittstelle für Implementierungen von von der Persistenz detachten Eigenschaften.
 * 
 * @author arndtmar
 */
public interface IPropertyDto extends IBaseObjectDto
{
    /**
     * @return Den Schlüssel der Eigenschaft
     */
    String getKey();

    /**
     * @return Den Wert der Eigenschaft
     */
    String getValue();

    /**
     * Setyt den Schlüssel der Eigenschaft
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
