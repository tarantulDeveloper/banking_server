create table catalogs_products
(
    catalog_id bigint NOT NULL,
    products_id bigint NOT NULL,
    FOREIGN KEY (catalog_id) REFERENCES catalogs (id),
    FOREIGN KEY (products_id) REFERENCES products (id)
);
