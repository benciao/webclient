package com.ecg.webclient.feature.administration.persistence.dbmodell;

import java.util.List;

import javax.persistence.Transient;

public class User extends BaseObject
{
    private String       login;
    private String       password;
    private String       firstname;
    private String       lastname;
    private boolean      enabled;
    private boolean      changePasswordOnNextLogin;
    private boolean      type;
    private String       email;
    private Client       defaultClient;
    @Transient
    private Object       defaultClientRid;
    private List<Group>  groups;
    @Transient
    private List<Object> groupRids;

    public User()
    {}

    public void bind(User user)
    {
        setLogin(user.getLogin());
        setType(user.isType());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        // wichtig, damit es nicht genullt wird in der DB bei Nichtänderung
        if (user.getPassword() != null && !user.getPassword().isEmpty())
        {
            setPassword(user.getPassword());
        }
        setEnabled(user.isEnabled());
        setEmail(user.getEmail());
        setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());
    }

    public Client getDefaultClient()
    {
        return defaultClient;
    }

    public Object getDefaultClientRid()
    {
        return defaultClientRid;
    }

    public String getEmail()
    {
        return email;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public List<Object> getGroupRids()
    {
        return groupRids;
    }

    public List<Group> getGroups()
    {
        return groups;
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

    public boolean isType()
    {
        return type;
    }

    public void setChangePasswordOnNextLogin(boolean changePasswordOnNextLogin)
    {
        this.changePasswordOnNextLogin = changePasswordOnNextLogin;
    }

    public void setDefaultClient(Client defaultClient)
    {
        this.defaultClient = defaultClient;
    }

    public void setDefaultClientRid(Object defaultClientRid)
    {
        this.defaultClientRid = defaultClientRid;
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

    public void setGroupRids(List<Object> groupRids)
    {
        this.groupRids = groupRids;
    }

    public void setGroups(List<Group> groups)
    {
        this.groups = groups;
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
