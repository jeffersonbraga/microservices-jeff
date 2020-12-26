package br.com.jeffersonbraga.tickers.api

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("ticker/resume")
class TickerResumeApi {

    @CrossOrigin
    @GetMapping
    fun getResume() {

    }
}
