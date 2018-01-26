-- begin GANTTDEMO_TASK
create table GANTTDEMO_TASK (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NAME varchar(255) not null,
    START_DATE timestamp not null,
    END_DATE timestamp not null,
    DESCRIPTION longvarchar,
    PARENT_ID varchar(36),
    --
    primary key (ID)
)^
-- end GANTTDEMO_TASK
