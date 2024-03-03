create table incomes
(
    deleted boolean NOT NULL ,
    income_amount numeric(38,2),
    created_at timestamp(6),
    id bigint NOT NULL ,
    purchase_id bigint,
    updated_at timestamp(6),
    owner_type character varying(255)  NOT NULL,
    FOREIGN KEY (purchase_id) REFERENCES purchases (id),
    CONSTRAINT users_role_check CHECK (((owner_type)::text = ANY
                                        ((ARRAY ['SYSTEM'::character varying, 'DEALER'::character varying])::text[])))
);

CREATE SEQUENCE incomes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE incomes_id_seq OWNED BY incomes.id;


ALTER TABLE ONLY incomes
    ALTER COLUMN id SET DEFAULT nextval('incomes_id_seq'::regclass);

ALTER TABLE ONLY incomes
    ADD CONSTRAINT incomes_pkey PRIMARY KEY (id);