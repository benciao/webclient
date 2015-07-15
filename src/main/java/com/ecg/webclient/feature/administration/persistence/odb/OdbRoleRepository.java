package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IRoleRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Benutzerrollen bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbRoleRepository implements IRoleRepository
{
    static final Logger          logger = LogManager.getLogger(OdbRoleRepository.class.getName());
    private OdbConnectionFactory connectionFactory;

    @Autowired
    public OdbRoleRepository(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteRoles(List<Role> roles)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Role role : roles)
            {
                Role persistentRole = getRoleByRid(role.getRid());

                if (persistentRole != null)
                {
                    db.delete(persistentRole);
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
    public List<Role> getAllRoles()
    {
        List<Role> roles = new ArrayList<Role>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            roles = db.query(new OSQLSynchQuery<Client>("select from Role"));
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

        AutoPopulatingList<Role> result = new AutoPopulatingList<Role>(Role.class);
        result.addAll(roles);

        return result;
    }

    @Override
    public void saveRoles(List<Role> roles)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Role role : roles)
            {
                Role persistentRole = getRoleByRid(role.getRid());

                if (persistentRole != null)
                {
                    persistentRole.update(role);
                    db.save(persistentRole);
                }
                else
                {
                    db.save(role);
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

    private Role getRoleByRid(Object rid)
    {
        Role role = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<Role> resultSet = db
                    .query(new OSQLSynchQuery<Client>("select from Role where @rid = " + rid));
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

        return role;
    }
}
