package es.mhp.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import es.mhp.views.utils.MatchViewConstants;
import es.mhp.views.utils.PlayerViewConstants;
import es.mhp.views.utils.TeamViewConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@SpringUI
public class MyUI extends UI {

    @Autowired
    private transient SpringViewProvider viewProvider;

    private VerticalLayout viewContainer;

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        getPage().setTitle("Euro 2016");

        HorizontalLayout generalLayout = createUILayout();
        setupNavigator();
        setContent(generalLayout);
        navigator.navigateTo(TeamViewConstants.VIEW_NAME);
    }

    private void setupNavigator(){

        navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
    }

    private HorizontalLayout createUILayout() {

        HorizontalLayout generalLayout = new HorizontalLayout();
        //LAYOUT MENU
        Layout menuLayout = createMenu();

        //LAYOUT VIEW
        Layout viewLayout = createView();

        generalLayout.addComponent(menuLayout);
        generalLayout.addComponent(viewLayout);

        generalLayout.setExpandRatio(menuLayout, 2);
        generalLayout.setExpandRatio(viewLayout, 8);

        return generalLayout;
    }

    private Layout createMenu() {

        VerticalLayout verticalLayout = new VerticalLayout();
        VerticalLayout vl = new VerticalLayout();
        vl.setMargin(true);
        vl.setWidth("200px");
        verticalLayout.setMargin(true);
        verticalLayout.setWidth("200px");
        verticalLayout.setHeight("100%");
        verticalLayout.setStyleName("GeneralMenu");
        verticalLayout.addComponent(vl);

        vl.addComponent(createCustomButton(PlayerViewConstants.VIEW_NAME));
        vl.addComponent(createCustomButton(TeamViewConstants.VIEW_NAME));

        return verticalLayout;
    }

    private Component createCustomButton(String view) {

        Button button = new Button(view, (Button.ClickListener) event -> getNavigator().navigateTo(view));
        button.setWidth("100px");
        return button;
    }

    private Layout createView() {

        //LAYOUT GENERAL
        viewContainer = new VerticalLayout();
        viewContainer.addStyleName("views-container");
        return viewContainer;
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }
}
