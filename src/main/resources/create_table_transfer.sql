CREATE TABLE IF NOT EXISTS transfer
(
    id          serial primary key,
    id_debited  integer          not null,
    id_credited integer          not null,
    date        timestamp        not null default current_timestamp,
    amount      double precision not null check ( amount > 0 )
);

INSERT INTO transfer (id_debited, id_credited, amount)
SELECT * FROM (VALUES (1, 3, 300)) AS new_transfer
WHERE NOT EXISTS (SELECT 1 FROM transfer WHERE transfer.amount = new_transfer.column3);
