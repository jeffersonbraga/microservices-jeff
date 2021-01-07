<template>
  <div>
    <card v-for="item in lista">
      <h5 slot="header" class="title">{{item.ticker}}</h5>
      <div class="row">
        <div class="col-md-2 pr-md-1">
          <base-input label="Company (disabled)"
                    placeholder="Company"
                    v-model="item.ticker"
                    disabled>
          </base-input>
        </div>
        <div class="col-md-8 px-md-1">
          <base-input label="Nome"
                    placeholder="Nome"
                    v-model="item.nome">
          </base-input>
        </div>
        <div class="col-md-2 pl-md-1">
          <base-input label="Valor Atual"
                    placeholder="Valor Atual"
                    v-model="item.valorAtual">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-4 pr-md-1">
          <base-input label="Diferença Valores"
                    v-model="item.diferencaPreco"
                    placeholder="Diferença Valores">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input label="Valor Total Atual"
                      v-model="item.valorTotalAtual"
                      placeholder="Valor Total Atual">
          </base-input>
        </div>
        <div class="col-md-4">
          <base-input label="Media Mayer"
                      v-model="item.mediaMayer"
                      placeholder="Media Mayer">
          </base-input>
        </div>
      </div>
      <div class="col-lg-12">
        <div class="chart-area">
          <GChart
            type="LineChart"
            :data="item.googleChartData"
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
        colors: ["#0fc174","#00bcd4","#ff0000","#0ff000","#D4F1F4","#009688","#4caf50","#c6c6c6"],
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
          4: { lineWidth: 0.7 },
          5: { lineWidth: 0.5 },
          6: { lineWidth: 0.3 },
          //6: { lineWidth: 0.3, targetAxisIndex:1 },
          //7: { lineWidth: 0.3, logscale: true, targetAxisIndex:1 }
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
