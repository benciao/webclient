package com.ecg.webclient.feature.administration.persistence.api;

/**
 * Schnittstelle für von der Persistenz detachte Basisobjekte.
 * 
 * @author arndtmar
 */
public interface IBaseObjectDto
{
    /**
     * @param obj
     *            anderes Basisobjekt
     * @return true, wenn Basisobjekte gleich sind, sonst false
     */
    @Override
    boolean equals(Object obj);

    /**
     * @return Die Id des Objektes
     */
    Object getRid();

    /**
     * @return HashCode des Objektes
     */
    @Override
    int hashCode();

    /**
     * @return true, wenn Persistenzobjekt gelöscht werden soll, sonst false
     */
    boolean isDelete();

    /**
     * Setz, ob das Persistenzobjekt gelöscht werden soll oder nicht
     * 
     * @param delete
     *            true, wenn Persistenzobjekt gelöscht werden soll, sonst false
     */
    void setDelete(boolean delete);

    /**
     * Setzt die Id des Objektes
     * 
     * @param rid
     *            id
     */
    void setRid(Object rid);
}
