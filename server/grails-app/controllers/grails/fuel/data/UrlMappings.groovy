package grails.fuel.data

class UrlMappings {

    static mappings = {
        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/fill-ups"(resources: "fillUp") {
            "/gas-station"(resources: "gasStation")
        }
        "/gas-stations"(resources: "gasStation")

        "/vehicles"(resources: "vehicle") {
            "/fill-ups"(resources: "fillUp")
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
