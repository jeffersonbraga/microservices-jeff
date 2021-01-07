package br.com.jeffersonbraga.tickers.model.discovery

import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import java.util.*

class TickerDiscovery {
    var ticker: String? = null
    var nome: String? = null
    var valorAtual: Double? = null
    var mediaMayer: Double? = null
    var diferencaUltimoFechamento: Double? = null
    var percentualDiferenca: Double? = null
    var listaDadosHistorico : MutableList<TickerHistory>? = null
}
