CREATE TABLE BRAND (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255) NOT NULL,
    CONSTRAINT UN_BRAND UNIQUE(NAME)
);

CREATE TABLE INVENTORY (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BRAND_ID BIGINT NOT NULL,
    QUANTITY INT NOT NULL,
    TIME_RECEIVED TIMESTAMP NOT NULL,
    CONSTRAINT FK_INVENTORY FOREIGN KEY (BRAND_ID)
    REFERENCES BRAND(ID)
);