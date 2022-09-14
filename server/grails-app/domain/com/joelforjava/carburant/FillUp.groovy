package com.joelforjava.carburant

import grails.rest.Resource

import java.text.SimpleDateFormat

@Resource//(superClass=FillUpController)
class FillUp {

    Date dateCreated
    Date lastUpdated
    Date dateOccurred
    String octane
    BigDecimal numGallons
    BigDecimal pricePerUnit
    BigDecimal totalCost
    BigDecimal odometerReading

    static hasOne = [gasStation : GasStation]

    static belongsTo = [vehicle: Vehicle]

    static constraints = {
        octane nullable: false, blank: false
        numGallons nullable: false, min: 0.0, scale: 3
        pricePerUnit nullable: false, min: 0.0, scale: 3
        totalCost nullable: false, min: 0.0, scale: 2
        odometerReading nullable: false, min: 0.0, scale: 2
    }

    private static final CSV_DATE_FORMAT = new SimpleDateFormat('M/dd/yyyy HH:mm:ss')


    String toCSV() {
        "${gasStation.toCSV()},${CSV_DATE_FORMAT.format(dateOccurred)},$octane,$numGallons,$pricePerUnit,$totalCost,$odometerReading"
    }

    static String csvMetadata() {
        "${GasStation.csvMetadata()}," + '"DATE","OCTANE","NUM_GALLONS","PRICE_PER_GALLON","TOTAL_COST","ODOMETER"'
    }
}
