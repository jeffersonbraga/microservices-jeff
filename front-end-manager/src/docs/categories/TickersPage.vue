<template>
  <mdb-container>
    <mdb-row class="mt-5 align-items-center justify-content-start">
      <h4 class="demo-title"><strong>Tickers </strong></h4>
      <a href="https://mdbootstrap.com/docs/vue/components/cards/?utm_source=DemoApp&utm_medium=MDBVueFree" waves-fixed class="border grey-text px-2 border-light rounded ml-2" target="_blank"><mdb-icon icon="graduation-cap" class="mr-2"/>Docs</a>
    </mdb-row>
    <hr class="mb-5" />
    <mdb-row class="mt-5">
      <mdb-col sm="4">
        <mdb-card>
          <mdb-card-image src="https://mdbootstrap.com/img/Photos/Horizontal/Nature/4-col/img%20%286%29.jpg" alt="Card image cap"></mdb-card-image>
          <mdb-card-body>
            <mdb-card-title>Basic card</mdb-card-title>
            <mdb-card-text>Some quick example text to build on the card title and make up the bulk of the card's content.</mdb-card-text>
            <mdb-btn color="primary">Button</mdb-btn>
          </mdb-card-body>
        </mdb-card>
      </mdb-col>
      <mdb-col sm="8">
        <section class="demo-section">
          <h4>{{tickerItem.ticker}}</h4>
          <section>
            <mdb-tbl striped>
              <mdb-tbl-head>
                <tr>
                  <th>Data</th>
                  <th>P/M</th>
                  <th>Qtde</th>
                  <th>Valor Atual</th>
                  <th>% Dif.</th>
                  <th>Investido</th>
                  <th>Atual</th>
                </tr>
              </mdb-tbl-head>
              <mdb-tbl-body>
                <tr scope="row">
                  <td>{{tickerItem.data}}</td>
                  <td>{{tickerItem.precoMedio}}</td>
                  <td>{{tickerItem.quantidade}}</td>
                  <td>{{tickerItem.valorAtual}}</td>
                  <td>{{tickerItem.diferencaPreco}}</td>
                  <td>{{tickerItem.valorTotalInvestido}}</td>
                  <td>{{tickerItem.valorTotalAtual}}</td>
                </tr>
                <tr scope="row">
                  <td>2</td>
                  <td>tbody col 1</td>
                  <td>tbody col 2</td>
                  <td>tbody col 3</td>
                  <td>tbody col 1</td>
                  <td>tbody col 2</td>
                  <td>tbody col 3</td>
                </tr>
                <tr scope="row">
                  <td>3</td>
                  <td>tbody col 1</td>
                  <td>tbody col 2</td>
                  <td>tbody col 3</td>
                  <td>tbody col 1</td>
                  <td>tbody col 2</td>
                  <td>tbody col 3</td>
                </tr>
              </mdb-tbl-body>
            </mdb-tbl>
          </section>
        </section>
      </mdb-col>
    </mdb-row>
  </mdb-container>
</template>

<script>
import {
  mdbContainer,
  mdbRow,
  mdbCol,
  mdbCard,
  mdbCardImage,
  mdbCardBody,
  mdbCardTitle,
  mdbCardText,
  mdbBtn,
  mdbIcon,
  mdbTbl, mdbTblHead, mdbTblBody
} from 'mdbvue';

export default {
  name: 'TickersPage',
  data () {
    return {
      listaTickers : [],
      tickerItem : {
        ticker: "",
        data: "",
        precoMedio: 10,
        valorAtual: "",
        diferencaPreco: "",
        percentualDiferenca: "",
        quantidade: "",
        valorTotalInvestido: "",
        valorTotalAtual: ""
      }
    }
  },
  mounted() {
    this.$http.get("http://localhost:8085/ticker/cards").then(result => {
      console.log(result.body);
      this.listaTickers = (result.body);
      this.tickerItem = this.listaTickers[0];
    }, error => {
      console.error(error);
    });
  },
  methods: {
    sendData() {
      this.$http.post("https://httpbin.org/post", this.input, { headers: { "content-type": "application/json" } }).then(result => {
        this.response = result.data;
      }, error => {
        console.error(error);
      });
    }
  },
  components: {
    mdbContainer,
    mdbRow,
    mdbCol,
    mdbCard,
    mdbCardImage,
    mdbCardBody,
    mdbCardTitle,
    mdbCardText,
    mdbBtn,
    mdbTbl,
    mdbTblHead,
    mdbTblBody,
    mdbIcon
  }
};
</script>
