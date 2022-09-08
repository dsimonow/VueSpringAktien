<template>
  <div class="add">
    <h2>Search for Stocks and Add them!</h2>
    <div class="search-wrapper">
      <p>Press Enter to start search!</p>
      <input type="text" v-model.lazy="search" placeholder="Search title.."/>
      <p>Searchterm: {{ search }} </p>
    </div>
    <div class="searchResultWrapper">
      <div class="addableResult" 
        v-for="aktie in aktien"
        :key="aktie['symbol']"
        >
          <p>{{ aktie['symbol'] }} {{ aktie['name'] }} {{ aktie['type'] }} {{ aktie['region'] }}</p>
          <b-button v-on:click="addToAccount(aktie)">Add</b-button>
      </div>
    </div>     
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'Add',
  components: {
  },
    data () {
        return {
          toastCount: 0,
          aktien: [],
          errors: [],
          search: '',
          auth: 'Basic dXNlcjpwYXNzd29yZA==',
        }

    },
    mounted() {
      //empty - referenz nutzt aus Manage.vue
    },
    methods: {
      fetchAPIData(value) {
        console.log(value);
        axios
        .get('http://localhost:8081/aviav/search/'+value, {
          headers: { 
            'Authorization': this.auth
          }
        })
        .then(response => (this.amIAuthed(response)))
      },
      addToAccount(aktie) {
        axios
        .get('http://localhost:8081/aviav/addStockJson?stockSymbol='+aktie["symbol"]+'&stockName='+aktie["name"]+'&requestingUserId='+'1'+'&amount='+'123', 
        {
          headers: { 
            'Authorization': this.auth
          }
        })
        .then(response => (this.checkIfValid(response)));
      },
      amIAuthed(response){
        var helper = response.data;
        console.log("am I authed: "+ helper);
        if(helper == "you should login"){
          this.makeToastLogin();
        } else {
          this.aktien = response.data;
        }
      },
      checkIfValid(response){
        var checkfirst = response.data;
        console.log("check If Valid: "+checkfirst);
        if(checkfirst == "added"){
          this.makeToastSuccess();
        }
        else if("Error Message" in checkfirst){
          this.makeToastNoQuote();
        } 
        else if ("Note" in checkfirst){
          this.makeToastReachedQuota();
        }
      },
      makeToastNoQuote(append = false) {
        this.toastCount++
        this.$bvToast.toast(`Besitzt kein GlobalQuote bitte nimm ein anderen Stock!`, {
          title: 'Toasty Alarm!!!!!',
          autoHideDelay: 5000,
          appendToast: append
        })
      },
      makeToastReachedQuota(append = false) {
        this.toastCount++
        this.$bvToast.toast(`5 Requests die Minute erreicht. Bitte einen Moment warten und nochmal probieren`, {
          title: 'Quota erreicht',
          autoHideDelay: 5000,
          appendToast: append
        })
      },
      makeToastSuccess(append = false) {
        this.toastCount++
        this.$bvToast.toast(`Succesfully Added`, {
          title: 'Erfolg!',
          autoHideDelay: 5000,
          appendToast: append
        }) 

      },
      makeToastLogin(append = false) {
        this.toastCount++
        this.$bvToast.toast(`Can't send Request, please Login or Register first`, {
          title: 'Authenticate First!',
          autoHideDelay: 5000,
          appendToast: append
          })
      }

    },
    watch: {
      search: function(value) {
        this.fetchAPIData(value);
      }
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div.add {
  margin: 10px;
}
a {
  padding: 10px;
}
</style>
