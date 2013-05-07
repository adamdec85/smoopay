
    alter table BankAccount 
        drop constraint FK305B88316DA39B4A;

    alter table BankAccount 
        drop constraint FK305B8831C8167CB8;

    alter table BankAccount 
        drop constraint FK305B8831DC8B6E9B;

    alter table BankAddress 
        drop constraint FK321F99F86DA39B4A;

    alter table ClientAccount 
        drop constraint FK27F93C21DFBB4FB;

    alter table ClientAccount 
        drop constraint FK27F93C26F869798;

    alter table ClientAccount 
        drop constraint FK27F93C21FB443FB;

    alter table ClientAddress 
        drop constraint FK443A5891DFBB4FB;

    alter table ClientPayment 
        drop constraint FK19C83B9B1DFBB4FB;

    alter table MerchantAccount 
        drop constraint FK44C3FF25A495E2BE;

    alter table MerchantAccount 
        drop constraint FK44C3FF25BA0E4938;

    alter table MerchantAccount 
        drop constraint FK44C3FF25AA7DAA9B;

    alter table MerchantAddress 
        drop constraint FK468810ECA495E2BE;

    alter table Payment 
        drop constraint FK3454C9E6A495E2BE;

    drop table if exists Bank cascade;

    drop table if exists BankAccount cascade;

    drop table if exists BankAccountData cascade;

    drop table if exists BankAddress cascade;

    drop table if exists BankFinancialBalance cascade;

    drop table if exists BankRegistry cascade;

    drop table if exists Client cascade;

    drop table if exists ClientAccount cascade;

    drop table if exists ClientAccountData cascade;

    drop table if exists ClientAddress cascade;

    drop table if exists ClientFinancialBalance cascade;

    drop table if exists ClientPayment cascade;

    drop table if exists Merchant cascade;

    drop table if exists MerchantAccount cascade;

    drop table if exists MerchantAccountData cascade;

    drop table if exists MerchantAddress cascade;

    drop table if exists MerchantFinancialBalance cascade;

    drop table if exists Payment cascade;

    drop sequence hibernate_sequence;

    create table Bank (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        krs varchar(255),
        nip varchar(255),
        regon varchar(255),
        emailAddress varchar(255),
        name varchar(255),
        swift varchar(255),
        telephone varchar(255),
        www varchar(255),
        primary key (id)
    );

    create table BankAccount (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        accountStatus varchar(255),
        currency varchar(255),
        expiryDate timestamp,
        openingDate timestamp,
        virtualAccNo varchar(255),
        bank_id int8,
        bankAccountData_id int8,
        bankFinancialBalance_id int8,
        primary key (id)
    );

    create table BankAccountData (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        bankName varchar(255),
        cashAccIBAN varchar(255),
        cashAccNRB varchar(255),
        primary key (id)
    );

    create table BankAddress (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        city varchar(255),
        country varchar(255),
        postCode varchar(255),
        resident boolean not null,
        street varchar(255),
        bank_id int8,
        primary key (id)
    );

    create table BankFinancialBalance (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        lockedBalance numeric(19, 2),
        primaryAccountBalance numeric(19, 2),
        primary key (id)
    );

    create table BankRegistry (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        bankId int8,
        bankName varchar(255),
        primary key (id)
    );

    create table Client (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        login varchar(255),
        password varchar(255),
        role varchar(255),
        status varchar(255),
        emailAddress varchar(255),
        firstName varchar(255),
        lastName varchar(255),
        pesel int8,
        primary key (id)
    );

    create table ClientAccount (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        accountStatus varchar(255),
        currency varchar(255),
        expiryDate timestamp,
        openingDate timestamp,
        virtualAccNo varchar(255),
        client_id int8,
        clientAccountData_id int8,
        clientFinancialBalance_id int8,
        primary key (id)
    );

    create table ClientAccountData (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        bankName varchar(255),
        cashAccIBAN varchar(255),
        cashAccNRB varchar(255),
        primary key (id)
    );

    create table ClientAddress (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        city varchar(255),
        country varchar(255),
        postCode varchar(255),
        resident boolean not null,
        street varchar(255),
        client_id int8,
        primary key (id)
    );

    create table ClientFinancialBalance (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        lockedBalance numeric(19, 2),
        primaryAccountBalance numeric(19, 2),
        primary key (id)
    );

    create table ClientPayment (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        amount numeric(19, 2),
        creationTime timestamp,
        currency varchar(255),
        paymentStatus varchar(255),
        client_id int8,
        primary key (id)
    );

    create table Merchant (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        krs varchar(255),
        nip varchar(255),
        regon varchar(255),
        emailAddress varchar(255),
        enabled boolean not null,
        name varchar(255),
        telephone varchar(255),
        www varchar(255),
        primary key (id)
    );

    create table MerchantAccount (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        accountStatus varchar(255),
        currency varchar(255),
        expiryDate timestamp,
        openingDate timestamp,
        virtualAccNo varchar(255),
        merchant_id int8,
        merchantAccountData_id int8,
        merchantFinancialBalance_id int8,
        primary key (id)
    );

    create table MerchantAccountData (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        bankName varchar(255),
        cashAccIBAN varchar(255),
        cashAccNRB varchar(255),
        primary key (id)
    );

    create table MerchantAddress (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        city varchar(255),
        country varchar(255),
        postCode varchar(255),
        resident boolean not null,
        street varchar(255),
        merchant_id int8,
        primary key (id)
    );

    create table MerchantFinancialBalance (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        lockedBalance numeric(19, 2),
        primaryAccountBalance numeric(19, 2),
        primary key (id)
    );

    create table Payment (
        id int8 not null,
        createdBy varchar(255),
        createdDate timestamp,
        lastModifiedDate timestamp,
        modifiedBy varchar(255),
        amount numeric(19, 2),
        creationTime timestamp,
        currency varchar(255),
        paymentStatus varchar(255),
        modifyTime date,
        posId int8,
        merchant_id int8,
        primary key (id)
    );

    create index bankNameIndex on Bank (name);

    create index bankSwiftIndex on Bank (swift);

    create index bankVirtualAccNoIndex on BankAccount (virtualAccNo);

    create index bankAccStatusIndex on BankAccount (accountStatus);

    create index bankAccCurrencyIndex on BankAccount (currency);

    create index bankAccOpeningDateIndex on BankAccount (openingDate);

    alter table BankAccount 
        add constraint FK305B88316DA39B4A 
        foreign key (bank_id) 
        references Bank;

    alter table BankAccount 
        add constraint FK305B8831C8167CB8 
        foreign key (bankAccountData_id) 
        references BankAccountData;

    alter table BankAccount 
        add constraint FK305B8831DC8B6E9B 
        foreign key (bankFinancialBalance_id) 
        references BankFinancialBalance;

    create index bankAccBankNameIndex on BankAccountData (bankName);

    alter table BankAddress 
        add constraint FK321F99F86DA39B4A 
        foreign key (bank_id) 
        references Bank;

    create index bankIdIndex on BankRegistry (bankId);

    create index clientLoginIndex on Client (login);

    create index clientFirstNameIndex on Client (firstName);

    create index clientLastNameIndex on Client (lastName);

    create index clientPeselIndex on Client (pesel);

    create index clientVirtualAccNoIndex on ClientAccount (virtualAccNo);

    create index clientAccStatusIndex on ClientAccount (accountStatus);

    create index clientAccCurrencyIndex on ClientAccount (currency);

    create index clientAccOpeningDateIndex on ClientAccount (openingDate);

    alter table ClientAccount 
        add constraint FK27F93C21DFBB4FB 
        foreign key (client_id) 
        references Client;

    alter table ClientAccount 
        add constraint FK27F93C26F869798 
        foreign key (clientAccountData_id) 
        references ClientAccountData;

    alter table ClientAccount 
        add constraint FK27F93C21FB443FB 
        foreign key (clientFinancialBalance_id) 
        references ClientFinancialBalance;

    create index clientAccBankNameIndex on ClientAccountData (bankName);

    alter table ClientAddress 
        add constraint FK443A5891DFBB4FB 
        foreign key (client_id) 
        references Client;

    create index clientIdIndex on ClientPayment (client_id);

    create index clientPaymentStatusIndex on ClientPayment (paymentStatus);

    alter table ClientPayment 
        add constraint FK19C83B9B1DFBB4FB 
        foreign key (client_id) 
        references Client;

    alter table Merchant 
        add constraint uc_Merchant_1 unique (name);

    create index merchantVirtualAccNoIndex on MerchantAccount (virtualAccNo);

    create index merchantAccStatusIndex on MerchantAccount (accountStatus);

    create index merchantAccCurrencyIndex on MerchantAccount (currency);

    create index merchantAccOpeningDateIndex on MerchantAccount (openingDate);

    alter table MerchantAccount 
        add constraint FK44C3FF25A495E2BE 
        foreign key (merchant_id) 
        references Merchant;

    alter table MerchantAccount 
        add constraint FK44C3FF25BA0E4938 
        foreign key (merchantAccountData_id) 
        references MerchantAccountData;

    alter table MerchantAccount 
        add constraint FK44C3FF25AA7DAA9B 
        foreign key (merchantFinancialBalance_id) 
        references MerchantFinancialBalance;

    create index merchantAccBankNameIndex on MerchantAccountData (bankName);

    alter table MerchantAddress 
        add constraint FK468810ECA495E2BE 
        foreign key (merchant_id) 
        references Merchant;

    create index merchantIdIndex on Payment (merchant_id);

    create index paymentStatusIndex on Payment (paymentStatus);

    create index posIdIndex on Payment (posId);

    alter table Payment 
        add constraint FK3454C9E6A495E2BE 
        foreign key (merchant_id) 
        references Merchant;

    create sequence hibernate_sequence;
