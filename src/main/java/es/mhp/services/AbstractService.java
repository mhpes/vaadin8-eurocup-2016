package es.mhp.services;

import es.mhp.services.dto.AbstractDTO;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
public interface AbstractService {

    Set<AbstractDTO> findAll();
}
