<template>
  <div class="manage">
    <p>manage your stocks</p>
    <div class="break"></div>
    <div class="mac">
    <ManageAktienCard id="mac"
      v-for="aktie in aktien"
      :key="aktie.stockSymbol"
      :aktie="aktie"
    /> </div>
  </div>
</template>

<script>
import axios from 'axios';
import ManageAktienCard from './ManageAktienCard.vue'

export default {
  name: 'Manage',
  components: {
    ManageAktienCard,
  },
    data () {
        return {
          aktien: [],
          errors: [],
          auth: 'Basic dXNlcjpwYXNzd29yZA==',
        }
    },
    mounted() {
      axios
        .get('http://localhost:8081/aviav/getAllStockforUser?requestingUserId=1', {
          headers: { 
            'Authorization': this.auth
          }
        })
        .then(response => (this.aktien = response.data))
        //this.aktien = response.data
    },
    methods: {
        fetchAPIData() {

        }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.mac {
  margin: 10px;
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
}
a {
  padding: 10px;
}
.break {
  flex-basis: 100%;
  height: 0;
}
p {
  align-content: center;
}
</style>
