package org.transaction_db.job

import org.transaction_db.domain.entity.BankTransaction
import org.transaction_db.domain.repository.BankTransactionRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.transaction_db.domain.entity.JustTransaction
import org.transaction_db.utils.EasyRandomFactory
import java.util.stream.IntStream

@Configuration
class BankInsertTestConfig(
    private val jobRepository: JobRepository,
    private val transactionRepository: BankTransactionRepository
) {
    @Bean
    fun bankTransactionBulkInsertTestJob(bankTransactionBulkInsertTestStep: Step): Job {
        return JobBuilder("bankTransactionBulkInsertTestJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(bankTransactionBulkInsertTestStep)
            .build()
    }

    @Bean
    fun bankTransactionBulkInsertTestStep(transactionManager: PlatformTransactionManager): Step {
        val testTasklet = Tasklet { _, _ ->
            val easyRandom = EasyRandomFactory.newBankTransactionInstance()

            val transactions = IntStream.range(0, 10_000)
                .parallel()
                .mapToObj { easyRandom.nextObject(BankTransaction::class.java) }
                .toList()

            transactionRepository.bulkInsert(transactions)

            RepeatStatus.FINISHED
        }

        return StepBuilder("bankTransactionBulkInsertTestStep", jobRepository)
            .tasklet(testTasklet, transactionManager)
            .build()
    }
}