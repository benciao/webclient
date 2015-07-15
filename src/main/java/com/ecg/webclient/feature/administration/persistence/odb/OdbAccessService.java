package com.ecg.webclient.feature.administration.persistence.odb;

import java.io.IOException;
import java.util.Optional;

import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.orientechnologies.orient.object.db.OObjectDatabasePool;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;

/**
 * AccessService für eine OrientDB.
 * 
 * @author arndtmar
 *
 */
public class OdbAccessService
{
    private static final String STORAGE_TYPE = "plocal";

    private static final String DATABASE     = "Security";

    private final String        serverUrl;
    private final String        username;
    private final String        password;

    OdbAccessService(final String serverUrl, final String username, final String password)
    {
        this.serverUrl = serverUrl.endsWith("/") ? serverUrl : serverUrl + "/";
        this.username = username;
        this.password = password;
    }

    boolean createDatabase()
    {
        boolean created = false;
        Optional<OServerAdmin> server = Optional.empty();

        try
        {
            server = connectServer(DATABASE);

            if (server.isPresent())
            {
                if (!server.get().existsDatabase(STORAGE_TYPE))
                {
                    server.get().createDatabase("object", STORAGE_TYPE);
                }

                created = true;
            }
        }
        catch (final IOException e)
        {
            e.getStackTrace();
        }
        finally
        {
            server.ifPresent(x -> x.close());
        }

        return created;
    }

    OObjectDatabaseTx getTxFromPool()
    {
        return OObjectDatabasePool.global().acquire(serverUrl + DATABASE, username, password);
    }

    private Optional<OServerAdmin> connectServer(final String databaseName) throws IOException
    {
        return Optional.ofNullable(new OServerAdmin(serverUrl + databaseName).connect(username, password));
    }
}
