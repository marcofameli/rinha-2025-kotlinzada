package plugins

import br.com.plugins.configureDatabases
import br.com.plugins.configureHTTP
import br.com.plugins.configureRouting
import br.com.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureHTTP()
    configureStatusPages()
    configureRouting()
}

