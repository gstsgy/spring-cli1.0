

CREATE TABLE g_sequence (
                            id bigint NOT NULL,
                            g_key nvarchar(255) COLLATE Chinese_PRC_CS_AI NOT NULL,
                            subkey nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            startnum bigint NULL,
                            step int NULL,
                            maxnum bigint NULL,
                            dayresetting varchar(1) COLLATE Chinese_PRC_CS_AI NULL,
                            lastday nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            value bigint NULL,
                            insert_id bigint NULL,
                            insert_ymd nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            update_ymd nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            update_id bigint NULL,
                            remark nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            remark1 nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            remark2 nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            remark3 nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            remark4 nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            remark5 nvarchar(255) COLLATE Chinese_PRC_CS_AI NULL,
                            effective int NULL,
                            update_flag int NULL,
                            CONSTRAINT PK__g_sequen__3213E83F0FCA37FE PRIMARY KEY (id)
);