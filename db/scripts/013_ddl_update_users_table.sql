ALTER TABLE users ADD COLUMN utc VARCHAR;

COMMENT ON COLUMN users.utc IS 'Смещение времени в соответствии с геолокацией пользователя';