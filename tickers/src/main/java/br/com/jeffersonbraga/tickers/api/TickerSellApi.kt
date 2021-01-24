package br.com.jeffersonbraga.tickers.api

import org.springframework.jms.core.JmsTemplate
import kotlin.Throws
import br.com.jeffersonbraga.tickers.model.sell.TickerSell
import br.com.jeffersonbraga.tickers.repository.TickerSellRepository
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ticker/sell")
class TickerSellApi {
    @Autowired
    private val jmsTemplate: JmsTemplate? = null

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val repository: TickerSellRepository? = null
    @PostMapping
    @Throws(JsonProcessingException::class)
    fun insert(@RequestBody ticker: TickerSell) : TickerSell {
        repository!!.save(ticker)
        return ticker
    }
}