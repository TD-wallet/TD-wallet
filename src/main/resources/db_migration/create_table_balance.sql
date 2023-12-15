CREATE TABLE IF NOT EXISTS balance
(
    id         bigserial        NOT NULL,
    amount     double precision NOT NULL DEFAULT 0,
    date       timestamp        NOT NULL DEFAULT current_timestamp,
    id_account integer          NOT NULL,
    CONSTRAINT balance_pkey PRIMARY KEY (id)
);

INSERT INTO balance (amount, date, id_account)
SELECT *
FROM (VALUES (5000.00, current_timestamp, 3),
             (7500.50, current_timestamp, 3),
             (3000.25, current_timestamp, 3)) AS new_balance
WHERE NOT EXISTS (SELECT 1 FROM balance WHERE balance.id_account = new_balance.column3);
