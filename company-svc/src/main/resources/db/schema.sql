CREATE TABLE IF NOT EXISTS company (
                         id VARCHAR(255),
                         name VARCHAR(255) NOT NULL DEFAULT '',
                         archived boolean DEFAULT false,
                         default_timezone VARCHAR(255) NOT NULL DEFAULT '',
                         default_day_week_starts VARCHAR(20) NOT NULL DEFAULT 'Monday',
                         PRIMARY KEY (id)
) ;

CREATE TABLE IF NOT EXISTS directory (
                           id VARCHAR(255),
                           company_id VARCHAR(255) NOT NULL,
                           user_id VARCHAR(255) NOT NULL,
                           internal_id VARCHAR(255) NOT NULL,
                           PRIMARY KEY (id)
                          ,
                           CONSTRAINT ix_directory_company_user_internal_id UNIQUE (company_id, user_id, internal_id)
) ;

create index ix_directory_company_id on directory (company_id);
create index ix_directory_user_id on directory (user_id);
create index ix_directory_internal_id on directory (internal_id);

CREATE TABLE IF NOT EXISTS admin (
                       id VARCHAR(255),
                       company_id VARCHAR(255) NOT NULL,
                       user_id VARCHAR(255) NOT NULL,
                       PRIMARY KEY (id)
                      ,
                       CONSTRAINT ix_admin_company_user_id UNIQUE (company_id, user_id)
) ;

CREATE INDEX ix_admin_company_id ON admin (company_id);
CREATE INDEX ix_admin_user_id ON admin (user_id);

CREATE TABLE IF NOT EXISTS team (
                      id VARCHAR(255) NOT NULL,
                      company_id VARCHAR(255) NOT NULL DEFAULT '',
                      name VARCHAR(255) NOT NULL DEFAULT '',
                      archived boolean NOT NULL DEFAULT false,
                      timezone VARCHAR(255) NOT NULL DEFAULT '',
                      day_week_starts VARCHAR(20) NOT NULL DEFAULT 'Monday',
                      color VARCHAR(10) NOT NULL DEFAULT '#48B7AB',
                      PRIMARY KEY (id)
) ;

CREATE INDEX ix_team_company_id ON team (company_id);

CREATE TABLE IF NOT EXISTS worker (
                        id VARCHAR(255),
                        team_id VARCHAR(255) NOT NULL,
                        user_id VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id)
                       ,
                        CONSTRAINT ix_worker_team_user_id UNIQUE (team_id, user_id)
) ;

CREATE INDEX ix_team_team_id ON worker (team_id);
CREATE INDEX ix_team_user_id ON worker (user_id);

CREATE TABLE IF NOT EXISTS job (
                     id VARCHAR(255) NOT NULL,
                     team_id VARCHAR(255) NOT NULL DEFAULT '',
                     name VARCHAR(255) NOT NULL DEFAULT '',
                     archived boolean NOT NULL DEFAULT false,
                     color VARCHAR(10) NOT NULL DEFAULT '#48B7AB',
                     PRIMARY KEY (id)
) ;

CREATE INDEX ix_job_team_id ON job (team_id);


CREATE TABLE IF NOT EXISTS shift (
                                   id VARCHAR(255) NOT NULL,
                                   team_id VARCHAR(255) NOT NULL DEFAULT '',
                                   job_id VARCHAR(255) NOT NULL DEFAULT '',
                                   user_id VARCHAR(255) NOT NULL DEFAULT '',
                                   published boolean NOT NULL DEFAULT false,
                                   start TIMESTAMP(0) NOT NULL DEFAULT current_timestamp,
                                   stop TIMESTAMP(0) NOT NULL DEFAULT current_timestamp,
                                   PRIMARY KEY (id)
) ;

CREATE INDEX ix_job_shift_id ON shift (job_id);
CREATE INDEX ix_job_user_id ON shift (user_id);
