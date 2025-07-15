package core.model

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

/**
 * Define os possíveis estados de um pagamento.
 */

enum class PaymentStatus {
    PENDING,
    PROCESSED,
    FAILED
}

/**
 * Define quais processadores podemos usar.
 */

enum class PaymentProcessor {
    DEFAULT,
    FALLBACK
}

// --- Modelos para o ticket RINHA-02 (já podemos deixar aqui) ---

/**
 * Representa a resposta do endpoint GET /payments-summary.
 */

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

