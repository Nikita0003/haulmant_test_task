package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Theme(ValoTheme.THEME_NAME)
public class ClientsWindow extends Window {

    private static ListDataProvider<Client> clientList;
    private static List<Grid> gridList = new ArrayList<>();
    private Grid<Client> grid = new Grid<>();

    public ClientsWindow(){
        super("Клиенты");
        setHeight("400px");
        setWidth("600px");

        //содержимое окна
        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button addButton = new Button("Добавить");
        Button changeButton = new Button("Изменить");
        Button deleteButton = new Button("Удалить");

        //текущий выбранный объект
        AtomicReference<Client> selectedClient =  new AtomicReference<Client>();

        // кнопки заблокированы до выбора значения
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);

        //установка значений в таблице
        clientList = DataProvider.ofCollection(Controller.getClientList());
        grid.setDataProvider(clientList);

        //установка колонок в таблице
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Client::getName).setCaption("ФИО");
        grid.addColumn(Client::getPhoneNumber).setCaption("Телефон");
        grid.addColumn(Client::getEmail).setCaption("Почта");
        grid.addColumn(Client::getPassNumber).setCaption("Паспорт");

        //добавление таблицы в список обновляемых таблиц
        gridList.add(grid);

        //обработчик события выбранного объекта
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedClient.set(valueChangeEvent.getValue());
            if (valueChangeEvent.getValue() != null)
            {
                changeButton.setEnabled(true);
                deleteButton.setEnabled(true);
            }
            else
            {
                changeButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        //обработчик нажатия кнопок
        addButton.addClickListener(clickEvent -> {
            selectedClient.set(new Client(-1, "", "", "", ""));
            getUI().addWindow(new ClientEditorWindow(selectedClient.get(), MainUI.OPTIONS.ADD));
        });
        changeButton.addClickListener(clickEvent -> {
            getUI().addWindow(new ClientEditorWindow(selectedClient.get(), MainUI.OPTIONS.UPDATE));
        });
        deleteButton.addClickListener(clickEvent -> {
            int errCode = Controller.deleteClient(selectedClient.get().getId());
            if (errCode == 1)
            {
                VerticalLayout errContentLayout = new VerticalLayout();
                errContentLayout.addComponent(new Label("Нельзя удалить клиента, который числится в банке или" +
                        " для которого существуют кредитные предложения"));
                Window errWindow = new Window("Ошибка");
                errWindow.setContent(errContentLayout);
                errWindow.setModal(true);
                errWindow.center();
                getUI().addWindow(errWindow);
            }
            if (errCode == 0) RefreshList();
        });

        //удаление таблицы из списка при закрытии окна
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        //установка содержимого
        buttonsLayout.addComponent(addButton);
        buttonsLayout.addComponent(changeButton);
        buttonsLayout.addComponent(deleteButton);
        contentLayout.addComponent(grid, "top: 2%; left: 2%;");
        contentLayout.addComponent(buttonsLayout, "top: 85%; left: 2%;");
        center();
        setContent(contentLayout);
    }

    public static void RefreshList(){
        clientList = DataProvider.ofCollection(Controller.getClientList());
        gridList.forEach(grid1 -> {grid1.setDataProvider(clientList);});
    }
}