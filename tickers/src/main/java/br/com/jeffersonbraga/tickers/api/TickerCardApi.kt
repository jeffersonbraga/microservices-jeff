package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.card.TickerCard
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.repository.*
import br.com.jeffersonbraga.tickers.service.TickerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("ticker/cards")
class TickerCardApi {

    @Autowired
    private lateinit var buyRepository: BuyTickerRepository

    @Autowired
    private lateinit var sellRepository: TickerSellRepository

    @Autowired
    private lateinit var tickerRepository: TickerRepository

    @Autowired
    private lateinit var historyRepository: TickerHistoryRepository

    @Autowired
    private lateinit var currentStatusRepository: TickerCurrentStatusRepository

    @CrossOrigin
    @GetMapping
    fun getCards(): MutableList<TickerCard> {
        val listaTickers = tickerRepository.findAll()
        val listaHistory = historyRepository.findAll()
        val listaCompras = buyRepository.findAll()
        val listaVendas = sellRepository.findAll()
        val listaCurrentStatus = currentStatusRepository.findAll()

        val listaCards = mutableListOf<TickerCard>()
        listaTickers.forEach {
            itTicker ->

            val tickerCard = TickerCard()
            tickerCard.ticker = itTicker.ticker
            /*
            listaHistory.filter {
                itHistory ->
                itHistory.idTicker == itTicker.ticker
            }?.forEach {

            }
            */

            var quantidadeAtual = 0.0
            var quantidadeCompras = 0.0
            var quantidadeVendas = 0.0
            var valorTotalInvestido = 0.0
            var valorTotalCompras = 0.0
            var valorTotalVendas = 0.0
            var qtdCompras = 0.0
            var qtdVendas = 0.0

            var isTickerComprado = false
            listaCompras.filter {
                    itCompra ->
                itCompra.ticker == itTicker.ticker
            }?.forEach {
                    itCompraTicker ->
                quantidadeCompras += itCompraTicker.quantidade!!
                valorTotalCompras += itCompraTicker.valor!!
                qtdCompras = qtdCompras.plus(1.0)
                tickerCard.listaDadosCompras.add(itCompraTicker)
                tickerCard.listaDadosHistorico = listaHistory.filter { itHistory -> itHistory.idTicker == itTicker.ticker }.toMutableList()

                isTickerComprado = true
            }

            listaVendas.filter {
                itVenda ->
                itVenda.ticker == itTicker.ticker
            }?.forEach {
                itVendaTicker ->
                quantidadeVendas += itVendaTicker.quantidade!!
                valorTotalVendas += itVendaTicker.valor!!
                qtdVendas = qtdVendas.plus(1.0)
                tickerCard.listaDadosVendas.add(itVendaTicker)
            }

            quantidadeAtual = quantidadeCompras - quantidadeVendas

            tickerCard.precoMedio = valorTotalCompras.div(quantidadeCompras)
            tickerCard.quantidade = quantidadeAtual
            tickerCard.valorTotalInvestido = quantidadeAtual.times(tickerCard.precoMedio?:0.0)
            tickerCard.quantidadeVendido = quantidadeVendas
            tickerCard.valorResultadoCompraVenda = valorTotalVendas.minus(quantidadeVendas.toDouble().times(tickerCard.precoMedio!!))

            val tickerCurrentStatus = listaCurrentStatus.find { it.ticker == tickerCard.ticker }
            tickerCard.valorAtual = tickerCurrentStatus?.valorAtual
            tickerCard.diferencaPreco = tickerCurrentStatus?.valorAtual!!.minus(tickerCard.precoMedio!!)
            tickerCard.valorTotalAtual = tickerCard.valorAtual!!.times(quantidadeAtual)

            //tickerCard.valorAtual!!.div(tickerCard.precoMedio!!).multiply(BigDecimal(100)).subtract(BigDecimal(100)
            tickerCard.data = Date()
            if (isTickerComprado) {
                tickerCard.percentualDiferenca = (tickerCard.valorAtual!!.div(tickerCard.precoMedio!!).times(100.0)).minus(100.0)
                tickerCard.listaDadosHistorico?.sortedBy { it.data }

                if (tickerCard.listaDadosHistorico?.size?:0 > 20) {
                    TickerService.getMediaMovel(tickerCard.listaDadosHistorico)

                    tickerCard.mediaMayer = tickerCard?.valorAtual?.div(
                        tickerCard?.listaDadosHistorico?.last()?.mediaMovel200
                            ?: tickerCard?.listaDadosHistorico?.last()?.mediaMovel100
                            ?: tickerCard?.listaDadosHistorico?.last()?.mediaMovel50
                            ?: tickerCard?.listaDadosHistorico?.last()?.mediaMovel20 ?: 1.0
                    )
                }

                if (tickerCard.listaDadosHistorico?.size?:0 > 10) {
                    tickerCard.listaDadosTop10MelhoresPrecos =
                        tickerCard?.listaDadosHistorico?.sortedByDescending { it.close }?.subList(0, 10)
                            ?.toMutableList()
                    tickerCard.listaDadosTop10MenoresPrecos =
                        tickerCard?.listaDadosHistorico?.sortedBy { it.close }?.subList(0, 10)?.toMutableList()

                    tickerCard.listaDadosTop10MelhoresVolume =
                        tickerCard?.listaDadosHistorico?.sortedByDescending { it.volume }?.subList(0, 10)
                            ?.toMutableList()
                    tickerCard.listaDadosTop10MenoresVolume =
                        tickerCard?.listaDadosHistorico?.sortedBy { it.volume }?.subList(0, 10)?.toMutableList()
                }

                tickerCard?.listaDadosHistorico?.sortedBy { it.close }?.elementAtOrNull((tickerCard?.listaDadosHistorico?.size?.div(2)?:0))
                    ?.also { tickerCard?.mediana = it.close }

                listaCards.add(tickerCard)
            }
        }
        /*
        for (itemTicker in lista) {
            val tickerHistoryFilter = TickerHistory()
            tickerHistoryFilter.idTicker = itemTicker.ticker
        }*/
        listaCards.sortByDescending { it.quantidade }
        return listaCards
    }
}
