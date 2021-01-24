<template>
  <card type="user">
    <p class="card-text">
    </p>
    <div class="author">
      <div class="block block-one"></div>
      <div class="block block-two"></div>
      <div class="block block-three"></div>
      <div class="block block-four"></div>

      <p class="description">
        Resumo
      </p>
    </div>
    <p></p>
    <p class="card-description">
      Total Aportes: {{dadosResumo.valorTotalAportes}}
    </p>
    <p class="card-description">
      Total Investido: {{dadosResumo.valorTotalInvestido}}
    </p>
    <p class="card-description">
      Patrimônio atual: {{dadosResumo.valorTotalPatrimonio}}
    </p>
    <p class="card-description">
      Valorizacão: {{dadosResumo.valorTotalPatrimonio - dadosResumo.valorTotalInvestido}}
    </p>

    <card type="user">
      <p class="description text-uppercase">
        Aportes
      </p>
      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="PieChart"
            :data="listaResume"
            :options="chartOptions"/>
        </div>
      </div>
    </card>


    <card type="user">
      <p class="description text-uppercase">
        Compras
      </p>
      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="PieChart"
            :data="listaCompras"
            :options="chartOptions"/>
        </div>
      </div>
    </card>
    <card type="user">
      <p class="description text-uppercase">
        Patrimônio
      </p>
      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="PieChart"
            :data="listaPatrimonio"
            :options="chartOptions"/>
        </div>
      </div>
    </card>
    <div slot="footer" class="button-container">
      <base-button icon round class="btn-facebook">
        <i class="fab fa-facebook"></i>
      </base-button>
      <base-button icon round class="btn-twitter">
        <i class="fab fa-twitter"></i>
      </base-button>
      <base-button icon round class="btn-google">
        <i class="fab fa-google-plus"></i>
      </base-button>
    </div>
  </card>
</template>
<script>
  import { GChart } from 'vue-google-charts';
  export default {
    components: {
      GChart
    },
    data() {
      return {
        chartOptions: {
          hAxis: {
            titleTextStyle: {color: '#607d8b'},
            gridlines: {color:'#37474f'},
            textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '12', bold: true}
          },
          vAxis: {
            gridlines: {color:'#37474f', count:0},
            baselineColor: 'transparent',
            textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '12', bold: true}
          },
          legend: {position: 'top', alignment: 'center', textStyle: {color:'#ffffff', fontName: 'Roboto', fontSize: '12'} },
          //colors: ["#0fc174","#00bcd4","#ff0000","#ff0000","#D4F1F4","#009688","#4caf50","#c6c6c6"],
          backgroundColor: 'transparent',
          pieSliceBorderColor : 'transparent',
          curveType: 'function',
          lineWidth: 0,
          height: 500,
          chartArea: {
            backgroundColor: "transparent",
            width: '90%',
            height: '85%'
          },
          series: {
            0: { lineWidth: 1 },
            2: { pointShape: 'square' , pointSize: 5 },
            3: { lineWidth: 1 },
            4: { lineWidth: 0.7 },
            5: { lineWidth: 0.5 },
            6: { lineWidth: 0.3 },
            //6: { lineWidth: 0.3, targetAxisIndex:1 },
            //7: { lineWidth: 0.3, logscale: true, targetAxisIndex:1 }
          },
          pieHole: 0.3,
          //is3D: true,
          slices: {
            0: {offset: 0.1},
            1: {offset: 0.1},
            2: {offset: 0.1},
            3: {offset: 0.1},
            4: {offset: 0.1},
            5: {offset: 0.1},
            6: {offset: 0.1},
          },
        },
        dataChart : []
      }
    },
    props: {
      listaPatrimonio: {
        type: Array,
        default: () => {
          return [];
        }
      },
      listaCompras: {
        type: Array,
        default: () => {
          return [];
        }
      },
      listaResume: {
        type: Array,
        default: () => {
          return [];
        }
      },
      dadosResumo: {
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
