CREATE TABLE IF NOT EXISTS "user" (
	id serial NOT NULL,
	username character varying(50) NOT NULL,
	email character varying(70) NOT NULL,
	password character varying(50) NOT NULL,
	CONSTRAINT user_pkey PRIMARY KEY (id),
	CONSTRAINT user_email_key UNIQUE (email)
);

INSERT INTO "user" (username, email, password)
SELECT * FROM (
                  VALUES
                      ('john.doe', 'john.doe@email.com', 'gg1234'),
                      ('jane.smith', 'jane.smith@email.com', 'ff1234'),
                      ('david pope', 'david.james@email.com', 'edamame1313!')
              ) AS new_users
WHERE NOT EXISTS (SELECT 1 FROM "user" WHERE "user".email = new_users.column2);
