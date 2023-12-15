-- Didn't found a way to make altertable idempotent too

ALTER TABLE account
    ADD CONSTRAINT currency_fk FOREIGN KEY (id_currency)
        REFERENCES currency (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE account
    ADD CONSTRAINT account_uq UNIQUE (id_currency);

ALTER TABLE account
    ADD CONSTRAINT user_fk FOREIGN KEY (id_user)
        REFERENCES "user" (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE transaction
    ADD CONSTRAINT account_transaction_fk FOREIGN KEY (id_account)
        REFERENCES account (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE balance
    ADD CONSTRAINT balance_fk FOREIGN KEY (id_account)
        REFERENCES account (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE transfer
    ADD CONSTRAINT debited_transfer_fk FOREIGN KEY (id_debited)
        REFERENCES account (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE transfer
    ADD CONSTRAINT credited_transfer_fk FOREIGN KEY (id_credited)
        REFERENCES account (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE currency_value
    ADD CONSTRAINT source_fk FOREIGN KEY (id_source_currency)
        REFERENCES currency (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE currency_value
    ADD CONSTRAINT destination_fk FOREIGN KEY (id_destination_currency)
        REFERENCES currency (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE transaction
    ADD CONSTRAINT category_fk FOREIGN KEY (id_category)
        REFERENCES category (id) MATCH FULL
        ON DELETE RESTRICT ON UPDATE CASCADE;