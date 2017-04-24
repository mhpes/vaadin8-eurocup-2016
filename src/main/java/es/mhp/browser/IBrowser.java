package es.mhp.browser;

import es.mhp.exceptions.UIException;
import es.mhp.services.dto.AbstractDTO;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
public interface IBrowser {

    void buildBrowser();
    void updateAndDisplayGrid(Set<AbstractDTO> dataSource);
}
