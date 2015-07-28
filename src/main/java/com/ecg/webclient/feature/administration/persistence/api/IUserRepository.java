package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.persistence.dbmodell.User;

/**
 * Schnittstelle für Persistenzimplementierung von Benutzern.
 * 
 * @author arndtmar
 *
 */
public interface IUserRepository
{
    /**
     * Löscht alle in der Liste enthaltenen Benutzer
     * 
     * @param users
     *            Liste von Benutzern
     */
    void deleteUsers(List<User> users);

    /**
     * @return alle Benutzer
     */
    List<User> getAllUsers();

    /**
     * @param id
     *            technische Id des Benutzers
     * @return Den zur Id gehörigen Benutzer.
     */
    User getUserById(Object id);

    /**
     * Lädt einen Benutzer anhand seines Benutzernamen
     * 
     * @param string
     *            Benutzername
     * @return Benutzer wenn existent, sonst null
     */
    User getUserByLogin(String string);

    /**
     * Prüft, ob es einen Benutzer mit übereinstimmenden Logindaten gibt
     * @param user Benutzerlogin
     * @param password Benutzerpasswort
     * @return true, wenn existent, sonst false
     */
    boolean isUserAuthorized(String user, String password);

    /**
     * Speichert einen Benutzer
     * 
     * @param setupUser
     *            Zu speichernder Benutzer
     */
    void saveUser(User setupUser);

    /**
     * Speichert alle in der Liste enthaltenen Benutzer
     * 
     * @param users
     *            Liste von Benutzern
     */
    void saveUsers(List<User> users);
}
