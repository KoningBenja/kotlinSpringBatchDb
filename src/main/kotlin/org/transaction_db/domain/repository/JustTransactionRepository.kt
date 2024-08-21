package org.transaction_db.domain.repository

import org.transaction_db.domain.entity.JustTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface JustTransactionRepository : JpaRepository<JustTransaction, Long>, JustTransactionJdbcRepository {
}