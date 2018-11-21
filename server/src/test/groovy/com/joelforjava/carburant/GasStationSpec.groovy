package com.joelforjava.carburant

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class GasStationSpec extends Specification implements DomainUnitTest<GasStation> {

    def setup() {
    }

    def cleanup() {
    }

    void 'A GasStation object without an address can be saved'() {
        given: 'A newly populated GasStation object'
        def station = new GasStation(name: "Frank's Fill 'Em Up", city: 'Cedar Rapids', state: 'IA', zipCode: 52402)

        when: 'We attempt to save it'
        station.save()

        then: 'The object does not contain any errors'
        !station.hasErrors()
    }
}
