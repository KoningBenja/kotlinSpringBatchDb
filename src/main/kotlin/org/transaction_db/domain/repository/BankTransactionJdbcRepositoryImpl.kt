package org.transaction_db.domain.repository


import org.transaction_db.domain.entity.BankTransaction
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.transaction_db.common.constant.DataSourceConstants.SERVICE_TRANSACTION_MANAGER

@Repository
@Transactional(SERVICE_TRANSACTION_MANAGER)
class BankTransactionJdbcRepositoryImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : BankTransactionJdbcRepository {
    companion object {
        const val TABLE_NAME = "bank_transaction"
    }

    override fun bulkInsert(transactions: List<BankTransaction>) {
        val sql = String.format(
            """
            |INSERT INTO `%s` (reference, account_number)
            |VALUES (:reference, :accountNumber)
            """.trimMargin(),
            TABLE_NAME
        )

        val params = transactions
            .map { BeanPropertySqlParameterSource(it) }
            .toTypedArray()

        namedParameterJdbcTemplate.batchUpdate(sql, params)
    }
}