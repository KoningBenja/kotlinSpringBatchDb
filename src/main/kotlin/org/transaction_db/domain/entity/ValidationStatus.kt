package org.transaction_db.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "validation_status")
class ValidationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(name = "name")
    val name: String? = null

    @Column(name = "description")
    val description: String? = null

    @OneToMany(mappedBy = "validationStatus", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val bankTransactions: MutableList<BankTransaction> = arrayListOf()
}