package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Schnittstelle für Persistenzimplementierung von Mandanten und deren Eigenschaften.
 * 
 * @author arndtmar
 *
 */
public interface IClientRepository
{
    /**
     * Löscht alle Mandanten der Liste
     * 
     * @param clients
     *            Liste von Mandanten
     */
    void deleteClients(List<ClientDto> clients);

    /**
     * Löscht alle Mandanteneigenschaften der Liste
     * 
     * @param properties
     *            Mandanteneigenschaften
     */
    void deleteProperties(List<PropertyDto> properties);

    /**
     * @param true, wenn nur die aktiven; false, wenn alle
     * @return alle Mandanten bei false, sonst nur die aktiven
     */
    List<ClientDto> getAllClients(boolean onlyEnabled);

    /**
     * @param groupRids
     *            liste von Rids
     * @return Alle Client-Objekte, welche den Rids zugehörigen Gruppen angehören.
     */
    List<ClientDto> getAssignedClientsForGroups(List<Object> groupRids);

    /**
     * @param id
     *            Id des Mandanten
     * @return Mandant
     */
    ClientDto getClient(Object id);

    /**
     * Lädt einen Mandanten anhand seines Namens
     * 
     * @param string
     *            Name
     * @return Mandanten wenn vorhanden, sonst null
     */
    ClientDto getClientByName(String string);

    /**
     * Speichert den Mandanten
     * 
     * @param client
     *            Mandant
     * @return gespeicherter Mandant
     */
    ClientDto saveClient(ClientDto client);

    /**
     * Speichert die in der Liste enthaltenen Mandanten
     * 
     * @param clients
     *            Liste von Mandanten
     */
    void saveClients(List<ClientDto> clients);

    /**
     * Speichert die in der Liste enthaltenen Mandanteneigenschaften
     * 
     * @param properties
     *            Liste von Mandanteneigenschaften
     */
    void saveProperties(List<PropertyDto> properties);
}
