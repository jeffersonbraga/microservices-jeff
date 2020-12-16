<template>
  <div>
    <card v-for="item in lista">
      <h5 slot="header" class="title">{{item.ticker}}</h5>
      <div class="row">
        <div class="col-md-5 pr-md-1">
          <base-input label="Company (disabled)"
                    placeholder="Company"
                    v-model="item.ticker"
                    disabled>
          </base-input>
        </div>
        <div class="col-md-3 px-md-1">
          <base-input label="Preço Médio"
                    placeholder="Preço Médio"
                    v-model="item.precoMedio">
          </base-input>
        </div>
        <div class="col-md-4 pl-md-1">
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
        <div class="col-md-4 pl-md-1">
          <base-input label="Quantidade"
                      v-model="item.quantidade"
                      placeholder="Quantidade">
          </base-input>
        </div>
        <div class="col-md-4 pl-md-1">
          <base-input label="Valor Multiplicado"
                      v-model="item.quantidade * item.diferencaPreco"
                      placeholder="Valor Multiplicado">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <base-input label="Valor Total Investido"
                    v-model="item.valorTotalInvestido"
                    placeholder="Valor Total Investido">
          </base-input>
        </div>
        <div class="col-md-6">
          <base-input label="Valor Total Atual"
                      v-model="item.valorTotalAtual"
                      placeholder="Valor Total Atual">
          </base-input>
        </div>
      </div>
      <!--<div class="col-lg-12">
        <card type="chart">
          <template slot="header">
            <h1 class="card-category">Valor Fechamento Mensal</h1>
          </template>
          <div class="chart-area">
            <line-chart style="height: 100%"
                        chart-id="purple-line-chart"
                        :chart-data="item.chartData"
                        :gradient-colors="purpleLineChart.gradientColors"
                        :gradient-stops="purpleLineChart.gradientStops"
                        :extra-options="purpleLineChart.extraOptions">
            </line-chart>
          </div>
        </card>
      </div>-->
      <div>
        <GChart
          type="ColumnChart"
          :data="chartData"
          :options="chartOptions"/>
      </div>
      <base-button slot="footer" type="primary" fill>Save</base-button>
    </card>
  </div>
</template>
<script>

import config from "@/config";
import {basicOptions} from "@/components/Charts/config";
import LineChart from '@/components/Charts/LineChart';
import * as chartConfigs from '@/components/Charts/config';
import { GChart } from 'vue-google-charts';

export default {
  components: {
    LineChart,
    GChart
  },
  mounted() {
    //this.purpleLineChart.chartData.datasets[0].data = [20, 100, 70, 80, 120, 80];
/*
    let data = google.visualization.arrayToDataTable([
      ['Year', 'Sales', 'Expenses'],
      ['2004',  1000,      400],
      ['2005',  1170,      460],
      ['2006',  660,       1120],
      ['2007',  1030,      540]
    ]);

    let options = {
      title: 'Company Performance',
      curveType: 'function',
      legend: { position: 'bottom' }
    };

    let chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

    chart.draw(data, options);*/
  },
  data() {
    return {
      // Array will be automatically processed with visualization.arrayToDataTable function
      chartData: [
        ['Year', 'Sales', 'Expenses', 'Profit'],
        ['2014', 1000, 400, 200],
        ['2015', 1170, 460, 250],
        ['2016', 660, 1120, 300],
        ['2017', 1030, 540, 350]
      ],
      chartOptions: {
        chart: {
          title: 'Company Performance',
          subtitle: 'Sales, Expenses, and Profit: 2014-2017',
        }
      },
      dataChart : [],
      purpleLineChart: {
        extraOptions: chartConfigs.purpleChartOptions,
        gradientColors: config.colors.primaryGradient,
        gradientStops: [1, 0.2, 0],
      }
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

export let purpleChartOptions = {
  ...basicOptions,
  tooltips: {
    backgroundColor: '#f5f5f5',
    titleFontColor: '#333',
    bodyFontColor: '#666',
    bodySpacing: 4,
    xPadding: 12,
    mode: "nearest",
    intersect: 0,
    position: "nearest"
  },
  scales: {
    yAxes: [{
      barPercentage: 1.6,
      gridLines: {
        drawBorder: false,
        color: 'rgba(29,140,248,0.0)',
        zeroLineColor: "transparent",
      },
      ticks: {
        suggestedMin: 60,
        suggestedMax: 125,
        padding: 20,
        fontColor: "#9a9a9a"
      }
    }],

    xAxes: [{
      barPercentage: 1.6,
      gridLines: {
        drawBorder: false,
        color: 'rgba(225,78,202,0.1)',
        zeroLineColor: "transparent",
      },
      ticks: {
        padding: 20,
        fontColor: "#9a9a9a"
      }
    }]
  }
};
</script>
<style>
</style>
