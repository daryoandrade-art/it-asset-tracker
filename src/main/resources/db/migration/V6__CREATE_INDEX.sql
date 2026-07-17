CREATE INDEX idx_site_user_site_id ON site_user(site_id);
CREATE INDEX idx_asset_site_id ON asset(site_id);
CREATE INDEX idx_asset_user_id ON asset(user_id);
CREATE INDEX idx_asset_history_asset_id ON asset_history(asset_id);