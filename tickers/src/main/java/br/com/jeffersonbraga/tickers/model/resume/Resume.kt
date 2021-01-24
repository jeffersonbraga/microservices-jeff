package br.com.jeffersonbraga.tickers.model.resume

import br.com.jeffersonbraga.tickers.model.aporte.AporteFinanceiro
import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import br.com.jeffersonbraga.tickers.model.sell.TickerSell

class Resume {

    var listaDadosAportes : MutableList<AporteFinanceiro> = mutableListOf<AporteFinanceiro>()
    var listaDadosCompras : MutableList<BuyTicker> = mutableListOf<BuyTicker>()
    var listaDadosVendas : MutableList<TickerSell> = mutableListOf<TickerSell>()
}