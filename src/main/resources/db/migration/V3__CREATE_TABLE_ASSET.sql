CREATE TABLE asset (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hostname VARCHAR(100),
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'IN_STOCK'
        CHECK (status IN (
            'IN_TRANSIT', 'CONFIGURING', 'IN_STOCK', 'IN_USE',
            'RESERVED', 'UNDER_REPAIR', 'IN_WARRANTY',
            'STOLEN_LOST', 'DISPOSED'
        )),
    image_url VARCHAR(500),
    site_id INTEGER REFERENCES site(id) ON DELETE RESTRICT,
    user_id INTEGER REFERENCES site_user(id) ON DELETE RESTRICT,
    updated_by_admin_id INTEGER REFERENCES admin_user(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_asset_status_user CHECK (
        (status IN ('IN_USE', 'RESERVED') AND user_id IS NOT NULL) OR
        (status NOT IN ('IN_USE', 'RESERVED') AND user_id IS NULL)
    )
);

CREATE INDEX idx_asset_site_id ON asset(site_id);
CREATE INDEX idx_asset_user_id ON asset(user_id);
CREATE INDEX idx_asset_updated_by_admin_id ON asset(updated_by_admin_id);