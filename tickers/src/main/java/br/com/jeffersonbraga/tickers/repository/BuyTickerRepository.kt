package br.com.jeffersonbraga.tickers.repository

import br.com.jeffersonbraga.tickers.model.buyticker.BuyTicker
import org.springframework.data.jpa.repository.JpaRepository

interface BuyTickerRepository : JpaRepository<BuyTicker, Long>