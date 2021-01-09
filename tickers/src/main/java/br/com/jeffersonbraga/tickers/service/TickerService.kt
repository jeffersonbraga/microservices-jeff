package br.com.jeffersonbraga.tickers.service

import br.com.jeffersonbraga.tickers.model.history.TickerHistory

class TickerService {

    companion object {
        fun getMediaMovel(listaDadosHistorico: List<TickerHistory>?) {

            /*
            Períodos Considerados	20	    50	    100	    200
            Valor do K (na MME)	    0,10	0,04	0,02	0,01
             */
            val fator20 = 0.10
            val fator50 = 0.04
            val fator100 = 0.02
            val fator200 = 0.01

            var periodoAtual = 0.0
            var totalAtual = 0.0
            var mme20 = listaDadosHistorico!!.subList(0, 20).sumByDouble { it.close ?: 0.0 }.div(20.0)
            var mme50 = listaDadosHistorico!!.subList(0, 50).sumByDouble { it.close ?: 0.0 }.div(50.0)
            var mme100 = if (listaDadosHistorico!!.size > 100) {
                listaDadosHistorico!!.subList(0, 100).sumByDouble { it.close ?: 0.0 }.div(100.0)
            } else {
                0.0
            }
            var mme200 = if (listaDadosHistorico!!.size > 200) {
                listaDadosHistorico!!.subList(0, 200).sumByDouble { it.close ?: 0.0 }.div(200.0)
            } else {
                0.0
            }

            listaDadosHistorico!!.forEach { itHistory ->

                periodoAtual += 1.0
                totalAtual += itHistory.close ?: 0.0
                if (periodoAtual > 20) {
                    val primeiroParam: Double = ((itHistory.close ?: 0.0).minus(mme20))
                    itHistory.mediaMovel20 = primeiroParam.times(fator20).plus(mme20)
                    println(itHistory.mediaMovel20)
                    mme20 = itHistory.mediaMovel20!!
                }

                if (periodoAtual >= 50) {
                    val primeiroParam: Double = ((itHistory.close ?: 0.0).minus(mme50))
                    itHistory.mediaMovel50 = primeiroParam.times(fator50).plus(mme50)
                    println(itHistory.mediaMovel50)
                    mme50 = itHistory.mediaMovel50!!
                }

                if (periodoAtual >= 100) {
                    val primeiroParam: Double = ((itHistory.close ?: 0.0).minus(mme100))
                    itHistory.mediaMovel100 = primeiroParam.times(fator100).plus(mme100)
                    println(itHistory.mediaMovel100)
                    mme100 = itHistory.mediaMovel100!!
                }

                if (periodoAtual >= 200) {
                    val primeiroParam: Double = ((itHistory.close ?: 0.0).minus(mme200))
                    itHistory.mediaMovel200 = primeiroParam.times(fator200).plus(mme200)
                    println(itHistory.mediaMovel200)
                    mme200 = itHistory.mediaMovel200!!
                }

                //mediaAtual = totalAtual.div(periodoAtual)
            }
        }
    }
}