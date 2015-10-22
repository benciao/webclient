package com.ecg.webclient.feature.administration.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.common.authentication.WebClientFeature;
import com.ecg.webclient.feature.administration.persistence.mapper.FeatureMapper;
import com.ecg.webclient.feature.administration.persistence.modell.Feature;
import com.ecg.webclient.feature.administration.persistence.repo.FeatureRepository;
import com.ecg.webclient.feature.administration.viewmodell.FeatureDto;

/**
 * Service zum Bearbeiten von Features.
 * 
 * @author arndtmar
 */
@Component
public class FeatureService
{
    static final Logger    logger = LogManager.getLogger(FeatureService.class.getName());

    FeatureRepository      featureRepo;
    FeatureMapper          featureMapper;
    List<WebClientFeature> featuresToRegister;

    @Autowired
    public FeatureService(FeatureRepository featureRepo, FeatureMapper featureMapper,
            List<WebClientFeature> featuresToRegister)
    {
        this.featureRepo = featureRepo;
        this.featureMapper = featureMapper;

        registerFeatures(featuresToRegister);
    }

    /**
     * @return Alle im System verfügbaren Features
     */
    public List<FeatureDto> getAllFeatures()
    {
        List<Feature> attachedFeatures = new ArrayList<Feature>();

        try
        {

            featureRepo.findAll().forEach(e -> attachedFeatures.add(e));
        }
        catch (final Exception e)
        {
            logger.error(e);
        }

        AutoPopulatingList<FeatureDto> result = new AutoPopulatingList<FeatureDto>(FeatureDto.class);

        for (Feature attachedFeature : attachedFeatures)
        {
            result.add(featureMapper.mapToDto(attachedFeature));
        }

        return result;
    }

    /**
     * Speichert das zu übergebende Feature.
     * 
     * @param detachedFeature
     *            das zu speichernde Feature
     * @return Das gespeicherte Feature
     */
    public FeatureDto saveFeature(FeatureDto detachedFeature)
    {
        try
        {
            Feature draftFeature = featureMapper.mapToEntity(detachedFeature);
            Feature persistedFeature = featureRepo.save(draftFeature);

            if (persistedFeature != null)
            {
                return featureMapper.mapToDto(persistedFeature);
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

    /**
     * Speichert die in der Liste enthaltenen Features.
     * 
     * @param detachedFeatures
     *            Liste mit zu speichernden Features
     */
    public void saveFeatures(List<FeatureDto> detachedFeatures)
    {
        try
        {
            detachedFeatures.forEach(e -> saveFeature(e));
        }
        catch (final Exception e)
        {
            logger.error(e);
        }
    }

    /**
     * @param featuresToRegister
     */
    @Transactional
    private void registerFeatures(List<WebClientFeature> featuresToRegister)
    {
        this.featuresToRegister = featuresToRegister;
        for (WebClientFeature feature : featuresToRegister)
        {
            Feature lookupFeature = featureRepo.findFeatureByName(feature.getFeatureKey());

            if (lookupFeature == null)
            {
                Feature newFeature = new Feature();
                newFeature.setName(feature.getFeatureKey());
                newFeature.setDeactivatable(feature.isDeactivatable());
                newFeature.setEnabled(true);
                featureRepo.save(newFeature);
            }
        }
    }
}
