package com.ecg.webclient.common.feature;

/**
 * Dieses Interface beschreibt ein Feature im Client, welches über das horizontale Menü zu Verfügung gestellt
 * wird.
 * 
 * @author arndtmar
 *
 */
/**
 * @author arndtmar
 *
 */
public interface IFeature
{
    /**
     * @return true, wenn das Objekt gleich ist, sonst false.
     */
    boolean equals(IFeature other);

    /**
     * @return Den Pfad zum darzustellenden Icon.
     */
    String getIconPath();

    /**
     * @return Id des Features
     */
    String getId();

    /**
     * @return Link zum Thymeleaf Template.
     */
    String getLink();

    /**
     * @return i18n Variable.
     */
    String getText();

    /**
     * @return true, wenn Feature ausgew'hlt ist, sonst false.
     */
    boolean isActive();

    /**
     * Setzt das Feature in den Status ausgewählt (true) oder nicht ausgewählt (false).
     * 
     * @param isActive
     */
    void setActive(boolean isActive);
}
