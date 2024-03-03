INSERT INTO users (email, role, password, activated, phone_number, first_name, last_name, patronymic,
                   profile_image_path, deleted, created_at)
VALUES ('user1', 'ROLE_USER', '$2a$12$.0dqs20b6NWoLI25FAVyWuy3I0QeP8qEN7ZPqxWE8BIWpBFqaB4kW', TRUE, '+996509091625',
        'Ким', 'Чан', 'Ди',
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png', FALSE, NOW());

INSERT INTO documents(document_expires_at, document_issued_at, document_id, birth_date, authority, citizenship,
                      personal_number, deleted)
VALUES ('2029-04-23 12:34:56.789', NOW(), 'AN565674', '2029-04-23 12:34:56.789', 'MKK-524-23', 'Kyrgyzstan',
        '228092005719', FALSE);

UPDATE documents
SET user_id = (SELECT id FROM users WHERE email = 'user1'), created_at = NOW()
WHERE id = 2;

INSERT INTO users (email, role, password, activated, phone_number, first_name, last_name, patronymic,
                   profile_image_path, deleted, created_at)
VALUES ('user2', 'ROLE_USER', '$2a$12$xD.AIucLDL8TVXGi82W7uuXlUdNn8VQWc132nOwJfYXIMO6CbpbO.', TRUE, '+996509091625',
        'Том', 'Холланд', 'Ниггер',
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png', FALSE, NOW());

INSERT INTO documents(document_expires_at, document_issued_at, document_id, birth_date, authority, citizenship,
                      personal_number, deleted)
VALUES ('2029-04-23 12:34:56.789', NOW(), 'AN565674', '2029-04-23 12:34:56.789', 'MKK-524-23', 'Kyrgyzstan',
        '228092005719', FALSE);

UPDATE documents
SET user_id = (SELECT id FROM users WHERE email = 'user2'), created_at = NOW()
WHERE id = 3;

INSERT INTO client_accounts(balance,deleted,created_at,updated_at,user_id) VALUES (10000.0,false,NOW(),NOW(),(SELECT id FROM users WHERE email = 'user1'));
INSERT INTO client_accounts(balance,deleted,created_at,updated_at,user_id) VALUES (20000.0,false,NOW(),NOW(),(SELECT id FROM users WHERE email = 'user2'));


