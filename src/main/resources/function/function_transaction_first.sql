-- Create an SQL function that takes three parameters (bank account ID, start date-time
-- and end date-time)
-- which returns the sum of money in and money out
-- between the given date range

CREATE OR REPLACE FUNCTION get_sum_of_transactions(
    account_id integer,
    start_date timestamp,
    end_date timestamp
)
    RETURNS TABLE
            (
                entry_sum integer,
                debit_sum integer
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT COALESCE(SUM(CASE WHEN type = 'CREDIT' THEN amount ELSE 0 END), 0) AS entry_sum,
               COALESCE(SUM(CASE WHEN type = 'DEBIT' THEN amount ELSE 0 END), 0)  AS debit_sum
        FROM transaction
        WHERE id_account = account_id
          AND date BETWEEN start_date AND end_date;
END;
$$ LANGUAGE plpgsql;

-- EXAMPLE

SELECT *
FROM get_sum_of_transactions(1, '2023-12-11', '2023-12-12');
