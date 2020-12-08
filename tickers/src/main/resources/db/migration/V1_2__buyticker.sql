CREATE TABLE BUY_TICKER (
                        ID serial NOT NULL,
                        VALOR BIGINT NOT NULL,
                        DATA TIMESTAMP NOT NULL,
                        QUANTIDADE double precision NOT NULL,
                        TICKER TEXT NOT NULL);
