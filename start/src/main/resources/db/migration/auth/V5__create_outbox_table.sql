CREATE TABLE auth.outbox_events (
    id UUID PRIMARY KEY,
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id VARCHAR(255) NOT NULL,
    event_type VARCHAR(255) NOT NULL,
    payload JSONB NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT DEFAULT 0 NOT NULL
);

CREATE INDEX idx_outbox_pending_events
ON auth.outbox_events (event_type, created_at)
WHERE status = 'PENDING';

ALTER TABLE auth.users
    ALTER COLUMN locked_at TYPE TIMESTAMPTZ USING locked_at AT TIME ZONE 'UTC',
    ALTER COLUMN created_at TYPE TIMESTAMPTZ USING created_at AT TIME ZONE 'UTC',
    ALTER COLUMN updated_at TYPE TIMESTAMPTZ USING updated_at AT TIME ZONE 'UTC';