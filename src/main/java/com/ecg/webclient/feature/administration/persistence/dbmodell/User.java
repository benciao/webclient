package com.ecg.webclient.feature.administration.persistence.dbmodell;

public class User extends BaseObject
{
    private String  login;
    private String  password;
    private String  firstname;
    private String  lastname;
    private boolean enabled;
    private boolean type;
    private Group   defaultGroup;

    public User()
    {}

    public Group getDefaultGroup()
    {
        return defaultGroup;
    }

    public String getFirstname()
    {
        return firstname;
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

    public boolean isEnabled()
    {
        return enabled;
    }

    public boolean isType()
    {
        return type;
    }

    public void setDefaultGroup(Group defaultGroup)
    {
        this.defaultGroup = defaultGroup;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
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

    public void update(User user)
    {
        setLogin(user.getLogin());
        setType(user.isType());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        setPassword(user.getPassword());
        setEnabled(user.isEnabled());
    }
}
