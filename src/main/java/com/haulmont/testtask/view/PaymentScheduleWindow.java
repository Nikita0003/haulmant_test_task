package com.haulmont.testtask.view;

import com.haulmont.testtask.model.Offer;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentScheduleWindow extends Window {

    private ListDataProvider<LocalDate> dateList;
    private Grid<LocalDate> grid = new Grid<>();

    public PaymentScheduleWindow(Offer offer) {
        super();
        setHeight("400px");
        setWidth("300px");
        setCaption("График платежей");

        /*расчет всех дат*/
        List<LocalDate> sourceList = new ArrayList<>();
        sourceList.add(offer.getNextDate());
        for(int i = 1; i < offer.getCountPayment(); i++) {
            sourceList.add(sourceList.get(i-1).plusMonths(1));
        }

        dateList = DataProvider.ofCollection(sourceList);
        grid.setDataProvider(dateList);

        grid.setHeight("80%");
        grid.setWidth("98%");
        grid.addColumn(LocalDate::toString).setCaption("Даты всех платежей");

        Button okButton = new Button("Ок");
        okButton.addClickListener(clickEvent -> {
            this.close();
        });

        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();

        contentLayout.addComponent(grid, "top: 2%; left: 2%;");
        contentLayout.addComponent(okButton, "top: 85%; left: 40%;");
        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(contentLayout);
        center();
    }
}
