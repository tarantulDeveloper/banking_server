CREATE TABLE products
(
    commission                      numeric(38, 0)         NOT NULL,
    currency_id                     integer,
    deleted                         boolean                NOT NULL,
    past_rate                       numeric(38, 4)         NOT NULL,
    percentage_of_profit_for_dealer numeric(38, 2)         NOT NULL,
    percentage_of_profit_for_system numeric(38, 2)         NOT NULL,
    price                           numeric(38, 0)         NOT NULL,
    created_at                      timestamp(6) without time zone,
    id                              bigint                 NOT NULL,
    updated_at                      timestamp(6) without time zone,
    user_id                         bigint,
    image_url                       character varying(255) NOT NULL,
    name                            character varying(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (currency_id) REFERENCES currencies (id)
);


CREATE SEQUENCE products_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE products_id_seq OWNED BY products.id;


ALTER TABLE ONLY products
    ALTER COLUMN id SET DEFAULT nextval('products_id_seq'::regclass);


ALTER TABLE ONLY products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);