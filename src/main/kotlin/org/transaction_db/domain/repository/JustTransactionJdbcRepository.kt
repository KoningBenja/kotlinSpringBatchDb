package org.transaction_db.domain.repository

import org.transaction_db.domain.entity.JustTransaction

interface JustTransactionJdbcRepository {
    fun bulkInsert(transactions: List<JustTransaction>)
}