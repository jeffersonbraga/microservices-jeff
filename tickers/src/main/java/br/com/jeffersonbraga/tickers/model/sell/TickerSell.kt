package br.com.jeffersonbraga.tickers.model.sell

import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "SELL_TICKER")
class TickerSell : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column
    var valor: Double? = null

    @Column
    var quantidade: Double? = null

    @Column
    var data: Date? = null

    @Column
    var ticker: String? = null
}