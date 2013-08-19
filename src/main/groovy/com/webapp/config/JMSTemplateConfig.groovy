package com.webapp.config

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
@Configuration
class JMSTemplateConfig {
    @Inject
    ConnectionFactory jConnectionFactory

    @Inject @Qualifier("dQueue")
    Destination dQueue

    @Inject @Qualifier("dReplyQueue")
    Destination dReplyQueue


    @Bean(name = ["dJmsTemplate"])
    public JmsTemplate dateJmsTemplate() throws JMSException {
        def template = new JmsTemplate(jConnectionFactory)
        template.setDefaultDestination(dQueue)
        template
    }

    @Bean(name = ["dReplyJmsTemplate"])
    public JmsTemplate dateReplyJmsTemplate() throws JMSException {
        def template = new JmsTemplate(jConnectionFactory)
        template.setDefaultDestination(dReplyQueue)
        template
    }
}
