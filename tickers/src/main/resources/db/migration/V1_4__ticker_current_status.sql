CREATE TABLE TICKER_CURRENT_STATUS (
                                    ID serial NOT NULL,
                                    TICKER TEXT NOT NULL,
                                    VALOR_ATUAL double precision NOT NULL,
                                    FECHAMENTO_ANTERIOR double precision NOT NULL,
                                    MUDANCA double precision NOT NULL,
                                    AVG50 double precision,
                                    AVG200 double precision);
