package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.persistence.api.IUserRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
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

            users = db.query(new OSQLSynchQuery<User>("select from User"));
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
    public User getUserById(Object id)
    {
        User user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<User> users = db.query(new OSQLSynchQuery<User>("select from User where @rid = " + id));

            return (users.size() != 0) ? users.get(0) : null;

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

    @Override
    public User getUserByLogin(String login)
    {
        User user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<User> users = db.query(new OSQLSynchQuery<User>("select from User where login = '" + login
                    + "'"));

            return (users.size() != 0) ? users.get(0) : null;

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

    @Override
    public boolean isUserAuthorized(String login, String password)
    {
        User user = getUserByLogin(login);

        if (user == null)
        {
            return false;
        }
        else
        {
            String finalPw = PasswordEncoder.encodeComplex(password, user.getRid().toString());
            if (finalPw.equalsIgnoreCase(user.getPassword()))
            {
                if (user.isEnabled() && !user.getGroups().isEmpty() && user.getDefaultClient().isEnabled())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }

    @Override
    public void saveUser(User user)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            User persistentUser = getUserByRid(user.getRid());

            if (persistentUser != null)
            {
                persistentUser.bind(user);
                db.save(persistentUser);
            }
            else
            {
                persistentUser = db.save(user);
                user.setRid(persistentUser.getRid());

                String pw = user.getPassword();

                if (pw == null || pw.isEmpty())
                {
                    pw = PasswordEncoder.encodeSimple("NewUser123?");
                }

                OCommandRequest command = new OCommandSQL("update " + user.getRid() + " set password = '"
                        + PasswordEncoder.encodeComplex(pw, user.getRid().toString()) + "'");
                db.command(command).execute();
            }

            OCommandRequest command = new OCommandSQL("update " + user.getRid() + " set groups = "
                    + user.getGroupRids());
            db.command(command).execute();

            command = new OCommandSQL("update " + user.getRid() + " set defaultClient = "
                    + user.getDefaultClientRid());
            db.command(command).execute();
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
    public void saveUsers(List<User> users)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (User user : users)
            {
                saveUser(user);
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
