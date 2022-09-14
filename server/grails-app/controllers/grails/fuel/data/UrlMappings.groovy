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
            // NOTE: You have to use controller here. If you try to use resource,
            //       you will need to update the @Resource to point to the controller,
            //       and when you do this, all hell breaks loose and the code no longer compiles
            //       because of something to do with @Log? Will revisit later.
            "/fill-ups"(resources: "fillUp")
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
