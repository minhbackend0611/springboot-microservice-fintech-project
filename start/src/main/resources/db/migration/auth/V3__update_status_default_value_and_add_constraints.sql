UPDATE auth.users
SET status = 'ACTIVE'
WHERE status = 'OPENED';

ALTER TABLE auth.users
ALTER COLUMN status
SET DEFAULT 'ACTIVE';

ALTER TABLE auth.users
ADD CONSTRAINT chk_account_status
CHECK (
    status IN (
        'ACTIVE',
        'TEMPORARILY_LOCKED',
        'PERMANENTLY_LOCKED'
    )
);

ALTER TABLE auth.users
ADD CONSTRAINT chk_account_role
CHECK (
    role IN (
        'USER',
        'ADMIN'
    )
);