package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Client;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;

public class ClientEditorWindow extends Window {

    public ClientEditorWindow(Client client, MainUI.OPTIONS options)
    {
        super();
        setHeight("400px");
        setWidth("300px");

        //заголовок
        if(options == MainUI.OPTIONS.ADD) setCaption("Добавить");
        else setCaption("Изменить");

        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Binder<Client> binder = new Binder<>(Client.class);

        TextField nameTextField = new TextField();
        nameTextField.setCaption("ФИО");
        TextField phoneTextField = new TextField();
        phoneTextField.setCaption("Номер телефона");
        TextField emailTextField = new TextField();
        emailTextField.setCaption("Почта");
        TextField passTextField = new TextField();
        passTextField.setCaption("Номер паспорта");

        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Отмена");
        okButton.setEnabled(false);

        //валидация полей
        binder.forField(nameTextField).withValidator(s -> s.length() <= 64, "Максимальная длина 64 знака")
                .withValidator(s -> s.matches("[-a-zA-ZA-яА-Я]+"),
                        "ФИО не может содержать цифр, пробелов и спецсимволов")
                .asRequired("Обязательное значение")
                .bind("name");

        binder.forField(phoneTextField)
                .withValidator(str -> str.matches("[+]?\\d{7}[-]?\\d{2}[-]?\\d{2}") //в регулярках особо не разбирался, нашел в инете
                                || str.matches("\\d{3}[-]?\\d{2}[-]?\\d{2}"),
                        "Телефонный номер не может содержать букв и пробелов и должен быть формата ХХХ-ХХ-ХХ или +Х-ХХХ-ХХХ-ХХ-ХХ")
                .asRequired("Обязательное значение")
                .bind("phoneNumber");

        binder.forField(emailTextField).withValidator(s -> s.length() <= 64, "Максимальная длина 64 знака")
                //место для регулярки для проверки почты, пока без проверки
                .asRequired("Обязательное значение")
                .bind("email");

        binder.forField(passTextField).withValidator(s -> s.length() <= 64, "Максимальная длина 64 знака")
                .asRequired("Обязательное значение")
                .bind("passNumber");

        binder.addStatusChangeListener(statusChangeEvent -> {
            okButton.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        binder.readBean(client);

        //обработка кнопок
        okButton.addClickListener(clickEvent -> {
            try{
                binder.writeBean(client);
                if(options == MainUI.OPTIONS.ADD) Controller.addClient(client);
                if(options == MainUI.OPTIONS.UPDATE) Controller.updateClient(client);
                ClientsWindow.RefreshList();
                this.close();
            }
            catch (ValidationException e) {
                e.printStackTrace();
            }
        });

        cancelButton.addClickListener(clickEvent -> {
            this.close();
        });

        //добавление компонентов
        buttonsLayout.addComponent(okButton);
        buttonsLayout.addComponent(cancelButton);
        contentLayout.addComponent(nameTextField, "left: 20%; top: 10%");
        contentLayout.addComponent(phoneTextField, "left: 20%; top: 30%");
        contentLayout.addComponent(emailTextField, "left: 20%; top: 50%");
        contentLayout.addComponent(passTextField, "left: 20%; top: 70%");
        contentLayout.addComponent(buttonsLayout, "left: 20%; top: 85%");

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(contentLayout);
        center();
    }
}