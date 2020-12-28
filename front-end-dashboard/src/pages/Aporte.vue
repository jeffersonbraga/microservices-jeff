<template>
  <div>
    <card>
      <h5 slot="header" class="title">Inserir Aporte</h5>
      <div class="row">
        <div class="col-md-4 pr-md-1">
          <base-input label="Data" type="date"
                      v-model="modelAporte.data"
                      placeholder="Data">
          </base-input>
        </div>
        <div class="col-md-4 pl-md-1">
          <base-input label="Valor" type="number"
                      v-model="modelAporte.valor"
                      placeholder="Valor">
          </base-input>
        </div>
      </div>
      <base-button slot="footer" type="primary" fill @click="salvar()">Salvar Aporte</base-button>
    </card>
    <card>
      <h5 slot="header" class="title">Inserir Compra</h5>
      <div class="row">
        <div class="col-md-3 pr-md-1">
          <base-input label="Data" type="date"
                      v-model="modelTickerBuy.data"
                      placeholder="Data">
          </base-input>
        </div>
        <div class="col-md-3 pl-md-1">
          <base-input label="Ticker"
                      v-model="modelTickerBuy.ticker"
                      placeholder="Ticker">
          </base-input>
        </div>
        <div class="col-md-3 pl-md-1">
          <base-input label="Valor" type="number"
                      v-model="modelTickerBuy.valor"
                      placeholder="Valor">
          </base-input>
        </div>
        <div class="col-md-3 pl-md-1">
          <base-input label="Quantidade" type="number"
                      v-model="modelTickerBuy.quantidade"
                      placeholder="Quantidade">
          </base-input>
        </div>
      </div>
      <base-button slot="footer" type="primary" fill @click="salvarCompra()">Salvar Compra</base-button>
    </card>
  </div>
</template>
<script>
  import EditProfileForm from './Profile/EditProfileForm';
  import UserCard from './Profile/UserCard'
  export default {
    components: {
      EditProfileForm,
      UserCard
    },
    methods: {

      salvarAporte() {

        this.$http.post('http://localhost:8085/ticker/aporte',this.modelAporte, {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(result => {
          this.modelAporte.data = '';
          this.modelAporte.valor = '';
        });
      },

      salvarCompra() {

        this.$http.post('http://localhost:8085/ticker/buy',this.modelTickerBuy, {
          headers: {
            'Content-Type': 'application/json'
          }
        }).then(result => {
          this.modelTickerBuy.data = '';
          this.modelTickerBuy.valor = '';
          this.modelTickerBuy.ticker = '';
          this.modelTickerBuy.quantidade = '';
        });
      }
    },
    data() {
      return {
        modelAporte: {
          data: '',
          valor: ''
        },
        modelTickerBuy: {
          data: '',
          ticker: '',
          valor: '',
          quantidade: ''
        }
      }
    }
  }
</script>
<style>
</style>
