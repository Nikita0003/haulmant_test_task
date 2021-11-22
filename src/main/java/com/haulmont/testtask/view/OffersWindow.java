package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Offer;
import com.vaadin.annotations.Theme;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Theme(ValoTheme.THEME_NAME)
public class OffersWindow extends Window {

    private static ListDataProvider<Offer> offerList;
    private static List<Grid> gridList = new ArrayList<>();
    private Grid<Offer> grid = new Grid<>();

    public OffersWindow() {
        super("Кредитные предложения");
        setHeight("400px");
        setWidth("600px");

        /*содержимое окна*/
        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Button addButton = new Button("Добавить");
        Button changeButton = new Button("Изменить");
        Button deleteButton = new Button("Удалить");
        Button scheduleButton = new Button("График платежей");

        /*текущий выбранный объект*/
        AtomicReference<Offer> selectedOffer =  new AtomicReference<>();

        /*кнопки заблокированы до выбора значения*/
        changeButton.setEnabled(false);
        deleteButton.setEnabled(false);
        scheduleButton.setEnabled(false);

        /*установка значений в таблице*/
        offerList = DataProvider.ofCollection(Controller.getOfferList());
        grid.setDataProvider(offerList);

        /*установка колонок в таблице*/
        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(Offer::getClientName).setCaption("Клиент");
        grid.addColumn(Offer::getCreditDescription).setCaption("Кредит");
        grid.addColumn(Offer::getTotalSum).setCaption("Сумма кредита");
        grid.addColumn(Offer::getDateOffer).setCaption("Дата заключения договора");
        grid.addColumn(Offer::getCountPayment).setCaption("Количество оставшихся выплат");
        grid.addColumn(Offer::getNextDate).setCaption("Дата платежа");
        grid.addColumn(Offer::getNextSum).setCaption("Сумма платежа");
        grid.addColumn(Offer::getBodySum).setCaption("Гашение тела кредита");
        grid.addColumn(Offer::getPercentSum).setCaption("Гашение процентов");

        /*добавление таблицы в список обновляемых таблиц*/
        gridList.add(grid);

        /*обработчик события выбранного объекта*/
        grid.asSingleSelect().addValueChangeListener(valueChangeEvent -> {
            selectedOffer.set(valueChangeEvent.getValue());
            if (valueChangeEvent.getValue() != null) {
                changeButton.setEnabled(true);
                deleteButton.setEnabled(true);
                scheduleButton.setEnabled(true);
            }
            else {
                changeButton.setEnabled(false);
                deleteButton.setEnabled(false);
                scheduleButton.setEnabled(false);
            }
        });

        /*обработчик нажатия кнопок*/
        addButton.addClickListener(clickEvent -> {
            selectedOffer.set(new Offer(-1, Controller.getClientById(0), Controller.getCreditById(0),
                    0, LocalDate.now(), 60, LocalDate.now(), 0, 0, 0));
            getUI().addWindow(new OfferEditorWindow(selectedOffer.get(), MainUI.OPTIONS.ADD));
        });
        changeButton.addClickListener(clickEvent -> {
            getUI().addWindow(new OfferEditorWindow(selectedOffer.get(), MainUI.OPTIONS.UPDATE));
        });
        deleteButton.addClickListener(clickEvent -> {
            Controller.deleteOffer(selectedOffer.get().getId());
            RefreshList();
        });
        scheduleButton.addClickListener(clickEvent -> {
            getUI().addWindow(new PaymentScheduleWindow(selectedOffer.get()));
        });

        /*удаление таблицы из списка при закрытии окна*/
        addCloseListener(closeEvent -> {
            gridList.remove(grid);
        });

        /*установка содержимого*/
        buttonsLayout.addComponent(addButton);
        buttonsLayout.addComponent(changeButton);
        buttonsLayout.addComponent(deleteButton);
        buttonsLayout.addComponent(scheduleButton);
        contentLayout.addComponent(grid, "top: 2%; left: 2%;");
        contentLayout.addComponent(buttonsLayout, "top: 85%; left: 2%;");
        center();
        setContent(contentLayout);
    }

    public static void RefreshList(){
        offerList = DataProvider.ofCollection(Controller.getOfferList());
        gridList.forEach(grid1 -> {
            grid1.setDataProvider(offerList);
        });
    }
}
