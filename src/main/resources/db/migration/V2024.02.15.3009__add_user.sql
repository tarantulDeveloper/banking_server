INSERT INTO users (email, role, password, activated, phone_number, first_name, last_name, patronymic,
                   profile_image_path, deleted, created_at)
VALUES ('user', 'ROLE_USER', '$2a$10$w.MqxuNl0oPqmg6QHAmnsuomhs4IavgnMHK4Ej/ChpxIzh.1pN03u', TRUE, '+996509091625',
        'Айжамал', 'Кадырбекова', 'Асановна',
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png', FALSE, NOW());

INSERT INTO documents(document_expires_at, document_issued_at, document_id, birth_date, authority, citizenship,
                      personal_number, deleted)
VALUES ('2029-04-23 12:34:56.789', NOW(), 'AN56567', '2029-04-23 12:34:56.789', 'MKK-54-23', 'Kyrgyzstan',
        '228092005789', FALSE)