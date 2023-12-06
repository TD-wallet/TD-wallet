CREATE TABLE IF NOT EXISTS account (
	id integer NOT NULL,
	ref character varying(100) NOT NULL,
	balance double precision NOT NULL DEFAULT 0,
	type varchar NOT NULL,
	id_currency integer NOT NULL,
	id_user integer NOT NULL,
	CONSTRAINT account_pkey PRIMARY KEY (id),
	CONSTRAINT account_account_number_key UNIQUE (ref)
);

INSERT INTO account (id,ref, balance, id_user, id_currency, type)
SELECT * FROM(
               VALUES
                   (1,'ABC123', 5000.00, 1, 3, 'BANK'),
                   (2,'XYZ789', 7500.50, 2, 1, 'CASH'),
                   (3,'DEF456', 3000.25, 3, 2, 'CASH')
           ) AS new_account
WHERE NOT EXISTS (SELECT 1 FROM account WHERE account.id = new_account.column1);
