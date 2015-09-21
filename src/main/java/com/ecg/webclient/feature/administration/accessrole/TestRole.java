package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.WebClientAccessRole;

/**
 * @author arndtmar
 */
@Component
public class TestRole extends WebClientAccessRole
{
	@Autowired
    public TestRole(TestFeature feature)
    {
        super(feature, "DUMMY_ROLE");
    }
}
