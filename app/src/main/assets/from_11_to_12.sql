ALTER TABLE clinicSector ADD COLUMN clinic_sector_type_id INTEGER;
ALTER TABLE dispense_drug ADD COLUMN drug_id INTEGER;
UPDATE Dispense_drug SET drug_id = (SELECT drug_id FROM Stock WHERE id = Dispense_drug.stock_id) WHERE drug_id is null;
INSERT INTO patient_attribute (attribute, value, patient_id) SELECT 'PATIENT_DISPENSATION_STATUS', 'Activo', id FROM patient WHERE NOT EXISTS (SELECT * FROM patient_attribute WHERE patient_id = patient.id and attribute = 'PATIENT_DISPENSATION_STATUS');