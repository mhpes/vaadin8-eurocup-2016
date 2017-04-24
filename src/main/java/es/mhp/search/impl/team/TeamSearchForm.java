package es.mhp.search.impl.team;

import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import es.mhp.browser.IBrowser;
import es.mhp.search.impl.AbstractSearchForm;
import es.mhp.search.impl.team.presenter.TeamSearchFormPresenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static es.mhp.views.utils.TeamViewConstants.*;

/**
 * Created by User on 12/04/2017.
 */
@Component(TeamSearchForm.BEAN_NAME)
public class TeamSearchForm extends AbstractSearchForm {

    public static final String BEAN_NAME = "team_search_form";

    @Autowired
    private TeamSearchFormPresenter teamSearchFormPresenter;

    private TextField teamNameTextField;
    private ComboBox groupComboBox;
    private Button searchButton;

    public TeamSearchForm() {

        super();
        initializeComponents();
    }

    @Override
    public void buildSearchForm(IBrowser browser) {

        searchForm.removeAllComponents();
        teamSearchFormPresenter.fillGroupComboBox(groupComboBox);
        //teamSearchFormPresenter.updateAndDisplayGrid(browser);
        searchButton.addClickListener(teamSearchFormPresenter.createSearchButtonListener(browser, this));

        searchForm.addComponents(teamNameTextField, groupComboBox, searchButton);

        addComponent(searchForm);
    }

    @Override
    protected void initializeComponents() {

        teamNameTextField = new TextField(TEAM_NAME);
        groupComboBox = new ComboBox(GROUP);
        searchButton = new Button(TEAM_SEARCH);
    }

    public TextField getTeamNameTextField() {
        return teamNameTextField;
    }

    public ComboBox getGroupComboBox() {
        return groupComboBox;
    }


}
