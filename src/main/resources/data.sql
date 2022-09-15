

INSERT INTO users (id, bank_id,firstname, lastname, email, password, balance)
VALUES (1,1 ,'Jacob', 'Boyd', 'jboy@email.com', 'mdp1',  1000.0),
       (2, 2,'Tenley', 'Boyd', 'tenley@email.com', 'mdp2',  800.0),
       (3,3 ,'John', 'Wick', 'lovemydog@email.com', 'woof',  15000.0),
       (4,4 ,'Frodon', 'delacomté', 'frodon@email.com', 'precieux',  5000.0);

COMMIT;

INSERT INTO bank_account (account_name, iban, bic, user_id)
VALUES ('jacobCompteCourant', 'IBANEVASIONFISCALE', 'BICSTYLO',1),
       ('TenleyCompteCourant', 'IBANDONNELESSOUS', 'BICETCOLEGRAM',2),
       ('jJohnCompteCourant', 'IBANJAIMEMAMBANQUE', 'BICBICETGROMINET',3),
       ('FrodonCompteCourant', 'IBANJAIMEPASMAMBANQUE', 'BICBICETGROMINET',4);
COMMIT;

INSERT INTO relation (id, buddy, owner)
VALUES (1,1,2 ),
       (2,3,2 ),
       (3, 1,3),
       (4, 4,3);
COMMIT;

