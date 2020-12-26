<template>
  <div class="row">
    <div class="col-md-8">
      <ticker-list :model="itemDetail" :lista="listaTickers">
      </ticker-list>
    </div>
    <div class="col-md-4">
      <ticker-resume></ticker-resume>
    </div>
  </div>
</template>
<script>
  import TickerList from './TickerManager/TickerList';
  import config from "@/config";
  import TickerResume from "@/pages/TickerManager/TickerResume";
  //import moment from 'moment';

  export default {
    components: {
      TickerResume,
      TickerList
    },
    mounted() {

      this.$http.get("http://localhost:8085/ticker/cards").then(result => {
        //this.listaTickers = (result.body);
        let listaAux = (result.body);

        listaAux.forEach((value, index) => {

          let labels = [];
          let dataChartGoogle = [];
          dataChartGoogle.push(["Mes", "Fechamento", "Medio", "Compra", "MediaMovel20", "MediaMovel50", "MediaMovel100", "MediaMovel200"]);
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

            dataChartGoogle.push([label, valueHistorico.close, value.precoMedio, comprado, valueHistorico.mediaMovel20, valueHistorico.mediaMovel50, valueHistorico.mediaMovel100, valueHistorico.mediaMovel200]);
          });

          value["googleChartData"] = dataChartGoogle;
          this.listaTickers.push(value);
        });

        this.itemDetail = this.listaTickers[0];
      }, error => {
        console.error(error);
      });
    },
    data() {
      return {
        itemDetail : {},
        listaTickers : [],
        user: {
          fullName: 'Mike Andrew',
          title: 'Ceo/Co-Founder',
          description: `Do not be scared of the truth because we need to restart the human foundation in truth And I love you like Kanye loves Kanye I love Rick Owens’ bed design but the back is...`,
        }
      }
    }
  }
</script>
<style>
</style>
