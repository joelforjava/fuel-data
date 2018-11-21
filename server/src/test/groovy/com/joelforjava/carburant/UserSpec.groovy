package com.joelforjava.carburant

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserSpec extends Specification implements DomainUnitTest<User> {

    def setup() {
    }

    def cleanup() {
    }

    void 'A user must have a password'() {
        given: 'We have a user without a password'
        def user = new User(username: 'bobby')

        when: 'We try to save the user'
        user.save()

        then: 'The object contains errors'
        user.hasErrors()
        'nullable' == user.errors.getFieldError('password').code
        null == user.errors.getFieldError('password').rejectedValue

    }
}
