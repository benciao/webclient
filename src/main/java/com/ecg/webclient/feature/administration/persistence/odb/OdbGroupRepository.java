package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.api.IGroupRepository;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
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

    @Autowired
    public OdbGroupRepository(OdbConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void deleteGroups(List<Group> groups)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Group group : groups)
            {
                Group persistentGroup = getGroupByRid(group.getRid());

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
    public List<Group> getAllGroups()
    {
        List<Group> groups = new ArrayList<Group>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            groups = db.query(new OSQLSynchQuery<Client>("select from Group"));
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

        AutoPopulatingList<Group> result = new AutoPopulatingList<Group>(Group.class);
        result.addAll(groups);

        return result;
    }

    @Override
    public List<Group> getAllGroupsForClient(Object clientId)
    {
        List<Group> groups = new ArrayList<Group>();
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            groups = db.query(new OSQLSynchQuery<Client>("select from Group where client = " + clientId));
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

        AutoPopulatingList<Group> result = new AutoPopulatingList<Group>(Group.class);
        result.addAll(groups);

        return result;
    }

    @Override
    public void saveGroups(List<Group> groups)
    {
        final OObjectDatabaseTx db = connectionFactory.getTx();

        try
        {
            for (Group group : groups)
            {
                Group persistentGroup = getGroupByRid(group.getRid());

                if (persistentGroup != null)
                {
                    persistentGroup.bind(group);
                    db.save(persistentGroup);
                }
                else
                {
                    Group newGroup = db.save(group);
                    group.setRid(newGroup.getRid());
                }

                OCommandRequest command = new OCommandSQL("update " + group.getRid() + " set roles = "
                        + group.getRoleRids());
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

    private Group getGroupByRid(Object rid)
    {
        Group group = null;
        OObjectDatabaseTx db = null;

        try
        {
            db = connectionFactory.getTx();

            List<Group> resultSet = db.query(new OSQLSynchQuery<Client>("select from Group where @rid = "
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

        return group;
    }
}
