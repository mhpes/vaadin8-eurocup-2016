package es.mhp.browser.presenter;

import com.vaadin.ui.Grid;
import es.mhp.browser.IGridBrowser;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;

import java.util.Collection;
import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
public abstract class AbstractBrowserPresenter {

    public void updateAndDisplayGrid(IGridBrowser gridBrowser, Set<AbstractDTO> dataSource){

        gridBrowser.updateGrid(dataSource);
    }

    public abstract void updateAndDisplayGrid(IGridBrowser gridBrowser);

}
