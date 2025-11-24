-- ロールの初期データ
-- ROLE_USERとROLE_ADMINは必須なので起動時に自動作成
INSERT INTO role (id, name) VALUES (1, 'ROLE_USER') 
ON CONFLICT (id) DO NOTHING;

INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN') 
ON CONFLICT (id) DO NOTHING;
