package com.joelforjava.carburant

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Integration
class FillUpFuncSpec extends Specification {
    @Shared
    @AutoCleanup
    private HttpClient client

    @Shared
//    @AutoCleanup
    private Vehicle vehicle

    @Shared
    private GasStation gasStation

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client = HttpClient.create(baseUrl.toURL())
    }

    void onClose() {
        this.client.close()
    }

    def 'test retrieving a list of vehicle fill-ups'() {
        given:
        User user = User.withNewTransaction {
            new User(username: 'test', password: 'extralongpassword').save(failOnError: true)
        }
        Vehicle.withNewTransaction { status ->
            this.vehicle = new Vehicle(modelYear: 2005, make: 'Honda', model: 'Insight', user: user)
            this.vehicle.save(failOnError: true)
            status.isCompleted()
        }
        GasStation.withNewTransaction { status ->
            this.gasStation = new GasStation(name: 'BP 30220', address: '552 Giblets Drive',
                    city: 'Birmingham', state: 'AL', zipCode: 35127).save(failOnError: true)
            this.gasStation.save(failOnError: true)
            status.isCompleted()
        }
        List<Serializable> ids = []
        FillUp.withNewTransaction {
            FillUp fillUp = new FillUp(
                    dateOccurred: new Date().parse('MM/dd/yyyy', '06/28/2019'), octane: '87', numGallons: 8.281,
                    pricePerUnit: 3.439, totalCost: 28.48, odometerReading: 486.0, gasStation: this.gasStation,
                    vehicle: this.vehicle)
            fillUp.save(failOnError: true)
            ids << fillUp.id
        }

        when:
        HttpRequest request = HttpRequest.GET("/vehicles/${this.vehicle.id}/fill-ups")
        HttpResponse<List<Map>> resp = this.client.toBlocking().exchange(request, Argument.of(List, Map))

        then:
        resp.status == HttpStatus.OK
        resp.body().size() == 1
        resp.body().find { it.octane == '87' }

        cleanup:
        FillUp.withNewTransaction {
            ids.each {
                FillUp.load(it).delete()
            }
        }
        GasStation.withNewTransaction {
            this.gasStation.delete()
        }
        Vehicle.withNewTransaction {
            this.vehicle.delete()
        }
        User.withNewTransaction {
            user.delete()
        }

    }

    def 'test saving a new fill-up'() {
        given: 'A fill up with all the required fields populated'
        User user = User.withNewTransaction {
            new User(username: 'test', password: 'extralongpassword').save(failOnError: true)
        }
        Vehicle.withNewTransaction { status ->
            this.vehicle = new Vehicle(modelYear: 2004, make: 'Honda', model: 'Insight', user: user)
            this.vehicle.save(failOnError: true)
            status.isCompleted()
        }
        GasStation.withNewTransaction { status ->
            this.gasStation = new GasStation(name: 'Shell 30220', address: '552 Giblets Drive',
                    city: 'Birmingham', state: 'AL', zipCode: 35127).save(failOnError: true)
            this.gasStation.save(failOnError: true)
            status.isCompleted()
        }

        Map<String, Object> json = [
                gasStation: [
                        id: this.gasStation.id
                ],
                // I really don't like the idea of having to pass the ID here and in the URL, but I'm tired
                // and I really don't feel like trying to fixing the Controller to handle it the way I want
                vehicle: [
                        id: this.vehicle.id
                ],
                dateOccurred: LocalDateTime.now().minus(10, ChronoUnit.DAYS).format(DateTimeFormatter.ISO_DATE_TIME),
                numGallons: 5,
                octane: 91,
                odometerReading: 599,
                pricePerUnit: '3.00',
                totalCost: '15.00'
        ]

        when: 'We try to save the entry'
        HttpRequest request = HttpRequest.POST("/vehicles/${this.vehicle.id}/fill-ups", json)
                .contentType(MediaType.APPLICATION_JSON_TYPE)
                .accept(MediaType.APPLICATION_JSON_TYPE)
        HttpResponse response = client.toBlocking().exchange(request, Argument.of(Map))

        then:
        response.status == HttpStatus.CREATED
        def responseBody = response.body()
        responseBody.id

        cleanup:
        FillUp.withNewTransaction {
            FillUp.load(responseBody.id).delete()
        }
        Vehicle.withNewTransaction {
            this.vehicle.delete()
        }
        GasStation.withNewTransaction {
            this.gasStation.delete()
        }
        User.withNewTransaction {
            user.delete()
        }
    }

}
