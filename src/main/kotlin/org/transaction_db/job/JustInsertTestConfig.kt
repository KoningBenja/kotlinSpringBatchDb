package org.transaction_db.job

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
import org.transaction_db.domain.repository.JustTransactionRepository
import org.transaction_db.utils.EasyRandomFactory
import org.transaction_db.utils.EasyRandomFactoryOne
import java.util.stream.IntStream

@Configuration
class JustInsertTestConfig(
    private val jobRepository: JobRepository,
    private val transactionRepository: JustTransactionRepository
) {

    @Bean
    fun justTransactionBulkInsertTestJob(justTransactionBulkInsertTestStep: Step): Job {
        return JobBuilder("justTransactionBulkInsertTestJob", jobRepository)
            .incrementer(RunIdIncrementer())
            .start(justTransactionBulkInsertTestStep)
            .build()
    }

    @Bean
    fun justTransactionBulkInsertTestStep(transactionManager: PlatformTransactionManager): Step {
        val testTasklet = Tasklet { _, _ ->
            val easyRandom = EasyRandomFactoryOne.newJustTransactionInstance()

            val transactions = IntStream.range(0, 10_000)
                .parallel()
                .mapToObj { easyRandom.nextObject(JustTransaction::class.java) }
                .toList()

            transactionRepository.bulkInsert(transactions)

            RepeatStatus.FINISHED
        }

        return StepBuilder("justTransactionBulkInsertTestStep", jobRepository)
            .tasklet(testTasklet, transactionManager)
            .build()
    }
}