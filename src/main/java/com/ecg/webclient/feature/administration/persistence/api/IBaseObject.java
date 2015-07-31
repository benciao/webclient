package com.ecg.webclient.feature.administration.persistence.api;

/**
 * Schnittstelle für Implementierungen eines persistenten Basisobjektes
 * 
 * @author arndtmar
 */
public interface IBaseObject
{
    /**
     * @param obj
     *            anderes Basisobjekt
     * @return true, wenn Basisobjekte gleich sind, sonst false
     */
    @Override
    boolean equals(Object obj);

    /**
     * @return Id des Persistenzobjektes
     */
    Object getRid();

    /**
     * @return HashCode des Objektes
     */
    @Override
    int hashCode();

    /**
     * Setzt die Id des Persistenzobjektes
     * 
     * @param rid
     */
    void setRid(Object rid);
}
