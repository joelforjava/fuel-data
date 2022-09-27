package com.joelforjava.carburant

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


@Integration
class VehicleFuncSpec extends Specification {

    @Shared
    @AutoCleanup
    private HttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:$serverPort"
        this.client = HttpClient.create(baseUrl.toURL())
    }

    def 'test retrieving a list of vehicles when unauthenticated shows not found'() {
        when:
        HttpRequest request = HttpRequest.GET('/vehicles')
        this.client.toBlocking().exchange(request, Argument.of(List, Map))

        then:
        HttpClientException e = thrown(HttpClientException)
        e.response.status == HttpStatus.NOT_FOUND
    }

    def 'test retrieving a list of vehicles when authenticated'() {
        List<Serializable> ids = []
        User user = User.withNewTransaction {
            new User(username: 'tester', password: 'extralongpassword').save(failOnError: true)
        }
        Vehicle.withNewTransaction {
            Vehicle vehicle = new Vehicle(modelYear: 2005, make: 'Honda', model: 'Insight',
                    user: user)
            vehicle.save(failOnError: true)
            ids << vehicle.id
        }

        when:
        UserCredentials creds = new UserCredentials(username: 'tester', password: 'extralongpassword')
        HttpRequest loginRequest = HttpRequest.POST('/api/login', creds)
        HttpResponse<BearerToken> loginResp = client.toBlocking().exchange(loginRequest, BearerToken)

        HttpRequest request = HttpRequest.GET('/vehicles')
                .header('Authorization', "Bearer ${loginResp.body().accessToken}")
        HttpResponse<List<Map>> resp = this.client.toBlocking().exchange(request, Argument.of(List, Map))

        then:
        resp.status == HttpStatus.OK
        resp.body().size() == 1
        resp.body().find { it.make == 'Honda' }

        cleanup:
        Vehicle.withNewTransaction {
            ids.each {
                Vehicle.load(it).delete(flush: true)
            }
        }
        User.withNewTransaction {
            user.delete(flush: true)
        }
    }
}
