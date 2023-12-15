-- Create an SQL function that takes three parameters (bank account ID, start date-time
-- and end date-time)
-- which returns the sum of money in and money out
-- between the given date range

CREATE OR REPLACE FUNCTION get_sum_of_transactions(
    account_id bigint,
    start_date timestamp,
    end_date timestamp
)
    RETURNS TABLE
            (
                entry_sum double precision,
                debit_sum double precision
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT COALESCE(SUM(CASE WHEN type = 'CREDIT' THEN amount ELSE 0 END)::double precision, 0) AS entry_sum,
               COALESCE(SUM(CASE WHEN type = 'DEBIT' THEN amount ELSE 0 END)::double precision, 0)  AS debit_sum
        FROM transaction
        WHERE id_account = account_id
          AND date BETWEEN start_date AND end_date;
END;
$$ LANGUAGE plpgsql;

-- EXAMPLE

select *
from get_sum_of_transactions(1::bigint, '2023-12-11'::timestamp, '2023-12-12'::timestamp);
