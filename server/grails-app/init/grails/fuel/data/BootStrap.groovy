package grails.fuel.data

import com.joelforjava.carburant.FillUp
import com.joelforjava.carburant.GasStation

class BootStrap {

    def init = { servletContext ->
        environments {
            development {
                if (!FillUp.count()) {
                    def station = new GasStation(name: 'RaceTrac #717', address: '4420 Nantucket Expressway', city: 'Birmingham', state: 'AL', zipCode: 35127).save(failOnError: true)
                    new FillUp(dateOccurred: date('06/28/2011'), octane: '87', numGallons: 8.281, pricePerUnit: 3.439, totalCost: 28.48, odometerReading: 486.0, gasStation: station ).save(failOnError: true)
                }
            }
        }
    }
    def destroy = {
    }

    private Date date(String dateString) {
        new Date().parse('MM/dd/yyyy', dateString)
    }

}
