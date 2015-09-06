package com.ecg.webclient.common.authentication;

import org.springframework.security.core.GrantedAuthority;

public class OdbGrantedAuthoritiy implements GrantedAuthority
{
    private static final long serialVersionUID = -3442491340287701183L;
    private final String      role;

    public OdbGrantedAuthoritiy(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (obj instanceof OdbGrantedAuthoritiy)
        {
            return this.role.equals(((OdbGrantedAuthoritiy) obj).role);
        }

        return false;
    }

    @Override
    public String getAuthority()
    {
        return this.role;
    }

    @Override
    public int hashCode()
    {
        return this.role.hashCode();
    }

    @Override
    public String toString()
    {
        return this.role;
    }
}
