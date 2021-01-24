package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.card.TickerCard
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerCurrentStatusRepository
import br.com.jeffersonbraga.tickers.repository.TickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository
import br.com.jeffersonbraga.tickers.service.TickerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("ticker/agrupados")
class TickerAgrupadosApi {

    @Autowired
    private lateinit var buyRepository: BuyTickerRepository

    @Autowired
    private lateinit var tickerRepository: TickerRepository

    @Autowired
    private lateinit var historyRepository: TickerHistoryRepository

    @Autowired
    private lateinit var currentStatusRepository: TickerCurrentStatusRepository

    @CrossOrigin
    @GetMapping
    fun getCards(): List<Pair<Date?, List<TickerHistory>>> {
        val listaTickers = tickerRepository.findAll()
        val listaHistory = historyRepository.findAll()
        val listaCompras = buyRepository.findAll()
        val listaCurrentStatus = currentStatusRepository.findAll()

        val listaCards = mutableListOf<TickerCard>()
        listaTickers.forEach {
            itTicker ->

            val tickerCard = TickerCard()
            tickerCard.ticker = itTicker.ticker

            var quantidade = 0.0
            var valorTotalInvestido = 0.0
            var qtdCompras = 0.0

            var isTickerComprado = false
            listaCompras.filter {
                itCompra ->
                itCompra.ticker == itTicker.ticker
            }?.forEach {
                itCompraTicker ->
                quantidade += itCompraTicker.quantidade!!
                valorTotalInvestido += itCompraTicker.valor!!
                qtdCompras = qtdCompras.plus(1.0)
                tickerCard.listaDadosCompras.add(itCompraTicker)
                tickerCard.listaDadosHistorico = listaHistory.filter { itHistory -> itHistory.idTicker == itTicker.ticker }.toMutableList()

                isTickerComprado = true
            }

            tickerCard.precoMedio = valorTotalInvestido.div(quantidade)

            tickerCard.quantidade = quantidade
            tickerCard.valorTotalInvestido = valorTotalInvestido

            val tickerCurrentStatus = listaCurrentStatus.find { it.ticker == tickerCard.ticker }
            tickerCard.valorAtual = tickerCurrentStatus?.valorAtual
            tickerCard.diferencaPreco = tickerCurrentStatus?.valorAtual!! - tickerCard.precoMedio!!
            tickerCard.valorTotalAtual = tickerCard.valorAtual!! * quantidade

            tickerCard.data = Date()
            if (isTickerComprado) {
                tickerCard.percentualDiferenca = (tickerCard.valorAtual!!.div(tickerCard.precoMedio!!) * 100.0) - 100.0
                tickerCard.listaDadosHistorico?.sortedBy { it.data }
                TickerService.getMediaMovel(tickerCard.listaDadosHistorico)

                tickerCard.mediaMayer = tickerCard?.valorAtual?.div(
                    tickerCard?.listaDadosHistorico?.last()?.mediaMovel200?:
                    tickerCard?.listaDadosHistorico?.last()?.mediaMovel100?:
                    tickerCard?.listaDadosHistorico?.last()?.mediaMovel50?:
                    tickerCard?.listaDadosHistorico?.last()?.mediaMovel20?:
                    1.0)
                listaCards.add(tickerCard)
            }
        }

        listaCards.sortByDescending { it.quantidade }

        val listaHistoricos = mutableListOf<TickerHistory>()

        listaCards.forEach {
            listaHistoricos.addAll(it.listaDadosHistorico!!)
        }

        listaHistoricos.sortBy { it.data }

        //list.sortedWith(compareBy({ it.age }, { it.name }))
        return listaHistoricos.groupBy { it.data }.toList()
    }
}
