package br.com.jeffersonbraga.tickers.model.resume

import br.com.jeffersonbraga.tickers.model.aporte.AporteFinanceiro
import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker

class Resume {

    var listaDadosAportes : MutableList<AporteFinanceiro> = mutableListOf<AporteFinanceiro>()
    var listaDadosCompras : MutableList<BuyTicker> = mutableListOf<BuyTicker>()
}