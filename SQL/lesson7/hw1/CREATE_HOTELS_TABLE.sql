CREATE TABLE HOTELS (
H_ID NUMBER,
CONSTRAINT HOTEL_ID PRIMARY KEY (H_ID),
NAME VARCHAR2(200) NOT NULL,
COUNTRY VARCHAR2(200),
CITY VARCHAR2(200),
STREET VARCHAR2(200)
);