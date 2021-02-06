package br.com.jeffersonbraga.tickers

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
open class TickersApplication

fun main(args: Array<String>) {

    val app = SpringApplicationBuilder(TickersApplication::class.java)
    app.build().addListeners(ApplicationPidFileWriter("./bin/shutdown.pid"))
    app.run(*args)
}
