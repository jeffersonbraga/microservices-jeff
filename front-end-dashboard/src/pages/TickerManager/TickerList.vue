<template>
  <div>
    <card v-for="item in lista">
      <h5 slot="header" class="title">{{item.ticker}}</h5>
      <div class="row">
        <div class="col-md-6">
          <base-input label="Company (disabled)"
                    placeholder="Company"
                    v-model="item.ticker"
                    disabled>
          </base-input>
        </div>
        <div class="col-md-2">
          <base-input label="Quantidade"
                      v-model="item.quantidade"
                      placeholder="Quantidade">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3">
          <base-input label="Valor Atual"
                      placeholder="Valor Atual"
                      v-model="item.valorAtual">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Preço Médio"
                      placeholder="Preço Médio"
                      v-model="item.precoMedio">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Mediana"
                      placeholder="Mediana"
                      v-model="item.mediana">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Media Mayer"
                      v-model="item.mediaMayer"
                      placeholder="Media Mayer">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3">
          <base-input label="Valor Total Investido"
                      v-model="item.valorTotalInvestido"
                      placeholder="Valor Total Investido">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Valor Total Atual"
                      v-model="item.valorTotalAtual"
                      placeholder="Valor Total Atual">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Diferença Valores"
                      v-model="item.diferencaPreco"
                      placeholder="Diferença Valores">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Valor Multiplicado"
                      v-model="item.quantidade * item.diferencaPreco"
                      placeholder="Valor Multiplicado">
          </base-input>
        </div>
      </div>
      <div class="row">
        <div class="col-md-3">
          <base-input label="Quantidade Vendida"
                      v-model="item.quantidadeVendido"
                      placeholder="Quantidade Vendida">
          </base-input>
        </div>
        <div class="col-md-3">
          <base-input label="Valor Resultado Total Venda"
                      v-model="item.valorResultadoCompraVenda"
                      placeholder="Valor Resultado Total Venda">
          </base-input>
        </div>
      </div>
      <!--<div class="col-lg-12">
        <card type="chart">
          <template slot="header">
            <h1 class="card-category">Histórico Valor Fechamento <b>{{item.ticker}}</b></h1>
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
      <div class="col-lg-12">

        <!-- GRÁFICO LINHAS VALOR FECHAMENTO -->
        <div class="chart-area">
          <GChart
            type="LineChart"
            :data="item.chartFechamentos"
            :options="chartOptions"/>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-4">
          <!-- GRÁFICO COLUNAS INVESTIDO / VALOR ATUAL -->
          <div class="chart-area">
            <GChart
              type="ColumnChart"
              :data="item.chartInvestimento"
              :options="columnChartOptionsValor"/>
          </div>
        </div>

        <div class="col-lg-4">
          <!-- GRÁFICO VOLUME MÉDIO -->
          <div class="chart-area">
            <GChart
              type="ColumnChart"
              :data="item.chartVolume"
              :options="columnChartOptionsVolume"/>
          </div>
        </div>

        <div class="col-lg-4">
          <!-- GRÁFICO EVOLUÇÃO PATRIMONIO INVESTIDO -->
          <div class="chart-area">
            <GChart
              type="LineChart"
              :data="item.chartEvolucaoPatrimonio"
              :options="chartOptionsEvolucao"/>
          </div>
        </div>
      </div>

      <div class="col-lg-12">
        <!-- TABELAS COM VOLUMES -->
        <div class="row" style="padding-top: 50px">
          <div class="col-6">
            <card title="Maiores Fechamentos">
              <div class="table-responsive" style="overflow: hidden">
                <base-table :data="item.listaDadosTop10MelhoresPrecos"
                            :columns="table1.columns"
                            thead-classes="text-primary">
                </base-table>
              </div>
            </card>
          </div>
          <div class="col-6">
            <card title="Menores Fechamentos">
              <div class="table-responsive" style="overflow: hidden">
                <base-table :data="item.listaDadosTop10MenoresPrecos"
                            :columns="table1.columns"
                            thead-classes="text-primary">
                </base-table>
              </div>
            </card>
          </div>
        </div>
        <div class="row">
          <div class="col-6">
            <card title="Maiores Volumes">
              <div class="table-responsive" style="overflow: hidden">
                <base-table :data="item.listaDadosTop10MelhoresVolume"
                            :columns="table2.columns"
                            thead-classes="text-primary">
                </base-table>
              </div>
            </card>
          </div>
          <div class="col-6">
            <card title="Menores Volumes">
              <div class="table-responsive" style="overflow: hidden">
                <base-table :data="item.listaDadosTop10MenoresVolume"
                            :columns="table2.columns"
                            thead-classes="text-primary">
                </base-table>
              </div>
            </card>
          </div>
        </div>
      </div>

    </card>
  </div>
