package br.com.jeffersonbraga.tickers.model.aporte

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "APORTE_FINANCEIRO")
class AporteFinanceiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var iD: Int? = null

    @Column
    var data : Date? = null

    @Column
    var valor : Double? = null
}