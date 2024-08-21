package org.transaction_db.domain.repository

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.transaction_db.common.constant.DataSourceConstants.SERVICE_TRANSACTION_MANAGER
import org.transaction_db.domain.entity.BankTransaction
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(SERVICE_TRANSACTION_MANAGER)
class BankTransactionJpaRepositoryImpl(
    @PersistenceContext private val entityManager: EntityManager
) : BankTransactionJpaRepository {


    override fun bulkInsert(transactions: List<BankTransaction>) {
        transactions.forEachIndexed { idx, transaction ->
            val newTransaction = BankTransaction(
                reference = transaction.reference,
                accountNumber = transaction.accountNumber
            )
            entityManager.persist(newTransaction)

            if (idx % 20 == 0) {
                entityManager.flush()
                entityManager.clear()
            }
        }
    }
}