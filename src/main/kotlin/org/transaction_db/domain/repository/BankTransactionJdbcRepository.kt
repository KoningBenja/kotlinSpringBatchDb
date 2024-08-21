package org.transaction_db.domain.repository

import org.transaction_db.domain.entity.BankTransaction

interface BankTransactionJdbcRepository {
    fun bulkInsert(transactions: List<BankTransaction>)
}