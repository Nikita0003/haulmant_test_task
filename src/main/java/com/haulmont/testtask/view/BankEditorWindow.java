package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Bank;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Credit;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;

public class BankEditorWindow extends Window {

    public BankEditorWindow(Bank bank, MainUI.OPTIONS options)
    {
        super();
        setHeight("300px");
        setWidth("500px");

        if(options == MainUI.OPTIONS.ADD) setCaption("Добавить");
        else setCaption("Изменить");

        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout choiceLayout = new HorizontalLayout();

        Binder<Bank> binder = new Binder<>(Bank.class);

        ComboBox<Client> clientComboBox = new ComboBox<>();
        clientComboBox.setCaption("Клиенты");
        ComboBox<Credit> creditComboBox = new ComboBox<>();
        creditComboBox.setCaption("Кредиты");
        TextField idBankTextField = new TextField();
        idBankTextField.setCaption("Номер банка");

        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Отмена");
        okButton.setEnabled(false);

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

        binder.forField(clientComboBox)
                .asRequired("Обязательное значение")
                .bind("client");

        binder.forField(creditComboBox)
                .asRequired("Обязательное значение")
                .bind("credit");

        binder.forField(idBankTextField)
                .withConverter(
                        Long::valueOf,
                        String::valueOf,
                        "Введите целое число"
                )
                .asRequired("Обязательное значение")
                .bind("bankId");

        binder.addStatusChangeListener(statusChangeEvent -> {
            okButton.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        binder.readBean(bank);

        okButton.addClickListener(clickEvent -> {
            try {
                binder.writeBean(bank);
                if(options == MainUI.OPTIONS.ADD) {
                    Controller.addBank(bank);
                }
                if(options == MainUI.OPTIONS.UPDATE) {
                    Controller.updateBank(bank);
                }
                BanksWindow.RefreshList();
                this.close();
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

        cancelButton.addClickListener(clickEvent -> {
            this.close();
        });

        /*добавление компонентов*/
        buttonsLayout.addComponent(okButton);
        buttonsLayout.addComponent(cancelButton);

        choiceLayout.addComponent(clientComboBox);
        choiceLayout.addComponent(creditComboBox);

        contentLayout.addComponent(choiceLayout,"left: 10%; top: 10%");
        contentLayout.addComponent(idBankTextField, "left: 10%; top: 50%");
        contentLayout.addComponent(buttonsLayout,"left: 10%; top: 80%");

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(contentLayout);
        center();
    }
}
