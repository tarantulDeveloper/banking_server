ALTER TABLE products
    RENAME COLUMN user_id TO manager_id;
ALTER TABLE products
    ADD COLUMN quantity integer NOT NULL ;
