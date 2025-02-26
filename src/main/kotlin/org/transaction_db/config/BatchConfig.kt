package org.transaction_db.config

import org.transaction_db.common.constant.DataSourceConstants.BATCH_DATASOURCE
import org.transaction_db.common.constant.DataSourceConstants.BATCH_TRANSACTION_MANAGER
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.support.BeanDefinitionRegistry
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.support.JdbcTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
class BatchConfig {
    @Primary
    @Bean(BATCH_TRANSACTION_MANAGER)
    fun batchTransactionManager(
        @Qualifier(BATCH_DATASOURCE) batchDataSource: DataSource
    ): PlatformTransactionManager {
        return JdbcTransactionManager(batchDataSource)
    }

    //This method is here to get rid of some warnings.
    //https://github.com/spring-projects/spring-batch/issues/4519
    @Bean
    fun jobRegistryBeanPostProcessorRemover(): BeanDefinitionRegistryPostProcessor {
        return BeanDefinitionRegistryPostProcessor { registry: BeanDefinitionRegistry ->
            registry.removeBeanDefinition("jobRegistryBeanPostProcessor")
        }
    }
}