package br.com.jeffersonbraga.tickers.model.history

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TICKER_HISTORY")
class TickerHistory : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "ID_TICKER")
    var idTicker: String? = null

    @Column(name = "DATA")
    var data: Date? = null

    @Column(name = "OPEN")
    var open: Double? = null

    @Column(name = "CLOSE")
    var close: Double? = null

    @Column(name = "HIGH")
    var high: Double? = null

    @Column(name = "LOW")
    var low: Double? = null

    @Column(name = "VOLUME")
    var volume: Double? = null
}
