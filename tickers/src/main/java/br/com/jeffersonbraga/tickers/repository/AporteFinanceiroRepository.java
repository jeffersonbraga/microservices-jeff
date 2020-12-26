package br.com.jeffersonbraga.tickers.repository;

import br.com.jeffersonbraga.tickers.model.aporte.AporteFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AporteFinanceiroRepository extends JpaRepository<AporteFinanceiro, Long> {}
