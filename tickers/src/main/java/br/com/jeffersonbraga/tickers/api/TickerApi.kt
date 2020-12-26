package br.com.jeffersonbraga.tickers.api

import org.springframework.jms.core.JmsTemplate
import br.com.jeffersonbraga.tickers.repository.TickerRepository
import kotlin.Throws
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.model.ticker.Ticker
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("ticker")
class TickerApi {
    @Autowired
    private val jmsTemplate: JmsTemplate? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val repository: TickerRepository? = null
    @GetMapping
    fun all(): List<Ticker> {
        return repository!!.findAll()
    }

    @GetMapping("{id}")
    fun one(@PathVariable("id") id: Long): Optional<Ticker> {
        return repository!!.findById(id)
    }

    @PostMapping
    @Throws(JsonProcessingException::class)
    fun insert(@RequestBody ticker: Ticker): Ticker {
        repository!!.save(ticker)
        val tickerJson = objectMapper!!.writeValueAsString(ticker)
        jmsTemplate!!.convertAndSend("queue.ticker.insert", tickerJson)
        return ticker
    }

    @DeleteMapping("{id}")
    fun delete(@PathVariable("id") id: Long) {
        repository!!.deleteById(id)
    }
}