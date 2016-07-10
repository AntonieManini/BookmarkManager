CREATE TABLE users
(
  nickname character varying(25) NOT NULL,
  email character varying(45) NOT NULL,
  password character varying(1024) NOT NULL,
  enabled boolean NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (email)
);

CREATE TABLE user_roles
(
  user_role_id serial NOT NULL,
  email character varying(45) NOT NULL,
  role character varying(45) NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (user_role_id),
  CONSTRAINT user_roles_email_fkey FOREIGN KEY (email)
      REFERENCES users (email) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT user_roles_role_email_key UNIQUE (role, email)
);

CREATE TABLE persistent_logins
(
  username character varying(64) NOT NULL,
  series character varying(64) NOT NULL,
  token character varying(64) NOT NULL,
  last_used timestamp without time zone NOT NULL,
  CONSTRAINT persistent_logins_pkey PRIMARY KEY (series)
);

CREATE TABLE folder
(
  folder_id serial NOT NULL,
  name character varying(45) NOT NULL,
  parent_id integer,
  username character varying(64) NOT NULL,
  CONSTRAINT folder_pkey PRIMARY KEY (folder_id),
  CONSTRAINT folder_parent_id_fkey FOREIGN KEY (parent_id)
      REFERENCES folder (folder_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT folder_username_fkey FOREIGN KEY (username)
      REFERENCES users (email) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE bookmark
(
  bookmark_id serial NOT NULL,
  description character varying(200) NOT NULL,
  url character varying(1024) NOT NULL,
  folder_id integer NOT NULL,
  CONSTRAINT bookmark_pkey PRIMARY KEY (bookmark_id),
  CONSTRAINT bookmark_folder_id_fkey FOREIGN KEY (folder_id)
      REFERENCES folder (folder_id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)