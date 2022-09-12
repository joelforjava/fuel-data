package com.joelforjava.carburant

import grails.rest.Resource

@Resource
class Vehicle {

    Integer modelYear
    String make
    String model

    static hasMany = [fillups: FillUp]

    // TODO set up
    // static belongsTo = [user: User]

    static constraints = {
        modelYear nullable: false
        make blank: false
        model blank: false
    }
}
