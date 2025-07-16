package payment.repository

import payment.data.Payment

interface PaymentRepository {

    suspend fun save(payment: Payment)
}