ALTER TABLE auth.users ADD status VARCHAR(20) DEFAULT 'OPENED' NOT NULL;
ALTER TABLE auth.users ADD locked_at TIMESTAMP;
ALTER TABLE auth.users ADD failed_cycle_count INT DEFAULT 0 NOT NULL;
