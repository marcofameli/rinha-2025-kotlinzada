package payment.data

import kotlinx.serialization.Serializable
import java.math.BigDecimal
import utils.BigDecimalSerializer
import java.time.Instant
import java.util.*


@Serializable
data class PaymentRequest (
    val correlationId: String,

    @Serializable(with = BigDecimalSerializer::class)
    val amount: BigDecimal
)

data class Payment(
    val id: UUID = UUID.randomUUID(),
    val correlationId: UUID,
    val amount: BigDecimal,
    var status: PaymentStatus,
    var processor: PaymentProcessor? = null,
    val createdAt: Instant = Instant.now(),
    var processedAt: Instant? = null
)

enum class PaymentStatus {
    PENDING,
    PROCESSED,
    FAILED
}

enum class PaymentProcessor {
    DEFAULT,
    FALLBACK
}

@Serializable
data class PaymentSummaryResponse(
    val default: SummaryDetails = SummaryDetails(),
    val fallback: SummaryDetails = SummaryDetails()
)

@Serializable
data class SummaryDetails(
    val totalRequests: Long = 0,

    @Serializable(with = BigDecimalSerializer::class)
    val totalAmount: BigDecimal = BigDecimal.ZERO
)


