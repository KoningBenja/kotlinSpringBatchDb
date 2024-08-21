package org.transaction_db.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "just_transaction")
class JustTransaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "reference")
    val reference: String? = null,

    @Column(name = "account_number")
    val accountNumber: String? = null,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "start_balance")
    val startBalance: BigDecimal,

    @Column(name = "mutation")
    val mutation: BigDecimal,

    @Column(name = "end_balance")
    val endBalance: BigDecimal,
)