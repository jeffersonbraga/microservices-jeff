package br.com.jeffersonbraga.tickers.repository

import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import org.springframework.data.jpa.repository.JpaRepository

interface TickerHistoryRepository : JpaRepository<TickerHistory, Long>