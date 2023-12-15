CREATE TABLE IF NOT EXISTS currency_value
(
    id                      bigserial        NOT NULL,
    id_source_currency      integer          NOT NULL,
    id_destination_currency integer          NOT NULL,
    amount                  double precision not null check ( amount > 0 ),
    date                    timestamp        not null default current_timestamp
);

INSERT INTO currency_value (id_source_currency, id_destination_currency, amount)
SELECT *
FROM (VALUES (1, 2, 1.60),
             (1, 3, 1.70),
             (2, 3, 2)) AS new_currency_value
WHERE NOT EXISTS (SELECT 1 FROM currency_value WHERE currency_value.id_source_currency = new_currency_value.column1);
