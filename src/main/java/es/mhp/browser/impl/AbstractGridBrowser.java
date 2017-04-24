package es.mhp.browser.impl;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import es.mhp.browser.IGridBrowser;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;

import java.lang.reflect.Constructor;

/**
 * Created by User on 12/04/2017.
 */
public abstract class AbstractGridBrowser<T extends AbstractDTO> extends HorizontalLayout implements IGridBrowser {

    // The grid component was moved to the non Abstract class TeamGridBrowser and PlayerTeamBrowser because i don´t know
    // how to set the data provider to a grid that do not have the type in the declaration
    protected Grid<T> grid;
    // For the test, i put the form into GridBrowser, i didn `t create a IFormBrowse, AbstractFormBrowser, TeamFormBrowser, ...
    protected FormLayout form;

    public AbstractGridBrowser() {

        this.setSizeFull();
        this.setMargin(false);
        //grid = new Grid();
        //this.addComponent(grid);
        form = new FormLayout();
        form.setVisible(false);
        this.addComponent(form);
    }

    public FormLayout getForm() {
        return form;
    }

    public void setForm(FormLayout form) {
        this.form = form;
    }

    @Override
    public void configure() {


        ;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public T getSelectedGridRow(){

        return (T) grid.asSingleSelect().getValue();
    }

    protected abstract void setColumnProperties();

//    private void newItem(Binder<T>  binder) {
//
//        T teamDTO = new T();
//        binder.setBean(T);
//    }
//
//    private void saveItem(TeamDTO teamDTO) {
//
//        teamBrowserPresenter.save(teamDTO);
//        teamBrowserPresenter.updateAndDisplayGrid(this);
//        form.setVisible(false);
//        dataProvider.refreshAll();
//    }
//
//    private void deleteItem(TeamDTO teamDTO) {
//
//        teamBrowserPresenter.delete(teamDTO);
//        teamBrowserPresenter.updateAndDisplayGrid(this);
//        form.setVisible(false);
//        dataProvider.refreshAll();
//    }
}
