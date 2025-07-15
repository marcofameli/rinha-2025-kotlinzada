package br.com.plugins

import io.ktor.server.application.*
import plugins.configureStatusPages

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

