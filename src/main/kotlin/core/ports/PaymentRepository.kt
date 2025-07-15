package core.ports

import core.model.Payment

interface PaymentRepository {

    suspend fun save(payment: Payment)
}