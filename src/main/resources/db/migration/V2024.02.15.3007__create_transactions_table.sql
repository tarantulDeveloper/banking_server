CREATE TABLE transactions
(
    amount      numeric(38, 2) NOT NULL,
    deleted     boolean        NOT NULL,
    created_at  timestamp(6) without time zone,
    id          bigint         NOT NULL,
    receiver_id bigint,
    sender_id   bigint,
    updated_at  timestamp(6) without time zone,
    CONSTRAINT transactions_amount_check CHECK ((amount >= (0)::numeric)),
    FOREIGN KEY (receiver_id) REFERENCES client_accounts (id),
    FOREIGN KEY (sender_id) REFERENCES client_accounts (id)
);


CREATE SEQUENCE transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;


ALTER TABLE ONLY transactions
    ALTER COLUMN id SET DEFAULT nextval('transactions_id_seq'::regclass);


ALTER TABLE ONLY transactions
    ADD CONSTRAINT transactions_pkey PRIMARY KEY (id);