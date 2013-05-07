insert into Client (id, login, password, role, status, emailAddress, firstName, lastName, pesel) values (1, 'adam1_login', '214ae071decce13324cf2f6138717b38', 'ROLE_NORMAL', 'ACTIVE', 'adam.dec@smoopay.com', 'Adam', 'Dec', 85122413455);
insert into Client (id, login, password, role, status, emailAddress, firstName, lastName, pesel) values (2, 'adam2_login', '1ebf194c152894c22afdae46a4142f73', 'ROLE_NORMAL', 'ROLE_BLOCKED', 'adam.latuszek@smoopay.com', 'Adam', 'Latuszek', 85042413125);
insert into Client (id, login, password, role, status, emailAddress, firstName, lastName, pesel) values (3, 'witold1_login', '7e177c03e5db49682aec774632c27563', 'ROLE_NORMAL', 'ROLE_BLOCKED', 'witold.slizowski@smoopay.com', 'Witold', 'Slizowski', 85082416755);

insert into ClientAddress (id, client_id, street, postCode, city, country, resident) values (1, 1, 'M.C Sklodowskiej 5/6', '37-300', 'Lezajsk', 'Poland', true);
insert into ClientAddress (id, client_id, street, postCode, city, country, resident) values (2, 1, 'Weryhy Darowskiego', '30-198', 'Krakow', 'Poland', false);
insert into ClientAddress (id, client_id, street, postCode, city, country, resident) values (3, 2, 'Jakas ulica 1/2', '30-167', 'Krakow', 'Poland', false);
insert into ClientAddress (id, client_id, street, postCode, city, country, resident) values (4, 3, 'Jakas ulica 1/2', '30-678', 'Krakow', 'Poland', false);

insert into ClientAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (1, 'Alior Bank', '12 2490 5678 1234 5678 9012 3456', 'PL 12 1234 5678 1234 5678 9012 3456');
insert into ClientAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (2, 'PKO', '12 1240 5678 1234 5678 9012 3456', 'EN 12 1234 5678 1234 5678 9012 3456');
insert into ClientAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (3, 'BZ WBK', '11 1090 5678 1234 5678 9012 3456', 'PL 11 1234 5678 1234 5678 9012 3456');
insert into ClientAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (4, 'Alior Bank', '10 2490 5678 1234 5678 9012 3456', 'PL 10 1234 5678 1234 5678 9012 3456');

insert into ClientFinancialBalance (id, primaryAccountBalance, lockedBalance) values (1, 100.0, 0.0);
insert into ClientFinancialBalance (id, primaryAccountBalance, lockedBalance) values (2, 100.0, 0.0);
insert into ClientFinancialBalance (id, primaryAccountBalance, lockedBalance) values (3, 100.0, 0.0);
insert into ClientFinancialBalance (id, primaryAccountBalance, lockedBalance) values (4, 100.0, 0.0);

insert into ClientAccount (id, client_id, clientAccountData_id, clientFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (1, 1, 1, 1, '1-Alior Bank', 'ACTIVE', 'PLN', now());
insert into ClientAccount (id, client_id, clientAccountData_id, clientFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (2, 1, 2, 2, '2-PKO', 'ACTIVE', 'CHF', now());
insert into ClientAccount (id, client_id, clientAccountData_id, clientFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (3, 2, 3, 3, '3-BZ WBK', 'ACTIVE', 'PLN', now());
insert into ClientAccount (id, client_id, clientAccountData_id, clientFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (4, 3, 4, 4, '4-Alior Bank', 'ACTIVE', 'PLN', now());

insert into ClientPayment (id, client_id, paymentStatus, amount, currency, creationTime) values (1, 1, 'FILLED', 10.0, 'PLN', now());
insert into ClientPayment (id, client_id, paymentStatus, amount, currency, creationTime) values (2, 1, 'FILLED', 200.0, 'PLN', now());
insert into ClientPayment (id, client_id, paymentStatus, amount, currency, creationTime) values (3, 1, 'FILLED', 300.30, 'USD', now());
insert into ClientPayment (id, client_id, paymentStatus, amount, currency, creationTime) values (4, 2, 'FILLED', 100.0, 'PLN', now());
insert into ClientPayment (id, client_id, paymentStatus, amount, currency, creationTime) values (5, 3, 'FILLED', 100.0, 'PLN', now());