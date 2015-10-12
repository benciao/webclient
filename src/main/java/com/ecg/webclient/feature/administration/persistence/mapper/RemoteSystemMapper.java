package com.ecg.webclient.feature.administration.persistence.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.AutoPopulatingList;

import com.ecg.webclient.feature.administration.persistence.modell.RemoteSystem;
import com.ecg.webclient.feature.administration.viewmodell.RemoteSystemDto;

/**
 * Mapped die Eigenschaften einer der Persistenz bekannten Entität auf ein detachtes Fremdsystem oder
 * umgekehrt.
 * 
 * @author arndtmar
 */
@Component
public class RemoteSystemMapper
{
    /**
     * Wandelt ein attachtes Fremdsystem in ein detachtes um.
     * 
     * @param remoteSystem
     *            attachtes Fremdsystem
     * @return Detachetes Fremdsystem
     */
    public RemoteSystemDto mapToDto(RemoteSystem rm)
    {
        RemoteSystemDto dto = new RemoteSystemDto();
        dto.setDescription(rm.getDescription());
        dto.setName(rm.getName());
        dto.setEnabled(rm.isEnabled());
        dto.setDelete(false);
        dto.setId(rm.getId());
        dto.setLoginUrl(rm.getLoginUrl());
        dto.setLoginParameter(rm.getLoginParameter());
        dto.setPasswordParameter(rm.getPasswordParameter());
        dto.setLogoutUrl(rm.getLogoutUrl());

        return dto;
    }

    /**
     * Wandelt eine Liste von attachten Fremdsystemen in eine Liste von detachten Fremdsystemen um.
     * 
     * @param remoteSystems
     *            Liste von attachten Fremdsystemen
     * @return Liste von detachten Fremdsystemen
     */
    public List<RemoteSystemDto> mapToDtos(List<RemoteSystem> remoteSystems)
    {
        List<RemoteSystemDto> result = new AutoPopulatingList<RemoteSystemDto>(RemoteSystemDto.class);

        remoteSystems.forEach(e -> result.add(mapToDto(e)));

        return result;
    }
}
