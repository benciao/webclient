package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecg.webclient.feature.administration.persistence.modell.Client;
import com.ecg.webclient.feature.administration.persistence.modell.Group;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Gruppen-Objekten.
 * 
 * @author arndtmar
 */
public interface GroupRepository extends CrudRepository<Group, Long>
{
	@Query("select g from Group g where g.enabled = :enabled")
	public Iterable<Group> findAllEnabledGroups(@Param("enabled") boolean isEnabled);
	
	@Query("select g from Group g where g.client.id = :id")
    public Iterable<Group> findAllGroupsAssignedToClientId(@Param("id") long clientId);
	
	public Group findGroupByName(String name);
}
