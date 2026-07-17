ALTER TABLE asset
ADD COLUMN updated_by_admin_id INTEGER REFERENCES admin_user(id);

ALTER TABLE asset_history
ADD COLUMN admin_id INTEGER NOT NULL REFERENCES admin_user(id);
