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
        name nullable: false
        address nullable: true
        city nullable: false
        state nullable: false
        zipCode nullable: false
    }
}
