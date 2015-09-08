package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.Group;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Gruppen-Objekten.
 * 
 * @author arndtmar
 */
public interface GroupRepository extends CrudRepository<Group, Long>
{

}
