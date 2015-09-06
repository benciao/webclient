package com.ecg.webclient.feature.administration.persistence.odbrepo;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.persistence.api.IClientDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroup;
import com.ecg.webclient.feature.administration.persistence.api.IGroupDto;
import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.odbmapper.OdbClientMapper;
import com.ecg.webclient.feature.administration.persistence.odbmapper.OdbGroupMapper;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbClient;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbGroup;
import com.orientechnologies.orient.core.command.OCommandRequest;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Repository für Benutzergruppen bei Nutzung einer OrientDB.
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbGroupRepository implements IGroupRepository
{
    static final Logger          logger = LogManager.getLogger(OdbGroupRepository.class.getName());
    private OdbConnectionFactory connectionFactory;
    private AuthenticationUtil   authenticationUtil;

    @Autowired
    public OdbGroupRepository(OdbConnectionFactory connectionFactory, AuthenticationUtil authenticationUtil)
    {
        this.connectionFactory = connectionFactory;
        this.authenticationUtil = authenticationUtil;
    }

    @Override
    public void deleteGroups(List<IGroupDto> detachedGroups)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (IGroupDto group : detachedGroups)
            {
                IGroup persistentGroup = getGroupByRid(group.getRid());

                if (persistentGroup != null)
                {
                    db.delete(persistentGroup);
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
    public List<IGroupDto> getAllGroups(boolean onlyEnabledGroups)
    {
        List<IGroup> attachedGroups = new ArrayList<IGroup>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            if (!onlyEnabledGroups)
            {
                attachedGroups = db.query(new OSQLSynchQuery<OdbClient>("select from Group"));
            }
            else
            {
                attachedGroups = db.query(new OSQLSynchQuery<OdbClient>(
                        "select from Group where enabled = true"));
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

        AutoPopulatingList<IGroupDto> result = new AutoPopulatingList<IGroupDto>(IGroupDto.class);
        for (IGroup attachedGroup : attachedGroups)
        {
            result.add(OdbGroupMapper.mapToDto(attachedGroup));
        }

        return result;
    }

    @Override
    public List<IGroupDto> getAllGroupsForClient(Object clientId)
    {
        List<IGroup> attachedGroups = new ArrayList<IGroup>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            attachedGroups = db.query(new OSQLSynchQuery<OdbClient>("select from Group where client = "
                    + clientId));
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

        AutoPopulatingList<IGroupDto> result = new AutoPopulatingList<IGroupDto>(IGroupDto.class);
        for (IGroup attachedGroup : attachedGroups)
        {
            result.add(OdbGroupMapper.mapToDto(attachedGroup));
        }

        return result;
    }

    @Override
    public IClientDto getClientForGroupId(Object rid)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IGroup persistentGroup = getGroupByRid(rid);
            if (persistentGroup != null)
            {
                return OdbClientMapper.mapToDto(persistentGroup.getClient());
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
    public IGroupDto getGroupByName(String name)
    {
        IGroupDto group = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<IGroup> resultSet = db.query(new OSQLSynchQuery<OdbClient>(
                    "select from Group where name = '" + name + "'"));
            return (resultSet.size() != 0) ? OdbGroupMapper.mapToDto(resultSet.get(0)) : null;
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

        return group;
    }

    @Override
    public List<IGroupDto> getGroupsForIds(List<Object> groupRidObjects)
    {
        List<IGroupDto> result = new ArrayList<IGroupDto>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            for (Object rid : groupRidObjects)
            {
                IGroup persistentGroup = getGroupByRid(rid);
                result.add(OdbGroupMapper.mapToDto(persistentGroup));
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
    public IGroupDto saveGroup(IGroupDto detachedGroup)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            IGroup attachedGroup = OdbGroupMapper.mapToEntity(detachedGroup);
            IGroup persistentGroup = getGroupByRid(attachedGroup.getRid());

            if (persistentGroup != null)
            {
                persistentGroup.bind(attachedGroup);
                persistentGroup = db.save(persistentGroup);
            }
            else
            {
                persistentGroup = db.save(attachedGroup);
            }

            OCommandRequest command = new OCommandSQL("update " + persistentGroup.getRid() + " set roles = "
                    + persistentGroup.getRoleRids());
            db.command(command).execute();

            return OdbGroupMapper.mapToDto(persistentGroup);
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
    public void saveGroups(List<IGroupDto> detachedGroups)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            List<IGroup> attachedGroups = OdbGroupMapper.mapToEntities(detachedGroups);
            for (IGroup group : attachedGroups)
            {
                IGroup persistentGroup = getGroupByRid(group.getRid());
                group.setClient(OdbClientMapper.mapToEntity(authenticationUtil.getSelectedClient()));

                if (persistentGroup != null)
                {
                    persistentGroup.bind(group);
                    persistentGroup = db.save(persistentGroup);
                }
                else
                {
                    persistentGroup = db.save(group);
                }

                OCommandRequest command = new OCommandSQL("update " + persistentGroup.getRid()
                        + " set roles = " + persistentGroup.getRoleRids());
                db.command(command).execute();
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

    private IGroup getGroupByRid(Object rid)
    {
        IGroup group = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<OdbGroup> resultSet = db.query(new OSQLSynchQuery<OdbClient>(
                    "select from Group where @rid = " + rid));
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

        return group;
    }
}
