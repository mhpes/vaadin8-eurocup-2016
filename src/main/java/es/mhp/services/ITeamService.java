package es.mhp.services;

import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
public interface ITeamService extends AbstractService{

    Set<AbstractDTO> find(String teamName, String group);
    void save(Object dto);
    void delete(Object teamDTO);
}
