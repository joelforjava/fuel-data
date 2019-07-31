package com.joelforjava.carburant


import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class FillUpIntegrationSpec extends Specification {

    def setupSpec() {
    }

    def cleanup() {
    }

    @Unroll("octane = #oct, gallons = #gals, pricePerUnit = #ppu, totalCost = #total, dateOccurred = #dtOccurred, odometer = #odo, gasStation = #station validates to #expValid")
    void 'The FillUp class validates as expected'() {
        given: 'A new Fill Up'
        def fillup = new FillUp()

        and: 'We have populated the values'
        fillup.with {
            octane = oct
            numGallons = gals
            pricePerUnit = ppu
            totalCost = total
            dateOccurred = dtOccurred
            odometerReading = odo
            gasStation = station
        }

        when: 'We invoke validation'
        def isValid = fillup.validate()

        then: 'The appropriate fields are flagged as errors'
        isValid == expValid
        fillup.errors.allErrors.size() == numErrors
        fillup.errors.getFieldError(fieldInError)?.code == errorCode

        where:

        oct  | gals   | ppu   | total  | dtOccurred         | odo     | station      || expValid | numErrors | fieldInError   | errorCode
        '87' | 9.993  | 3.399 | 33.97  | date('09/09/2017') | 45_098  | gasStation() || true     | 0         | null           | null
        null | 9.993  | 3.399 | 33.97  | date('09/09/2017') | 33_901  | gasStation() || false    | 1         | 'octane'       | 'nullable'
        ''   | 10.881 | 2.799 | 30.46  | date('09/09/2017') | 101_354 | gasStation() || false    | 1         | 'octane'       | 'blank'
        '91' | null   | 2.619 | 28.78  | date('09/09/2015') | 113_782 | gasStation() || false    | 1         | 'numGallons'   | 'nullable'
        '91' | -1.0   | 2.619 | 28.78  | date('09/09/2015') | 113_782 | gasStation() || false    | 1         | 'numGallons'   | 'min.notmet'
        '93' | 21.908 | null  | 107.17 | date('10/10/2018') | 4_991   | gasStation() || false    | 1         | 'pricePerUnit' | 'nullable'
    }

    private GasStation gasStation() {
        new GasStation(name: "Jimbo's One Stop", city: 'Montgomery', state: 'AL', zipCode: 36103)
    }
    private Date date(String dateString) {
        new Date().parse('MM/dd/yyyy', dateString)
    }
}
