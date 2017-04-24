package es.mhp.views;

/**
 * Created by User on 11/04/2017.
 */

import com.vaadin.spring.annotation.SpringView;
import es.mhp.browser.IBrowser;
import es.mhp.browser.impl.AbstractBrowser;
import es.mhp.browser.impl.player.PlayerBrowser;
import es.mhp.search.ISearchForm;
import es.mhp.search.impl.AbstractSearchForm;
import es.mhp.search.impl.player.PlayerSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import static es.mhp.views.utils.PlayerViewConstants.VIEW_NAME;

@SpringView(name = VIEW_NAME)
public class PlayerView extends AbstractView {

    @Autowired
    @Qualifier(PlayerSearchForm.BEAN_NAME)
    private ISearchForm searchForm;

    @Autowired
    @Qualifier(PlayerBrowser.BEAN_NAME)
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
