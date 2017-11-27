CREATE DATABASE mmf_backoffice_db;
SELECT "Database mmf_backoffice_db created" as "";
GRANT ALL PRIVILEGES ON mmf_backoffice_db.* TO 'mmfuser';
FLUSH PRIVILEGES;
SELECT "Privilege granted" as "";