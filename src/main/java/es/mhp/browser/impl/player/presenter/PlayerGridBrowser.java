package es.mhp.browser.impl.player.presenter;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationResult;
import com.vaadin.data.Validator;
import com.vaadin.data.ValueContext;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.DateRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import es.mhp.browser.impl.AbstractGridBrowser;
import es.mhp.services.IPlayerService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.PlayerDTO;
import es.mhp.services.dto.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

import static es.mhp.views.utils.PlayerViewConstants.*;
import static es.mhp.views.utils.TeamViewConstants.COUNTRY_FLAG;

/**
 * Created by User on 12/04/2017.
 */
@Component(PlayerGridBrowser.BEAN_NAME)
@Scope("prototype")
public class PlayerGridBrowser extends AbstractGridBrowser<PlayerDTO> {

    public static final String BEAN_NAME = "player_grid_browser";
    Binder<PlayerDTO> binder;
    ListDataProvider dataProvider;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private PlayerBrowserPresenter playerBrowserPresenter;

    public PlayerGridBrowser() {

        super();
        grid = new Grid<>(PlayerDTO.class);
        grid.setWidth("800px");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setColumnReorderingAllowed(true);
        this.addComponentAsFirst(grid);
        grid.addSelectionListener(event-> {
            createFormBrowser(getSelectedGridRow());
        });
    }

    @PostConstruct
    private void createForm() {

        binder = new Binder(PlayerDTO.class);
        TextField name = new TextField("Player name");
        form.addComponent(name);
        binder.bind(name, PlayerDTO::getName, PlayerDTO::setName);

        ComboBox<TeamDTO> team = new ComboBox<TeamDTO>("Team");
        team.setItemCaptionGenerator(t->t.getName());
        form.addComponent(team);
        binder.bind(team, PlayerDTO::getTeam, PlayerDTO::setTeam);
        playerBrowserPresenter.fillTeamComboBox(team);

        CheckBox injured = new CheckBox("Injured");
        form.addComponent(injured);
        binder.bind(injured, PlayerDTO::getInjured, PlayerDTO::setInjured);

        DateField birthday = new DateField("Birthday");
        form.addComponent(birthday);
        Validator<LocalDate> validator = new Validator<LocalDate>() {
            @Override
            public ValidationResult apply(LocalDate d, ValueContext valueContext) {

                // Test the validators
                if (d == null || d.isAfter(LocalDate.now())) {
                    return ValidationResult.error("Date invalid");
                } else {
                    return ValidationResult.ok();
                }
            }
        };
        binder.forField(birthday)
                .withValidator(validator)
                .bind(PlayerDTO::getBirthdate, PlayerDTO::setBirthdate);
        //bind(birthday, PlayerDTO::getBirthdate, PlayerDTO::setBirthdate);

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

        PlayerDTO playerDTO = new PlayerDTO();
        binder.setBean(playerDTO);
    }

    private void saveItem(PlayerDTO playerDTO) {

        if (binder.validate().isOk()) {
            playerBrowserPresenter.save(playerDTO);
            playerBrowserPresenter.updateAndDisplayGrid(this);
            form.setVisible(false);
            dataProvider.refreshAll();
        }
    }

    private void deleteItem(PlayerDTO playerDTO) {

        playerBrowserPresenter.delete(playerDTO);
        playerBrowserPresenter.updateAndDisplayGrid(this);
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
        Grid.Column nameColumn = grid.addColumn(PLAYER_NAME_FIELD);
        Grid.Column<PlayerDTO, String> teamColumn = grid.addColumn(player -> player.getTeam().getName()).setCaption(TEAM);
        Grid.Column<PlayerDTO, String> flagColumn = grid.addColumn(player -> "<img src='" + player.getCountryFlag() + "'>", new HtmlRenderer()).setCaption(COUNTRY_FLAG);
        Grid.Column birthDateColumn = grid.addColumn(BIRTHDATE_FIELD);
        // Test the DateRenderer
        //Grid.Column<PlayerDTO, Date> birthDateColumn = grid.addColumn(PlayerDTO::getBirthdateAsDate, new DateRenderer("%1$te de %1$tB de %1$tY")).setCaption(COUNTRY_FLAG);
        Grid.Column injuredColumn = grid.addColumn(INJURED_FIELD);

        grid.setColumnOrder(nameColumn, teamColumn, birthDateColumn, injuredColumn, flagColumn);

        grid.setWidth("60%");
    }

    @Override
    public void createFormBrowser(Object dto) {

        form.setVisible(true);
        //binder.bindInstanceFields(form);
        PlayerDTO teamDTO = new PlayerDTO((PlayerDTO) dto);
        binder.setBean(teamDTO);
    }
}
