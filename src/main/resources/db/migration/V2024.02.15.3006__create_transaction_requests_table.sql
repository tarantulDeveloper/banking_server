CREATE TABLE transaction_requests
(
    amount      numeric(38, 2)         NOT NULL,
    deleted     boolean                NOT NULL,
    created_at  timestamp(6) without time zone,
    id          bigint                 NOT NULL,
    receiver_id bigint,
    sender_id   bigint,
    updated_at  timestamp(6) without time zone,
    status      character varying(255) NOT NULL,
    CONSTRAINT transaction_requests_amount_check CHECK ((amount >= (0)::numeric)),
    CONSTRAINT transaction_requests_status_check CHECK (((status)::text = ANY
                                                         ((ARRAY ['PENDING'::character varying, 'APPROVED'::character varying, 'REJECTED'::character varying])::text[]))),
    FOREIGN KEY (sender_id) REFERENCES client_accounts (id),
    FOREIGN KEY (receiver_id) REFERENCES client_accounts (id)
);


CREATE SEQUENCE transaction_requests_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE transaction_requests_id_seq OWNED BY transaction_requests.id;


ALTER TABLE ONLY transaction_requests
    ALTER COLUMN id SET DEFAULT nextval('transaction_requests_id_seq'::regclass);


ALTER TABLE ONLY transaction_requests
    ADD CONSTRAINT transaction_requests_pkey PRIMARY KEY (id);
