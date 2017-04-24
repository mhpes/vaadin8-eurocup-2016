package es.mhp.browser.impl.team;

import com.vaadin.ui.Grid;
import es.mhp.browser.IGridBrowser;
import es.mhp.browser.impl.AbstractBrowser;
import es.mhp.browser.impl.AbstractGridBrowser;
import es.mhp.browser.impl.team.presenter.TeamBrowserPresenter;
import es.mhp.browser.impl.team.presenter.TeamGridBrowser;
import es.mhp.services.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
@Component(TeamBrowser.BEAN_NAME)
@Scope("prototype")
public class TeamBrowser extends AbstractBrowser {

    public static final String BEAN_NAME = "team_browser";

    @Autowired
    @Qualifier(TeamGridBrowser.BEAN_NAME)
    private IGridBrowser gridBrowser;

    @Autowired
    private TeamBrowserPresenter teamBrowserPresenter;

    public TeamBrowser() {

        super();
    }

    @Override
    public void buildBrowser() {

        this.removeAllComponents();
        this.addComponent((AbstractGridBrowser)gridBrowser);
        teamBrowserPresenter.updateAndDisplayGrid(gridBrowser);
    }

    @Override
    public void updateAndDisplayGrid(Set<AbstractDTO> dataSource) {

        gridBrowser.updateGrid(dataSource);
        //teamBrowserPresenter.updateAndDisplayGrid(gridBrowser);
    }
}
