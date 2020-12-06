package br.com.jeffersonbraga.tickers.model.history;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TICKER_HISTORY")
public class TickerHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ID_TICKER")
    private String idTicker;

    @Column(name = "DATA")
    private Date data;

    @Column(name = "OPEN")
    private Long open;

    @Column(name = "CLOSE")
    private Long close;

    @Column(name = "HIGH")
    private Long high;

    @Column(name = "LOW")
    private Long low;

    @Column(name = "VOLUME")
    private Long volume;

    public TickerHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdTicker() {
        return idTicker;
    }

    public void setIdTicker(String idTicker) {
        this.idTicker = idTicker;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getOpen() {
        return open;
    }

    public void setOpen(Long open) {
        this.open = open;
    }

    public Long getClose() {
        return close;
    }

    public void setClose(Long close) {
        this.close = close;
    }

    public Long getHigh() {
        return high;
    }

    public void setHigh(Long high) {
        this.high = high;
    }

    public Long getLow() {
        return low;
    }

    public void setLow(Long low) {
        this.low = low;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
