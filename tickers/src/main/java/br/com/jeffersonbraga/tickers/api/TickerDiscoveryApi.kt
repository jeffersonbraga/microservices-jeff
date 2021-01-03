package br.com.jeffersonbraga.tickers.api

import br.com.jeffersonbraga.tickers.model.card.TickerCard
import br.com.jeffersonbraga.tickers.model.history.TickerHistory
import br.com.jeffersonbraga.tickers.model.status.TickerCurrentStatus
import br.com.jeffersonbraga.tickers.model.ticker.Ticker
import br.com.jeffersonbraga.tickers.repository.BuyTickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerCurrentStatusRepository
import org.springframework.jms.core.JmsTemplate
import br.com.jeffersonbraga.tickers.repository.TickerRepository
import br.com.jeffersonbraga.tickers.repository.TickerHistoryRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.math.BigDecimal
import yahoofinance.histquotes.HistoricalQuote
import yahoofinance.histquotes.Interval
import java.io.IOException
import java.lang.Exception
import java.util.*

@RestController
@RequestMapping("ticker/discovery")
class TickerDiscoveryApi {

    @Autowired
    private lateinit var jmsTemplate: JmsTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var repository: TickerRepository

    @Autowired
    private lateinit var repositoryHistory: TickerHistoryRepository

    @Autowired
    private lateinit var repositoryCurrentStatus: TickerCurrentStatusRepository

    @Autowired
    private lateinit var buyRepository: BuyTickerRepository

