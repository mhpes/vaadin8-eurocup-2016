package es.mhp.browser.impl;

import com.vaadin.ui.VerticalLayout;
import es.mhp.browser.IBrowser;

/**
 * Created by User on 12/04/2017.
 */
public abstract class AbstractBrowser extends VerticalLayout implements IBrowser {

    public AbstractBrowser() {
        this.setSizeFull();
        this.setMargin(false);
    }
}
