package com.joelforjava.carburant

import grails.rest.Resource

@Resource
class FillUp {

    Date dateCreated
    Date lastUpdated
    Date dateOccurred
    String octane
    BigDecimal numGallons
    BigDecimal pricePerUnit
    BigDecimal totalCost
    BigDecimal odometer

    static hasOne = [gasStation : GasStation]

    static constraints = {
        octane nullable: false, blank: false
        numGallons nullable: false, min: 0.0, scale: 3
        pricePerUnit nullable: false, min: 0.0, scale: 3
        totalCost nullable: false, min: 0.0, scale: 2
        odometer nullable: false, min: 0.0, scale: 2
    }
}
