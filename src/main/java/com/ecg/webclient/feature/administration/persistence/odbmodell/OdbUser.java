package com.ecg.webclient.feature.administration.persistence.odbmodell;

import java.util.List;

import javax.persistence.Transient;

import com.ecg.webclient.common.authentication.PasswordEncoder;
import com.ecg.webclient.feature.administration.persistence.api.IClient;
import com.ecg.webclient.feature.administration.persistence.api.IGroup;
import com.ecg.webclient.feature.administration.persistence.api.IUser;

/**
 * Implementierung eines Benutzers. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
public class OdbUser extends OdbBaseObject implements IUser
{
    private String       login;
    private String       password;
    private String       firstname;
    private String       lastname;
    private boolean      enabled;
    private boolean      changePasswordOnNextLogin;
    private boolean      type;
    private String       email;
    private IClient      defaultClient;
    @Transient
    private Object       defaultClientRid;
    private List<IGroup> groups;
    @Transient
    private List<Object> groupRids;

    public OdbUser()
    {}

    @Override
    public void bind(IUser user)
    {
        setLogin(user.getLogin());
        setType(user.isType());
        setFirstname(user.getFirstname());
        setLastname(user.getLastname());
        // wichtig, damit es nicht genullt wird in der DB bei Nichtänderung
        if (user.getPassword() != null && !user.getPassword().isEmpty())
        {
            setPassword(PasswordEncoder.encodeComplex(user.getPassword(), getRid().toString()));
        }
        setEnabled(user.isEnabled());
        setEmail(user.getEmail());
        setChangePasswordOnNextLogin(user.isChangePasswordOnNextLogin());
    }

    @Override
    public IClient getDefaultClient()
    {
        return defaultClient;
    }

    @Override
    public Object getDefaultClientRid()
    {
        return defaultClientRid;
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
    public List<Object> getGroupRids()
    {
        return groupRids;
    }

    @Override
    public List<IGroup> getGroups()
    {
        return groups;
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
    public void setDefaultClient(IClient defaultClient)
    {
        this.defaultClient = defaultClient;
    }

    @Override
    public void setDefaultClientRid(Object defaultClientRid)
    {
        this.defaultClientRid = defaultClientRid;
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
    public void setGroupRids(List<Object> groupRids)
    {
        this.groupRids = groupRids;
    }

    @Override
    public void setGroups(List<IGroup> groups)
    {
        this.groups = groups;
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
