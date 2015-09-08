package com.ecg.webclient.feature.administration.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.odbmapper.ClientMapper;
import com.ecg.webclient.feature.administration.persistence.odbmapper.PropertyMapper;
import com.ecg.webclient.feature.administration.persistence.repo.ClientRepository;
import com.ecg.webclient.feature.administration.viewmodell.ClientDto;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Service zum Bearbeiten von Mandanten und deren Eigenschaften.
 * 
 * @author arndtmar
 */
@Component
public class ClientService
{
    static final Logger logger = LogManager.getLogger(ClientService.class.getName());

    @Autowired
    ClientRepository    clientRepo;

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

    @Override
    public void deleteProperties(List<IPropertyDto> detachedProperties)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IPropertyDto property : detachedProperties)
            {
                IProperty persistentProperty = getPropertyById(property.getRid());

                if (persistentProperty != null)
                {
                    db.delete(persistentProperty);
                }
            }
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.commit();
                db.close();
            }
        }
    }

    @Override
    public List<IClientDto> getAllClients(boolean onlyEnabled)
    {
        List<IClient> attachedClients = new ArrayList<IClient>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            if (!onlyEnabled)
            {
                attachedClients = db.query(new OSQLSynchQuery<Client>("select from Client"));
            }
            else
            {
                attachedClients = db.query(new OSQLSynchQuery<Client>(
                        "select from Client where enabled = true"));
            }
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }

        AutoPopulatingList<IClientDto> result = new AutoPopulatingList<IClientDto>(IClientDto.class);

        for (IClient attachedClient : attachedClients)
        {
            result.add(ClientMapper.mapToDto(attachedClient));
        }

        return result;
    }

    @Override
    public List<IClientDto> getAssignedClientsForGroups(List<Object> groupRids)
    {
        List<IClientDto> result = new ArrayList<IClientDto>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IGroup> groups = db.query(new OSQLSynchQuery<Client>("select from Group"));

            Map<String, IClientDto> clientMap = new HashMap<String, IClientDto>();

            for (IGroup group : groups)
            {
                if (containsGroupRid(groupRids, group.getRid()))
                {
                    clientMap.put(group.getClient().toString(), ClientMapper.mapToDto(group.getClient()));
                }
            }

            result.addAll(clientMap.values());
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }

        return result;
    }

    @Override
    public IClientDto getClient(Object id)
    {
        IClientDto result = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IClient> resultSet = db.query(new OSQLSynchQuery<Client>("select from Client where @rid = "
                    + id));
            return (resultSet.size() != 0) ? ClientMapper.mapToDto(resultSet.get(0)) : null;
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }

        return result;
    }

    @Override
    public IClientDto getClientByName(String name)
    {
        IClientDto client = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IClient> resultSet = db.query(new OSQLSynchQuery<Client>("select from Client where name = '"
                    + name + "'"));
            return (resultSet.size() != 0) ? ClientMapper.mapToDto(resultSet.get(0)) : null;
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }

        return client;
    }

    @Override
    public IClientDto saveClient(IClientDto detachedClient)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IClient attachedClient = ClientMapper.mapToEntity(detachedClient);
            IClient persistentClient = getClientById(detachedClient.getRid());

            if (persistentClient != null)
            {
                persistentClient.bind(attachedClient);
                persistentClient = db.save(persistentClient);

                if (persistentClient != null)
                {
                    return ClientMapper.mapToDto(persistentClient);
                }
                else
                {
                    return null;
                }
            }
            else
            {
                persistentClient = db.save(persistentClient);

                if (persistentClient != null)
                {
                    return ClientMapper.mapToDto(persistentClient);
                }
                else
                {
                    return null;
                }
            }
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.commit();
                db.close();
            }
        }

        return null;
    }

    @Override
    public void saveClients(List<IClientDto> detachedClients)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IClientDto client : detachedClients)
            {
                IClient attachedClient = ClientMapper.mapToEntity(client);
                IClient persistentClient = getClientById(client.getRid());

                if (persistentClient != null)
                {
                    if (client.getProperties().size() == 0)
                    {
                        attachedClient.setProperties(persistentClient.getProperties());
                    }

                    persistentClient.bind(attachedClient);
                    db.save(persistentClient);
                }
                else
                {
                    db.save(attachedClient);
                }
            }
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.commit();
                db.close();
            }
        }
    }

    @Override
    public void saveProperties(List<IPropertyDto> detachedProperties)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IPropertyDto property : detachedProperties)
            {
                IProperty attachedProperty = PropertyMapper.mapToEntity(property);
                IProperty persistentProperty = getPropertyById(property.getRid());

                if (persistentProperty != null)
                {
                    persistentProperty.bind(attachedProperty);
                    db.save(persistentProperty);
                }
                else
                {
                    db.save(attachedProperty);
                }
            }
        }
        catch (final RuntimeException e)
        {
            logger.error(e);
        }
        finally
        {
            if (db != null)
            {
                db.commit();
                db.close();
            }
        }
    }

    private boolean containsGroupRid(List<Object> groupRids, Object currentGroupRid)
    {
        for (Object assignedGroup : groupRids)
        {
            if (assignedGroup.toString().equalsIgnoreCase(currentGroupRid.toString()))
            {
                return true;
            }
        }

        return false;
    }
}
