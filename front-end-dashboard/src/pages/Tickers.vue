<template>
  <div class="row">
    <div class="col-md-12">
      <edit-profile-form :model="itemDetail" :lista="listaTickers">
      </edit-profile-form>
    </div>
  </div>
</template>
<script>
  import EditProfileForm from './TickerManager/EditProfileForm';
  import config from "@/config";
  //import moment from 'moment';

  export default {
    components: {
      EditProfileForm
    },
    mounted() {

      this.$http.get("http://localhost:8085/ticker/cards").then(result => {
        //this.listaTickers = (result.body);
        let listaAux = (result.body);

        listaAux.forEach((value, index) => {

          let labels = [];
          let dataChartGoogle = [];
          dataChartGoogle.push(["Mes", "Fechamento", "Medio", "Compra"]);
          value.listaDadosHistorico.forEach((valueHistorico, indexHistorico) => {
            //console.log(moment(String(valueHistorico.data)).format('MM/DD/YYYY hh:mm'));

            // request a weekday along with a long date
            let options = { year: 'numeric', month: 'short', day: 'numeric' };
            let label = new Date(valueHistorico.data).toLocaleString('default', options).toString().toUpperCase()
            labels.push(label);

            let comprado = null;

            value.listaDadosCompras.forEach((valueComprado, indexComprado) => {
              if (new Date(valueComprado.data).toLocaleString('default', options).toString().toUpperCase() == label) {
                comprado = (valueComprado.valor / valueComprado.quantidade);
              }
            });

            dataChartGoogle.push([label, valueHistorico.close, value.precoMedio, comprado]);
          });

          value["googleChartData"] = dataChartGoogle;
          this.listaTickers.push(value);
        });

        this.itemDetail = this.listaTickers[0];
      }, error => {
        console.error(error);
      });
    },
    data() {
      return {
        itemDetail : {},
        listaTickers : [],
        user: {
          fullName: 'Mike Andrew',
          title: 'Ceo/Co-Founder',
          description: `Do not be scared of the truth because we need to restart the human foundation in truth And I love you like Kanye loves Kanye I love Rick Owens’ bed design but the back is...`,
        }
      }
    }
  }
</script>
<style>
</style>
