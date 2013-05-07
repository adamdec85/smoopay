insert into Bank (id, name, emailAddress, telephone, krs, regon, nip, www, swift) values (1, 'Alior Bank', 'kontakt@alior.pl', '+48 12 19 502', '0000305178', '141387142', '1070010731', 'www.alior.pl', 'ALBPPLPW');
insert into Bank (id, name, emailAddress, telephone, krs, regon, nip, www, swift) values (2, 'mBank', 'kontakt@mbank.pl', '+48 022 829 00 00', '0000025237', '001254524', '5260215088', 'www.mbank.pl', 'BREXPLPWMBK');

insert into BankAddress (id, bank_id, street, postCode, city, country, resident) values (5, 1, 'Jerozolimskie 94', '00-807', 'Warszawa', 'Poland', true);
insert into BankAddress (id, bank_id, street, postCode, city, country, resident) values (6, 2, 'Al. Piłsudskiego 3', '90-368', 'Łódź', 'Poland', true);

insert into BankAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (5, 'Alior Bank', '73 2490 0005 0000 4600 7139 2761', 'PL 73 2490 0005 0000 4600 7139 2761');
insert into BankAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (6, 'mBank', '55 1140 2004 0000 3202 7402 6118', 'PL 55 1140 2004 0000 3202 7402 6118');

insert into BankFinancialBalance (id, primaryAccountBalance, lockedBalance) values (5, 0.0, 0.0);
insert into BankFinancialBalance (id, primaryAccountBalance, lockedBalance) values (6, 0.0, 0.0);

insert into BankAccount (id, bank_id, bankAccountData_id, bankFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (1, 1, 5, 5, '1-Alior Bank', 'ACTIVE', 'PLN', now());
insert into BankAccount (id, bank_id, bankAccountData_id, bankFinancialBalance_id, virtualAccNo, accountStatus, currency, openingDate) values (2, 2, 6, 6, '2-mBank', 'ACTIVE', 'PLN', now());