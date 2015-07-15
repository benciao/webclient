package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Benutzer bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbUserRepository implements IUserRepository
{
    static final Logger          logger = LogManager.getLogger(OdbUserRepository.class.getName());
    private OdbConnectionFactory connectionFactory;

    @Autowired
    public OdbUserRepository(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteUsers(List<User> users)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (User user : users)
            {
                User persistentUser = getUserByRid(user.getRid());

                if (persistentUser != null)
                {
                    db.delete(persistentUser);
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
    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<User>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            users = db.query(new OSQLSynchQuery<Client>("select from User"));
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

        AutoPopulatingList<User> result = new AutoPopulatingList<User>(User.class);
        result.addAll(users);

        return result;
    }

    @Override
    public void saveUsers(List<User> users)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (User user : users)
            {
                User persistentUser = getUserByRid(user.getRid());

                if (persistentUser != null)
                {
                    persistentUser.update(user);
                    db.save(persistentUser);
                }
                else
                {
                    db.save(user);
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

    private User getUserByRid(Object rid)
    {
        User user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<User> resultSet = db
                    .query(new OSQLSynchQuery<Client>("select from User where @rid = " + rid));
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

        return user;
    }
}
