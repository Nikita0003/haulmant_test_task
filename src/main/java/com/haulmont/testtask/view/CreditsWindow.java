package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Credit;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Theme(ValoTheme.THEME_NAME)
public class CreditsWindow extends Window {

    private static ListDataProvider<Credit> creditList;
    private static List<Grid> gridList = new ArrayList<>();
    private Grid<Credit> grid = new Grid<>();

    public CreditsWindow(){
        super("Кредиты");
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
        AtomicReference<Credit> selectedCredit =  new AtomicReference<>();

        // кнопки заблокированы до выбора значения
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);

        //установка значений в таблице
        creditList = DataProvider.ofCollection(Controller.getCreditList());
        grid.setDataProvider(creditList);

        //установка колонок в таблице
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Credit::getLimit).setCaption("Лимит");
        grid.addColumn(Credit::getPercent).setCaption("Процентная ставка");

        //добавление таблицы в список обновляемых таблиц
        gridList.add(grid);

        //обработчик события выбранного объекта
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedCredit.set(valueChangeEvent.getValue());
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
            selectedCredit.set(new Credit(-1, 0, 0));
            getUI().addWindow(new CreditEditorWindow(selectedCredit.get(), MainUI.OPTIONS.ADD));
        });
        changeButton.addClickListener(clickEvent -> {
            getUI().addWindow(new CreditEditorWindow(selectedCredit.get(), MainUI.OPTIONS.UPDATE));
        });
        deleteButton.addClickListener(clickEvent -> {
            int errCode = Controller.deleteCredit(selectedCredit.get().getId());
            if (errCode == 1)
            {
                VerticalLayout errContentLayout = new VerticalLayout();
                errContentLayout.addComponent(new Label("Нельзя удалить кредит, который числится в банке или" +
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
        creditList = DataProvider.ofCollection(Controller.getCreditList());
        gridList.forEach(grid1 -> {grid1.setDataProvider(creditList);});
    }
}
