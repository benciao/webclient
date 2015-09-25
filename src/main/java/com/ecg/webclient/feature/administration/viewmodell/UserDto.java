package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Implementierung eines von der Persistenz detachten Benutzers.
 * 
 * @author arndtmar
 */
public class UserDto extends BaseObjectDto
{
    private String  login;
    private boolean internal;
    private String  lastname;
    private String  firstname;
    private String  password;
    private String  email;
    private boolean enabled;
    private boolean changePasswordOnNextLogin;
    private String  defaultClient;
    private String  groupIds;
    private Date    passwortChangedTimeStamp;

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

    public Date getPasswortChangedTimeStamp()
    {
        return passwortChangedTimeStamp;
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

    public void setPasswortChangedTimeStamp(Date passwortChangedTimeStamp)
    {
        this.passwortChangedTimeStamp = passwortChangedTimeStamp;
    }
}
