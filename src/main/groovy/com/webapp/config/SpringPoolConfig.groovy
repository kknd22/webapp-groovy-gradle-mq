package com.webapp.config

import com.ibm.mq.jms.MQConnectionFactory
import com.ibm.msg.client.wmq.WMQConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.jms.connection.CachingConnectionFactory
import org.springframework.jms.core.JmsTemplate

import javax.inject.Inject
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.JMSException

/**
 * JMS Template Configuration, specifically for MQ
 *
 */
@Configuration
class SpringPoolConfig {

    @Autowired
    private Environment env;

    @Inject @Qualifier("mqConnectionFactory")
    ConnectionFactory mqConnectionFactory

    @Bean(name = ["springCachingConnectionFactory"])
    public ConnectionFactory springCachingConnectionFactory() throws JMSException {
        CachingConnectionFactory cf = new CachingConnectionFactory(mqConnectionFactory)
        cf

    }

}
