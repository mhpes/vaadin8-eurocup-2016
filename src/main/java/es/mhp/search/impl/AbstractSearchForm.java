package es.mhp.search.impl;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.VerticalLayout;
import es.mhp.browser.IBrowser;
import es.mhp.search.ISearchForm;

/**
 * Created by User on 12/04/2017.
 */
public abstract class AbstractSearchForm extends VerticalLayout implements ISearchForm {

    protected FormLayout searchForm;

    public AbstractSearchForm() {
        
        searchForm = new FormLayout();
        this.setSizeFull();
        this.setMargin(false);
        this.addComponent(searchForm);
    }

    @Override
    public abstract void buildSearchForm(IBrowser browser);

    protected abstract void initializeComponents();
}
