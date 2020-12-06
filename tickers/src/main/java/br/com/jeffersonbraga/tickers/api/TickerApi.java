package br.com.jeffersonbraga.tickers.api;

import br.com.jeffersonbraga.tickers.model.ticker.Ticker;
import br.com.jeffersonbraga.tickers.repository.TickerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("ticker")
public class TickerApi {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TickerRepository repository;

    @GetMapping
    public List<Ticker> all() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Ticker> one(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Ticker insert(@RequestBody Ticker ticker) throws JsonProcessingException {
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
