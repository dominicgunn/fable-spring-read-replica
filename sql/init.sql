-- Create our users.
CREATE ROLE fable_read WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION PASSWORD 'md5010b66d00c67730d26587f13212502f4';
CREATE ROLE fable_write WITH NOSUPERUSER INHERIT NOCREATEROLE NOCREATEDB LOGIN NOREPLICATION PASSWORD 'md5e9b324e24230fe0105eb9d29dac29690';

-- Enable statement logging for our users.
ALTER ROLE fable_read SET log_statement = 'all';
ALTER ROLE fable_write SET log_statement = 'all';

-- Create Books table.
CREATE TABLE books (
  id INTEGER NOT NULL,
  author VARCHAR(254) NOT NULL,
  CONSTRAINT pk_id PRIMARY KEY (id)
);

-- Create grants for our users on the books table.
GRANT SELECT ON TABLE books TO fable_read;
GRANT SELECT, INSERT, DELETE, UPDATE ON TABLE books TO fable_write;

-- Create a sequence for Hibernate.
CREATE SEQUENCE hibernate_sequence START 1;

-- Grant permissions to fable_write on the Hibernate Sequence.
GRANT USAGE, SELECT ON SEQUENCE hibernate_sequence TO fable_write;
