CREATE TABLE SELL_TICKER (
                            ID serial NOT NULL,
                            VALOR double precision NOT NULL,
                            DATA TIMESTAMP NOT NULL,
                            QUANTIDADE double precision NOT NULL,
                            TICKER TEXT NOT NULL);