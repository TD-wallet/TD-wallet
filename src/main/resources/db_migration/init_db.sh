#!/bin/bash

psql -U postgres -f create_database.sql
find -name "create_table_*" -exec psql -U postgres -d wallet -f {} \;
psql -U postgres -f create_relations.sql -d wallet