package com.joelforjava.carburant

trait DomainFactory {

    GasStation validGasStation() {
        new GasStation(name: "Jimbo's One Stop", city: 'Montgomery', state: 'AL', zipCode: 36103)
    }
}