CREATE TABLE asset_history (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    asset_id INTEGER NOT NULL REFERENCES asset(id) ON DELETE CASCADE,
    old_status asset_status_enum,
    new_status asset_status_enum NOT NULL,

    old_user_id INTEGER REFERENCES site_user(id),
    new_user_id INTEGER REFERENCES site_user(id),

    old_site_id INTEGER REFERENCES site(id),
    new_site_id INTEGER REFERENCES site(id),
    
    moved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);