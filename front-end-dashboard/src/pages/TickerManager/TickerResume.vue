<template>
  <card type="user">
    <p class="card-text">
    </p>
    <div class="author">
      <div class="block block-one"></div>
      <div class="block block-two"></div>
      <div class="block block-three"></div>
      <div class="block block-four"></div>

      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="PieChart"
            :data="listaResume"
            :options="chartOptions"/>
        </div>
      </div>

      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="PieChart"
            :data="listaCompras"
            :options="chartOptions"/>
        </div>
      </div>

      <p class="description">
        Titulo
      </p>
    </div>
    <p></p>
    <p class="card-description">
      Descricao
    </p>
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
          legend: {position: 'top', alignment: 'center', textStyle: {color:'#607d8b', fontName: 'Roboto', fontSize: '12'} },
          //colors: ["#0fc174","#00bcd4","#ff0000","#ff0000","#D4F1F4","#009688","#4caf50","#c6c6c6"],
          backgroundColor: 'transparent',
          curveType: 'function',
          lineWidth: 1,
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
          pieHole: 0.4
        },
        dataChart : []
      }
    },
    props: {
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
      }
    }
  }
</script>
<style>
</style>
