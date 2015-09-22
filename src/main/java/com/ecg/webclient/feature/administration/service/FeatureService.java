package com.ecg.webclient.feature.administration.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientFeature;
import com.ecg.webclient.feature.administration.persistence.mapper.FeatureMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Feature;
import com.ecg.webclient.feature.administration.persistence.repo.FeatureRepository;

/**
 * Service zum Bearbeiten von Features.
 * 
 * @author arndtmar
 */
@Component
public class FeatureService
{
	static final Logger logger = LogManager.getLogger(FeatureService.class.getName());

	FeatureRepository		featureRepo;
	FeatureMapper			featureMapper;
	List<WebClientFeature>	featuresToRegister;

	@Autowired
	public FeatureService(FeatureRepository featureRepo, FeatureMapper featureMapper,
			List<WebClientFeature> featuresToRegister)
	{
		this.featureRepo = featureRepo;
		this.featureMapper = featureMapper;

		registerFeatures(featuresToRegister);
	}

	@Transactional
	private void registerFeatures(List<WebClientFeature> featuresToRegister)
	{
		this.featuresToRegister = featuresToRegister;
		for (WebClientFeature feature : featuresToRegister)
		{
			Feature lookupFeature = featureRepo.findFeatureByName(feature.getFeatureId());

			if (lookupFeature == null)
			{
				Feature newFeature = new Feature();
				newFeature.setName(feature.getFeatureId());
				newFeature.setDeactivatable(feature.isDeactivatable());
				newFeature.setEnabled(true);
				featureRepo.save(newFeature);
			}
		}
	}
}
