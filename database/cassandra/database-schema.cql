create keyspace reports with replication = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE reports;

consistency one;

create table if not exists Reports
(
  id varchar PRIMARY KEY,
  x  int,
  y  int
);

create table if not exists Locks
(
  officerID varchar,
  reportID  varchar,
  timestamp timestamp,
  PRIMARY KEY (reportID, officerID)
);

create table if not exists Officers
(
  id varchar PRIMARY KEY,
  x  int,
  y  int
);

create table if not exists Logs
(
  sheriffID varchar,
  reportID  varchar,
  departure timestamp,
)