package br.com.jeffersonbraga.tickers.model.card

import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import java.util.*

class TickerCard {
    var ticker: String? = null
    var data: Date? = null
    var precoMedio: Double? = null
    var valorAtual: Double? = null
    var diferencaPreco: Double? = null
    var percentualDiferenca: Double? = null
    var quantidade: Double? = null
    var valorTotalInvestido: Double? = null
    var valorTotalAtual: Double? = null
    var listaDadosHistorico : List<TickerHistory>? = null
    var listaDadosCompras : MutableList<BuyTicker> = mutableListOf<BuyTicker>()
}
