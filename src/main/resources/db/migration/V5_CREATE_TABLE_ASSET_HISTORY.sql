CREATE TABLE asset_history (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id INTEGER REFERENCES asset(id) ON DELETE SET NULL,
    asset_serial_number VARCHAR(100) NOT NULL,

    old_status VARCHAR(20)
        CHECK (old_status IN (
            'IN_TRANSIT', 'CONFIGURING', 'IN_STOCK', 'IN_USE',
            'RESERVED', 'UNDER_REPAIR', 'IN_WARRANTY',
            'STOLEN_LOST', 'DISPOSED'
        )),
    new_status VARCHAR(20) NOT NULL
        CHECK (new_status IN (
            'IN_TRANSIT', 'CONFIGURING', 'IN_STOCK', 'IN_USE',
            'RESERVED', 'UNDER_REPAIR', 'IN_WARRANTY',
            'STOLEN_LOST', 'DISPOSED'
        )),

    old_user_id INTEGER REFERENCES site_user(id) ON DELETE SET NULL,
    new_user_id INTEGER REFERENCES site_user(id) ON DELETE SET NULL,

    old_site_id INTEGER REFERENCES site(id) ON DELETE SET NULL,
    new_site_id INTEGER REFERENCES site(id) ON DELETE SET NULL,

    admin_id INTEGER REFERENCES admin_user(id) ON DELETE SET NULL,
    maintenance_id INTEGER REFERENCES maintenance_record(id) ON DELETE SET NULL,

    moved_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_asset_history_asset_id ON asset_history(asset_id);
CREATE INDEX idx_asset_history_admin_id ON asset_history(admin_id);
CREATE INDEX idx_asset_history_maintenance_id ON asset_history(maintenance_id);