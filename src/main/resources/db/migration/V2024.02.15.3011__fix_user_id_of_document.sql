UPDATE documents
SET user_id = (SELECT id FROM users WHERE email = 'user'), created_at = NOW()
WHERE id = 1;
