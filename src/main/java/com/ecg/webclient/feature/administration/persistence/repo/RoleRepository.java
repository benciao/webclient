package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ecg.webclient.feature.administration.persistence.modell.Role;

/**
 * Repository zum Bereitstellen von CRUD-Operationen auf Rollen-Objekten.
 * 
 * @author arndtmar
 */
public interface RoleRepository extends CrudRepository<Role, Long>
{
	@Query("select r from Role r where r.enabled = :enabled")
	public Iterable<Role> findAllEnabledRoles(@Param("enabled") boolean isEnabled);
}
