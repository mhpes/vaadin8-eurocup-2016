package es.mhp.browser.impl.player.presenter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;
import es.mhp.browser.IGridBrowser;
import es.mhp.browser.presenter.AbstractBrowserPresenter;
import es.mhp.services.IPlayerService;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.PlayerDTO;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
@Component
@Scope("prototype")
public class PlayerBrowserPresenter extends AbstractBrowserPresenter {

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private ITeamService teamService;

    @Override
    public void updateAndDisplayGrid(IGridBrowser gridBrowser) {

        updateAndDisplayGrid(gridBrowser, playerService.findAll());
    }

    public void save(PlayerDTO playerDTO) {

        playerService.save(playerDTO);
    }

    public void delete(PlayerDTO playerDTO) {

        playerService.delete(playerDTO);
    }

    public void fillTeamComboBox(ComboBox teamComboBox) {

        Set<AbstractDTO> teamDTOs = teamService.findAll();
        ListDataProvider dataProvider = DataProvider.ofCollection(teamDTOs);
        teamComboBox.setDataProvider(dataProvider);
        // TODO: ¿How to do this with lambda function? Something as teamComboBox.setItemCaptionGenerator(t -> t.getName());
        ItemCaptionGenerator<TeamDTO> caption = new ItemCaptionGenerator<TeamDTO>() {
            @Override
            public String apply(TeamDTO teamDTO) {
                return teamDTO.getName();
            }
        };
        teamComboBox.setItemCaptionGenerator(caption);
    }
}
