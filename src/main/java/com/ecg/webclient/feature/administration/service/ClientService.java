package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.mapper.ClientMapper;
import com.ecg.webclient.feature.administration.persistence.mapper.PropertyMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.Group;
import com.ecg.webclient.feature.administration.persistence.modell.Property;
import com.ecg.webclient.feature.administration.persistence.repo.ClientRepository;
import com.ecg.webclient.feature.administration.persistence.repo.GroupRepository;
import com.ecg.webclient.feature.administration.persistence.repo.PropertyRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Service zum Bearbeiten von Mandanten und deren Eigenschaften.
 * 
 * @author arndtmar
 */
@Component
public class ClientService
{
    static final Logger logger = LogManager.getLogger(ClientService.class.getName());

    ClientRepository    clientRepo;
    PropertyRepository  propertyRepo;
    GroupRepository     groupRepo;
    PropertyMapper      propertyMapper;
    ClientMapper        clientMapper;

    @Autowired
    public ClientService(PropertyRepository propertyRepo, GroupRepository groupRepo,
            ClientRepository clientRepo, PropertyMapper propertyMapper, ClientMapper clientMapper)
    {
        this.propertyRepo = propertyRepo;
        this.groupRepo = groupRepo;
        this.clientRepo = clientRepo;
        this.propertyMapper = propertyMapper;
        this.clientMapper = clientMapper;
    }

    /**
     * Löscht alle in der Liste enthaltenen Mandanten.
     * 
     * @param detachedClients
     *            Liste von zu löschenden Mandanten
     */
    public void deleteClients(List<ClientDto> detachedClients)
    {
        try
        {
            for (ClientDto client : detachedClients)
            {
                Client persistentClient = clientRepo.findOne(client.getId());

                if (persistentClient != null)
                {
                    clientRepo.delete(persistentClient);
                }
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * Löscht alle in der Liste enthaltenen Mandanteneigenschaften.
     * 
     * @param detachedProperties
     *            Liste von Mandanteneigenschaften
     */
    public void deleteProperties(List<PropertyDto> detachedProperties)
    {
        try
        {
            for (PropertyDto property : detachedProperties)
            {
                Property persistentProperty = propertyRepo.findOne(property.getId());

                if (persistentProperty != null)
                {
                    propertyRepo.delete(persistentProperty);
                }
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * @param onlyEnabled
     *            true, wenn nur die aktiven Mandanten geladen werden sollen, sonst false
     * @return Alle Mandanten, wenn false, sonst nur die aktivierten Mandanten
     */
    public List<ClientDto> getAllClients(boolean onlyEnabled)
    {
        final List<Client> attachedClients = new ArrayList<Client>();

        try
        {
            if (!onlyEnabled)
            {
                clientRepo.findAll().forEach(e -> attachedClients.add(e));
            }
            else
            {
                clientRepo.findAllEnabledClients(true).forEach(e -> attachedClients.add(e));
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        AutoPopulatingList<ClientDto> result = new AutoPopulatingList<ClientDto>(ClientDto.class);

        for (Client attachedClient : attachedClients)
        {
            result.add(clientMapper.mapToDto(attachedClient));
        }

        return result;
    }

    /**
     * @param groupIds
     *            Liste von Gruppen-IDs
     * @return Alle zu den Gruppen gehörende Mandanten
     */
    public List<ClientDto> getAssignedClientsForGroups(List<Long> groupIds)
    {
        List<ClientDto> result = new ArrayList<ClientDto>();

        try
        {
            Iterable<Group> groups = groupRepo.findAll(groupIds);

            Map<String, ClientDto> clientMap = new HashMap<String, ClientDto>();

            for (Group group : groups)
            {
                clientMap.put(group.getClient().toString(), clientMapper.mapToDto(group.getClient()));
            }

            result.addAll(clientMap.values());
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return result;
    }

    /**
     * @param id
     *            Mandanten-ID
     * @return Zur ID gehörender Mandant
     */
    public ClientDto getClient(Long id)
    {
        try
        {
            Client client = clientRepo.findOne(id);
            return (client != null) ? clientMapper.mapToDto(client) : null;
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * @param name
     *            Mandantenname
     * @return Zum Namen gehörender Mandant
     */
    public ClientDto getClientByName(String name)
    {
        try
        {
            Client client = clientRepo.findClientByName(name);
            return (client != null) ? clientMapper.mapToDto(client) : null;
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * Speichert einen Mandanten.
     * 
     * @param detachedClient
     *            zu aktualisierender Mandant
     * @return Aktualisierter Mandant
     */
    @Transactional
    public ClientDto saveClient(ClientDto detachedClient)
    {
        try
        {
            List<Property> draftedProperties = propertyMapper.mapToEntities(detachedClient.getProperties());
            Iterable<Property> persistedProperties = propertyRepo.save(draftedProperties);
            List<Property> relatedProperties = new ArrayList<Property>();
            persistedProperties.forEach(e -> relatedProperties.add(e));

            Client draftClient = clientMapper.mapToEntity(detachedClient);
            draftClient.setProperties(relatedProperties);
            Client persistedClient = clientRepo.save(draftClient);

            if (persistedClient != null)
            {
                return clientMapper.mapToDto(persistedClient);
            }
            else
            {
                return null;
            }
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        return null;
    }

    /**
     * Speichert eine Liste von Mandanten.
     * 
     * @param detachedClients
     *            Liste von Mandanten
     */
    public void saveClients(List<ClientDto> detachedClients)
    {
        try
        {
            detachedClients.forEach(e -> saveClient(e));
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }
}
