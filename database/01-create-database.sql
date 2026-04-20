-- Execute este script conectado ao banco administrativo do PostgreSQL,
-- por exemplo "postgres", usando o psql.
SELECT 'CREATE DATABASE rede_solidaria WITH ENCODING ''UTF8'''
WHERE NOT EXISTS (
    SELECT 1
    FROM pg_database
    WHERE datname = 'rede_solidaria'
)\gexec
