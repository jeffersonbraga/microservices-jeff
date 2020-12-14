package br.com.jeffersonbraga.tickers.model.buyticker

import java.io.Serializable
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "BUY_TICKER")
class BuyTicker : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var iD: Int? = null

    @Column
    var valor: Double? = null

    @Column
    var quantidade: Double? = null

    @Column
    var data: Date? = null

    @Column
    var ticker: String? = null
}
