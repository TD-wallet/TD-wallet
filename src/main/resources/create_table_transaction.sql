-- Didn't found a way to make type creation idempotent.
CREATE TYPE "TRANSACTION_TYPE" AS
ENUM ('DEBIT','CREDIT');

CREATE TABLE IF NOT EXISTS transaction (
	id integer NOT NULL,
	amount integer NOT NULL,
    label varchar(70) NOT NULL DEFAULT 'Default',
	date timestamp NOT NULL DEFAULT current_timestamp,
	type "TRANSACTION_TYPE" NOT NULL DEFAULT 'DEBIT'::"TRANSACTION_TYPE",
	id_account integer NOT NULL,
	CONSTRAINT transaction_pkey PRIMARY KEY (id)
);

INSERT INTO transaction (id, amount, label, date, type, id_account)
SELECT * FROM(
         VALUES
              (1, 1000.00, 'Default', CURRENT_TIMESTAMP, 'CREDIT'::"TRANSACTION_TYPE", 2),
              (2, 500.50, 'Food', CURRENT_TIMESTAMP, 'DEBIT'::"TRANSACTION_TYPE", 2),
              (3, 200.25, 'Sports', CURRENT_TIMESTAMP, 'CREDIT'::"TRANSACTION_TYPE", 2)
      ) AS new_transaction
WHERE NOT EXISTS (SELECT 1 FROM transaction WHERE transaction.id = new_transaction.column1);
