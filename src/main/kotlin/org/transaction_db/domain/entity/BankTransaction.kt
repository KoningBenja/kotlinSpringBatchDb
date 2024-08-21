package org.transaction_db.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "bank_transaction")
class BankTransaction (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "reference")
    val reference: String? = null,

    @Column(name = "account_number")
    val accountNumber: String? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    val validationStatus: ValidationStatus? = null
) {
    constructor(reference: String?, accountNumber: String?)
            : this(null, reference, accountNumber)

}