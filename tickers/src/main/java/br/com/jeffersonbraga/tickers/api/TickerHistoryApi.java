package br.com.jeffersonbraga.tickers.api;

import br.com.jeffersonbraga.tickers.model.history.TickerHistory;
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ticker/history")
public class TickerHistoryApi {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TickerHistoryRepository repository;

    @GetMapping
    public List<TickerHistory> all() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<TickerHistory> one(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public TickerHistory insert(@RequestBody TickerHistory ticker) throws JsonProcessingException {
        repository.save(ticker);

        var tickerJson = objectMapper.writeValueAsString(ticker);
        jmsTemplate.convertAndSend("queue.ticker.insert", tickerJson);

        return ticker;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
