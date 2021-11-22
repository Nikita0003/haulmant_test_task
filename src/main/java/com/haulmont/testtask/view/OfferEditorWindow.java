package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Credit;
import com.haulmont.testtask.model.Offer;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import java.time.LocalDate;

public class OfferEditorWindow extends Window {

    public OfferEditorWindow(Offer offer, MainUI.OPTIONS options) {
        super();
        setHeight("500px");
        setWidth("500px");

        Button makingPaymentButton = new Button("Произвести платеж"); /*Кнопка совершения платежа*/
        if (offer.getCountPayment() <= 0) {
            makingPaymentButton.setEnabled(false);
        }

        /*заголовок*/
        if(options == MainUI.OPTIONS.ADD) {
            setCaption("Добавить");
            makingPaymentButton.setVisible(false);
        }
        else {
            setCaption("Изменить");
        }

        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout clientAndCreditLayout = new HorizontalLayout();
        HorizontalLayout sumAndDateLayout = new HorizontalLayout();
        HorizontalLayout countPaymentLayout = new HorizontalLayout();

        Binder<Offer> binder = new Binder<>(Offer.class);

        TextField sumTextField = new TextField();
        sumTextField.setCaption("Сумма кредита");
        TextField countPaymentTextField = new TextField();
        countPaymentTextField.setCaption("Количество платежей");

        ComboBox<Client> clientComboBox = new ComboBox<>();
        clientComboBox.setCaption("Клиенты");
        ComboBox<Credit> creditComboBox = new ComboBox<>();
        creditComboBox.setCaption("Кредиты");

        DateField dateOfferDateField = new DateField();
        dateOfferDateField.setCaption("Дата заключения договора");

        /*заполнение комбо боксов*/
        clientComboBox.setItems(Controller.getClientList());
        clientComboBox.setItemCaptionGenerator(client -> client.getName());
        clientComboBox.setEmptySelectionAllowed(true);
        clientComboBox.setEmptySelectionCaption("null");
        creditComboBox.setItems(Controller.getCreditList());
        creditComboBox.setItemCaptionGenerator(credit -> "Лимит: "
                + credit.getLimit() + " Процент: " + credit.getPercent());
        creditComboBox.setEmptySelectionAllowed(true);
        creditComboBox.setEmptySelectionCaption("null");

        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Отмена");
        okButton.setEnabled(false);

        /*валидация полей*/
        binder.forField(sumTextField)
                .withConverter(
                        Double::valueOf,
                        String::valueOf,
                        "Введите число"
                )
                .withValidator(sum -> sum > 0, "Сумма не может быть отрицательной")
                .asRequired("Обязательное значение")
                .bind(Offer::getTotalSum, Offer::setTotalSum);

        binder.forField(countPaymentTextField)
                .withConverter(
                        Integer::valueOf,
                        String::valueOf,
                        "Введите целое число"
                )
                .withValidator(sum -> sum > 0, "Количество платежей не может быть отрицательным")
                .asRequired("Обязательное значение")
                .bind(Offer::getCountPayment, Offer::setCountPayment);

        binder.forField(clientComboBox)
                .asRequired("Обязательное значение")
                .bind("client");

        binder.forField(creditComboBox)
                .asRequired("Обязательное значение")
                .bind("credit");

        binder.forField(dateOfferDateField)
                .asRequired("Обязательное значение")
                .bind("dateOffer");

        binder.addStatusChangeListener(statusChangeEvent -> {
            okButton.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        binder.readBean(offer);

        /*обработка кнопок*/
        okButton.addClickListener(clickEvent -> {
            try {
                binder.writeBean(offer);

                /*Рассчет графика платежей*/
                LocalDate tempDate = offer.getDateOffer().plusMonths(1);         /*дата первого платежа*/
                double tempSum = offer.getTotalSum() * (1 + offer.getCredit().getPercent()/ 100) / offer.getCountPayment();
                double tempBodySum = offer.getTotalSum() / offer.getCountPayment();
                double tempPercentSum = (tempSum - tempBodySum);
                offer.setNextDate(tempDate);
                offer.setNextSum(tempSum);
                offer.setBodySum(tempBodySum);
                offer.setPercentSum(tempPercentSum);

                if (offer.getTotalSum() > offer.getCredit().getLimit()) {
                    VerticalLayout errContentLayout = new VerticalLayout();
                    errContentLayout.addComponent(new Label("Сумма кредита не должна превышать лимит"));
                    Window errWindow = new Window("Ошибка");
                    errWindow.setContent(errContentLayout);
                    errWindow.setModal(true);
                    errWindow.center();
                    getUI().addWindow(errWindow);
                }
                else {
                    if (options == MainUI.OPTIONS.ADD) {
                        Controller.addOffer(offer);
                    }
                    if (options == MainUI.OPTIONS.UPDATE) {
                        Controller.updateOffer(offer);
                    }
                    OffersWindow.RefreshList();
                    this.close();
                }
            }
            catch (ValidationException e) {
                VerticalLayout errContentLayout = new VerticalLayout();
                errContentLayout.addComponent(new Label("Ошибка валидации! Перепроверьте введенные данные!"));
                Window errWindow = new Window("Ошибка");
                errWindow.setContent(errContentLayout);
                errWindow.setModal(true);
                errWindow.center();
                getUI().addWindow(errWindow);
                e.printStackTrace();
            }
        });

        makingPaymentButton.addClickListener(clickEvent -> {
            offer.makingPayment();
            Controller.updateOffer(offer);
            OffersWindow.RefreshList();
            this.close();
        });

        cancelButton.addClickListener(clickEvent -> {
            this.close();
        });

        /*добавление компонентов*/
        clientAndCreditLayout.addComponent(clientComboBox);
        clientAndCreditLayout.addComponent(creditComboBox);

        sumAndDateLayout.addComponent(sumTextField);
        sumAndDateLayout.addComponent(dateOfferDateField);

        countPaymentLayout.addComponent(countPaymentTextField);

        buttonsLayout.addComponent(okButton);
        buttonsLayout.addComponent(cancelButton);

        contentLayout.addComponent(clientAndCreditLayout,"left: 10%; top: 10%");
        contentLayout.addComponent(sumAndDateLayout,"left: 10%; top: 30%");
        contentLayout.addComponent(countPaymentLayout,"left: 10%; top: 50%");
        contentLayout.addComponent(makingPaymentButton,"left: 10%; top: 70%");
        contentLayout.addComponent(buttonsLayout,"left: 10%; top: 90%");

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(contentLayout);
        center();
    }
}
