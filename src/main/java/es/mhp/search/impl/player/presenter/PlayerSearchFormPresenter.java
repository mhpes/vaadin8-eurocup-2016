package es.mhp.search.impl.player.presenter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.ItemCaptionGenerator;
import es.mhp.browser.IBrowser;
import es.mhp.search.impl.player.PlayerSearchForm;
import es.mhp.search.impl.presenter.AbstractSearchFormPresenter;
import es.mhp.services.IPlayerService;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Set;

import static com.vaadin.sass.internal.selector.UniversalSelector.it;
import static es.mhp.views.utils.TeamViewConstants.GROUP_LIST;

/**
 * Created by User on 12/04/2017.
 */
@Component
@Scope("singleton")
public class PlayerSearchFormPresenter extends AbstractSearchFormPresenter {

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private ITeamService teamService;

    public Button.ClickListener createSearchButtonListener(IBrowser browser, PlayerSearchForm playerSearchForm) {

        return (Button.ClickListener) event -> {

            String playerName = playerSearchForm.getPlayerNameTextField().getValue();
            String teamId = null;
            if (playerSearchForm.getTeamComboBox().getValue() != null) {
                teamId = ((TeamDTO)playerSearchForm.getTeamComboBox().getValue()).getTeamId();
            }
            browser.updateAndDisplayGrid(playerService.find(playerName, teamId));
        };
    }

    public void updateAndDisplayGrid(IBrowser browser) {

        browser.updateAndDisplayGrid(playerService.findAll());
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
