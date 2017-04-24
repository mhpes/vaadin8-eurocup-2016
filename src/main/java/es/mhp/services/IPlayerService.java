package es.mhp.services;

import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.PlayerDTO;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
public interface IPlayerService extends AbstractService{

    Set<AbstractDTO> find(String playerName, String teamId);
    void save(Object dto);
    void delete(Object playerDTO);
}
