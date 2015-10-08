package com.ecg.webclient.feature.administration.service;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.feature.administration.persistence.mapper.LdapConfigMapper;
import com.ecg.webclient.feature.administration.persistence.modell.LdapConfig;
import com.ecg.webclient.feature.administration.persistence.repo.LdapConfigRepository;
import com.ecg.webclient.feature.administration.viewmodell.LdapConfigDto;

/**
 * Service zum Bearbeiten von LDAP-Konfigurationen und deren Eigenschaften.
 * 
 * @author arndtmar
 */
@Component
public class LdapConfigService
{
	static final Logger logger = LogManager.getLogger(LdapConfigService.class.getName());

	LdapConfigRepository	ldapConfigRepo;
	LdapConfigMapper		ldapConfigMapper;

	@Autowired
	public LdapConfigService(LdapConfigRepository ldapConfigRepo, LdapConfigMapper ldapConfigMapper)
	{
		this.ldapConfigRepo = ldapConfigRepo;
		this.ldapConfigMapper = ldapConfigMapper;
	}

	/**
	 * @return Die aktuelle LDAP-Konfiguration
	 */
	public LdapConfigDto getLdapConfig()
	{
		try
		{
			Iterable<LdapConfig> ldapConfig = ldapConfigRepo.findAll();
			return (ldapConfig != null && ldapConfig.iterator().hasNext())
					? ldapConfigMapper.mapToDto(ldapConfig.iterator().next()) : null;
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}

	/**
	 * Speichert eine LDAP-konfiguration
	 * 
	 * @param detachedLdapConfig
	 *            zu aktualisierende LDAP-Konfiguration
	 * @return Aktualisierte LDAP-Konfiguration
	 */
	@Transactional
	public LdapConfigDto saveLdapConfig(LdapConfigDto detachedLdapConfig)
	{
		try
		{
			LdapConfig draftLdapConfig = ldapConfigMapper.mapToEntity(detachedLdapConfig);
			LdapConfig persistedLdapConfig = ldapConfigRepo.save(draftLdapConfig);

			if (persistedLdapConfig != null)
			{
				return ldapConfigMapper.mapToDto(persistedLdapConfig);
			}
			else
			{
				return null;
			}
		}
		catch (final Exception e)
		{
			logger.error(e);
		}

		return null;
	}
}
