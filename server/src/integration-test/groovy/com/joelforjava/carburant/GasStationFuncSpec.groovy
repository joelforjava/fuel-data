package com.joelforjava.carburant

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


@Integration
class GasStationFuncSpec extends Specification {
    @Shared
    @AutoCleanup
    private HttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client = HttpClient.create(baseUrl.toURL())
    }

    def 'test retrieving a global list of gas stations'() {
        given:
        List<Serializable> ids = []
        GasStation.withNewTransaction {
            GasStation station = new GasStation(name: 'RaceTrac #1812', address: '6420 Nantucket Expressway',
                    city: 'Birmingham', state: 'AL', zipCode: 35127).save(failOnError: true)
            station.save(failOnError: true)
            ids << station.id
        }

        when:
        HttpRequest request = HttpRequest.GET('/gas-stations')
        HttpResponse<List<Map>> resp = this.client.toBlocking().exchange(request, Argument.of(List, Map))

        then:
        resp.status == HttpStatus.OK
        resp.body().size() == 1
        resp.body().find { it.name == 'RaceTrac #1812' }

        cleanup:
        GasStation.withNewTransaction {
            ids.each {
                GasStation.load(it).delete()
            }
        }
    }
}
