CREATE TABLE IF NOT EXISTS "user" (
    id INT PRIMARY KEY ,
    username VARCHAR(50) NOT NULL ,
    email VARCHAR(50) NOT NULL UNIQUE ,
    password VARCHAR(50) NOT NULL
);

INSERT INTO "user" (id, username, email, password)
SELECT * FROM (
                  VALUES
                      (1, 'john.doe', 'john.doe@email.com', 'gg1234'),
                      (2, 'jane.smith', 'jane.smith@email.com', 'ff1234'),
                      (3, 'david pope', 'david.james@email.com', 'edamame1313!')
              ) AS new_users
WHERE NOT EXISTS (SELECT 1 FROM "user" WHERE "user".id = new_users.column1);
