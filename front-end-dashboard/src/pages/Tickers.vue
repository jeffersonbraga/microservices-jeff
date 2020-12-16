<template>
  <div class="row">
    <div class="col-md-8">
      <edit-profile-form :model="itemDetail" :lista="listaTickers">
      </edit-profile-form>
    </div>
    <div class="col-md-4">
      <user-card :user="user"></user-card>
    </div>
  </div>
</template>
<script>
  import EditProfileForm from './TickerManager/EditProfileForm';
  import UserCard from './TickerManager/UserCard'
  import config from "@/config";
  //import moment from 'moment';

  export default {
    components: {
      EditProfileForm,
      UserCard
    },
    mounted() {

      this.$http.get("http://localhost:8085/ticker/cards").then(result => {
        //this.listaTickers = (result.body);
        let listaAux = (result.body);
        listaAux.forEach((value, index) => {

          let labels = [];
          let data = [];
          value.listaDadosHistorico.forEach((valueHistorico, indexHistorico) => {
            //console.log(moment(String(valueHistorico.data)).format('MM/DD/YYYY hh:mm'));
            labels.push(new Date(valueHistorico.data).toLocaleString('default', { month: 'short' }).toString().toUpperCase());
            data.push(valueHistorico.close);
          });

          let chartData = {
          labels: labels,
          datasets: [{
              label: "Data",
              fill: true,
              borderColor: config.colors.primary,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              pointBackgroundColor: config.colors.primary,
              pointBorderColor: 'rgba(255,255,255,0)',
              pointHoverBackgroundColor: config.colors.primary,
              pointBorderWidth: 20,
              pointHoverRadius: 4,
              pointHoverBorderWidth: 15,
              pointRadius: 4,
              data: data,
            }]
          };

          value["chartData"] = chartData;
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
