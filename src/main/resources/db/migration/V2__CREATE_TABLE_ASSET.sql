CREATE TYPE asset_status_enum AS ENUM (
    'IN_TRANSIT',
    'CONFIGURING',
    'IN_STOCK',
    'IN_USE',
    'RESERVED',
    'UNDER_REPAIR',
    'IN_WARRANTY',
    'STOLEN_LOST',
    'DISPOSED'
);


CREATE TABLE asset (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    hostname VARCHAR(100),
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    status asset_status_enum DEFAULT 'IN_STOCK',
    image_url VARCHAR(500),
    site_id INTEGER REFERENCES site(id),
    user_id INTEGER REFERENCES site_user(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_status_user CHECK (
        (status IN ('IN_USE', 'RESERVED') AND user_id IS NOT NULL) OR
        (status NOT IN ('IN_USE', 'RESERVED') AND user_id IS NULL)
    )
);