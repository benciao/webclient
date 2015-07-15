package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;

/**
 * Schnittstelle für Persistenzimplementierung von Benutzergruppen.
 * 
 * @author arndtmar
 *
 */
public interface IGroupRepository
{
    /**
     * Löscht alle Benutzergruppen der Liste
     * 
     * @param groups
     *            Liste von Benutzergruppen
     */
    void deleteGroups(List<Group> groups);

    /**
     * @return alle Benutzergruppen
     */
    List<Group> getAllGroups();

    /**
     * @param clientId
     *            technische Id des Mandanten
     * @return alle Benutzergruppen des zur Id gehörenden Mandanten
     */
    List<Group> getAllGroupsForClient(Object clientId);

    /**
     * Speichert alle Benutzergruppen der Liste
     * 
     * @param groups
     *            Benutzergruppen
     */
    void saveGroups(List<Group> groups);
}
