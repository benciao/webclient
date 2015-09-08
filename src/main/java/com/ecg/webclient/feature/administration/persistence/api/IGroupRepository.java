package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.GroupDto;

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
    void deleteGroups(List<GroupDto> groups);

    /**
     * @param onlyEnabledGroups
     *            true, wenn nur aktivierte Benutzergruppen zurückgegeben werden sollen, sonst false
     * @return Alle aktivierten Benutzergruppen, wenn Parameter == true, sonst alle
     */
    List<GroupDto> getAllGroups(boolean onlyEnabledGroups);

    /**
     * @param clientId
     *            technische Id des Mandanten
     * @return alle Benutzergruppen des zur Id gehörenden Mandanten
     */
    List<GroupDto> getAllGroupsForClient(Object clientId);

    /**
     * @param rid
     *            Gruppen ID
     * @return Mandant
     */
    ClientDto getClientForGroupId(Object rid);

    /**
     * Lädt eine Gruppe anhand ihres Namens
     * 
     * @param string
     *            Gruppenname
     * @return Gruppe wenn existent, sonst null
     */
    GroupDto getGroupByName(String string);

    /**
     * @param groupRidObjects Liste von Gruppen IDs
     * @return Alle Gruppen mit den im Parameter enhaltenen IDs
     */
    List<GroupDto> getGroupsForIds(List<Object> groupRidObjects);

    /**
     * Speichert eine Benutzergruppe
     * 
     * @param group
     *            zu speichernde Gruppe
     * @return Gespeicherte Gruppe
     */
    GroupDto saveGroup(GroupDto group);

    /**
     * Speichert alle Benutzergruppen der Liste
     * 
     * @param groups
     *            Benutzergruppen
     */
    void saveGroups(List<GroupDto> groups);
}
