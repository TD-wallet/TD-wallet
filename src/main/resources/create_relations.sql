-- Didn't found a way to make altertable idempotent too

ALTER TABLE account ADD CONSTRAINT currency_fk FOREIGN KEY (id_currency)
REFERENCES currency (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE account ADD CONSTRAINT account_uq UNIQUE (id_currency);

ALTER TABLE account ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
REFERENCES "user" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE transaction ADD CONSTRAINT account_fk FOREIGN KEY (id_account)
REFERENCES account (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;