INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (0, 'Belyaev Nikita', '89170357500', 'saper.yaya@ya.ru', '367655440');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (1, 'Morozov Viktor', '89170357501', 'saper1.yaya@ya.ru', '367655441');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (2, 'Dyatlov Yurii', '89170357502', 'saper2.yaya@ya.ru', '367655442');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (3, 'Vasilkina Ludmila', '89170357503', 'saper3.yaya@ya.ru', '367655443');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (4, 'Bah Alex', '89170357504', 'saper4.yaya@ya.ru', '367655444');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (5, 'Artamonov Andrey', '89170357505', 'saper5.yaya@ya.ru', '367655445');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (6, 'Krutov Mihail', '89170357506', 'saper6.yaya@ya.ru', '367655446');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (7, 'Tarasov Vadim', '89170357507', 'saper7.yaya@ya.ru', '367655447');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (8, 'Yarmov Roman', '89170357508', 'saper8.yaya@ya.ru', '367655448');
INSERT INTO Client (Client_ID, Name, PhoneNumber, Email, PassNumber) VALUES (9, 'Bykov Evgeniy', '89170357509', 'saper9.yaya@ya.ru', '367655449');

INSERT INTO Credit (Credit_ID, Limit, Percent) VALUES (0, 700000, 7);
INSERT INTO Credit (Credit_ID, Limit, Percent) VALUES (1, 1000000, 5);
INSERT INTO Credit (Credit_ID, Limit, Percent) VALUES (2, 200000, 12);
INSERT INTO Credit (Credit_ID, Limit, Percent) VALUES (3, 10000000, 10);
INSERT INTO Credit (Credit_ID, Limit, Percent) VALUES (4, 600000, 8);

INSERT INTO Bank (Record_ID, Bank_ID, Client, Credit) VALUES (0, 0, 0, 0);

INSERT INTO Offer (Offer_ID, Client, Credit, TotalSum, DateOffer, CountPayment, NextDate, NextSum, BodySum, PercentSum) VALUES (0, 0, 0, 600000, '2020-04-21', 60, '2020-05-21', 10700, 10000, 700);