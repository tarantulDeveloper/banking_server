CREATE TABLE documents
(
    deleted             boolean                        NOT NULL,
    birth_date          timestamp(6) without time zone NOT NULL,
    created_at          timestamp(6) without time zone,
    document_expires_at timestamp(6) without time zone NOT NULL,
    document_issued_at  timestamp(6) without time zone NOT NULL,
    id                  bigint                         NOT NULL,
    updated_at          timestamp(6) without time zone,
    user_id             bigint,
    authority           character varying(255)         NOT NULL,
    citizenship         character varying(255)         NOT NULL,
    document_id         character varying(255)         NOT NULL,
    personal_number     character varying(255)         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE SEQUENCE documents_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE documents_id_seq OWNED BY documents.id;


ALTER TABLE ONLY documents
    ALTER COLUMN id SET DEFAULT nextval('documents_id_seq'::regclass);


ALTER TABLE ONLY documents
    ADD CONSTRAINT documents_pkey PRIMARY KEY (id);


ALTER TABLE ONLY documents
    ADD CONSTRAINT documents_user_id_key UNIQUE (user_id);

