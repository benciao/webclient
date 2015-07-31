package com.ecg.webclient.feature.administration.persistence.api;

/**
 * Schnittstelle für Implementierungen von von der Persistenz detachten Benutzerrollen.
 * 
 * @author arndtmar
 */
public interface IRoleDto extends IBaseObjectDto
{
    /**
     * @return Beschreibung der Benutzerrolle
     */
    String getDescription();

    /**
     * @return Name der Benutzerrolle
     */
    String getName();

    /**
     * @return true, wenn Benutzerrolle aktiviert ist, sonst false
     */
    boolean isEnabled();

    /**
     * Setzt die Beschreibung der Benutzerrolle
     * 
     * @param description
     *            Beschreibung
     */
    void setDescription(String description);

    /**
     * Aktiviert oder deaktiviert die Benutzerrolle
     * 
     * @param enabled
     *            true, wenn Benutzerrolle aktiviert werden soll, sonst false
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
