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
                tickerCard.listaDadosHistorico = listaHistory.filter { itHistory -> itHistory.idTicker == itTicker.ticker }

                isTickerComprado = true
            }

            tickerCard.precoMedio = valorTotalInvestido.div(quantidade)

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
                tickerCard.listaDadosHistorico?.sortedBy { it.data }
                getMediaMovel(tickerCard.listaDadosHistorico)
                listaCards.add(tickerCard)
            }
        }
        /*
        for (itemTicker in lista) {
            val tickerHistoryFilter = TickerHistory()
            tickerHistoryFilter.idTicker = itemTicker.ticker
        }*/
        listaCards.sortBy { it.ticker }
        return listaCards
    }

    fun getMediaMovel(listaDadosHistorico: List<TickerHistory>?) {

        /*
        Períodos Considerados	20	    50	    100	    200
        Valor do K (na MME)	    0,10	0,04	0,02	0,01
         */

        val fator20 = 0.10
        val fator50 = 0.04
        val fator100 = 0.02
        val fator200 = 0.01

        var periodoAtual    = 0.0
        var totalAtual      = 0.0
        var mme20       = listaDadosHistorico!!.subList(0, 20).sumByDouble { it.close?:0.0 }.div(20.0)
        var mme50       = listaDadosHistorico!!.subList(0, 50).sumByDouble { it.close?:0.0 }.div(50.0)
        var mme100      = listaDadosHistorico!!.subList(0, 100).sumByDouble { it.close?:0.0 }.div(100.0)
        var mme200      = listaDadosHistorico!!.subList(0, 200).sumByDouble { it.close?:0.0 }.div(200.0)
        listaDadosHistorico!!.forEach { itHistory ->

            periodoAtual += 1.0
            totalAtual += itHistory.close?:0.0
            if (periodoAtual > 20) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme20))
                itHistory.mediaMovel20 = primeiroParam.times(fator20).plus(mme20)
                println(itHistory.mediaMovel20)
                mme20 = itHistory.mediaMovel20!!
            }

            if (periodoAtual >= 50) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme50))
                itHistory.mediaMovel50 = primeiroParam.times(fator50).plus(mme50)
                println(itHistory.mediaMovel50)
                mme50 = itHistory.mediaMovel50!!
            }

            if (periodoAtual >= 100) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme100))
                itHistory.mediaMovel100 = primeiroParam.times(fator100).plus(mme100)
                println(itHistory.mediaMovel100)
                mme100 = itHistory.mediaMovel100!!
            }

            if (periodoAtual >= 200) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme200))
                itHistory.mediaMovel200 = primeiroParam.times(fator200).plus(mme200)
                println(itHistory.mediaMovel200)
                mme200 = itHistory.mediaMovel200!!
            }

            //mediaAtual = totalAtual.div(periodoAtual)
        }

    }
}
