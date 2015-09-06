package com.ecg.webclient.feature.nomination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ecg.webclient.common.feature.FeatureRegistry;

@Controller
@RequestMapping(value = "/nom")
public class NominationController
{
    @Autowired
    private FeatureRegistry featureRegistry;

    @RequestMapping(method = RequestMethod.GET)
    public String load()
    {
        return getLoadingRedirectTemplate();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String load(@ModelAttribute("isMinimized") Boolean isMinimized,
            @ModelAttribute("currentFeature") FeatureNomination feature)
    {
        featureRegistry.updateActiveFeature(feature, isMinimized);
        return getLoadingRedirectTemplate();
    }

    protected String getLoadingRedirectTemplate()
    {
        return "nomination";
    }

}
