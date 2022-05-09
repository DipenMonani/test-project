
CREATE TABLE public.roles (
id bigserial NOT NULL,
role_name varchar(50) NOT NULL,
description varchar(1000) NOT NULL,
created_by varchar(255) NULL,
created_date timestamp NULL,
last_modified_by varchar(255) NULL,
last_modified_date timestamp NULL,
CONSTRAINT roles_pkey PRIMARY KEY (id)
);
CREATE TABLE public.users (
id bigserial NOT NULL,
email varchar(100) NOT NULL,
password varchar(500) NOT NULL,
first_name varchar(100) NOT NULL,
last_name varchar(50) NULL,
created_by varchar(255) NULL,
created_date timestamp NULL,
last_modified_by varchar(255) NULL,
last_modified_date timestamp NULL,
CONSTRAINT user_pkey PRIMARY KEY (id)
);
CREATE TABLE users_roles (
  user_id int8 NOT NULL,
  role_id int8 NOT NULL,
  CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES roles (id),
  CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO roles (id,role_name,description) VALUES
(1,'ROLE_ADMIN','Admin User');

INSERT INTO roles (id,role_name,description) VALUES
(2,'ROLE_USER','Normal User');

INSERT INTO users (email,"password",first_name,last_name) VALUES
('admin@admin.com','$2a$10$exflNymfdqidvo4sGmMzL.R3YcK5SZ5uBeF6LW/25NtTcjYi7sUm6','Admin','Admin');

insert into users_roles(user_id,role_id) values(1,1);

CREATE TABLE public.properties (
id bigserial NOT NULL,
created_by varchar(255) NULL,
created_date timestamp NULL,
last_modified_by varchar(255) NULL,
last_modified_date timestamp NULL,
user_id int8 references users(id) Null,
room_number int NULL,
square numeric NULL,
description varchar(1000) NULL,
latitude numeric NULL,
longitude numeric NULL,
address_1 varchar(256) NULL,
address_2 varchar(256) NULL,
city varchar(256) NULL,
country varchar(256) NULL,
zip varchar(256) NULL,
CONSTRAINT properties_pkey PRIMARY KEY (id)
);

CREATE TABLE public.properties_image (
id bigserial NOT NULL,
image_url varchar(1000) NULL,
properties_id int8 references properties(id) NULL,
created_by varchar(255) NULL,
created_date timestamp NULL,
last_modified_by varchar(255) NULL,
last_modified_date timestamp NULL,
CONSTRAINT properties_image_pkey PRIMARY KEY (id)
);