package es.mhp.browser;

import es.mhp.services.dto.AbstractDTO;

import java.util.Collection;

/**
 * Created by User on 12/04/2017.
 */
public interface IGridBrowser {

    void configure();
    void updateGrid(Collection<? extends AbstractDTO> dataSource);
    Object getSelectedGridRow();
    void createFormBrowser(Object id);
}
