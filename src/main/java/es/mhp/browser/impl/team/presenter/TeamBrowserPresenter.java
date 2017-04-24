package es.mhp.browser.impl.team.presenter;

import com.vaadin.ui.Grid;
import es.mhp.browser.IGridBrowser;
import es.mhp.browser.presenter.AbstractBrowserPresenter;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by User on 12/04/2017.
 */
@Component
@Scope("prototype")
public class TeamBrowserPresenter extends AbstractBrowserPresenter {

    @Autowired
    private ITeamService teamService;

    @Override
    public void updateAndDisplayGrid(IGridBrowser gridBrowser) {

        updateAndDisplayGrid(gridBrowser, teamService.findAll());
    }

    public void save(TeamDTO teamDTO) {

        teamService.save(teamDTO);
    }

    public void delete(TeamDTO teamDTO) {

        teamService.delete(teamDTO);
    }
}
