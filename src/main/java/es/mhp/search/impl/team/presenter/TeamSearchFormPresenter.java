package es.mhp.search.impl.team.presenter;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import es.mhp.browser.IBrowser;
import es.mhp.search.impl.presenter.AbstractSearchFormPresenter;
import es.mhp.search.impl.team.TeamSearchForm;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.Set;

import static es.mhp.views.utils.TeamViewConstants.GROUP_LIST;

/**
 * Created by User on 12/04/2017.
 */
@Component
@Scope("singleton")
public class TeamSearchFormPresenter extends AbstractSearchFormPresenter {

    @Autowired
    private ITeamService teamService;

    public Button.ClickListener createSearchButtonListener(IBrowser browser, TeamSearchForm teamSearchForm) {

        return (Button.ClickListener) event -> {

            String teamName = teamSearchForm.getTeamNameTextField().getValue();
            String group = null;
            if (teamSearchForm.getGroupComboBox().getValue() != null) {
                group = teamSearchForm.getGroupComboBox().getValue().toString();
            }
            browser.updateAndDisplayGrid(teamService.find(teamName, group));
        };
    }

    public void updateAndDisplayGrid(IBrowser browser) {

        browser.updateAndDisplayGrid(teamService.findAll());
    }

    public void fillGroupComboBox(ComboBox groupComboBox) {

        groupComboBox.setDataProvider(DataProvider.ofItems(GROUP_LIST));
    }
}
