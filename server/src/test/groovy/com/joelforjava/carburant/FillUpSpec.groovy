package com.joelforjava.carburant

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.LocalDateTime

class FillUpSpec extends Specification implements DomainUnitTest<FillUp> {

    def setup() {
    }

    def cleanup() {
    }

    void 'A FillUp object without a GasStation cannot be saved'() {
        given: 'We have created a FillUp object'
        def fillup = new FillUp(dateOccurred: new Date(), octane: "93", numGallons: 9.445, pricePerUnit: 2.499, totalCost: 20.00)

        when: 'We attempt to save it'
        fillup.save()

        then:
        fillup.hasErrors()
        'nullable' == fillup.errors.getFieldError('gasStation').code
        null == fillup.errors.getFieldError('gasStation').rejectedValue

    }
}
