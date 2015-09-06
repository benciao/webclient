package com.ecg.webclient.feature.administration.viewmodell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.ecg.webclient.feature.administration.persistence.api.IUserDto;

/**
 * Implementierung eines von der Persistenz detachten Benutzers.
 * 
 * @author arndtmar
 */
public class UserDto extends BaseObjectDto implements IUserDto
{
    @Size(min = 5, max = 20)
    @NotNull
    private String  login;
    private boolean type;
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
    private String  groupRids;

    public UserDto()
    {}

    @Override
    public String getDefaultClient()
    {
        return defaultClient;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public String getFirstname()
    {
        return firstname;
    }

    @Override
    public List<Object> getGroupRidObjects()
    {
        List<Object> result = new ArrayList<Object>();

        if (groupRids != null && !groupRids.isEmpty())
        {
            List<String> rids = Arrays.asList(groupRids.split(","));

            for (String rid : rids)
            {
                result.add("#" + rid);
            }
        }

        return result.size() != 0 ? result : null;
    }

    @Override
    public String getGroupRids()
    {
        return groupRids;
    }

    @Override
    public String getLastname()
    {
        return lastname;
    }

    @Override
    public String getLogin()
    {
        return login;
    }

    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean isChangePasswordOnNextLogin()
    {
        return changePasswordOnNextLogin;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    @Override
    public boolean isType()
    {
        return type;
    }

    @Override
    public void setChangePasswordOnNextLogin(boolean changePasswordOnNextLogin)
    {
        this.changePasswordOnNextLogin = changePasswordOnNextLogin;
    }

    @Override
    public void setDefaultClient(String defaultClient)
    {
        this.defaultClient = defaultClient;
    }

    @Override
    public void setEmail(String email)
    {
        this.email = email;
    }

    @Override
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    @Override
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    @Override
    public void setGroupRids(String groupRids)
    {
        this.groupRids = groupRids;
    }

    @Override
    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    @Override
    public void setLogin(String login)
    {
        this.login = login;
    }

    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public void setType(boolean type)
    {
        this.type = type;
    }
}
