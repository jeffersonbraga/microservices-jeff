<template>
  <div>
    <card>
      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="LineChart"
            :data="lista"
            :options="chartOptions"/>
        </div>
      </div>
    </card>
  </div>
</template>
<script>

import config from "@/config";
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
        colors: ["#c5ce03","#6b6bf3","#991fc7","#894d4d","#017666", "#ff0000","#00ff00","#ffffff"],
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
          2: { lineWidth: 1 },
          3: { lineWidth: 1 },
          4: { lineWidth: 1 },
          5: { lineWidth: 1 },
          6: { lineWidth: 1 },
          7: { lineWidth: 1 },
          8: { lineWidth: 1 }
        }
      },
      dataChart : []
    }
  },
  props: {
    model:{
      type: Object,
      default: () => {
        return {};
      }
    },
    lista:{
      type: Array,
      default: () => {
        return [];
      }
    }
  }
};
</script>
<style>
</style>
