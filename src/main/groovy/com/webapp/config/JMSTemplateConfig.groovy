package com.webapp.config

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.core.JmsTemplate

import javax.inject.Inject
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.JMSException

/**
 * JMS Template Configuration, specifically for MQ
 *
 */

@Slf4j
@Configuration
class JMSTemplateConfig {


    @Inject  @Qualifier("mqConnectionFactory")
    ConnectionFactory connectionFactory


    @Inject  @Qualifier("springCachingConnectionFactory")
    ConnectionFactory sConnectionFactory

    @Inject @Qualifier("dQueue")
    Destination dQueue

    @Inject @Qualifier("dReplyQueue")
    Destination dReplyQueue


    @Bean(name = ["dJmsTemplate"])
    public JmsTemplate dateJmsTemplate() throws JMSException {
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::")
        log.info(":   using ConnectionFactory class: " + connectionFactory.getClass().getSimpleName() )
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::")
        def template = new JmsTemplate(connectionFactory)
        template.setDefaultDestination(dQueue)
        template.setReceiveTimeout(10000)
        template
    }

    @Bean(name = ["dReplyJmsTemplate"])
    public JmsTemplate dateReplyJmsTemplate() throws JMSException {
        def template = new JmsTemplate(connectionFactory)
        template.setDefaultDestination(dReplyQueue)
        template.setReceiveTimeout(10000)
        template
    }


    @Bean(name = ["sJmsTemplate"])
    public JmsTemplate sDateJmsTemplate() throws JMSException {
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::")
        log.info(":   using ConnectionFactory class: " + sConnectionFactory.getClass().getSimpleName() )
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::")
        def template = new JmsTemplate(sConnectionFactory)
        template.setDefaultDestination(dQueue)
        template.setReceiveTimeout(10000)
        template
    }

    @Bean(name = ["sReplyJmsTemplate"])
    public JmsTemplate sDateReplyJmsTemplate() throws JMSException {
        def template = new JmsTemplate(sConnectionFactory)
        template.setDefaultDestination(dReplyQueue)
        template.setReceiveTimeout(10000)
        template
    }
}
