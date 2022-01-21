INSERT INTO patient_attribute (attribute, value, patient_id) SELECT 'PATIENT_DISPENSATION_STATUS', 'Activo', id FROM patient WHERE NOT EXISTS (SELECT * FROM patient_attribute WHERE patient_id = patient.id and attribute = 'PATIENT_DISPENSATION_STATUS');
ALTER TABLE clinicSector ADD COLUMN clinic_sector_type_id INTEGER;
INSERT INTO clinic_sector_type (description, code) values ('Paragem Única', 'PARAGEM_UNICA');
INSERT INTO clinic_sector_type (description, code) values ('Provedor', 'PROVEDOR');
INSERT INTO clinic_sector_type (description, code) values ('Agente Polivalente', 'APE');
INSERT INTO clinic_sector_type (description, code) values ('Clinica Móvel', 'CLINICA_MOVEL');
INSERT INTO clinic_sector_type (description, code) values ('Brigada Móvel', 'BRIGADA_MOVEL');