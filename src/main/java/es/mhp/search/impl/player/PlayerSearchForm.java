package es.mhp.search.impl.player;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import es.mhp.browser.IBrowser;
import es.mhp.search.impl.AbstractSearchForm;
import es.mhp.search.impl.player.presenter.PlayerSearchFormPresenter;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static es.mhp.views.utils.PlayerViewConstants.*;

/**
 * Created by User on 12/04/2017.
 */
@Component(PlayerSearchForm.BEAN_NAME)
public class PlayerSearchForm extends AbstractSearchForm {

    public static final String BEAN_NAME = "player_search_form";

    @Autowired
    private PlayerSearchFormPresenter playerSearchFormPresenter;

    private TextField playerNameTextField;
    private ComboBox<TeamDTO> teamComboBox;
    private Button searchButton;

    public PlayerSearchForm() {

        super();
        initializeComponents();
    }

    @Override
    public void buildSearchForm(IBrowser browser) {

        searchForm.removeAllComponents();
        playerSearchFormPresenter.fillTeamComboBox(teamComboBox);
        //playerSearchFormPresenter.updateAndDisplayGrid(browser);
        searchButton.addClickListener(playerSearchFormPresenter.createSearchButtonListener(browser, this));

        searchForm.addComponents(playerNameTextField, teamComboBox, searchButton);

        addComponent(searchForm);
    }

    @Override
    protected void initializeComponents() {

        playerNameTextField = new TextField(PLAYER_NAME);
        teamComboBox = new ComboBox(TEAM);
        searchButton = new Button(TEAM_SEARCH);
    }

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }

    public ComboBox getTeamComboBox() {
        return teamComboBox;
    }


}
