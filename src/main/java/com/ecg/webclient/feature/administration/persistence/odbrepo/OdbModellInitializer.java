package com.ecg.webclient.feature.administration.persistence.odbrepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbBaseObject;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbClient;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbGroup;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbProperty;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbRole;
import com.ecg.webclient.feature.administration.persistence.odbmodell.OdbUser;
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
        objectTypes.add(OdbBaseObject.class);
        objectTypes.add(OdbClient.class);
        objectTypes.add(OdbProperty.class);
        objectTypes.add(OdbUser.class);
        objectTypes.add(OdbGroup.class);
        objectTypes.add(OdbRole.class);
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
