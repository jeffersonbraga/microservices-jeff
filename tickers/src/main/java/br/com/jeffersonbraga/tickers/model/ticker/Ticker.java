package br.com.jeffersonbraga.tickers.model.ticker;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TICKER")
public class Ticker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TICKER")
    private String ticker;

    public Ticker() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
