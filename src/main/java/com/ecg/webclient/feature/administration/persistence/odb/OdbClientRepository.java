package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IClientRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Property;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Mandanten und deren Eigenschaften bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbClientRepository implements IClientRepository
{
    static final Logger          logger = LogManager.getLogger(OdbClientRepository.class.getName());
    private OdbConnectionFactory connectionFactory;

    @Autowired
    public OdbClientRepository(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteClients(List<Client> clients)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Client client : clients)
            {
                Client persistentClient = getClientById(client.getRid());

                if (persistentClient != null)
                {
                    db.delete(persistentClient);
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
    public void deleteProperties(List<Property> properties)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Property property : properties)
            {
                Property persistentProperty = getPropertyById(property.getRid());

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
    public List<Client> getAllClients()
    {
        List<Client> clients = new ArrayList<Client>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            clients = db.query(new OSQLSynchQuery<Client>("select from Client"));
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

        AutoPopulatingList<Client> bla = new AutoPopulatingList<Client>(Client.class);
        bla.addAll(clients);

        return bla;
    }

    @Override
    public Client getClientById(Object id)
    {
        Client client = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<Client> resultSet = db.query(new OSQLSynchQuery<Client>("select from Client where @rid = "
                    + id));
            return (resultSet.size() != 0) ? resultSet.get(0) : null;
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
    public Property getPropertyById(Object id)
    {
        Property property = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<Property> resultSet = db.query(new OSQLSynchQuery<Property>(
                    "select from Property where @rid = " + id));
            return (resultSet.size() != 0) ? resultSet.get(0) : null;
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

        return property;
    }

    @Override
    public void saveClient(Client client)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            Client persistentClient = getClientById(client.getRid());

            if (persistentClient != null)
            {
                persistentClient.update(client);
                db.save(persistentClient);
            }
            else
            {
                db.save(client);
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
    public void saveClients(List<Client> clients)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Client client : clients)
            {
                Client persistentClient = getClientById(client.getRid());

                if (persistentClient != null)
                {
                    if (client.getProperties().size() == 0)
                    {
                        client.setProperties(persistentClient.getProperties());
                    }

                    persistentClient.update(client);
                    db.save(persistentClient);
                }
                else
                {
                    db.save(client);
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
    public void saveProperties(List<Property> properties)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Property property : properties)
            {
                Property persistentProperty = getPropertyById(property.getRid());

                if (persistentProperty != null)
                {
                    persistentProperty.update(property);
                    db.save(persistentProperty);
                }
                else
                {
                    db.save(property);
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
}
