package com.ecg.webclient.feature.administration.viewmodell.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ecg.webclient.feature.administration.authentication.AuthenticationUtil;
import com.ecg.webclient.feature.administration.viewmodell.ClientProperties;
import com.ecg.webclient.feature.administration.viewmodell.PropertyDto;

/**
 * Dieser Validator prüft die Eingaben für ein Objekt vom Typ {@link PropertyDto}.
 * 
 * @author arndtmar
 */
@Component
public class PropertyDtoValidator implements Validator
{
    @Autowired
    AuthenticationUtil authUti;

    @Override
    public boolean supports(Class<?> clazz)
    {
        return ClientProperties.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors)
    {
        List<PropertyDto> properties = ((ClientProperties) object).getProperties();

        List<PropertyDto> persistedProperties = authUti.getSelectedClient().getProperties();

        int counter = 0;
        for (PropertyDto property : properties)
        {
            if (property.getId() == -1 && propertyKeyExists(persistedProperties, property))
            {
                errors.rejectValue("properties[" + counter + "].key", "property.rejected.duplicated.key");
            }

            if (property.getKey().isEmpty() || property.getKey().length() < 4
                    || property.getKey().length() > 30)
            {
                errors.rejectValue("properties[" + counter + "].key", "property.rejected.size.key");
            }
            if (property.getValue().isEmpty())
            {
                errors.rejectValue("properties[" + counter + "].value", "property.rejected.size.value");
            }

            counter++;
        }
    }

    private boolean propertyKeyExists(List<PropertyDto> persistedProperties, PropertyDto property)
    {
        for (PropertyDto persistedProperty : persistedProperties)
        {
            if (persistedProperty.getKey().equals(property.getKey()))
            {
                return true;
            }
        }

        return false;
    }
}
