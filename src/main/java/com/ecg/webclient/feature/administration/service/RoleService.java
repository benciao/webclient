package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IRole;
import com.ecg.webclient.feature.administration.persistence.api.IRoleDto;
import com.ecg.webclient.feature.administration.persistence.api.RoleRepository;
import com.ecg.webclient.feature.administration.persistence.mapper.RoleMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.Role;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Benutzerrollen bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 */
@Component
public class RoleService implements RoleRepository
{
    static final Logger          logger = LogManager.getLogger(RoleService.class.getName());
    private OdbConnectionFactory connectionFactory;

    @Autowired
    public RoleService(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteRoles(List<IRoleDto> detachedRoles)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IRoleDto detachedRole : detachedRoles)
            {
                IRole persistentRole = getRoleByRid(detachedRole.getRid());

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
    public List<IRoleDto> getAllRoles(boolean onlyEnabledRoles)
    {
        List<IRole> attachedRoles = new ArrayList<IRole>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            if (!onlyEnabledRoles)
            {
                attachedRoles = db.query(new OSQLSynchQuery<Client>("select from Role"));
            }
            else
            {
                attachedRoles = db.query(new OSQLSynchQuery<Client>(
                        "select from Role where enabled = true"));
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

        AutoPopulatingList<IRoleDto> result = new AutoPopulatingList<IRoleDto>(IRoleDto.class);

        for (IRole attachedRole : attachedRoles)
        {
            result.add(RoleMapper.mapToDto(attachedRole));
        }

        return result;
    }

    @Override
    public List<IRoleDto> getRolesForIds(List<Object> roleRidObjects)
    {
        List<IRoleDto> result = new ArrayList<IRoleDto>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            for (Object rid : roleRidObjects)
            {
                IRole persistentRole = getRoleByRid(rid);
                result.add(RoleMapper.mapToDto(persistentRole));
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

        return result;
    }

    @Override
    public IRoleDto saveRole(IRoleDto detachedRole)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IRole attachedRole = RoleMapper.mapToEntity(detachedRole);

            IRole persistentRole = getRoleByRid(attachedRole.getRid());

            if (persistentRole != null)
            {
                persistentRole.bind(attachedRole);

                persistentRole = db.save(persistentRole);

                if (persistentRole != null)
                {
                    return RoleMapper.mapToDto(persistentRole);
                }
                else
                {
                    return null;
                }
            }
            else
            {
                persistentRole = db.save(persistentRole);

                if (persistentRole != null)
                {
                    return RoleMapper.mapToDto(persistentRole);
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
    public void saveRoles(List<IRoleDto> detachedRoles)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            List<IRole> attachedRoles = RoleMapper.mapToEntities(detachedRoles);

            for (IRole role : attachedRoles)
            {
                IRole persistentRole = getRoleByRid(role.getRid());

                if (persistentRole != null)
                {
                    persistentRole.bind(role);
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

    /**
     * Sucht eine Rolle in der Db anhand der Rid und gibt diese zurück
     * 
     * @param rid
     *            Rid der Rolle
     * @return Persistente Rolle, wenn existent, sonst null
     */
    private IRole getRoleByRid(Object rid)
    {
        IRole role = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<Role> resultSet = db.query(new OSQLSynchQuery<Client>("select from Role where @rid = "
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

        return role;
    }
}