<template>
  <div class="wrapper">
    <side-bar>
      <template slot="links">
        <sidebar-link to="/aporte" :name="$t('sidebar.aporte')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/tickers" :name="$t('sidebar.tickersDetail')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/tickersvolume" :name="$t('sidebar.tickersvolume')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/tickersagrupados" :name="$t('sidebar.tickersagrupados')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/ibrx100" :name="$t('sidebar.ibrx100')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/ibrx50" :name="$t('sidebar.ibrx50')" icon="tim-icons icon-single-02"/>
        <sidebar-link to="/imobiliario" :name="$t('sidebar.imobiliario')" icon="tim-icons icon-single-02"/>
      </template>
    </side-bar>
    <div class="main-panel">
      <top-navbar></top-navbar>
      <dashboard-content @click.native="toggleSidebar">
      </dashboard-content>
      <content-footer></content-footer>
    </div>
  </div>
</template>
<style lang="scss">
</style>
<script>
import TopNavbar from "./TopNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import MobileMenu from "./MobileMenu";
export default {
  components: {
    TopNavbar,
    ContentFooter,
    DashboardContent,
    MobileMenu
  },
  methods: {
    toggleSidebar() {
      if (this.$sidebar.showSidebar) {
        this.$sidebar.displaySidebar(false);
      }
    }
  }
};
</script>
