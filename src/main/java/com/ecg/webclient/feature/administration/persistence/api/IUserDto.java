package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

/**
 * Schnittstelle für Implementierungen von von der Persistenz detachten Benutzern.
 * 
 * @author arndtmar
 */
public interface IUserDto extends IBaseObjectDto
{
    /**
     * @return Den Standardmandanten
     */
    String getDefaultClient();

    /**
     * @return Die Email des Benutzers
     */
    String getEmail();

    /**
     * @return Den Vornamen des Benutzers
     */
    String getFirstname();

    /**
     * @return Liste mit ID-Objekten
     */
    List<Object> getGroupRidObjects();

    /**
     * @return Eine Liste von Gruppen IDs, welche dem Nutzer zugeordnet sind
     */
    String getGroupRids();

    /**
     * @return Den Nachnamen des Benutzers
     */
    String getLastname();

    /**
     * @return Den Login-Namen des Benutzers
     */
    String getLogin();

    /**
     * @return Das verschlüsselte Passwort des Benutzers
     */
    String getPassword();

    /**
     * @return true, wenn der Benutzer sein Passwort beim nächsten Login ändern muss, sonst false
     */
    boolean isChangePasswordOnNextLogin();

    /**
     * @return true, wenn der Benutzer aktiviert ist, sonst false
     */
    boolean isEnabled();

    /**
     * @return true, wenn die Authentifizierung gegen ATLAS erfolgt, false gegen LDAP
     */
    boolean isType();

    /**
     * @param changePasswordOnNextLogin
     *            true, wenn der Benutzer sein Passwort beim nächsten Login ändern soll, sonst false
     */
    void setChangePasswordOnNextLogin(boolean changePasswordOnNextLogin);

    /**
     * Setzt den Standardmandant
     * 
     * @param defaultClient
     *            Standardmandant
     */
    void setDefaultClient(String defaultClient);

    /**
     * Setzt die Email des Benutzers
     * 
     * @param email
     *            Email
     */
    void setEmail(String email);

    /**
     * Aktiviert oder deaktiviert den Benutzer
     * 
     * @param enabled
     *            true, wenn der Benutzer aktiviert werden soll, sonst false
     */
    void setEnabled(boolean enabled);

    /**
     * Setzt den Vornamen des Benutzers
     * 
     * @param firstname
     *            Vorname
     */
    void setFirstname(String firstname);

    /**
     * Setzt eine Liste von Gruppen-IDs
     * 
     * @param groupRids
     *            Liste von Gruppen-IDs, welche dem Nutzer zugeordnet werden sollen
     */
    void setGroupRids(String groupRids);

    /**
     * Setzt den Nachnamen des Benutzers
     * 
     * @param lastname
     *            Nachname
     */
    void setLastname(String lastname);

    /**
     * Setzt das Login des Benutzers
     * 
     * @param login
     *            Benutzerlogin
     */
    void setLogin(String login);

    /**
     * Setzt das Passwort des Benutzers
     * 
     * @param password
     *            Passwort
     */
    void setPassword(String password);

    /**
     * Setzt den Authentifizierungstyp
     * 
     * @param type
     *            true, wenn Benutzer gegen ATLAS authentifiziert werden soll, sonst false gegen LDAP
     */
    void setType(boolean type);
}
