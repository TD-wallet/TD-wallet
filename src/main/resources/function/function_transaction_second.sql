-- Create  SQL function that takes three parameters (bank account ID, start datetime
-- date-time and end-date-time) that returns the sum of the amounts in each
-- amounts for each category, between the given date range. In particular, if certain categories
--  are not associated with any transactions, then return 0.

CREATE OR REPLACE FUNCTION get_sum_of_transactions_for_each_category(
    account_id bigint,
    start_date timestamp,
    end_date timestamp
)
    RETURNS TABLE
            (
                category_name varchar(60),
                total_amount  double precision
            )
AS
$$
BEGIN
    RETURN QUERY
        SELECT c.name         AS category_name,
               Case
                   WHEN SUM(t.amount)::double precision IS NOT NULL THEN SUM(t.amount)::double precision
                   ELSE 0 END AS total_amount
        FROM category c
                 LEFT JOIN
             transaction t ON c.id = t.id_category
                 AND t.date BETWEEN start_date
                                  AND end_date
                 AND t.id_account = account_id
        GROUP BY c.name;
END;
$$ LANGUAGE plpgsql;

-- EXAMPLE

SELECT *
FROM get_sum_of_transactions_for_each_category(1, '2023-12-11', '2023-12-12');