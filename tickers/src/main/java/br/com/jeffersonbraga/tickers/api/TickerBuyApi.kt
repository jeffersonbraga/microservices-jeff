package br.com.jeffersonbraga.tickers.api

import org.springframework.jms.core.JmsTemplate
import br.com.jeffersonbraga.tickers.repository.TickerRepository
import kotlin.Throws
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ticker/buy")
class TickerBuyApi {
    @Autowired
    private val jmsTemplate: JmsTemplate? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val repository: BuyTickerRepository? = null
    @PostMapping
    @Throws(JsonProcessingException::class)
    fun insert(@RequestBody ticker: BuyTicker): BuyTicker {
        repository!!.save(ticker)
        return ticker
    }
}