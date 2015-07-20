package com.ecg.webclient.feature.administration.viewmodell;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class UserDto extends BaseObjectDto
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

    public boolean equals(UserDto otherDto)
    {
        return otherDto.getRid().toString().equalsIgnoreCase(this.getRid().toString()) ? true : false;
    }

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

    public String getGroupRids()
    {
        return groupRids;
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

    public boolean getType()
    {
        return type;
    }

    public boolean isChangePasswordOnNextLogin()
    {
        return changePasswordOnNextLogin;
    }

    public boolean isEnabled()
    {
        return enabled;
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

    public void setGroupRids(String groupRids)
    {
        this.groupRids = groupRids;
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

    public void setType(boolean type)
    {
        this.type = type;
    }
}
