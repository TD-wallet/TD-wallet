CREATE TABLE IF NOT EXISTS currency (
	id integer NOT NULL,
	code character varying(3) NOT NULL,
	name character varying(70) NOT NULL,
	symbol varchar(3),
	CONSTRAINT currency_pkey PRIMARY KEY (id),
	CONSTRAINT uq_code UNIQUE (code)
);

INSERT INTO currency (id, code, name, symbol)
SELECT * FROM(
                 VALUES
                     (1,'USD', 'US Dollar', '$'),
                     (2,'EUR', 'Euro', '€'),
                     (3,'GBP', 'British Pound', '£')
             ) AS new_currency
WHERE NOT EXISTS (SELECT 1 FROM currency WHERE currency.id = new_currency.column1);
