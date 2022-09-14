<template>
  <div>
    <div id="content-form" role="form">
      <form>
        <div class="form-group">
          <label for="dateOccurred">Date</label>
          <input type="text" class="form-control" id="dateOccurred" v-model="fillUp.dateOccurred"/>
        </div>
        <div class="form-group">
          <label for="gasStationName">Gas Station Name</label>
          <!-- NOTE: Only the typed characters are being sent to the service! -->
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
          <input type="text" class="form-control" id="gasStationState" v-model="gasStation.state"/>
        </div>
        <div class="form-group">
          <label for="gasStationZip">Zip</label>
          <input type="text" class="form-control" id="gasStationZip" v-model="gasStation.zipCode"/>
        </div>
        <div class="form-group">
          <label for="octane">Octane</label>
          <input type="text" class="form-control" id="octane" v-model="fillUp.octane"/>
        </div>
        <div class="form-group">
          <label for="numGallons">Number of Gallons</label>
          <input type="text" class="form-control" id="numGallons" v-model="fillUp.numGallons"/>
        </div>
        <div class="form-group">
          <label for="pricePerUnit">Price Per Gallon</label>
          <input type="text" class="form-control" id="pricePerUnit" v-model="fillUp.pricePerUnit"/>
        </div>
        <div class="form-group">
          <label for="total">Total</label>
          <input type="text" class="form-control" id="total" v-model="fillUp.totalCost"/>
        </div>
        <div class="form-group">
          <label for="odometer">Odometer</label>
          <input type="text" class="form-control" id="odometer" v-model="fillUp.odometerReading"/>
        </div>
      </form>
      <button type="button" class="btn btn-primary btn-lg btn-block" @click="submit">Save</button>
    </div>
  </div>
</template>
<script>
import Autocomplete from './autocomplete'
export default {
  name: 'AddFillUp',
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
      fillUp: {
        octane: '',
        numGallons: '',
        pricePerUnit: '',
        totalCost: '',
        odometerReading: '',
        dateOccurred: new Date()
      },
      stationNameOptions: [],
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
      fetch(`${this.$data.serverURL}/gas-stations`, {
        method: 'POST',
        body: JSON.stringify(this.$data.gasStation),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => res.json())
        .then((data) => {
          console.log(data)
          if (data.id) {
            this.$data.fillUp.gasStation = {id: data.id}
            this.saveFillUp()
          }
        })
        .catch((err) => console.error(err))
    },
    saveFillUp: function () {
      console.log(this.$data.fillUp)
      fetch(`${this.$data.serverURL}/vehicles/${this.$route.params.vehicleId}/fill-ups`, {
        method: 'POST',
        body: JSON.stringify(this.$data.fillUp),
        headers: {
          'Content-Type': 'application/json'
        }
      }).then((res) => res.json())
        .then((data) => {
          console.log(data)
          this.$router.push('/fills')
        })
        .catch((err) => console.error(err))
    }
  },
  created: function () {
    // TODO - we should change this to pull from a user's saved stations
    fetch(`${this.$data.serverURL}/gas-stations`)
      .then(response => response.json())
      .then(json => {
        console.log(json)
        this.stationNameOptions = json.map(x => x.name)
      })
  }
}
</script>
