package br.com.jeffersonbraga.tickers.model.card

import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.model.sell.TickerSell
import java.util.*

class TickerCard {
    var ticker: String? = null
    var data: Date? = null
    var precoMedio: Double? = null
    var valorAtual: Double? = null
    var mediaMayer: Double? = null
    var mediana: Double? = null
    var diferencaPreco: Double? = null
    var percentualDiferenca: Double? = null
    var quantidade: Double? = null
    var quantidadeVendido: Double? = null
    var valorTotalInvestido: Double? = null
    var valorTotalAtual: Double? = null
    var valorResultadoCompraVenda: Double? = null
    var listaDadosHistorico : MutableList<TickerHistory>? = null
    var listaDadosTop10MelhoresPrecos : MutableList<TickerHistory>? = null
    var listaDadosTop10MenoresPrecos : MutableList<TickerHistory>? = null
    var listaDadosTop10MelhoresVolume : MutableList<TickerHistory>? = null
    var listaDadosTop10MenoresVolume : MutableList<TickerHistory>? = null
    var listaDadosCompras : MutableList<BuyTicker> = mutableListOf<BuyTicker>()
    var listaDadosVendas : MutableList<TickerSell> = mutableListOf<TickerSell>()
}
