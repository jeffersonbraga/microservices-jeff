CREATE TABLE TICKER (
                        ID serial NOT NULL,
                        TICKER VARCHAR(15) NOT NULL
);

CREATE TABLE TICKER_HISTORY (
                                ID serial NOT NULL,
                                ID_TICKER BIGINT NOT NULL,
                                DATA TIMESTAMP NOT NULL,
                                OPEN double precision NOT NULL,
                                CLOSE double precision NOT NULL,
                                HIGH double precision NOT NULL,
                                LOW double precision NOT NULL,
                                VOLUME double precision NOT NULL
);
