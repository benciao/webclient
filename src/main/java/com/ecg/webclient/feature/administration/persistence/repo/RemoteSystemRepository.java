package com.ecg.webclient.feature.administration.persistence.repo;

import org.springframework.data.repository.CrudRepository;

import com.ecg.webclient.feature.administration.persistence.modell.RemoteSystem;

public interface RemoteSystemRepository extends CrudRepository<RemoteSystem, Long>
{

}
