CREATE TABLE maintenance_record (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id INTEGER NOT NULL REFERENCES asset(id) ON DELETE CASCADE,
    maintenance_type VARCHAR(50) NOT NULL
        CHECK (maintenance_type IN (
            'PREVENTIVE','CORRECTIVE','WARRANTY','DIAGNOSTIC')),
    description TEXT NOT NULL,
    status VARCHAR(30) NOT NULL DEFAULT 'OPEN'
        CHECK (status IN ('OPEN', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),
    created_by_admin_id INTEGER REFERENCES admin_user(id),
    performed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_maintenance_asset_id ON maintenance_record(asset_id);
CREATE INDEX idx_maintenance_admin_id ON maintenance_record(created_by_admin_id);

ALTER TABLE asset_history
ADD COLUMN maintenance_id INTEGER REFERENCES maintenance_record(id);
