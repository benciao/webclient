package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

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
    void deleteClients(List<IClientDto> clients);

    /**
     * Löscht alle Mandanteneigenschaften der Liste
     * 
     * @param properties
     *            Mandanteneigenschaften
     */
    void deleteProperties(List<IPropertyDto> properties);

    /**
     * @param true, wenn nur die aktiven; false, wenn alle
     * @return alle Mandanten bei false, sonst nur die aktiven
     */
    List<IClientDto> getAllClients(boolean onlyEnabled);

    /**
     * @param groupRids
     *            liste von Rids
     * @return Alle Client-Objekte, welche den Rids zugehörigen Gruppen angehören.
     */
    List<IClientDto> getAssignedClientsForGroups(List<Object> groupRids);

    /**
     * @param id
     *            Id des Mandanten
     * @return Mandant
     */
    IClientDto getClient(Object id);

    /**
     * Lädt einen Mandanten anhand seines Namens
     * 
     * @param string
     *            Name
     * @return Mandanten wenn vorhanden, sonst null
     */
    IClientDto getClientByName(String string);

    /**
     * Speichert den Mandanten
     * 
     * @param client
     *            Mandant
     * @return gespeicherter Mandant
     */
    IClientDto saveClient(IClientDto client);

    /**
     * Speichert die in der Liste enthaltenen Mandanten
     * 
     * @param clients
     *            Liste von Mandanten
     */
    void saveClients(List<IClientDto> clients);

    /**
     * Speichert die in der Liste enthaltenen Mandanteneigenschaften
     * 
     * @param properties
     *            Liste von Mandanteneigenschaften
     */
    void saveProperties(List<IPropertyDto> properties);
}
