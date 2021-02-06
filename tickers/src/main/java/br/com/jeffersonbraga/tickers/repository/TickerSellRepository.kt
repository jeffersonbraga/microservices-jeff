package br.com.jeffersonbraga.tickers.repository

import br.com.jeffersonbraga.tickers.model.sell.TickerSell
import org.springframework.data.jpa.repository.JpaRepository

interface TickerSellRepository : JpaRepository<TickerSell, Long>