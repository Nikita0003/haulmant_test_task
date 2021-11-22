package com.haulmont.testtask.view;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    public enum OPTIONS {
        UPDATE,
        ADD
    }

    @Override
    protected void init(VaadinRequest request) {
        AbsoluteLayout mainLayout = new AbsoluteLayout();
        mainLayout.setSizeFull();

        Label creditBaseLabel = new Label("Кредитная база данных");
        creditBaseLabel.addStyleName(ValoTheme.LABEL_H1);

        VerticalLayout buttonsLayout = new VerticalLayout();
        Button clientsButton = new Button("Клиенты");
        Button creditsButton = new Button("Кредиты");
        Button banksButton = new Button("Банки");
        Button offersButton = new Button("Кредитные предложения");

        /*обработка нажатий*/
        clientsButton.addClickListener(clickEvent -> {
            addWindow(new ClientsWindow());
        });
        creditsButton.addClickListener(clickEvent -> {
            addWindow(new CreditsWindow());
        });
        banksButton.addClickListener(clickEvent -> {
            addWindow(new BanksWindow());
        });
        offersButton.addClickListener(clickEvent -> {
            addWindow(new OffersWindow());
        });

        /*добавление компонентов*/
        buttonsLayout.addComponent(clientsButton);
        buttonsLayout.addComponent(creditsButton);
        buttonsLayout.addComponent(banksButton);
        buttonsLayout.addComponent(offersButton);
        mainLayout.addComponent(creditBaseLabel, "left: 50px");
        mainLayout.addComponent(buttonsLayout, "left: 50px; top: 130px");
        setContent(mainLayout);
    }
}