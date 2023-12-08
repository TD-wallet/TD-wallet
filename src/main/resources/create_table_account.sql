CREATE TABLE IF NOT EXISTS account (
	id serial NOT NULL,
    name varchar NOT NULL,
	ref character varying(100) NOT NULL,
	type varchar NOT NULL,
	id_currency integer NOT NULL,
	id_user integer NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id),
	CONSTRAINT account_account_number_key UNIQUE (ref)
);

INSERT INTO account (name, ref, id_user, id_currency, type)
SELECT * FROM(
               VALUES
                   ('Hello Bank', 'ABC123', 1, 3, 'BANK'),
                   ('Mony Wallet', 'XYZ789', 2, 1, 'CASH'),
                   ('Cashew', 'DEF456', 3, 2, 'CASH')
           ) AS new_account
WHERE NOT EXISTS (SELECT 1 FROM account WHERE account.ref = new_account.column2);
