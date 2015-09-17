package com.ecg.webclient.feature.administration.accessrole;

import org.springframework.stereotype.Component;

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
