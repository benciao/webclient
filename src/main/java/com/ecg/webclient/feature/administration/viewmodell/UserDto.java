package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * Implementierung eines von der Persistenz detachten Benutzers.
 * 
 * @author arndtmar
 */
public class UserDto extends BaseObjectDto
{
    @Size(min = 5, max = 20)
    @NotNull
    private String  login;
    private boolean internal;
    @Size(min = 2, max = 50)
    @NotNull
    private String  lastname;
    @Size(min = 3, max = 20)
    @NotNull
    private String  firstname;
    private String  password;
    @Email
    private String  email;
    private boolean enabled;
    private boolean changePasswordOnNextLogin;
    @NotNull
    private String  defaultClient;
    private String  groupIds;

    public UserDto()
    {}

    public String getDefaultClient()
    {
        return defaultClient;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public List<Long> getGroupIdObjects()
    {
        List<Long> result = new ArrayList<Long>();

        if (groupIds != null && !groupIds.isEmpty())
        {
            List<String> ids = Arrays.asList(groupIds.split(","));

            for (String id : ids)
            {
                result.add(Long.parseLong(id));
            }
        }

        return result.size() != 0 ? result : null;
    }

    public String getGroupIds()
    {
        return groupIds;
    }

    public String getLastname()
    {
        return lastname;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isChangePasswordOnNextLogin()
    {
        return changePasswordOnNextLogin;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isInternal()
    {
        return internal;
    }

    public void setChangePasswordOnNextLogin(boolean changePasswordOnNextLogin)
    {
        this.changePasswordOnNextLogin = changePasswordOnNextLogin;
    }

    public void setDefaultClient(String defaultClient)
    {
        this.defaultClient = defaultClient;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setGroupIds(String groupIds)
    {
        this.groupIds = groupIds;
    }

    public void setInternal(boolean internal)
    {
        this.internal = internal;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
