CREATE TABLE currencies
(
    id       integer                NOT NULL,
    value    numeric(38, 4)         NOT NULL,
    iso_code character varying(255) NOT NULL
);


CREATE SEQUENCE currencies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE currencies_id_seq OWNED BY currencies.id;


ALTER TABLE ONLY currencies
    ALTER COLUMN id SET DEFAULT nextval('currencies_id_seq'::regclass);


ALTER TABLE ONLY currencies
    ADD CONSTRAINT currencies_pkey PRIMARY KEY (id);