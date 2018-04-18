CREATE TABLE ROOMS (
R_ID NUMBER,
CONSTRAINT ROOM_ID PRIMARY KEY (R_ID),
N_GUESTS NUMBER,
PRICE NUMBER(3,2),
BREAKFAST NUMBER DEFAULT 0 CHECK (BREAKFAST=1 OR BREAKFAST=0),
PETS NUMBER  DEFAULT 0 CHECK (PETS=1 OR PETS=0),
DATE_FROM DATE,
H_ID NUMBER,
CONSTRAINT HOTEL_FK FOREIGN KEY (H_ID) REFERENCES HOTELS(H_ID)
);