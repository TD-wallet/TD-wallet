CREATE TABLE IF NOT EXISTS balance
(
    id         serial           NOT NULL,
    amount     double precision NOT NULL DEFAULT 0,
    date       timestamp        NOT NULL DEFAULT current_timestamp,
    id_account integer          NOT NULL,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT account_account_number_key UNIQUE (ref)
);

INSERT INTO balance (amount, date, id_account)
SELECT *
FROM (VALUES (5000.00, current_timestamp, 3),
             (7500.50, current_timestamp, 3),
             (3000.25, current_timestamp, 3)) AS new_balance
WHERE NOT EXISTS (SELECT 1 FROM balance WHERE balance.id = new_balance.column1);
