package infra.database

import payment.data.PaymentProcessor
import payment.data.PaymentStatus
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object PaymentsTable : Table("payments") {
    val id = uuid("id").autoGenerate()
    val correlationId = uuid("correlation_id").uniqueIndex()
    val amount = decimal("amount", 12, 2)
    val status = enumerationByName("status", 10, PaymentStatus::class)
    val createdAt = timestamp("created_at")


    val processor = enumerationByName("processor", 10, PaymentProcessor::class).nullable()
    val processedAt = timestamp("processed_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
