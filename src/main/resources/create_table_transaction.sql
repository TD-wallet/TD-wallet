CREATE TABLE  IF NOT EXISTS transaction(
    id int primary key ,
    amount double precision not null,
    transaction_date timestamp not null,
    transaction_type varchar(50) not null
);

INSERT INTO transaction (id, amount, transaction_date, transaction_type)
SELECT * FROM(
         VALUES
              (1, 1000.00, CURRENT_TIMESTAMP, 'Deposit'),
              (2, 500.50, CURRENT_TIMESTAMP, 'Withdrawal'),
              (3, 200.25, CURRENT_TIMESTAMP, 'Deposit')
      ) AS new_transaction
WHERE NOT EXISTS (SELECT 1 FROM transaction WHERE transaction.id = new_transaction.column1);
