<template>
  <div class="row">
    <div class="col-md-8">
      <ticker-list :model="itemDetail" :lista="listaTickers">
      </ticker-list>
    </div>
    <div class="col-md-4">
      <ticker-resume :listaCompras="listaCompras" :listaResume="listaResume" :listaPatrimonio="listaPatrimonio" :dadosResumo="dadosResumo"></ticker-resume>
    </div>
  </div>
</template>
<script>
  import TickerList from './TickerVolume/TickerList';
  import config from "@/config";
  import TickerResume from "@/pages/TickerVolume/TickerResume";
  //import moment from 'moment';

  export default {

    methods : {
      efetuarBuscaDadosTickers() {

        this.$http.get("http://localhost:8085/ticker/volume").then(result => {
          //this.listaTickers = (result.body);
          let listaAux = (result.body);

          this.listaPatrimonio.push(["Ticker", "Valor"]);
          listaAux.forEach((value, index) => {

            let labels = [];
            let dataChartGoogle = [];

            if (value.listaDadosHistorico.length > 200) {
              dataChartGoogle.push(["Mes", "Volume", "MediaMovel20", "MediaMovel50", "MediaMovel100", "MediaMovel200"]);
            } else if (value.listaDadosHistorico.length > 100) {
              dataChartGoogle.push(["Mes", "Volume", "MediaMovel20", "MediaMovel50", "MediaMovel100"]);
            } else if (value.listaDadosHistorico.length > 50) {
              dataChartGoogle.push(["Mes", "Volume", "MediaMovel20", "MediaMovel50"]);
            } else {
              dataChartGoogle.push(["Mes", "Volume", "MediaMovel20"]);
            }

            //dataChartGoogle.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20", "MediaMovel50", "MediaMovel100", "MediaMovel200"]);
            value.listaDadosHistorico.forEach((valueHistorico, indexHistorico) => {
              //console.log(moment(String(valueHistorico.data)).format('MM/DD/YYYY hh:mm'));

              // request a weekday along with a long date
              let options = { year: 'numeric', month: 'short', day: 'numeric' };
              let label = new Date(valueHistorico.data).toLocaleString('default', options).toString().toUpperCase()
              labels.push(label);

              let comprado = null;

              value.listaDadosCompras.forEach((valueComprado, indexComprado) => {
                if (new Date(valueComprado.data).toLocaleString('default', options).toString().toUpperCase() == label) {
                  comprado = (valueComprado.valor / valueComprado.quantidade);
                }
              });

              //dataChartGoogle.push([label, valueHistorico.close, value.precoMedio, comprado, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100, valueHistorico.mediaMovel200]);
              if (value.listaDadosHistorico.length > 200) {
                dataChartGoogle.push([label, valueHistorico.volume, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100, valueHistorico.mediaMovel200]);
              } else if (value.listaDadosHistorico.length > 100) {
                dataChartGoogle.push([label, valueHistorico.volume, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100]);
              } else if (value.listaDadosHistorico.length > 50) {
                dataChartGoogle.push([label, valueHistorico.volume, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50]);
              } else {
                dataChartGoogle.push([label, valueHistorico.volume, valueHistorico.mediaMovel20]);
              }

            });

            value["googleChartData"] = dataChartGoogle;
            this.listaTickers.push(value);
            let valorPatrimonioItem = parseFloat(value.valorAtual * value.quantidade);

            this.listaPatrimonio.push([value.ticker, valorPatrimonioItem]);
            this.dadosResumo.valorTotalPatrimonio += valorPatrimonioItem;
          });

          this.itemDetail = this.listaTickers[0];
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
        dataChartAporte.push(["Data", "Valor"]);
        dataChartCompras.push(["Ticker", "Valor"]);
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

        this.listaResume = dataChartAporte;
        this.listaCompras = dataChartCompras;
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
