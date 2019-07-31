package com.joelforjava.carburant

import grails.rest.Resource

@Resource
class GasStation {

    String name
    String address
    String city
    String state
    BigInteger zipCode
    Date dateCreated
    Date lastUpdated

    static hasMany = [fillUps: FillUp]

    static constraints = {
        name nullable: false, column: 'station_name'
        address nullable: true
        city nullable: false
        state nullable: false, column: 'station_state'
        zipCode nullable: false
    }

    String toCSV() {
        "$name,$address,$city,$state,$zipCode"
    }

    static String csvMetadata() {
        '"STORE_NAME","STORE_ADDRESS","STORE_CITY","STORE_STATE","STORE_ZIP"'
    }
}
