package com.joelforjava.carburant

class ReportingController {
	static responseFormats = ['json', 'xml', 'csv']

    // TODO - move to different method
    def index() {
        withFormat {
            csv {
                def fillups = FillUp.findAll().collect { it.toCSV() } as List<String>

                def outs = response.outputStream

                outs << "${FillUp.csvMetadata()}\n"
                fillups.each { String line ->
                    outs << "${line}\n"
                }

                outs.flush()
                outs.close()
            }
        }
    }

    def export() {
    }
}
