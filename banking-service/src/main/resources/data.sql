INSERT INTO accounts (id, customer_id, branch_id, balance, created_at, branch_name,
                             account_number, "version")
VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'::uuid, '11111111-1111-1111-1111-111111111111'::uuid,
        'BR002', 3640583.00, '2026-03-10 00:24:12.310', 'Bandung Dago', 'BR00200000001', 11),
       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'::uuid, '11111111-1111-1111-1111-111111111111'::uuid,
        'BR001', 746976.00, '2026-03-10 00:24:12.310', 'Jakarta Sudirman', 'BR00100000001', 6);


INSERT INTO transactions (id, "type", amount, created_at, target_account_number,
                                 source_acc_number)
VALUES ('79dbc93d-65f4-42fd-890c-3dfc83f73870', 'DEPOSIT', 200000.00, '2026-03-10 00:25:30.434',
        NULL, 'BR00100000001'),
       ('b9ddcef5-f841-429a-af8a-f115d9a9f0da', 'WITHDRAW', 100000.00, '2026-03-10 00:26:33.438',
        NULL, 'BR00100000001'),
       ('22fa4215-298f-44cb-836f-69d46fe4d495', 'BI_FAST_TRANSFER', 200000.00,
        '2026-03-10 00:27:07.922', 'BR00200000001', 'BR00100000001'),
       ('758d995d-75bb-4842-97ca-0657b00beade', 'ONLINE_TRANSFER', 100000.00,
        '2026-03-10 00:33:12.384', 'BR00200000001', 'BR00100000001'),
       ('1d817f62-0fd2-4ff5-83c7-2ed3fef63f6f', 'RTGS_TRANSFER', 500000.00,
        '2026-03-10 00:33:39.885', 'BR00200000001', 'BR00100000001'),
       ('c78c4b23-8b7a-4737-801d-be96bfb6a35f', 'DEPOSIT', 50000.00, '2026-03-10 01:46:46.654',
        NULL, 'BR00100000001'),
       ('2ac4fb4c-371c-4794-bb11-eec2837a6824', 'DEPOSIT', 666666.00, '2026-03-10 20:29:38.094',
        NULL, 'BR00200000001'),
       ('3a216854-5617-480b-9c15-7034efd14049', 'DEPOSIT', 666666.00, '2026-03-10 21:16:45.319',
        NULL, 'BR00200000001'),
       ('7d4bd93a-dd0d-4187-a892-ec40cabd97aa', 'DEPOSIT', 7777.00, '2026-03-10 21:19:25.209', NULL,
        'BR00200000001'),
       ('5e7d14d0-49de-4830-a684-1c0b95864bd8', 'DEPOSIT', 888.00, '2026-03-10 21:46:45.327', NULL,
        'BR00200000001');
INSERT INTO transactions (id, "type", amount, created_at, target_account_number,
                                 source_acc_number)
VALUES ('aeb42260-0be6-4698-b3ae-aa387fb3e745', 'DEPOSIT', 999.00, '2026-03-10 21:54:19.184', NULL,
        'BR00200000001'),
       ('0f2e53fd-c77f-4ec0-8798-71a2be003110', 'DEPOSIT', 111.00, '2026-03-10 22:17:07.874', NULL,
        'BR00200000001'),
       ('b867d22b-9459-4a1d-adde-a0c54287a7d6', 'RTGS_TRANSFER', 22.00, '2026-03-10 22:17:31.627',
        'BR00200000001', 'BR00100000001'),
       ('3a3cbc7f-38de-45a2-b057-d0c830ce24fe', 'RTGS_TRANSFER', 33.00, '2026-03-10 22:20:52.273',
        'BR00200000001', 'BR00100000001'),
       ('76e4d725-36cb-491d-ad46-d66eee6e5357', 'ONLINE_TRANSFER', 44.00, '2026-03-10 22:21:04.273',
        'BR00200000001', 'BR00100000001'),
       ('0ca439eb-47bf-46cf-948f-09c0a8352be0', 'ONLINE_TRANSFER', 1000000.00,
        '2026-03-10 22:21:26.192', 'BR00200000001', 'BR00100000001'),
       ('005165d9-da60-458b-a5c2-553bad1f62c0', 'BI_FAST_TRANSFER', 123.00,
        '2026-03-10 23:30:06.008', 'BR00100000001', 'BR00200000001'),
       ('381c1705-edb7-44d3-a236-088c76cf3d28', 'DEPOSIT', 111.00, '2026-03-11 01:22:35.860', NULL,
        'BR00200000001'),
       ('917a86f0-b217-425c-917c-9ac801c96f26', 'BI_FAST_TRANSFER', 456.00,
        '2026-03-11 01:23:26.345', 'BR00100000001', 'BR00200000001'),
       ('e763dd37-7eb4-417d-a27b-d90818625911', 'BI_FAST_TRANSFER', 789.00,
        '2026-03-11 01:42:59.058', 'BR00100000001', 'BR00200000001');

INSERT INTO customers (id, national_id, "name", created_at)
VALUES ('11111111-1111-1111-1111-111111111111', '317500000001', 'Erick Gultom',
        '2026-03-10 00:24:10.158');

INSERT INTO master_data (group_name, "key", order_no, value)
VALUES ('TRANSFER', 'BIFAST_FEE', NULL, '2500'),
       ('TRANSFER', 'ONLINE_FEE', NULL, '6500'),
       ('TRANSFER', 'RTGS_FEE', NULL, '25000');