<template>
  <div>
    <card>
      <h5 slot="header" class="title">Inserir Aporte</h5>
      <div class="row">
        <div class="col-md-4 pr-md-1">
          <base-input label="Data" type="date"
                      v-model="model.data"
                      placeholder="Data">
          </base-input>
        </div>
        <div class="col-md-4 pl-md-1">
          <base-input label="Valor" type="number"
                      v-model="model.valor"
                      placeholder="Valor">
          </base-input>
        </div>
      </div>
      <base-button slot="footer" type="primary" fill @click="salvar()">Salvar</base-button>
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

      salvar() {

        this.$http.post('http://localhost:8085/ticker/aporte',this.model, {
          headers: {
            'Content-Type': 'application/json'
          }
        })
      }
    },
    data() {
      return {
        model: {
          data: '',
          valor: ''
        }
      }
    }
  }
</script>
<style>
</style>
