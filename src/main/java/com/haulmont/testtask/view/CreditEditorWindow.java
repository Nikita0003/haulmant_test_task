package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.Controller;
import com.haulmont.testtask.model.Credit;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;

public class CreditEditorWindow extends Window {

    public CreditEditorWindow(Credit credit, MainUI.OPTIONS options) {
        super();
        setHeight("400px");
        setWidth("300px");

        /*заголовок*/
        if(options == MainUI.OPTIONS.ADD) setCaption("Добавить");
        else setCaption("Изменить");

        AbsoluteLayout contentLayout = new AbsoluteLayout();
        contentLayout.setSizeFull();
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        Binder<Credit> binder = new Binder<>(Credit.class);

        TextField limitTextField = new TextField();
        limitTextField.setCaption("Лимит");
        TextField percentTextField = new TextField();
        percentTextField.setCaption("Процентная ставка");

        Button okButton = new Button("Ок");
        Button cancelButton = new Button("Отмена");
        okButton.setEnabled(false);

        /*валидация полей*/
        binder.forField(limitTextField)
                .withConverter(
                        Double::valueOf,
                        String::valueOf,
                        "Введите целое число"
                )
                .withValidator(sum -> sum > 0, "Лимит не может быть отрицательным")
                .asRequired("Обязательное значение")
                .bind("limit");

        binder.forField(percentTextField)
                .withConverter(
                        Double::valueOf,
                        String::valueOf,
                        "Введите целое число"
                )
                .withValidator(sum -> sum > 0, "Процентная ставка не может быть отрицательной")
                .asRequired("Обязательное значение")
                .bind("percent");

        binder.addStatusChangeListener(statusChangeEvent -> {
            okButton.setEnabled(!statusChangeEvent.hasValidationErrors());
        });

        binder.readBean(credit);

        /*обработка кнопок*/
        okButton.addClickListener(clickEvent -> {
            try {
                binder.writeBean(credit);
                if(options == MainUI.OPTIONS.ADD) {
                    Controller.addCredit(credit);
                }
                if(options == MainUI.OPTIONS.UPDATE) {
                    Controller.updateCredit(credit);
                }
                CreditsWindow.RefreshList();
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
        contentLayout.addComponent(limitTextField,"left: 20%; top: 10%");
        contentLayout.addComponent(percentTextField,"left: 20%; top: 30%");
        contentLayout.addComponent(buttonsLayout,"left: 20%; top: 85%");

        setModal(true);
        setResizable(false);
        setClosable(false);
        setContent(contentLayout);
        center();
    }
}