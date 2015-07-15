package com.ecg.webclient.feature.administration.persistence.odb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.dbmodell.BaseObject;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Client;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Group;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Property;
import com.ecg.webclient.feature.administration.persistence.dbmodell.Role;
import com.ecg.webclient.feature.administration.persistence.dbmodell.User;
import com.orientechnologies.orient.core.entity.OEntityManager;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * Modell Initialisierer für eine OrientDB (Object API)
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbModellInitializer
{
    private final List<Class<?>> objectTypes;

    public OdbModellInitializer()
    {
        objectTypes = new ArrayList<>();
        objectTypes.add(BaseObject.class);
        objectTypes.add(Client.class);
        objectTypes.add(Property.class);
        objectTypes.add(User.class);
        objectTypes.add(Group.class);
        objectTypes.add(Role.class);
    }

    void addObjectType(final Class<?> clazz)
    {
        objectTypes.add(clazz);
    }

    void registerCacheTypes(final OObjectDatabaseTx objectDatabase)
    {
        final OEntityManager entityManager = objectDatabase.getEntityManager();
        objectTypes.forEach(x -> registerEntity(entityManager, x));

        objectDatabase.commit();
    }

    private static void registerEntity(final OEntityManager entityManager, final Class<?> entityType)
    {
        entityManager.registerEntityClass(entityType);
    }
}
