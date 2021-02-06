package br.com.jeffersonbraga.tickers.model.ticker

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "TICKER")
class Ticker : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "TICKER")
    var ticker: String? = null
}