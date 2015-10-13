package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.RemoteSystem;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Fremdsystem-Objekten.
 * 
 * @author arndtmar
 */
public interface RemoteSystemRepository extends CrudRepository<RemoteSystem, Long>
{
    @Query("select r from RemoteSystem r where r.enabled = true")
    public Iterable<RemoteSystem> findAllEnabledRemoteSystems();
}
