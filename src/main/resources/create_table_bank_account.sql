CREATE TABLE IF NOT EXISTS  bank_account(
    id int primary key ,
    account_number varchar(100) not null unique,
    balance double precision not null
);

INSERT INTO bank_account (id,account_number, balance)
SELECT * FROM(
               VALUES
                   (1,'ABC123', 5000.00),
                   (2,'XYZ789', 7500.50),
                   (3,'DEF456', 3000.25)
           ) AS new_bank_account
WHERE NOT EXISTS (SELECT 1 FROM bank_account WHERE bank_account.id = new_bank_account.column1);
