create table purchases
(
    cost numeric(38,2) NOT NULL,
    deleted boolean NOT NULL ,
    quantity integer NOT NULL ,
    real_cost numeric(38,2) NOT NULL ,
    created_at timestamp(6) without time zone,
    dealer_id bigint,
    id bigint NOT NULL,
    product_id bigint,
    updated_at timestamp(6) without time zone,
    FOREIGN KEY (dealer_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);


CREATE SEQUENCE purchases_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE purchases_id_seq OWNED BY purchases.id;

ALTER TABLE ONLY purchases
    ALTER COLUMN id SET DEFAULT nextval('purchases_id_seq'::regclass);

ALTER TABLE ONLY purchases
    ADD CONSTRAINT purchases_pkey PRIMARY KEY (id);


