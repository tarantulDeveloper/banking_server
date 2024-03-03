INSERT INTO users (email, role, password, activated, phone_number, first_name, last_name, patronymic,
                   profile_image_path, deleted, created_at)
VALUES ('admin', 'ROLE_ADMIN', '$2a$10$ca9nY6S1iSocz9cJ8moT7uLyZEmvh2fQZeSt4MXHyPei7V6Myv2Ka', TRUE, '+996509091625',
        'Нияз', 'Кабылов', 'Саткынович',
        'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_960_720.png', FALSE, NOW())