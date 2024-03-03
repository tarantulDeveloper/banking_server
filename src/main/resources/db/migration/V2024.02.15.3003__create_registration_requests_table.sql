CREATE TABLE registration_requests
(
    deleted             boolean                        NOT NULL,
    birth_date          timestamp(6) without time zone NOT NULL,
    created_at          timestamp(6) without time zone,
    document_expires_at timestamp(6) without time zone NOT NULL,
    document_issued_at  timestamp(6) without time zone NOT NULL,
    id                  bigint                         NOT NULL,
    updated_at          timestamp(6) without time zone,
    profile_image_path  character varying(2000)        NOT NULL,
    authority           character varying(255)         NOT NULL,
    citizenship         character varying(255)         NOT NULL,
    document_id         character varying(255)         NOT NULL,
    email               character varying(255)         NOT NULL,
    first_name          character varying(255)         NOT NULL,
    last_name           character varying(255)         NOT NULL,
    password            character varying(255)         NOT NULL,
    patronymic          character varying(255)         NOT NULL,
    personal_number     character varying(255)         NOT NULL,
    phone_number        character varying(255)         NOT NULL,
    status              character varying(255)         NOT NULL,
    CONSTRAINT registration_requests_status_check CHECK (((status)::text = ANY
                                                          ((ARRAY ['PENDING'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying])::text[])))
);


CREATE SEQUENCE registration_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE registration_requests_id_seq OWNED BY registration_requests.id;


ALTER TABLE ONLY registration_requests
    ALTER COLUMN id SET DEFAULT nextval('registration_requests_id_seq'::regclass);


ALTER TABLE ONLY registration_requests
    ADD CONSTRAINT registration_requests_email_key UNIQUE (email);


ALTER TABLE ONLY registration_requests
    ADD CONSTRAINT registration_requests_pkey PRIMARY KEY (id);