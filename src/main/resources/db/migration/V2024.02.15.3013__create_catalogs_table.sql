create table catalogs
(
    deleted boolean NOT NULL,
    created_at timestamp(6),
    dealer_id bigint,
    id bigint NOT NULL,
    updated_at timestamp(6),
    FOREIGN KEY (dealer_id) REFERENCES users(id)
);

CREATE SEQUENCE catalogs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE catalogs_id_seq OWNED BY catalogs.id;

ALTER TABLE ONLY catalogs ALTER COLUMN id SET DEFAULT nextval('catalogs_id_seq'::regclass);

ALTER TABLE ONLY catalogs
    ADD CONSTRAINT catalogs_pkey PRIMARY KEY (id);

ALTER TABLE ONLY catalogs
    ADD CONSTRAINT catalogs_user_id_key UNIQUE (dealer_id);