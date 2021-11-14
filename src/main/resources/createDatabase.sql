CREATE TABLE IF NOT EXISTS Client (
    Client_ID bigint IDENTITY Primary Key not null,
    Name varchar(64) not null,
    PhoneNumber varchar(64) not null,
    Email varchar(64) not null,
    PassNumber varchar(64) not null
);

CREATE TABLE IF NOT EXISTS Credit (
    Credit_ID bigint IDENTITY Primary Key not null,
    Limit double not null,
    Percent double not null
);

CREATE TABLE IF NOT EXISTS Bank (
    Record_ID bigint IDENTITY Primary Key not null,
    Bank_ID bigint not null,
    Client bigint REFERENCES Client (Client_ID),
    Credit bigint REFERENCES Credit (Credit_ID)
);

CREATE TABLE IF NOT EXISTS Offer (
    Offer_ID bigint IDENTITY Primary Key not null,
    Client bigint REFERENCES Client (Client_ID),
    Credit bigint REFERENCES Credit (Credit_ID),
    TotalSum double not null,
    DateOffer date not null,
    CountPayment int not null,
    NextDate date not null,
    NextSum double not null,
    BodySum double not null,
    PercentSum double not null
);