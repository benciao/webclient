package com.ecg.webclient.feature.administration.persistence.modell;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import com.ecg.webclient.common.authentication.PasswordEncoder;

/**
 * Implementierung eines Benutzers. OrientDb spezifisch.
 * 
 * @author arndtmar
 */
@Entity
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long        id;
    private String      login;
    private String      password;
    private String      firstname;
    private String      lastname;
    private boolean     enabled;
    private boolean     changePasswordOnNextLogin;
    private boolean     internal;
    private String      email;
    @OneToOne
    private Client      defaultClient;
    @OneToMany(targetEntity = Group.class)
    private List<Group> groups;
    private long        defaultClientId;
    private List<Long>  groupIds;

    public User()
    {}

    @Transient
    public void bind(User newUser)
    {
        setLogin(newUser.getLogin());
        setInternal(newUser.isInternal());
        setFirstname(newUser.getFirstname());
        setLastname(newUser.getLastname());
        // wichtig, damit es nicht genullt wird in der DB bei Nichtänderung
        if (newUser.getPassword() != null && !newUser.getPassword().isEmpty())
        {
            setPassword(PasswordEncoder.encodeComplex(newUser.getPassword(), Long.toString(getId())));
        }
        setEnabled(newUser.isEnabled());
        setEmail(newUser.getEmail());
        setChangePasswordOnNextLogin(newUser.isChangePasswordOnNextLogin());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof User))
        {
            return false;
        }
        User other = (User) obj;
        if (email == null)
        {
            if (other.email != null)
            {
                return false;
            }
        }
        else if (!email.equals(other.email))
        {
            return false;
        }
        if (firstname == null)
        {
            if (other.firstname != null)
            {
                return false;
            }
        }
        else if (!firstname.equals(other.firstname))
        {
            return false;
        }
        if (id != other.id)
        {
            return false;
        }
        if (lastname == null)
        {
            if (other.lastname != null)
            {
                return false;
            }
        }
        else if (!lastname.equals(other.lastname))
        {
            return false;
        }
        if (login == null)
        {
            if (other.login != null)
            {
                return false;
            }
        }
        else if (!login.equals(other.login))
        {
            return false;
        }
        return true;
    }

    public Client getDefaultClient()
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

    public List<Group> getGroups()
    {
        return groups;
    }

    public long getId()
    {
        return id;
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
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

    public void setDefaultClient(Client defaultClient)
    {
        this.defaultClient = defaultClient;
    }

    @Transient
    public void setDefaultClientId(long defaultClientId)
    {
        this.defaultClientId = defaultClientId;
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

    @Transient
    public void setGroupIds(List<Long> groupIdObjects)
    {
        this.groupIds = groupIdObjects;
    }

    public void setGroups(List<Group> groups)
    {
        this.groups = groups;
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
