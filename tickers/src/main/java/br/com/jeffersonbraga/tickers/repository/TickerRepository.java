package br.com.jeffersonbraga.tickers.repository;

import br.com.jeffersonbraga.tickers.model.ticker.Ticker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerRepository extends JpaRepository<Ticker, Long> {}
