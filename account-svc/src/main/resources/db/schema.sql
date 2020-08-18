CREATE TABLE IF NOT EXISTS account (
                                     id VARCHAR(255),
                                     email VARCHAR(255) NOT NULL,
                                     name VARCHAR(255) NOT NULL default '',
                                     phone_number VARCHAR(255) NOT NULL,
                                     confirmed_and_active BOOLEAN NOT NULL DEFAULT false,
                                     member_since TIMESTAMP NOT NULL default current_timestamp,
                                     password_hash VARCHAR(100) default '',
                                     photo_url VARCHAR(255) NOT NULL,
                                     support BOOLEAN NOT NULL DEFAULT false,
                                     PRIMARY KEY (id)
) ;

create index IF NOT EXISTS  ix_account_email on account (email);
create index IF NOT EXISTS ix_account_phone_number on account (phone_number);

-- time-zone issue reference
-- https://blog.csdn.net/CHS007chs/article/details/81348291
