CREATE TABLE noti.email_histories (
    id UUID PRIMARY KEY,
    event_id UUID UNIQUE NOT NULL,
    type VARCHAR(20) NOT NULL,
    email VARCHAR(256) NOT NULL,
    subject VARCHAR(256) NOT NULL,
    content_html TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    retry_count INT DEFAULT 0,
    provider_response JSONB,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NULL
);

CREATE INDEX idx_email_histories
ON noti.email_histories (type, created_at)
WHERE status IN  ('PENDING', 'FAILED');