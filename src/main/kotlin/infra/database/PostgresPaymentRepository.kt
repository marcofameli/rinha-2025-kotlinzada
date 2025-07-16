package infra.database
import payment.data.Payment
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.slf4j.LoggerFactory
import payment.repository.PaymentRepository


class PostgresPaymentRepository : PaymentRepository {
    private val logger = LoggerFactory.getLogger(this::class.java)


    override suspend fun save(payment: Payment) {
        newSuspendedTransaction {
            logger.info("Saving payment with correlationId ${payment.correlationId} with PENDING status.")

            PaymentsTable.insert { statement ->
                statement[id] = payment.id
                statement[correlationId] = payment.correlationId
                statement[amount] = payment.amount
                statement[status] = payment.status
                statement[createdAt] = payment.createdAt
            }
        }
    }
}