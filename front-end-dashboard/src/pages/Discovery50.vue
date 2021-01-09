<template>
  <div class="row">
    <div class="col-md-12">
      <ticker-list :model="itemDetail" :lista="listaTickers">
      </ticker-list>
    </div>
  </div>
</template>
<script>
  import TickerList from './TickerDiscovery/TickerList';

  export default {

    methods : {
      efetuarBuscaDadosTickers() {

        this.$http.get("http://localhost:8085/ticker/discovery?search=ibrx50").then(result => {
          //this.listaTickers = (result.body);
          let listaAux = (result.body);

          this.listaPatrimonio.push(["Ticker", "Valor"]);
          listaAux.forEach((value, index) => {

            let labels = [];
            let dataChartGoogle = [];
            dataChartGoogle.push(["Mes", "Fechamento", "MediaMovel20", "MediaMovel50", "MediaMovel100", "MediaMovel200"]);
            value.listaDadosHistorico.forEach((valueHistorico, indexHistorico) => {
              //console.log(moment(String(valueHistorico.data)).format('MM/DD/YYYY hh:mm'));

              // request a weekday along with a long date
              let options = { year: 'numeric', month: 'short', day: 'numeric' };
              let label = new Date(valueHistorico.data).toLocaleString('default', options).toString().toUpperCase()
              labels.push(label);

              dataChartGoogle.push([label, valueHistorico.close, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100, valueHistorico.mediaMovel200]);
            });

            value["googleChartData"] = dataChartGoogle;
            this.listaTickers.push(value);
          });

          this.itemDetail = this.listaTickers[0];
        }, error => {
          console.error(error);
        });
      },
    },
    components: {
      TickerList
    },
    mounted() {
      this.dadosResumo.valorTotalAportes = 0;
      this.dadosResumo.valorTotalInvestido = 0;
      this.dadosResumo.valorTotalPatrimonio = 0;
      this.efetuarBuscaDadosTickers();
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
