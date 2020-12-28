package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.resume.Resume
import br.com.jeffersonbraga.tickers.repository.AporteFinanceiroRepository
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ticker/resume")
class TickerResumeApi {

    @Autowired
    private lateinit var buyRepository: BuyTickerRepository

    @Autowired
    private lateinit var aporteFinanceiroRepository: AporteFinanceiroRepository

    @CrossOrigin
    @GetMapping
    fun getResume(): Resume {

        val resume = Resume()
        resume.listaDadosCompras = buyRepository.findAll()
        resume.listaDadosAportes = aporteFinanceiroRepository.findAll()
        return resume
    }
}
