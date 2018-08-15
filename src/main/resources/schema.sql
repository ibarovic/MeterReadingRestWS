CREATE TABLE RATIO
(
  	MONTH VARCHAR(3) NOT NULL,
   	PROFILE VARCHAR(1) NOT NULL,
   	RATIO DECIMAL(20,2) NOT NULL,
   	TSTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   	CONSTRAINT PK_RATIO PRIMARY KEY (MONTH, PROFILE)
);

CREATE TABLE METER_READING
(
   	METER_ID VARCHAR(4) NOT NULL, 
   	PROFILE VARCHAR(5) NOT NULL,
   	MONTH VARCHAR(3) NOT NULL,
   	READING BIGINT NOT NULL,
   	CONSUMPTION BIGINT NOT NULL,
   	TSTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   	CONSTRAINT PK_METER_READING PRIMARY KEY (METER_ID, MONTH, PROFILE),
	CONSTRAINT FK_RATIO_METER_READING FOREIGN KEY(MONTH, PROFILE) REFERENCES RATIO(MONTH, PROFILE)
   
);