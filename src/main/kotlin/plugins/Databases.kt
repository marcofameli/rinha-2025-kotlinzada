package br.com.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import infra.database.PaymentsTable
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Connection

fun Application.configureDatabases() {
    // 1. Cria o objeto de configuração do HikariCP (nosso pool de conexões)
    val hikariConfig = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        // 2. Lê os dados do arquivo application.conf que acabamos de arrumar
        jdbcUrl = environment.config.property("db.jdbcUrl").getString()
        username = environment.config.property("db.username").getString()
        password = environment.config.property("db.password").getString()
        maximumPoolSize = 10 // Número de conexões simultâneas que o pool pode ter
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }

    // 3. Cria a fonte de dados (DataSource) com base na configuração do pool
    val datasource = HikariDataSource(hikariConfig)

    // 4. Conecta o Exposed (nossa ferramenta de query) a essa fonte de dados
    Database.connect(datasource)

    // 5. Roda uma transação inicial para criar nossa tabela se ela não existir
    transaction {
        SchemaUtils.create(PaymentsTable)
    }

    log.info("Database configured and connected successfully to Postgres using HikariCP.")
}