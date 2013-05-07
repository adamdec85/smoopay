insert into Merchant (id, name, emailAddress, telephone, krs, regon, nip, www, enabled) values (1, 'Orlen', 'kontakt@orlen.pl', '+48 24 256 94 18', '0000028860', '610188201', '7740001454', 'www.orlen.pl', true);
insert into Merchant (id, name, emailAddress, telephone, krs, regon, nip, www, enabled) values (2, 'Tesco', 'kontakt@tesco.pl', '+48 12 25 52 100', '0000016108', '011270099', '5261037737', 'www.tesco.pl', true);

insert into MerchantAddress (id, merchant_id, street, postCode, city, country, resident) values (1, 1, 'Chemików 7', '09-411', 'Płock', 'Poland', true);
insert into MerchantAddress (id, merchant_id, street, postCode, city, country, resident) values (2, 2, 'Kapelanka 56', '30-347', 'Kraków', 'Poland', true);

insert into MerchantAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (7, 'Alior', '73 2490 0005 0000 4600 7139 2761', 'PL 73 2490 0005 0000 4600 7139 2761');
insert into MerchantAccountData (id, bankName, cashAccNRB, cashAccIBAN) values (8, 'PEKAO', '55 1140 2004 0000 3202 7402 6118', 'PL 55 1140 2004 0000 3202 7402 6118');

insert into MerchantFinancialBalance (id, primaryAccountBalance, lockedBalance) values (7, 0.0, 0.0);
insert into MerchantFinancialBalance (id, primaryAccountBalance, lockedBalance) values (8, 0.0, 0.0);

insert into MerchantAccount (id, merchant_id, virtualAccNo, accountStatus, currency, openingDate, merchantAccountData_id, merchantFinancialBalance_id) values (1, 1, '1-Alior', 'ACTIVE', 'PLN', now(), 7, 7);
insert into MerchantAccount (id, merchant_id, virtualAccNo, accountStatus, currency, openingDate, merchantAccountData_id, merchantFinancialBalance_id) values (2, 2, '2-PEKAO', 'ACTIVE', 'PLN', now(), 8, 8);

insert into Payment (id, merchant_id, posId, paymentStatus, amount, currency, creationTime) values (1, 1, 1, 'PENDING', 100.0, 'PLN', now());
insert into Payment (id, merchant_id, posId, paymentStatus, amount, currency, creationTime) values (2, 1, 2, 'FILLED', 100.0, 'PLN', now());
insert into Payment (id, merchant_id, posId, paymentStatus, amount, currency, creationTime) values (3, 2, 1, 'PENDING', 100.0, 'PLN', now());
insert into Payment (id, merchant_id, posId, paymentStatus, amount, currency, creationTime) values (4, 2, 2, 'FILLED', 100.0, 'PLN', now());