<template>
  <div class="aktie">
    <b-card 
        border-variant="primary"
        :header="aktie.stockSymbol"
        header-bg-variant="primary"
        header-text-variant="white"
        align="center">
        <b-card-text>{{ aktie.stockName }} Menge: {{ aktie.amount}} <br>
        <div v-if="aktienPerformance !== null">
          <div 
            v-for="aktieP in aktienPerformance" 
            :key="aktieP.latestTradingDay"
            :aktieP="aktieP"
            >
            Latest Trading day {{ aktienPerformance[0].latestTradingDay }} Previous Close {{ aktienPerformance[0].previousClose }} 
            Open {{ aktienPerformance[0].open }} High {{ aktienPerformance[0].high }} Low {{ aktienPerformance[0].low }} 
            Change {{ aktienPerformance[0].change }} Change Percent {{ aktienPerformance[0].changePercent }}
            </div>
            
        </div>
        </b-card-text>
        <b-button variant="outline-primary" v-on:click=getAktiePerformance(aktie)>Show Performance</b-button>
        <b-button variant="outline-danger" v-on:click=deleteThisStock(aktie)>Delete</b-button>
      </b-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ManageAktienCard',
  props: {
    aktie: {
      // id: nextTodoId++, aktienHandle: 'ELA', aktienName: 'Electronic Arts', aktuellerWert: 311, letzterStatus: 'up'
      type: Object,
      required: true
    }
  },
  data () {
        return {
          // long stockPerformanceEntryId;String latestTradingDay;double open;double high;double low;double 
    //double change;String changePercent; price;long volume;double previousClose;
          aktienPerformance: [],
          auth: 'Basic dXNlcjpwYXNzd29yZA==',
        }
    },
  methods: {
    getAktieHandle(){
      return this.aktie.stockHandle;
    },
    getAktiePerformance(aktie){
      axios
        .get('http://localhost:8081/aviav/getStockPerformance?stockSymbol='+aktie.stockSymbol+"&requestingUserId=+1" , {
          headers: { 
            'Authorization': this.auth
          }
        })
        .then(response => (this.aktienPerformance = response.data ))
    },
    deleteThisStock(aktie){
      axios
        .get('http://localhost:8081/aviav/deleteStock?stockSymbol='+aktie.stockSymbol+'&requestingUserId='+'1'+'', {
          headers: { 
            'Authorization': this.auth
          }
        })
        .then(response => (this.aktien = response.data))
        .then( this.$router.go())
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div.aktie {
  min-height: 100px;
  min-width: 180px;
  margin: 10px;
}
a {
  color: #42b983;
}
</style>
