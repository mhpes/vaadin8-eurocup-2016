package es.mhp.browser.impl.player;

import es.mhp.browser.IGridBrowser;
import es.mhp.browser.impl.AbstractBrowser;
import es.mhp.browser.impl.AbstractGridBrowser;
import es.mhp.browser.impl.player.presenter.PlayerBrowserPresenter;
import es.mhp.browser.impl.player.presenter.PlayerGridBrowser;
import es.mhp.services.dto.AbstractDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
@Component(PlayerBrowser.BEAN_NAME)
@Scope("prototype")
public class PlayerBrowser extends AbstractBrowser {

    public static final String BEAN_NAME = "player_browser";

    @Autowired
    @Qualifier(PlayerGridBrowser.BEAN_NAME)
    private IGridBrowser gridBrowser;

    @Autowired
    private PlayerBrowserPresenter teamBrowserPresenter;

    public PlayerBrowser() {

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
