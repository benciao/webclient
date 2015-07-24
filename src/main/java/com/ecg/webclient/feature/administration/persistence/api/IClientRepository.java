package com.ecg.webclient.feature.administration.persistence.api;

import java.util.List;

import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Property;

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
    void deleteClients(List<Client> clients);

    /**
     * Löscht alle Mandanteneigenschaften der Liste
     * 
     * @param properties
     *            Mandanteneigenschaften
     */
    void deleteProperties(List<Property> properties);

    /**
     * @return alle Mandanten
     */
    List<Client> getAllClients();

    /**
     * @param groupRids
     *            liste von Rids
     * @return Alle Client-Objekte, welche den Rids zugehörigen Gruppen angehören.
     */
    List<Client> getAssignedClientsForGroups(List<Object> groupRids);

    /**
     * @param id
     *            technische Id
     * @return den Mandandanten zu welchem die id passt
     */
    Client getClientById(Object id);

    /**
     * @param id
     *            technische id
     * @return die Mandanteneigenschaft yu welcher die Id passt
     */
    Property getPropertyById(Object id);

    /**
     * Speichert den Mandanten
     * 
     * @param client
     *            Mandant
     */
    void saveClient(Client client);

    /**
     * Speichert die in der Liste enthaltenen Mandanten
     * 
     * @param clients
     *            Liste von Mandanten
     */
    void saveClients(List<Client> clients);

    /**
     * Speichert die in der Liste enthaltenen Mandanteneigenschaften
     * 
     * @param properties
     *            Liste von Mandanteneigenschaften
     */
    void saveProperties(List<Property> properties);
}
