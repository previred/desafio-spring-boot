-- Insertar estados posibles
INSERT INTO
    task_status (id, name)
VALUES (1, 'Pending'),
    (2, 'In Progress'),
    (3, 'Cancelled'),
    (4, 'Completed');

INSERT INTO
    users (ID, EMAIL, PASSWORD)
VALUES (
        '7f000101-95ec-1fb5-8195-ecb000ba0000',
        'popeyes@midomain.com',
        '$2a$10$urIUU9g4uS7vOzqLns.n7ergXpW6p55fDKa52W6Xm6vPLW6cGHFEe'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb035f50001',
        'olivia@midomain.com',
        '$2a$10$6ZpZaxCTTKRC0ius38RJnOtNu34rvWkCWI8KyROpL.ojKQGEOkbji'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb0546a0002',
        'superman@midomain.com',
        '$2a$10$D7bNvgp7kXPsldVW9XYOt.AFFY7vPc7M2XTBzwvbhUUGA8Bfg..4S'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb0785c0003',
        'tony.stark@midomain.com',
        '$2a$10$1emc3eCeDz98glS71W3Xw.8r4KC45l1AqXS5TB1KQ5h57COLAIRde'
    );

INSERT INTO
    tasks (
        ID,
        DESCRIPTION,
        STATUS_ID,
        TITLE,
        USER_ID
    )
VALUES (
        '7f000101-95ec-1fb5-8195-ecb163110004',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb035f50001'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb16a040005',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb035f50001'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb16bee0006',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb035f50001'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb16d9b0007',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb035f50001'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb1706d0008',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb035f50001'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb1f7470009',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb0785c0003'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb1fb6a000a',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb0785c0003'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb201f3000b',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb0785c0003'
    ),
    (
        '7f000101-95ec-1fb5-8195-ecb203ca000c',
        'Task description',
        1,
        'Test task',
        '7f000101-95ec-1fb5-8195-ecb0785c0003'
    );