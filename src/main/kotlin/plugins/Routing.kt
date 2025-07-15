package br.com.plugins

import core.model.Payment
import core.model.PaymentRequest
import core.model.PaymentStatus
import core.ports.PaymentRepository
import infra.database.PostgresPaymentRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting() {
    val paymentRepository: PaymentRepository = PostgresPaymentRepository()

    routing {
        post("/payments") {
            val request = call.receive<PaymentRequest>()
            val correlationId = try {
                UUID.fromString(request.correlationId)
            } catch (e: Exception) {
                throw IllegalArgumentException("correlationId must be a valid UUID")
            }

            val payment = Payment(
                correlationId = correlationId,
                amount = request.amount,
                status = PaymentStatus.PENDING
            )

            paymentRepository.save(payment)
            call.respond(HttpStatusCode.Accepted)
        }
    }
}
