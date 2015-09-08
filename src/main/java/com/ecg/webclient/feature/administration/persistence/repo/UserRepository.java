package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.User;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Benutzer-Objekten.
 * 
 * @author arndtmar
 */
public interface UserRepository extends CrudRepository<User, Long>
{

}
