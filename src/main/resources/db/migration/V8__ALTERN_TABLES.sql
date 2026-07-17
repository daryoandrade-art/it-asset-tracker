ALTER TABLE maintenance_record
DROP CONSTRAINT maintenance_record_created_by_admin_id_fkey;

ALTER TABLE maintenance_record
ADD CONSTRAINT maintenance_record_created_by_admin_id_fkey
FOREIGN KEY (created_by_admin_id)
REFERENCES admin_user(id)
ON DELETE SET NULL;

ALTER TABLE asset_history
DROP CONSTRAINT asset_history_maintenance_id_fkey;

ALTER TABLE asset_history
ADD CONSTRAINT asset_history_maintenance_id_fkey
FOREIGN KEY (maintenance_id)
REFERENCES maintenance_record(id)
ON DELETE SET NULL;
