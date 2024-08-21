package org.transaction_db.domain.repository


import org.transaction_db.domain.entity.JustTransaction
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.transaction_db.common.constant.DataSourceConstants.SERVICE_TRANSACTION_MANAGER

@Repository
@Transactional(SERVICE_TRANSACTION_MANAGER)
class JustTransactionJdbcRepositoryImpl(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) : JustTransactionJdbcRepository {
    companion object {
        const val TABLE_NAME = "just_transaction"
    }

    override fun bulkInsert(transactions: List<JustTransaction>) {
        val sql = String.format(
            """
            |INSERT INTO `%s` (reference, account_number, description, start_balance, mutation, end_balance)
            |VALUES (:reference, :accountNumber, :description, :startBalance, :mutation, :endBalance)
            """.trimMargin(),
            TABLE_NAME
        )

        val params = transactions
            .map { BeanPropertySqlParameterSource(it) }
            .toTypedArray()

        namedParameterJdbcTemplate.batchUpdate(sql, params)
    }
}