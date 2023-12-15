CREATE TABLE IF NOT EXISTS currency
(
    id     serial                NOT NULL,
    code   character varying(3)  NOT NULL,
    name   character varying(70) NOT NULL,
    symbol varchar(3),
    CONSTRAINT currency_pkey PRIMARY KEY (id),
    CONSTRAINT uq_code UNIQUE (code)
);

INSERT INTO currency (code, name, symbol)
SELECT *
FROM (VALUES ('USD', 'US Dollar', '$'),
             ('EUR', 'Euro', '€'),
             ('GBP', 'British Pound', '£')) AS new_currency
WHERE NOT EXISTS (SELECT 1 FROM currency WHERE currency.code = new_currency.column1);
