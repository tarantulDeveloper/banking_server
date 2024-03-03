CREATE TABLE users
(
    activated          boolean                 NOT NULL,
    deleted            boolean                 NOT NULL,
    created_at         timestamp(6) without time zone,
    id                 bigint                  NOT NULL,
    updated_at         timestamp(6) without time zone,
    profile_image_path character varying(2000) NOT NULL,
    activation_token   character varying(255),
    email              character varying(255)  NOT NULL,
    first_name         character varying(255)  NOT NULL,
    last_name          character varying(255)  NOT NULL,
    password           character varying(255)  NOT NULL,
    patronymic         character varying(255)  NOT NULL,
    phone_number       character varying(255)  NOT NULL,
    role               character varying(255)  NOT NULL,
    CONSTRAINT users_role_check CHECK (((role)::text = ANY
                                        ((ARRAY ['ROLE_ADMIN'::character varying, 'ROLE_USER'::character varying, 'ROLE_MANAGER'::character varying])::text[])))
);


CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE users_id_seq OWNED BY users.id;


ALTER TABLE ONLY users
    ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_email_key UNIQUE (email);


ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);