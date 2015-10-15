package com.ecg.webclient.feature.administration.persistence.modell;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entität eines Remote Logins für Fremdsysteme.
 * 
 * @author arndtmar
 */
@Entity
@Table(name = "SEC_REMOTE_LOGIN")
public class RemoteLogin
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long         id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User         user;

    @ManyToOne
    @JoinColumn(name = "REMOTE_SYSTEM_ID")
    private RemoteSystem remoteSystem;

    private boolean      enabled;
    private String       remoteSystemLoginName;
    private String       remoteSystemPassword;

    public RemoteLogin()
    {}

    public long getId()
    {
        return id;
    }

    public RemoteSystem getRemoteSystem()
    {
        return remoteSystem;
    }

    public String getRemoteSystemLoginName()
    {
        return remoteSystemLoginName;
    }

    public String getRemoteSystemPassword()
    {
        return remoteSystemPassword;
    }

    public User getUser()
    {
        return user;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setRemoteSystem(RemoteSystem remoteSystem)
    {
        this.remoteSystem = remoteSystem;
    }

    public void setRemoteSystemLoginName(String remoteSystemLoginName)
    {
        this.remoteSystemLoginName = remoteSystemLoginName;
    }

    public void setRemoteSystemPassword(String remoteSystemPassword)
    {
        this.remoteSystemPassword = remoteSystemPassword;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

}
