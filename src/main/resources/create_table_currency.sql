CREATE TABLE IF NOT EXISTS currency(
    id int primary key ,
    currency_code varchar(3) not null,
    currency_name varchar(50) not null
);

INSERT INTO currency (id,currency_code, currency_name)
SELECT * FROM(
                 VALUES
                     (1,'USD', 'US Dollar'),
                     (2,'EUR', 'Euro'),
                     (3,'GBP', 'British Pound')
             ) AS new_currency
WHERE NOT EXISTS (SELECT 1 FROM currency WHERE currency.id = new_currency.column1);
