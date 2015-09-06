package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

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
    void deleteUsers(List<IUserDto> users);

    /**
     * @return alle Benutzer aktiven benutzer, wenn true, sonst alle
     */
    List<IUserDto> getAllUsers(boolean onlyEnabledUsers);

    /**
     * @param user
     *            Benutzer
     * @return Standardmandant
     */
    IClientDto getDefaultClientForUser(IUserDto user);

    /**
     * @param id
     *            technische Id des Benutzers
     * @return Den zur Id gehörigen Benutzer.
     */
    IUserDto getUserById(Object id);

    /**
     * Lädt einen Benutzer anhand seines Benutzernamen
     * 
     * @param string
     *            Benutzername
     * @return Benutzer wenn existent, sonst null
     */
    IUserDto getUserByLogin(String string);

    /**
     * Prüft, ob es einen Benutzer mit übereinstimmenden Logindaten gibt
     * 
     * @param user
     *            Benutzerlogin
     * @param password
     *            Benutzerpasswort
     * @return true, wenn existent, sonst false
     */
    boolean isUserAuthorized(String user, String password);

    /**
     * Speichert einen Benutzer
     * 
     * @param setupUser
     *            Zu speichernder Benutzer
     */
    void saveUser(IUserDto setupUser);

    /**
     * Speichert alle in der Liste enthaltenen Benutzer
     * 
     * @param users
     *            Liste von Benutzern
     */
    void saveUsers(List<IUserDto> users);
}
