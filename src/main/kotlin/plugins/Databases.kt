package br.com.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import infra.database.PaymentsTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val hikariConfig = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = environment.config.property("db.jdbcUrl").getString()
        username = environment.config.property("db.username").getString()
        password = environment.config.property("db.password").getString()
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    val datasource = HikariDataSource(hikariConfig)

    Database.connect(datasource)

    transaction {
        SchemaUtils.create(PaymentsTable)
    }

    log.info("Database configured and connected successfully to Postgres using HikariCP.")
}