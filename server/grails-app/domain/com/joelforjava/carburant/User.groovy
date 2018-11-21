package com.joelforjava.carburant

class User {

    String username
    String password
    Date dateCreated

    static constraints = {
        username size: 3..150, nullable: false, blank: false, unique: true
        password nullable: false, blank: false, password: true
    }
}