</template>
<script>

import config from "@/config";
import { GChart } from 'vue-google-charts';
import { BaseTable } from "@/components";
const tableColumns = ["Data", "Close"];
const tableColumnsVolume = ["Data", "Volume", "Close"];

export default {
  components: {
    GChart,
    BaseTable
  },
  data() {
    return {
      table1: {
        title: "Simple Table",
        columns: [...tableColumns]
      },
      table2: {
        title: "Simple Table",
        columns: [...tableColumnsVolume]
      },
      columnChartOptionsVolume : {
        title : 'VOLUME',
        titleTextStyle : {
          color : '#ffffff'
        },
        vAxis: {
          minValue : 0,
          textStyle : {
            color: "#ffffff",
          }
        },
        hAxis: {
          titleTextStyle: {color: '#607d8b'},
          gridlines: {color:'#37474f'},
          textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '12', bold: true}
        },
        colors: ["#00d4bb"],
        legend: { position: "none" },
        backgroundColor: 'transparent',
        height: 300,
      },
      columnChartOptionsValor : {
        title : 'VALOR',
        titleTextStyle : {
          color : '#ffffff'
        },
        vAxis: {
          minValue : 0,
          textStyle : {
            color: "#ffffff",
          }
        },
        hAxis: {
          titleTextStyle: {color: '#607d8b'},
          gridlines: {color:'#37474f'},
          textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '12', bold: true}
        },
        colors: ["#00d4bb"],
        legend: { position: "none" },
        backgroundColor: 'transparent',
        height: 300,
      },
      chartOptionsEvolucao : {
        hAxis: {
          textPosition: 'none',
          titleTextStyle: { color: '#607d8b' },
          gridlines: { color:'#37474f' },
          textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '0', bold: true }
        },
        vAxis: {
          gridlines: {color:'#37474f', count:0},
          baselineColor: 'transparent',
          textStyle: { color: '#b0bec5', fontName: 'Roboto', fontSize: '12', bold: true }
        },
        legend: {
          position: 'top', alignment: 'center',
          textStyle: { color:'#607d8b', fontName: 'Roboto', fontSize: '12' }
        },
        colors: ["#ff0000", "#0fc174","#00bcd4","#ff0000","#D4F1F4","#009688","#4caf50","#c6c6c6"],
        backgroundColor: 'transparent',
        //curveType: 'function',
        lineWidth: 1,
        height: 300,
        /*chartArea: {
          backgroundColor: "transparent",
          width: '90%',
          height: '85%'
        },*/
        /*series: {
          0: { lineWidth: 1 },
          2: { pointShape: 'square' , pointSize: 5 },
          3: { lineWidth: 1 },
          4: { lineWidth: 0.7 },
          5: { lineWidth: 0.5 },
          6: { lineWidth: 0.3 }
        },*/
      },
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
        colors: ["#0fc174","#00bcd4","#ff0000","#ff0000","#D4F1F4","#009688","#4caf50","#c6c6c6"],
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
        //trendlines: { 0: {} }    // Draw a trendline for data series 0.
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