    //private String tickerList = "ABCB4.SA","EALT4.SA", "ADHM3.SA", "TIET3.SA", "TIET4.SA", "TIET11.SA", "AFLT3.SA", "BRGE3.SA", "BRGE5.SA", "BRGE6.SA", "BRGE8.SA", "BRGE11.SA", "BRGE12.SA", "CRIV3.SA", "CRIV4.SA", "RPAD3.SA", "RPAD5.SA", "RPAD6.SA", "BRIV3.SA", "BRIV4.SA", "ALSO3.SA", "AALR3.SA", "ALPA3.SA", "ALPA4.SA", "APER3.SA", "ALUP3.SA", "ALUP4.SA", "ALUP11.SA", "BAZA3.SA", "ABEV3.SA", "CBEE3.SA", "ANIM3.SA", "ARZZ3.SA", "ATOM3.SA", "AZEV3.SA", "AZEV4.SA", "AZUL4.SA", "BTOW3.SA", "B3SA3.SA", "BAHI3.SA", "BMGB4.SA", "BIDI3.SA", "BIDI4.SA", "BIDI11.SA", "BPAN4.SA", "BGIP3.SA", "BGIP4.SA", "BEES3.SA", "BEES4.SA", "BRSR3.SA", "BRSR5.SA", "BRSR6.SA", "BDLL4.SA", "BTTL3.SA", "BALM3.SA", "BALM4.SA", "BBSE3.SA", "BMKS3.SA", "BIOM3.SA", "BSEV3.SA", "BKBR3.SA", "BOBR4.SA", "BBRK3.SA", "BRML3.SA", "BRPR3.SA", "BBDC3.SA", "BBDC4.SA", "BRAP3.SA", "BRAP4.SA", "BBAS3.SA", "AGRO3.SA", "BRKM3.SA", "BRKM5.SA", "BRKM6.SA", "BSLI3.SA", "BSLI4.SA", "BRFS3.SA", "BPAC3.SA", "BPAC5.SA", "BPAC11.SA", "CAMB3.SA", "CAML3.SA", "CRFB3.SA", "CASN3.SA", "CASN4.SA", "CCRO3.SA", "CEAB3.SA", "CEBR3.SA", "CEBR5.SA", "CEBR6.SA", "CEDO3.SA", "CEDO4.SA", "CEED3.SA", "CEED4.SA", "EEEL3.SA", "EEEL4.SA", "CEGR3.SA", "CLSC3.SA", "CLSC4.SA", "GPAR3.SA", "CEPE5.SA", "CEPE6.SA", "RANI3.SA", "RANI4.SA", "MAPT4.SA", "CMIG3.SA", "CMIG4.SA", "CNTO3.SA", "CESP3.SA", "CESP5.SA", "CESP6.SA", "HGTX3.SA", "CIEL3.SA", "CEEB3.SA", "CEEB5.SA", "COCE3.SA", "COCE5.SA", "COGN3.SA", "CGAS3.SA", "CGAS5.SA", "CALI4.SA", "CSMG3.SA", "CPLE3.SA", "CPLE6.SA", "CORR4.SA", "CSAN3.SA", "RLOG3.SA", "CSRN3.SA", "CSRN5.SA", "CSRN6.SA", "CTNM3.SA", "CTNM4.SA", "CPFE3.SA", "CRDE3.SA", "CRPG3.SA", "CRPG5.SA", "CRPG6.SA", "CARD3.SA", "CVCB3.SA", "CCPR3.SA", "CYRE3.SA", "DASA3.SA", "PNVL3.SA", "PNVL4.SA", "DIRR3.SA", "DOHL3.SA", "DOHL4.SA", "DMMO3.SA", "DTCY3.SA", "DTEX3.SA", "ECOR3.SA", "EKTR3.SA", "EKTR4.SA", "ELET3.SA", "ELET5.SA", "ELET6.SA", "LIPR3.SA", "EMAE4.SA", "EMBR3.SA", "ENAT3.SA", "ECPR3.SA", "ECPR4.SA", "ENBR3.SA", "ENGI3.SA", "ENGI4.SA", "ENGI11.SA", "ENMT3.SA", "ENMT4.SA", "ENEV3.SA", "EGIE3.SA", "EQMA3B.SA", "EQPA5.SA", "EQTL3.SA", "ESTR4.SA", "ETER3.SA", "EUCA3.SA", "EUCA4.SA", "EVEN3.SA", "BAUH4.SA", "EZTC3.SA", "FHER3.SA", "FESA3.SA", "FESA4.SA", "FLRY3.SA", "FRAS3.SA", "GFSA3.SA", "GSHP3.SA", "GEPA3.SA", "GEPA4.SA", "GGBR3.SA", "GGBR4.SA", "GOAU3.SA", "GOAU4.SA", "GOLL4.SA", "GPIV33.SA", "GPCP3.SA", "CGRA3.SA", "CGRA4.SA", "GRND3.SA", "NTCO3.SA", "GUAR3.SA", "HBTS3.SA", "HBTS5.SA", "HBTS6.SA", "HAGA3.SA", "HAGA4.SA", "HAPV3.SA", "HBOR3.SA", "HETA4.SA", "HOOT4.SA", "HYPE3.SA", "IGBR3.SA", "IGSN3.SA", "IGTA3.SA", "PARD3.SA", "MEAL3.SA", "ROMI3.SA", "IDVL3.SA", "IDVL4.SA", "INEP3.SA", "INEP4.SA", "GNDI3.SA", "MYPK3.SA", "IRBR3.SA", "ITSA3.SA", "ITSA4.SA", "ITUB3.SA", "ITUB4.SA", "JBDU3.SA", "JBDU4.SA", "JBSS3.SA", "JPSA3.SA", "JPSA4.SA", "JHSF3.SA", "JFEN3.SA", "JOPA3.SA", "JOPA4.SA", "CTKA3.SA", "CTKA4.SA", "KEPL3.SA", "KLBN3.SA", "KLBN4.SA", "KLBN11.SA", "LLIS3.SA", "LIGT3.SA", "LINX3.SA", "RENT3.SA", "LCAM3.SA", "LWSA3.SA", "LOGG3.SA", "LOGN3.SA", "LAME3.SA", "LAME4.SA", "AMAR3.SA", "LREN3.SA", "LPSB3.SA", "LUPA3.SA", "MDIA3.SA", "MGLU3.SA", "MGEL4.SA", "POMO3.SA", "POMO4.SA", "MRFG3.SA", "MSPA3.SA", "MSPA4.SA", "BMEB3.SA", "BMEB4.SA", "MERC3.SA", "MERC4.SA", "BMIN3.SA", "BMIN4.SA", "MTIG4.SA", "LEVE3.SA", "FRIO3.SA", "MTSA3.SA", "MTSA4.SA", "MILS3.SA", "BEEF3.SA", "MNPR3.SA", "MTRE3.SA", "MMXM3.SA", "MOAR3.SA", "MOVI3.SA", "MRVE3.SA", "MULT3.SA", "MNDL3.SA", "NEOE3.SA", "BNBR3.SA", "NORD3.SA", "NUTR3.SA", "ODPV3.SA", "OIBR3.SA", "OIBR4.SA", "OMGE3.SA", "OSXB3.SA", "OFSA3.SA", "PCAR3.SA", "PATI3.SA", "PATI4.SA", "PEAB3.SA", "PEAB4.SA", "PMAM3.SA", "PDGR3.SA", "RPMG3.SA", "PETR3.SA", "PETR4.SA", "BRDT3.SA", "PRIO3.SA", "PTNT3.SA", "PTNT4.SA", "PINE4.SA", "PLAS3.SA", "FRTA3.SA", "PSSA3.SA", "PTBL3.SA", "POSI3.SA", "PPLA11.SA", "PRNR3.SA", "PFRM3.SA", "QUAL3.SA", "RADL3.SA", "RAPT3.SA", "RAPT4.SA", "RCSL3.SA", "RCSL4.SA", "REDE3.SA", "REDE4.SA", "RNEW3.SA", "RNEW4.SA", "RNEW11.SA", "RSUL4.SA", "RDNI3.SA", "RSID3.SA", "RAIL3.SA", "SBSP3.SA", "SAPR3.SA", "SAPR4.SA", "SAPR11.SA", "SNSY5.SA", "SANB3.SA", "SANB4.SA", "SANB11.SA", "CTSA3.SA", "CTSA4.SA", "STBP3.SA", "SCAR3.SA", "SMTO3.SA", "SLED3.SA", "SLED4.SA", "SHUL4.SA", "CSAB3.SA", "CSAB4.SA", "SEER3.SA", "CSNA3.SA", "SQIA3.SA", "SLCE3.SA", "SMLS3.SA", "SOND5.SA", "SOND6.SA", "SPRI3.SA", "SPRI5.SA", "SGPS3.SA", "AHEB3.SA", "STTR3.SA", "STKF3.SA", "SULA3.SA", "SULA4.SA", "SULA11.SA", "SUZB3.SA", "TAEE3.SA", "TAEE4.SA", "TAEE11.SA", "TASA3.SA", "TASA4.SA", "TECN3.SA", "TCSA3.SA", "TCNO3.SA", "TCNO4.SA", "TGMA3.SA", "TEKA3.SA", "TEKA4.SA", "TKNO4.SA", "TELB3.SA", "TELB4.SA", "VIVT3.SA", "VIVT4.SA", "TEND3.SA", "TESA3.SA", "TXRX3.SA", "TXRX4.SA", "SHOW3.SA", "TOTS3.SA", "TRPL3.SA", "TRPL4.SA", "LUXM4.SA", "TRIS3.SA", "TPIS3.SA", "TUPY3.SA", "UGPA3.SA", "UCAS3.SA", "UNIP3.SA", "UNIP5.SA", "UNIP6.SA", "USIM3.SA", "USIM5.SA", "USIM6.SA", "VALE3.SA", "VLID3.SA", "VVAR3.SA", "VIVA3.SA", "VIVR3.SA", "VULC3.SA", "WEGE3.SA", "MWET3.SA", "MWET4.SA", "WHRL3.SA", "WHRL4.SA", "WSON33.SA", "WIZS3.SA", "WLMM3.SA", "WLMM4.SA", "YDUQ3.SA";
    @CrossOrigin
    @GetMapping
    private fun tickerManager(): MutableList<TickerCard>? {
        println("tickerManager")
        //val listSearch = arrayOf("ABCB4.SA", "EALT4.SA", "ADHM3.SA", "TIET3.SA", "TIET4.SA", "TIET11.SA", "AFLT3.SA", "BRGE3.SA", "BRGE5.SA", "BRGE6.SA", "BRGE8.SA", "BRGE11.SA", "BRGE12.SA", "CRIV3.SA", "CRIV4.SA", "RPAD3.SA", "RPAD5.SA", "RPAD6.SA", "BRIV3.SA", "BRIV4.SA", "ALSO3.SA", "AALR3.SA", "ALPA3.SA", "ALPA4.SA", "APER3.SA", "ALUP3.SA", "ALUP4.SA", "ALUP11.SA", "BAZA3.SA", "ABEV3.SA", "CBEE3.SA", "ANIM3.SA", "ARZZ3.SA", "ATOM3.SA", "AZEV3.SA", "AZEV4.SA", "AZUL4.SA", "BTOW3.SA", "B3SA3.SA", "BAHI3.SA", "BMGB4.SA", "BIDI3.SA", "BIDI4.SA", "BIDI11.SA", "BPAN4.SA", "BGIP3.SA", "BGIP4.SA", "BEES3.SA", "BEES4.SA", "BRSR3.SA", "BRSR5.SA", "BRSR6.SA", "BDLL4.SA", "BTTL3.SA", "BALM3.SA", "BALM4.SA", "BBSE3.SA", "BMKS3.SA", "BIOM3.SA", "BSEV3.SA", "BKBR3.SA", "BOBR4.SA", "BBRK3.SA", "BRML3.SA", "BRPR3.SA", "BBDC3.SA", "BBDC4.SA", "BRAP3.SA", "BRAP4.SA", "BBAS3.SA", "AGRO3.SA", "BRKM3.SA", "BRKM5.SA", "BRKM6.SA", "BSLI3.SA", "BSLI4.SA", "BRFS3.SA", "BPAC3.SA", "BPAC5.SA", "BPAC11.SA", "CAMB3.SA", "CAML3.SA", "CRFB3.SA", "CASN3.SA", "CASN4.SA", "CCRO3.SA", "CEAB3.SA", "CEBR3.SA", "CEBR5.SA", "CEBR6.SA", "CEDO3.SA", "CEDO4.SA", "CEED3.SA", "CEED4.SA", "EEEL3.SA", "EEEL4.SA", "CEGR3.SA", "CLSC3.SA", "CLSC4.SA", "GPAR3.SA", "CEPE5.SA", "CEPE6.SA", "RANI3.SA", "RANI4.SA", "MAPT4.SA", "CMIG3.SA", "CMIG4.SA", "CNTO3.SA", "CESP3.SA", "CESP5.SA", "CESP6.SA", "HGTX3.SA", "CIEL3.SA", "CEEB3.SA", "CEEB5.SA", "COCE3.SA", "COCE5.SA", "COGN3.SA", "CGAS3.SA", "CGAS5.SA", "CALI4.SA", "CSMG3.SA", "CPLE3.SA", "CPLE6.SA", "CORR4.SA", "CSAN3.SA", "RLOG3.SA", "CSRN3.SA", "CSRN5.SA", "CSRN6.SA", "CTNM3.SA", "CTNM4.SA", "CPFE3.SA", "CRDE3.SA", "CRPG3.SA", "CRPG5.SA", "CRPG6.SA", "CARD3.SA", "CVCB3.SA", "CCPR3.SA", "CYRE3.SA", "DASA3.SA", "PNVL3.SA", "PNVL4.SA", "DIRR3.SA", "DOHL3.SA", "DOHL4.SA", "DMMO3.SA", "DTCY3.SA", "DTEX3.SA", "ECOR3.SA", "EKTR3.SA", "EKTR4.SA", "ELET3.SA", "ELET5.SA", "ELET6.SA", "LIPR3.SA", "EMAE4.SA", "EMBR3.SA", "ENAT3.SA", "ECPR3.SA", "ECPR4.SA", "ENBR3.SA", "ENGI3.SA", "ENGI4.SA", "ENGI11.SA", "ENMT3.SA", "ENMT4.SA", "ENEV3.SA", "EGIE3.SA", "EQMA3B.SA", "EQPA5.SA", "EQTL3.SA", "ESTR4.SA", "ETER3.SA", "EUCA3.SA", "EUCA4.SA", "EVEN3.SA", "BAUH4.SA", "EZTC3.SA", "FHER3.SA", "FESA3.SA", "FESA4.SA", "FLRY3.SA", "FRAS3.SA", "GFSA3.SA", "GSHP3.SA", "GEPA3.SA", "GEPA4.SA", "GGBR3.SA", "GGBR4.SA", "GOAU3.SA", "GOAU4.SA", "GOLL4.SA", "GPIV33.SA", "GPCP3.SA", "CGRA3.SA", "CGRA4.SA", "GRND3.SA", "NTCO3.SA", "GUAR3.SA", "HBTS5.SA", "HAGA3.SA", "HAGA4.SA", "HAPV3.SA", "HBOR3.SA", "HETA4.SA", "HOOT4.SA", "HYPE3.SA", "IGBR3.SA", "IGTA3.SA", "PARD3.SA", "MEAL3.SA", "ROMI3.SA", "IDVL3.SA", "IDVL4.SA", "INEP3.SA", "INEP4.SA", "GNDI3.SA", "MYPK3.SA", "IRBR3.SA", "ITSA3.SA", "ITSA4.SA", "ITUB3.SA", "ITUB4.SA", "JBDU3.SA", "JBDU4.SA", "JBSS3.SA", "JPSA3.SA", "JHSF3.SA", "JFEN3.SA", "JOPA3.SA", "JOPA4.SA", "CTKA3.SA", "CTKA4.SA", "KEPL3.SA", "KLBN3.SA", "KLBN4.SA", "KLBN11.SA", "LLIS3.SA", "LIGT3.SA", "LINX3.SA", "RENT3.SA", "LCAM3.SA", "LWSA3.SA", "LOGG3.SA", "LOGN3.SA", "LAME3.SA", "LAME4.SA", "AMAR3.SA", "LREN3.SA", "LPSB3.SA", "LUPA3.SA", "MDIA3.SA", "MGLU3.SA", "MGEL4.SA", "POMO3.SA", "POMO4.SA", "MRFG3.SA", "MSPA3.SA", "MSPA4.SA", "BMEB3.SA", "BMEB4.SA", "MERC3.SA", "MERC4.SA", "BMIN3.SA", "BMIN4.SA", "MTIG4.SA", "LEVE3.SA", "FRIO3.SA", "MTSA3.SA", "MTSA4.SA", "MILS3.SA", "BEEF3.SA", "MNPR3.SA", "MTRE3.SA", "MMXM3.SA", "MOAR3.SA", "MOVI3.SA", "MRVE3.SA", "MULT3.SA", "MNDL3.SA", "NEOE3.SA", "BNBR3.SA", "NORD3.SA", "NUTR3.SA", "ODPV3.SA", "OIBR3.SA", "OIBR4.SA", "OMGE3.SA", "OSXB3.SA", "OFSA3.SA", "PCAR3.SA", "PATI3.SA", "PATI4.SA", "PEAB3.SA", "PEAB4.SA", "PMAM3.SA", "PDGR3.SA", "RPMG3.SA", "PETR3.SA", "PETR4.SA", "BRDT3.SA", "PRIO3.SA", "PTNT3.SA", "PTNT4.SA", "PINE4.SA", "PLAS3.SA", "FRTA3.SA", "PSSA3.SA", "PTBL3.SA", "POSI3.SA", "PPLA11.SA", "PRNR3.SA", "PFRM3.SA", "QUAL3.SA", "RADL3.SA", "RAPT3.SA", "RAPT4.SA", "RCSL3.SA", "RCSL4.SA", "REDE3.SA", "RNEW3.SA", "RNEW4.SA", "RNEW11.SA", "RSUL4.SA", "RDNI3.SA", "RSID3.SA", "RAIL3.SA", "SBSP3.SA", "SAPR3.SA", "SAPR4.SA", "SAPR11.SA", "SNSY5.SA", "SANB3.SA", "SANB4.SA", "SANB11.SA", "CTSA3.SA", "CTSA4.SA", "STBP3.SA", "SCAR3.SA", "SMTO3.SA", "SLED3.SA", "SLED4.SA", "SHUL4.SA", "CSAB3.SA", "CSAB4.SA", "SEER3.SA", "CSNA3.SA", "SQIA3.SA", "SLCE3.SA", "SMLS3.SA", "SOND5.SA", "SOND6.SA", "SPRI3.SA", "SPRI5.SA", "SGPS3.SA", "AHEB3.SA", "SULA3.SA", "SULA4.SA", "SULA11.SA", "SUZB3.SA", "TAEE3.SA", "TAEE4.SA", "TAEE11.SA", "TASA3.SA", "TASA4.SA", "TECN3.SA", "TCSA3.SA", "TCNO3.SA", "TCNO4.SA", "TGMA3.SA", "TEKA3.SA", "TEKA4.SA", "TKNO4.SA", "TELB3.SA", "TELB4.SA", "VIVT3.SA", "VIVT4.SA", "TEND3.SA", "TESA3.SA", "TXRX3.SA", "TXRX4.SA", "SHOW3.SA", "TOTS3.SA", "TRPL3.SA", "TRPL4.SA", "LUXM4.SA", "TRIS3.SA", "TPIS3.SA", "TUPY3.SA", "UGPA3.SA", "UCAS3.SA", "UNIP3.SA", "UNIP5.SA", "UNIP6.SA", "USIM3.SA", "USIM5.SA", "USIM6.SA", "VALE3.SA", "VLID3.SA", "VVAR3.SA", "VIVA3.SA", "VIVR3.SA", "VULC3.SA", "WEGE3.SA", "MWET3.SA", "MWET4.SA", "WHRL3.SA", "WHRL4.SA", "WSON33.SA", "WIZS3.SA", "WLMM3.SA", "WLMM4.SA", "YDUQ3.SA")
        val listSearch = arrayOf("TIET3.SA", "TIET4.SA", "TIET11.SA", "VVAR3.SA", "VIVA3.SA", "VIVR3.SA", "WEGE3.SA")
        val listaTickerDiscovery = mutableListOf<TickerCard>()
        try {

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, -12)
            var stockList = YahooFinance.get(listSearch, calendar, Interval.DAILY)
            stockList.entries.forEach {
                var stock = it.value

                val price = stock.quote.price.toDouble()
                val previousClose = stock.quote.previousClose.toDouble()
                val change = stock.quote.changeInPercent.toDouble()
                val avg50 = stock.quote.priceAvg50.toDouble()
                val avg200 = stock.quote.priceAvg200.toDouble()

                val peg = stock.stats.peg
                val dividend = stock.dividend.annualYieldPercent
                val tickerCard = TickerCard()
                val ticker = Ticker()
                ticker.ticker = stock.symbol
                tickerCard.ticker = stock.symbol
                //repository!!.save(ticker)
                val tickerJson = objectMapper!!.writeValueAsString(ticker)
                try {

                    tickerCard.ticker = ticker.ticker
                    tickerCard.valorAtual = price
                    /*
                    tickerCard.fechamentoAnterior = previousClose
                    tickerCard.mudanca = change
                    tickerCard.avg50 = avg50
                    tickerCard.avg200 = avg200
                    */

                    //repositoryCurrentStatus.save(tickerCurrentStatus)
                    tickerCard.listaDadosHistorico = mutableListOf()
                    for (itHistory in stock.history) {
                        val tickerHistory = TickerHistory()
                        tickerHistory.idTicker = ticker.ticker
                        tickerHistory.data = itHistory.date.time
                        if (itHistory.close != null) {
                            tickerHistory.close = itHistory.close.toDouble()
                        } else {
                            tickerHistory.close = 0.0
                        }
                        if (itHistory.low != null) {
                            tickerHistory.low = itHistory.low.toDouble()
                        } else {
                            tickerHistory.low = 0.0
                        }
                        if (itHistory.high != null) {
                            tickerHistory.high = itHistory.high.toDouble()
                        } else {
                            tickerHistory.high = 0.0
                        }
                        if (itHistory.open != null) {
                            tickerHistory.open = itHistory.open.toDouble()
                        } else {
                            tickerHistory.open = 0.0
                        }
                        if (itHistory.volume != null) {
                            tickerHistory.volume = itHistory.volume.toDouble()
                        } else {
                            tickerHistory.volume = 0.0
                        }

                        if (tickerHistory.close!! > 0.0) {
                            tickerCard.listaDadosHistorico!!.add(tickerHistory)
                        }
                        //repositoryHistory.save(tickerHistory)
                    }

                    getMediaMovel(tickerCard.listaDadosHistorico)
                    listaTickerDiscovery.add(tickerCard)
                } catch (e1: Exception) {
                    e1.printStackTrace()
                }
                //jmsTemplate.convertAndSend("queue.ticker.insert", tickerJson)
                //stock.print()
            }

            return listaTickerDiscovery
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun getMediaMovel(listaDadosHistorico: List<TickerHistory>?) {

        /*
        Períodos Considerados	20	    50	    100	    200
        Valor do K (na MME)	    0,10	0,04	0,02	0,01
         */

        val fator20 = 0.10
        val fator50 = 0.04
        val fator100 = 0.02
        val fator200 = 0.01

        var periodoAtual    = 0.0
        var totalAtual      = 0.0
        var mme20       = listaDadosHistorico!!.subList(0, 20).sumByDouble { it.close?:0.0 }.div(20.0)
        var mme50       = listaDadosHistorico!!.subList(0, 50).sumByDouble { it.close?:0.0 }.div(50.0)
        var mme100      = listaDadosHistorico!!.subList(0, 100).sumByDouble { it.close?:0.0 }.div(100.0)
        var mme200      = listaDadosHistorico!!.subList(0, 200).sumByDouble { it.close?:0.0 }.div(200.0)
        listaDadosHistorico!!.forEach { itHistory ->

            periodoAtual += 1.0
            totalAtual += itHistory.close?:0.0
            if (periodoAtual > 20) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme20))
                itHistory.mediaMovel20 = primeiroParam.times(fator20).plus(mme20)
                println(itHistory.mediaMovel20)
                mme20 = itHistory.mediaMovel20!!
            }

            if (periodoAtual >= 50) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme50))
                itHistory.mediaMovel50 = primeiroParam.times(fator50).plus(mme50)
                println(itHistory.mediaMovel50)
                mme50 = itHistory.mediaMovel50!!
            }

            if (periodoAtual >= 100) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme100))
                itHistory.mediaMovel100 = primeiroParam.times(fator100).plus(mme100)
                println(itHistory.mediaMovel100)
                mme100 = itHistory.mediaMovel100!!
            }

            if (periodoAtual >= 200) {
                val primeiroParam : Double = ((itHistory.close?:0.0).minus(mme200))
                itHistory.mediaMovel200 = primeiroParam.times(fator200).plus(mme200)
                println(itHistory.mediaMovel200)
                mme200 = itHistory.mediaMovel200!!
            }

            //mediaAtual = totalAtual.div(periodoAtual)
        }

    }
}
