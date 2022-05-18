CREATE TABLE users
(
  user_id INTEGER PRIMARY KEY,
  first_name text NOT NULL,
  last_name text NOT NULL,
  email text NOT NULL UNIQUE,
  password text,
);

CREATE TABLE Role
(
  user_id INTEGER PRIMARY KEY,
  first_name text NOT NULL,
  last_name text NOT NULL,
  email text NOT NULL UNIQUE,
  password text,
);
