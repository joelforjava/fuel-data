package com.joelforjava.carburant

import com.joelforjava.carburant.util.ServerUtils
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.exceptions.HttpClientException
import spock.lang.AutoCleanup
import spock.lang.Requires
import spock.lang.Shared
import spock.lang.Specification


@Integration

/**
 * These tests assume we're running a local proxy server against the local development server
 * and therefore is using the example data generated in BootStrap.groovy.
 */
@Requires({ ServerUtils.isOnline('http://localhost:8880') })
class VehicleProxyFuncSpec extends Specification {
    @Shared
    @AutoCleanup
    private HttpClient client

    @OnceBefore
    void init() {
        String baseUrl = "http://localhost:8880"
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

        when:
        UserCredentials creds = new UserCredentials(username: 'cleverusername', password: 'difficultpassword')
        HttpRequest loginRequest = HttpRequest.POST('/api/login', creds)
        HttpResponse<BearerToken> loginResp = client.toBlocking().exchange(loginRequest, BearerToken)

        HttpRequest request = HttpRequest.GET('/vehicles')
                .header('Authorization', "Bearer ${loginResp.body().accessToken}")
        HttpResponse<List<Map>> resp = this.client.toBlocking().exchange(request, Argument.of(List, Map))

        then:
        resp.status == HttpStatus.OK
        resp.body().size() == 1
        resp.body().find { it.make == 'Honda' }
    }
}
