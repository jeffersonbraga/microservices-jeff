<template>
  <div>
    <div class="row">
      <div class="col-md-12">
        <ticker-agrupados :lista="listaTickers">
        </ticker-agrupados>
      </div>
    </div>
    <div class="row">
      <div class="col-md-8">
        <base-input
                    placeholder="Tickers Buscar">
        </base-input>
      </div>
      <div class="col-md-4">
        <base-button class="align-middle" type="primary" fill @click="efetuarBuscaSelecionados()">Buscar</base-button>
      </div>
      <div class="col-md-12">
        <ticker-agrupados :lista="listaTickers">
        </ticker-agrupados>
      </div>
    </div>
  </div>
</template>
<script>
  import TickerAgrupados from './TickerAgrupados/TickerAgrupados';

  export default {

    methods : {

      efetuarBuscaSelecionados() {

        console.log("efetuarBuscaSelecionados");
      },
      efetuarBuscaDadosTickers() {

        this.$http.get("http://localhost:8085/ticker/agrupados").then(result => {
          //this.listaTickers = (result.body);
          let listaAux = (result.body);
          let labels = [];
          let dataChartGoogle = [];
          //dataChartGoogle.push(["Data", "itsa", "totv", "sapr", "slce", "petz", "mglu", "mxrf", "cogn"]);
          dataChartGoogle.push(["Data", "itsa", "slce", "petz", "mglu", "mxrf", "cogn"]);

          listaAux.forEach((value, index) => {

            let options = { year: 'numeric', month: 'short', day: 'numeric' };
            let label = new Date(value.first).toLocaleString('default', options).toString().toUpperCase();
            labels.push(label);

            let itsa = null;
            let totv = null;
            let sapr = null;
            let slce = null;
            let petz = null;
            let mglu = null;
            let mxrf = null;
            let cogn = null;

            value.second.forEach((valueItem, indexItem) => {

              if (valueItem.idTicker == "ITSA4.SA") {
                itsa = valueItem.close;
              } else if (valueItem.idTicker == "TOTS3.SA") {
                //totv = valueItem.close;
              } else if (valueItem.idTicker == "SAPR11.SA") {
                //sapr = valueItem.close;
              } else if (valueItem.idTicker == "MGLU3.SA") {
                mglu = valueItem.close;
              } else if (valueItem.idTicker == "SLCE3.SA") {
                slce = valueItem.close;
              } else if (valueItem.idTicker == "COGN3.SA") {
                cogn = valueItem.close;
              } else if (valueItem.idTicker == "MXRF11.SA") {
                mxrf = valueItem.close;
              } else if (valueItem.idTicker == "PETZ3.SA") {
                petz = valueItem.close;
              }
            });

            //dataChartGoogle.push([label, itsa, totv, sapr, slce, petz, mglu, mxrf, cogn]);
            dataChartGoogle.push([label, itsa, slce, petz, mglu, mxrf, cogn]);
          });

          this.listaTickers = dataChartGoogle;
        }, error => {
          console.error(error);
        });
      },
    },
    components: {
      TickerAgrupados
    },
    mounted() {
      this.efetuarBuscaDadosTickers();
    },
    data() {
      return {
        itemDetail : {},
        listaTickers : []
      }
    },
    props: {
      model:{
        type: Object,
        default: () => {
          return {};
        }
      }
    }
  }
</script>
<style>
</style>
