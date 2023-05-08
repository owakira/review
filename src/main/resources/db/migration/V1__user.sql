BEGIN;

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = public, extensions;
SET default_tablespace = '';
SET default_with_oids = FALSE;

CREATE TABLE public.usr
(
    id         bigserial PRIMARY KEY,
    email      varchar(256) NOT NULL
        CONSTRAINT uk_email UNIQUE,
    username   varchar(32) NOT NULL
        CONSTRAINT uk_username UNIQUE,
    password   varchar(60) NOT NULL,
    created_at timestamp DEFAULT now()
);

COMMIT;