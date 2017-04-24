package es.mhp.views;

/**
 * Created by User on 11/04/2017.
 */

import com.vaadin.spring.annotation.SpringView;
import es.mhp.browser.IBrowser;
import es.mhp.browser.impl.AbstractBrowser;
import es.mhp.browser.impl.team.TeamBrowser;
import es.mhp.browser.utils.StateType;
import es.mhp.search.ISearchForm;
import es.mhp.search.impl.AbstractSearchForm;
import es.mhp.search.impl.team.TeamSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static es.mhp.views.utils.TeamViewConstants.VIEW_NAME;

@SpringView(name = VIEW_NAME)
public class TeamView extends AbstractView {

    @Autowired
    @Qualifier(TeamSearchForm.BEAN_NAME)
    private ISearchForm searchForm;

    @Autowired
    @Qualifier(TeamBrowser.BEAN_NAME)
    private IBrowser browser;


    @Override
    protected void addComponentsToView() {
        this.removeAllComponents();
        this.addComponent((AbstractSearchForm)searchForm);
        this.addComponent((AbstractBrowser)browser);
    }

    @Override
    protected void configureComponents() {

        searchForm.buildSearchForm(browser);
        browser.buildBrowser();
    }

    @Override
    protected IBrowser getBrowser() {
        return browser;
    }
}
