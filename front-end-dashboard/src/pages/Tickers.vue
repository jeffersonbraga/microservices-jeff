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
  export default {
    components: {
      EditProfileForm,
      UserCard
    },
    mounted() {

      this.$http.get("http://localhost:8085/ticker/cards").then(result => {
        this.listaTickers = (result.body);
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
