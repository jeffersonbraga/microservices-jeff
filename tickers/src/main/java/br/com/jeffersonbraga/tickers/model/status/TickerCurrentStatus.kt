package br.com.jeffersonbraga.tickers.model.status

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "TICKER_CURRENT_STATUS")
class TickerCurrentStatus : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    var id: Long? = null

    @Column(name = "TICKER")
    var ticker: String? = null

    @Column(name = "VALOR_ATUAL")
    var valorAtual: Double? = null

    @Column(name = "FECHAMENTO_ANTERIOR")
    var fechamentoAnterior: Double? = null

    @Column(name = "MUDANCA")
    var mudanca: Double? = null

    @Column(name = "AVG50")
    var avg50: Double? = null

    @Column(name = "AVG200")
    var avg200: Double? = null
}
