package org.transaction_db.domain.repository

import org.transaction_db.domain.entity.BankTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface BankTransactionRepository : JpaRepository<BankTransaction, Long>, BankTransactionJdbcRepository {
}