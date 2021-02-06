<template>
  <div class="row">
    <div class="col-md-8">
      <ticker-list :model="itemDetail" :lista="listaTickers">
      </ticker-list>
    </div>
    <div class="col-md-4">
      <ticker-resume
        :listaVendas="listaVendas"
        :listaCompras="listaCompras"
        :listaResume="listaResume"
        :listaPatrimonio="listaPatrimonio"
        :dadosResumo="dadosResumo"></ticker-resume>
    </div>
  </div>
</template>
<script>
  import TickerList from './TickerManager/TickerList';
  import config from "@/config";
  import TickerResume from "@/pages/TickerManager/TickerResume";
  //import moment from 'moment';

  export default {

    methods : {

      processarDadosHistoricos(ticker, chartFechamentos, chartEvolucaoPatrimonio, chartVolume, volumeMedio, volumeMedioQuinzena) {

        let inserirDadosEvolucao = false;
        let quantidade = 0;
        let valorAporte = 0;
        let labels = [];

        if (ticker.listaDadosHistorico.length > 200) {
          chartFechamentos.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20", "MediaMovel50", "MediaMovel100", "MediaMovel200"]);
        } else if (ticker.listaDadosHistorico.length > 100) {
          chartFechamentos.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20", "MediaMovel50", "MediaMovel100"]);
        } else if (ticker.listaDadosHistorico.length > 50) {
          chartFechamentos.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20", "MediaMovel50"]);
        } else if (ticker.listaDadosHistorico.length > 20) {
          chartFechamentos.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20"]);
        } else {
          chartFechamentos.push(["Mes", "Fechamento", "Medio", "Compra"]);
        }

        ticker.listaDadosHistorico.forEach((valueHistorico, indexHistorico) => {

          let options = { year: 'numeric', month: 'short', day: 'numeric' };
          let label = new Date(valueHistorico.data).toLocaleString('default', options).toString().toUpperCase()
          labels.push(label);

          let comprado = null;

          ticker.listaDadosCompras.forEach((valueComprado, indexComprado) => {
            if (new Date(valueComprado.data).toLocaleString('default', options).toString().toUpperCase() == label) {
              comprado = (valueComprado.valor / valueComprado.quantidade);
              quantidade += valueComprado.quantidade;
              valorAporte += valueComprado.valor;
              inserirDadosEvolucao = true;
            }
          });

          ticker.listaDadosVendas.forEach((valueVenda, indexComprado) => {
            if (new Date(valueVenda.data).toLocaleString('default', options).toString().toUpperCase() == label) {
              quantidade -= valueVenda.quantidade;
              valorAporte -= valueVenda.valor;
            }
          });

          if (inserirDadosEvolucao) {
            chartEvolucaoPatrimonio.push([label, valorAporte, (valueHistorico.close * quantidade)]);
          }

          if (ticker.listaDadosHistorico.length > 200) {
            chartFechamentos.push([label, valueHistorico.close, ticker.precoMedio, comprado, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100, valueHistorico.mediaMovel200]);
          } else if (ticker.listaDadosHistorico.length > 100) {
            chartFechamentos.push([label, valueHistorico.close, ticker.precoMedio, comprado, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100]);
          } else if (ticker.listaDadosHistorico.length > 50) {
            chartFechamentos.push([label, valueHistorico.close, ticker.precoMedio, comprado, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50]);
          } else if (ticker.listaDadosHistorico.length > 20) {
            chartFechamentos.push([label, valueHistorico.close, ticker.precoMedio, comprado, valueHistorico.mediaMovel20]);
          } else {
            chartFechamentos.push([label, valueHistorico.close, ticker.precoMedio, comprado]);
          }

          volumeMedio += valueHistorico.volume;

          if ((ticker.listaDadosHistorico.length > 20) && indexHistorico > (ticker.listaDadosHistorico.length - 16)) {
            volumeMedioQuinzena += valueHistorico.volume;
          }
        });

        let ultimoRegistroHistorico = ticker.listaDadosHistorico[(ticker.listaDadosHistorico.length - 1)];

        let volumeMedioPeriodo = (volumeMedio / ticker.listaDadosHistorico.length);
        let volumeQuinzena = (volumeMedioQuinzena / 15);
        chartVolume.push(["Médio", volumeMedioPeriodo]);
        chartVolume.push(["15 Dias", volumeQuinzena]);
        chartVolume.push(["Ultimo", ultimoRegistroHistorico.volume]);

      },

      processarInformacoesTickers(result) {

        let listaTickers = (result.body);

        this.listaPatrimonio.push(["Ticker", "Valor"]);
        listaTickers.forEach((value, index) => {

          let volumeMedio = 0;
          let volumeMedioQuinzena = 0;
          let chartFechamentos = [];
          let chartEvolucaoPatrimonio = [];
          let chartInvestimento = [];
          let chartVolume = [];

          chartEvolucaoPatrimonio.push(["Data", "Investido", "Patrimonio"]);
          chartVolume.push(["Tipo", "Valor"]);
          chartInvestimento.push(["Tipo", "Valor"]);

          this.processarDadosHistoricos(value, chartFechamentos, chartEvolucaoPatrimonio, chartVolume, volumeMedio, volumeMedioQuinzena);

          chartInvestimento.push(["Investido", value.valorTotalInvestido]);
          chartInvestimento.push(["Atual", value.valorTotalAtual]);

          value["chartFechamentos"] = chartFechamentos;
          value["chartInvestimento"] = chartInvestimento;
          value["chartVolume"] = chartVolume;
          value["chartEvolucaoPatrimonio"] = chartEvolucaoPatrimonio;

          this.listaTickers.push(value);
          let valorPatrimonioItem = parseFloat(value.valorAtual * value.quantidade);

          this.listaPatrimonio.push([value.ticker, valorPatrimonioItem]);
          this.dadosResumo.valorTotalPatrimonio += valorPatrimonioItem;
        });

        this.itemDetail = this.listaTickers[0];
      },

      efetuarBuscaDadosTickers() {

        this.$http.get("http://localhost:8085/ticker/cards").then(result => {
          this.processarInformacoesTickers(result);
        }, error => {
          console.error(error);
        });
      },

      efetuarBuscaDadosInvestimento() {

        this.$http.get("http://localhost:8085/ticker/resume").then(result => {
          this.processarDonutChart(result.body);
        }, error => {
          console.error(error);
        });
      },

      processarDonutChart(listaProcessar) {

        let labels = [];
        let dataChartAporte = [];
        let dataChartCompras = [];
        let dataChartVendas = [];
        dataChartAporte.push(["Data", "Valor"]);
        dataChartCompras.push(["Ticker", "Valor"]);
        dataChartVendas.push(["Ticker", "Valor"]);
        listaProcessar.listaDadosAportes.forEach((valueAporte, indexHistorico) => {

          let options = { year: 'numeric', month: 'short', day: 'numeric' };
          let label = new Date(valueAporte.data).toLocaleString('default', options).toString().toUpperCase()
          labels.push(label);
          dataChartAporte.push([label, valueAporte.valor]);
          this.dadosResumo.valorTotalAportes += valueAporte.valor;
        });

        listaProcessar.listaDadosCompras.forEach((valueCompras, indexHistorico) => {

          let options = { year: 'numeric', month: 'short', day: 'numeric' };
          let label = new Date(valueCompras.data).toLocaleString('default', options).toString().toUpperCase()
          labels.push(label);
          dataChartCompras.push([valueCompras.ticker, valueCompras.valor]);
          this.dadosResumo.valorTotalInvestido += valueCompras.valor;
        });

        listaProcessar.listaDadosVendas.forEach((valueVendas, indexHistorico) => {
          let options = { year: 'numeric', month: 'short', day: 'numeric' };
          let label = new Date(valueVendas.data).toLocaleString('default', options).toString().toUpperCase()
          labels.push(label);
          dataChartVendas.push([valueVendas.ticker, valueVendas.valor]);
          this.dadosResumo.valorTotalInvestido -= valueVendas.valor;
        });

        this.listaResume = dataChartAporte;
        this.listaCompras = dataChartCompras;
        this.listaVendas = dataChartVendas;
      }
    },
    components: {
      TickerResume,
      TickerList
    },
    mounted() {
      this.dadosResumo.valorTotalAportes = 0;
      this.dadosResumo.valorTotalInvestido = 0;
      this.dadosResumo.valorTotalPatrimonio = 0;
      this.efetuarBuscaDadosTickers();
      this.efetuarBuscaDadosInvestimento();
    },
    data() {
      return {
        itemDetail : {},
        listaTickers : [],
        listaResume : [],
        listaCompras : [],
        listaVendas : [],
        listaPatrimonio : [],
        user: {
          fullName: 'Mike Andrew',
          title: 'Ceo/Co-Founder',
          description: `Do not be scared of the truth because we need to restart the human foundation in truth And I love you like Kanye loves Kanye I love Rick Owens’ bed design but the back is...`,
        },
        dadosResumo : {
          valorTotalAportes : 0,
          valorTotalInvestido : 0,
          valorTotalPatrimonio : 0
        }
      }
    }
  }
</script>
<style>
</style>
