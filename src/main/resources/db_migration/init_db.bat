psql -U postgres -f create_database.sql
for /R %%i IN (create_table_*) DO psql -U postgres -d wallet -f %%i
psql -U postgres -f create_relations.sql -d wallet