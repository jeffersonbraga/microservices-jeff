package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.aporte.AporteFinanceiro
import br.com.jeffersonbraga.tickers.repository.AporteFinanceiroRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("ticker/aporte")
class AporteApi {

    @Autowired
    lateinit var aporteRepository: AporteFinanceiroRepository

    @CrossOrigin
    @PostMapping
    fun addAporte(@RequestBody aporte : AporteFinanceiro?) {
        aporteRepository.save(aporte)
    }
}