package br.com.jeffersonbraga.tickers.repository

import br.com.jeffersonbraga.tickers.model.status.TickerCurrentStatus
import org.springframework.data.jpa.repository.JpaRepository

interface TickerCurrentStatusRepository : JpaRepository<TickerCurrentStatus, Long>