<template>
  <div>
    <div id="content-form" role="form">
      <form>
        <!--<autocomplete :items="[ 'Apple', 'Banana', 'Orange', 'Mango', 'Pear', 'Peach', 'Grape', 'Tangerine', 'Pineapple']"></autocomplete>-->
        <div class="form-group">
          <label for="gasStationName">Gas Station Name</label>
          <autocomplete :items="stationNameOptions" v-model="gasStation.name" id="gasStationName"></autocomplete>
        </div>
        <div class="form-group">
          <label for="gasStationAddress">Address</label>
          <input type="text" class="form-control" id="gasStationAddress" v-model="gasStation.address"/>
        </div>
        <div class="form-group">
          <label for="gasStationCity">City</label>
          <input type="text" class="form-control" id="gasStationCity" v-model="gasStation.city"/>
        </div>
        <div class="form-group">
          <label for="gasStationState">State</label>
          <select class="form-control" id="gasStationState" v-model="gasStation.state">
            <option v-for="state in stateOptions"
                    :key="state"
                    v-bind:value="state">{{state}}</option>
          </select>
          <!--<input type="text" class="form-control" id="gasStationState" v-model="gasStation.state"/>-->
        </div>
        <div class="form-group">
          <label for="gasStationZip">Zip</label>
          <input type="text" class="form-control" id="gasStationZip" v-model="gasStation.zipCode"/>
        </div>
      </form>
      <button type="button" class="btn btn-primary btn-lg btn-block" @click="submit">Save</button>
    </div>
  </div>
</template>
<script>
import Autocomplete from './autocomplete'
export default {
  name: 'AddGasStation',
  components: {Autocomplete},
  data () {
    return {
      gasStation: {
        name: '',
        address: '',
        city: '',
        state: '',
        zipCode: ''
      },
      stationNameOptions: [],
      stateOptions: [ 'AL', 'AK', 'AR', 'FL', 'GA' ],
      serverURL: process.env.SERVER_URL
    }
  },
  methods: {
    submit: function () {
      this.saveGasStation()
    },
    saveGasStation: function () {
      console.log(this.$data.gasStation)
      console.log(JSON.stringify(this.$data.gasStation))
      this.$apiService.saveGasStation(this.$data.gasStation)
        .then((res) => res.json())
        .then((data) => {
          console.log(data)
          this.gasStation = {
            name: '',
            address: '',
            city: '',
            state: '',
            zipCode: ''
          }
        })
        .catch((err) => console.error(err))
    }
  },
  created: function () {
    this.$apiService.getGasStations()
      .then(response => response.json())
      .then(json => {
        console.log(json)
        this.stationNameOptions = json.map(x => x.name)
      })
  }
}
</script>
