package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Bank;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Theme(ValoTheme.THEME_NAME)
public class BanksWindow extends Window {

    private static ListDataProvider<Bank> bankList;
    private static List<Grid> gridList = new ArrayList<>();
    private Grid<Bank> grid = new Grid<>();

    public BanksWindow(){
        super("Банки");
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
        AtomicReference<Bank> selectedBank =  new AtomicReference<>();

        // кнопки заблокированы до выбора значения
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);

        //установка значений в таблице
        bankList = DataProvider.ofCollection(Controller.getBankList());
        grid.setDataProvider(bankList);

        //установка колонок в таблице
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Bank::getBankId).setCaption("Банк");
        grid.addColumn(Bank::getClientName).setCaption("Клиент");
        grid.addColumn(Bank::getCreditDescription).setCaption("Кредит");

        //добавление таблицы в список обновляемых таблиц
        gridList.add(grid);

        //обработчик события выбранного объекта
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedBank.set(valueChangeEvent.getValue());
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
            selectedBank.set(new Bank(-1, -1, null, null));
            getUI().addWindow(new BankEditorWindow(selectedBank.get(), MainUI.OPTIONS.ADD));
        });
        changeButton.addClickListener(clickEvent -> {
            getUI().addWindow(new BankEditorWindow(selectedBank.get(), MainUI.OPTIONS.UPDATE));
        });
        deleteButton.addClickListener(clickEvent -> {
            Controller.deleteBank(selectedBank.get().getId());
            RefreshList();
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
        bankList = DataProvider.ofCollection(Controller.getBankList());
        gridList.forEach(grid1 -> {grid1.setDataProvider(bankList);});
    }
}
