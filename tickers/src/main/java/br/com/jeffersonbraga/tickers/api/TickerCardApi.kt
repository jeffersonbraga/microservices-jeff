package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.card.TickerCard
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerCurrentStatusRepository
import br.com.jeffersonbraga.tickers.repository.TickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("ticker/cards")
class TickerCardApi {

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
    fun getCards(): MutableList<TickerCard> {
        val listaTickers = tickerRepository.findAll()
        val listaHistory = historyRepository.findAll()
        val listaCompras = buyRepository.findAll()
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


            var precoMedio = 0.0
            var quantidade = 0.0
            var valorTotalInvestido = 0.0

            var valorAtual = 0.0
            var diferencaPreco = 0.0
            var percentualDiferenca = 0.0
            var valorTotalAtual = 0.0

            var qtdCompras = 0.0

            var isTickerComprado = false
            listaCompras.filter {
                itCompra ->
                itCompra.ticker == itTicker.ticker
            }?.forEach {
                itCompraTicker ->
                quantidade += itCompraTicker.quantidade!!
                precoMedio += itCompraTicker.valor!!.div(itCompraTicker.quantidade!!)
                valorTotalInvestido += itCompraTicker.valor!!
                qtdCompras = qtdCompras.plus(1.0)

                tickerCard.listaDadosHistorico = listaHistory.filter { itHistory -> itHistory.idTicker == itTicker.ticker }
                isTickerComprado = true
            }

            tickerCard.precoMedio = if (qtdCompras > 0.0) {
                precoMedio.div(qtdCompras)
            } else {
                precoMedio
            }

            tickerCard.quantidade = quantidade
            tickerCard.valorTotalInvestido = valorTotalInvestido

            val tickerCurrentStatus = listaCurrentStatus.find { it.ticker == tickerCard.ticker }
            tickerCard.valorAtual = tickerCurrentStatus?.valorAtual
            tickerCard.diferencaPreco = tickerCurrentStatus?.valorAtual!! - tickerCard.precoMedio!!
            tickerCard.valorTotalAtual = tickerCard.valorAtual!! * quantidade

            //tickerCard.valorAtual!!.div(tickerCard.precoMedio!!).multiply(BigDecimal(100)).subtract(BigDecimal(100)
            tickerCard.data = Date()
            if (isTickerComprado) {
                tickerCard.percentualDiferenca = (tickerCard.valorAtual!!.div(tickerCard.precoMedio!!) * 100.0) - 100.0
                listaCards.add(tickerCard)
            }
        }
        /*
        for (itemTicker in lista) {
            val tickerHistoryFilter = TickerHistory()
            tickerHistoryFilter.idTicker = itemTicker.ticker
        }*/

        return listaCards
    }
}
