package com.ecg.webclient.feature.administration.persistence.odb;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * ConnectionFactory für Nutzung einer OrientDB
 * 
 * @author arndtmar
 *
 */
@Component
public class OdbConnectionFactory
{
    static final Logger          logger = LogManager.getLogger(OdbConnectionFactory.class.getName());
    private static final Object  LOCK   = new Object();
    private OdbAccessService     cacheService;
    private OdbModellInitializer dbInitializer;

    @Autowired
    public OdbConnectionFactory(OdbAccessService cacheService, OdbModellInitializer dbInitializer)
    {
        this.cacheService = cacheService;
        this.dbInitializer = dbInitializer;
        initDb();
    }

    public OObjectDatabaseTx getTx()
    {
        OObjectDatabaseTx tx = cacheService.getTxFromPool();
        return tx;
    }

    private void initDb()
    {
        synchronized (LOCK)
        {
            if (cacheService.createDatabase())
            {
                logger.info("Security database created and schema registered");
                OObjectDatabaseTx tx = cacheService.getTxFromPool();
                tx.setAutomaticSchemaGeneration(true);
                dbInitializer.registerCacheTypes(tx);
                tx.close();
            }
        }
    }

}
