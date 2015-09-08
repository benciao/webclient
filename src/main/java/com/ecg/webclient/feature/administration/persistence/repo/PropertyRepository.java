package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.Property;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Property-Objekten.
 * 
 * @author arndtmar
 */
public interface PropertyRepository extends CrudRepository<Property, Long>
{

}
