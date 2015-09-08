package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IUser;
import com.ecg.webclient.feature.administration.persistence.api.IUserDto;
import com.ecg.webclient.feature.administration.persistence.api.UserRepository;
import com.ecg.webclient.feature.administration.persistence.mapper.ClientMapper;
import com.ecg.webclient.feature.administration.persistence.mapper.UserMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.User;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Benutzer bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 */
@Component
public class UserService implements UserRepository
{
    static final Logger          logger = LogManager.getLogger(UserService.class.getName());
    private OdbConnectionFactory connectionFactory;

    @Autowired
    public UserService(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteUsers(List<IUserDto> detachedUsers)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IUserDto user : detachedUsers)
            {
                IUser persistentUser = getUserByRid(user.getRid());

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
    public List<IUserDto> getAllUsers(boolean onlyEnabledUsers)
    {
        List<IUser> attachedUsers = new ArrayList<IUser>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            if (!onlyEnabledUsers)
            {
                attachedUsers = db.query(new OSQLSynchQuery<User>("select from User"));
            }
            else
            {
                attachedUsers = db
                        .query(new OSQLSynchQuery<User>("select from User where enabled = true"));
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

        AutoPopulatingList<IUserDto> result = new AutoPopulatingList<IUserDto>(IUserDto.class);

        for (IUser attachedUser : attachedUsers)
        {
            result.add(UserMapper.mapToDto(attachedUser));
        }

        return result;
    }

    @Override
    public IClientDto getDefaultClientForUser(IUserDto user)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IUser persistentUser = getUserByRid(user.getRid());

            if (persistentUser != null)
            {
                return ClientMapper.mapToDto(persistentUser.getDefaultClient());
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
    public IUserDto getUserById(Object id)
    {
        IUserDto user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IUser> users = db.query(new OSQLSynchQuery<User>("select from User where @rid = " + id));

            return (users.size() != 0) ? UserMapper.mapToDto(users.get(0)) : null;

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
    public IUserDto getUserByLogin(String login)
    {
        IUserDto user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IUser> users = db.query(new OSQLSynchQuery<User>("select from User where login = '"
                    + login + "'"));

            return (users.size() != 0) ? UserMapper.mapToDto(users.get(0)) : null;

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
        IUserDto user = getUserByLogin(login);

        if (user == null)
        {
            return false;
        }
        else
        {
            IUser persistentUser = getUserByRid(user.getRid());

            String finalPw = PasswordEncoder.encodeComplex(password, user.getRid().toString());
            if (finalPw.equalsIgnoreCase(user.getPassword()))
            {
                if (persistentUser.isEnabled() && !persistentUser.getGroups().isEmpty()
                        && persistentUser.getDefaultClient().isEnabled())
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
    public void saveUser(IUserDto detachedUser)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IUser attachedUser = UserMapper.mapToEntity(detachedUser);
            IUser persistentUser = getUserByRid(detachedUser.getRid());

            if (persistentUser != null)
            {
                persistentUser.bind(attachedUser);
                persistentUser = db.save(persistentUser);
            }
            else
            {
                persistentUser = db.save(attachedUser);
                attachedUser.setRid(persistentUser.getRid());

                String pw = detachedUser.getPassword();

                if (pw == null || pw.isEmpty())
                {
                    pw = PasswordEncoder.encodeSimple("NewUser123?");
                }

                OCommandRequest command = new OCommandSQL("update " + persistentUser.getRid()
                        + " set password = '"
                        + PasswordEncoder.encodeComplex(pw, persistentUser.getRid().toString()) + "'");
                db.command(command).execute();
            }

            OCommandRequest command = new OCommandSQL("update " + attachedUser.getRid() + " set groups = "
                    + attachedUser.getGroupRids());
            db.command(command).execute();

            command = new OCommandSQL("update " + attachedUser.getRid() + " set defaultClient = "
                    + attachedUser.getDefaultClientRid());
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
    public void saveUsers(List<IUserDto> detachedUsers)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IUserDto user : detachedUsers)
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

    private IUser getUserByRid(Object rid)
    {
        IUser user = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IUser> resultSet = db.query(new OSQLSynchQuery<Client>("select from User where @rid = "
                    + rid));
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
