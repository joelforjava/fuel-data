<template>
  <div>
    <div id="content" role="main">
      <router-link class="btn btn-primary pull-right" to="fills/add">Add New</router-link>
      <table v-if="fillUps" class="table table-striped">
        <thead>
        <tr>
          <th>Date</th>
          <th>Gas Station</th>
          <th>Address</th>
          <th>City</th>
          <th>State</th>
          <th>Zip</th>
          <th>Octane</th>
          <th># of Gallons</th>
          <th>Price per Gallon</th>
          <th>Total</th>
          <th>Odometer</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="fillUp in fillUps" :key="fillUp.id">
          <td>{{fillUp.dateOccurred}}</td>
          <td>{{fillUp.gasStation.name}}</td>
          <td>{{fillUp.gasStation.address}}</td>
          <td>{{fillUp.gasStation.city}}</td>
          <td>{{fillUp.gasStation.state}}</td>
          <td>{{fillUp.gasStation.zipCode}}</td>
          <td>{{fillUp.octane}}</td>
          <td>{{fillUp.numGallons}}</td>
          <td>{{fillUp.pricePerUnit}}</td>
          <td>{{fillUp.totalCost}}</td>
          <td>{{fillUp.odometer}}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Fill Ups',
  data () {
    return {
      fillUps: null,
      serverURL: process.env.SERVER_URL
    }
  },
  created: function () {
    fetch(`${this.$data.serverURL}/fill-ups`)
      .then(response => response.json())
      .then(json => {
        this.fillUps = json
      })
  }
}
</script>
