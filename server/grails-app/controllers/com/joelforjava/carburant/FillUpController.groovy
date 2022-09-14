package com.joelforjava.carburant

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import grails.web.http.HttpHeaders

import static org.springframework.http.HttpStatus.CREATED

class FillUpController extends RestfulController<FillUp> {

    FillUpController() {
        super(FillUp)
    }
//    FillUpController(Class<FillUp> resource) {
//        this(resource, false)
//    }
//
//    FillUpController(Class<FillUp> resource, boolean readOnly) {
//        super(resource, readOnly)
//    }

    @Transactional
    def save() {
        if (!params.vehicleId) {
            notFound()
            return
        }
        Vehicle vehicle = Vehicle.get(params.vehicleId)

        if (vehicle == null) {
            notFound()
            return
        }

        FillUp fillUp = createResource()
        fillUp.vehicle = vehicle
        fillUp.validate()

        if (fillUp.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fillUp.errors, view: 'create'
            return
        }

        saveResource fillUp

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [classMessageArg, fillUp.id])
                redirect fillUp
            }
            '*' {
                response.addHeader(HttpHeaders.LOCATION,
                        grailsLinkGenerator.link( resource: this.controllerName, action: 'show',id: fillUp.id, absolute: true,
                                namespace: hasProperty('namespace') ? this.namespace : null ))
                respond fillUp, status: CREATED
            }
        }

    }

}
