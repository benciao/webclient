package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

import com.ecg.webclient.common.authentication.accessrole.WebClientAccessRole;

/**
 * @author arndtmar
 */
@Component
public class TestRole extends WebClientAccessRole
{
    public TestRole()
    {
        super("TEST", "DUMMY_ROLE");
    }
}
