package es.mhp.browser.impl.team.presenter;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import es.mhp.browser.impl.AbstractGridBrowser;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static es.mhp.views.utils.TeamViewConstants.*;

/**
 * Created by User on 12/04/2017.
 */
@Component(TeamGridBrowser.BEAN_NAME)
@Scope("prototype")
public class TeamGridBrowser extends AbstractGridBrowser<TeamDTO> {

    public static final String BEAN_NAME = "team_grid_browser";
    Binder<TeamDTO> binder;
    ListDataProvider dataProvider;

    @Autowired
    private TeamBrowserPresenter teamBrowserPresenter;

    public TeamGridBrowser() {

        super();
        grid = new Grid(TeamDTO.class);
        grid.setWidth("600px");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnReorderingAllowed(true);
        this.addComponentAsFirst(grid);
        grid.addSelectionListener(event-> {
            createFormBrowser(getSelectedGridRow());
        });

        binder = new Binder(TeamDTO.class);
        TextField name = new TextField("Team name");
        form.addComponent(name);
        binder.bind(name, TeamDTO::getName, TeamDTO::setName);

        TextField group = new TextField("Group");
        form.addComponent(group);
        binder.bind(group, TeamDTO::getGroup, TeamDTO::setGroup);

        HorizontalLayout buttons = new HorizontalLayout();
        Button saveButton = new Button("Save");
        saveButton.addClickListener(event -> saveItem(binder.getBean()));
        buttons.addComponent(saveButton);
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(event -> form.setVisible(false));
        buttons.addComponent(cancelButton);
        Button newButton = new Button("New");
        newButton.addClickListener(event -> newItem());
        buttons.addComponent(newButton);
        form.addComponent(buttons);
    }

    private void newItem() {

        TeamDTO teamDTO = new TeamDTO();
        binder.setBean(teamDTO);
    }

    private void saveItem(TeamDTO teamDTO) {

        teamBrowserPresenter.save(teamDTO);
        teamBrowserPresenter.updateAndDisplayGrid(this);
        form.setVisible(false);
        dataProvider.refreshAll();
    }

    private void deleteItem(TeamDTO teamDTO) {

        teamBrowserPresenter.delete(teamDTO);
        teamBrowserPresenter.updateAndDisplayGrid(this);
        form.setVisible(false);
        dataProvider.refreshAll();
    }

    @Override
    public void updateGrid(Collection<? extends AbstractDTO> dataSource) {

        dataProvider = DataProvider.ofCollection(dataSource);
        grid.setDataProvider(dataProvider);
        setColumnProperties();
    }

    @Override
    protected void setColumnProperties() {

        grid.removeAllColumns();
        Grid.Column teamColumn = grid.addColumn(TEAM_NAME_FIELD);
        Grid.Column groupColumn = grid.addColumn(GROUP_FIELD);
        Grid.Column<TeamDTO, String> flagColumn = grid.addColumn(team -> "<img src='" + team.getCountryFlag() + "'>", new HtmlRenderer());
        flagColumn.setCaption(COUNTRY_FLAG);
        Grid.Column<TeamDTO, String> wikiColumn = grid.addColumn(team -> "<a href='" + team.getWikipediaUrl() + "' target='blank'>More info</a>", new HtmlRenderer());
        wikiColumn.setCaption(WIKIPEDIA_LINK);
        Grid.Column<TeamDTO, String> deleteColumn = grid.addColumn(team -> "Delete", new ButtonRenderer(clickEvent -> {
            deleteItem((TeamDTO)clickEvent.getItem());}));
        deleteColumn.setCaption(DELETE);
        grid.setColumnOrder(teamColumn, groupColumn, flagColumn, wikiColumn);
    }

    @Override
    public void createFormBrowser(Object dto) {

        form.setVisible(true);
        //binder.bindInstanceFields(form);
        TeamDTO teamDTO = new TeamDTO((TeamDTO) dto);
        binder.setBean(teamDTO);
    }
}
