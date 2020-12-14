package br.com.jeffersonbraga.tickers.repository;

import br.com.jeffersonbraga.tickers.model.status.TickerCurrentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TickerCurrentStatusRepository extends JpaRepository<TickerCurrentStatus, Long> {}
