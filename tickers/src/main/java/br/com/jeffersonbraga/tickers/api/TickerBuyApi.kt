package br.com.jeffersonbraga.tickers.api;

import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker;
import br.com.jeffersonbraga.tickers.model.ticker.Ticker;
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository;
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticker/buy")
public class TickerBuyApi {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BuyTickerRepository repository;

    @PostMapping
    public BuyTicker insert(@RequestBody BuyTicker ticker) throws JsonProcessingException {
        repository.save(ticker);

        var tickerJson = objectMapper.writeValueAsString(ticker);
        jmsTemplate.convertAndSend("queue.buy_ticker.insert", tickerJson);

        return ticker;
    }
}
