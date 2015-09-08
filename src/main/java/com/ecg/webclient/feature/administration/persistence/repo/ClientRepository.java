package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.Client;

/**
 * Repository zum bereitstellen von CRUD-Operationen auf Mandanten-Objekten.
 * 
 * @author arndtmar
 */
public interface ClientRepository extends CrudRepository<Client, Long>
{
}
