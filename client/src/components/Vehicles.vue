<template>
  <div>
    <div id="content" role="main">
      <table v-if="vehicles" class="table table-striped">
        <thead>
          <tr>
            <th>Year</th>
            <th>Make/Model</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="vehicle in vehicles" :key="vehicle.id">
            <td>{{vehicle.modelYear}}</td>
            <td>
              <router-link :to="{name: 'Vehicle Fill Ups', params: {vehicleId: vehicle.id}}">{{vehicle.make}} {{vehicle.model}}</router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Vehicles',
  data () {
    return {
      vehicles: null,
      serverURL: process.env.SERVER_URL
    }
  },
  created: function () {
    this.$apiService.getVehicles()
      .then(response => response.json())
      .then(json => {
        this.vehicles = json
      })
  }
}
</script>

<style scoped>

</style>
