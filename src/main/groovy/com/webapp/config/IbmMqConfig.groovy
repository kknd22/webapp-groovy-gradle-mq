package com.webapp.config



import com.ibm.mq.jms.MQConnectionFactory
import com.ibm.mq.jms.MQDestination
import com.ibm.mq.jms.MQQueue
import com.ibm.msg.client.wmq.WMQConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jms.core.JmsTemplate


import javax.inject.Inject
import javax.jms.ConnectionFactory
import javax.jms.Destination
import javax.jms.JMSException

/**
 * JMS Configuration, specifically for MQ
 *
 */
//@PropertySource("classpath:/com/webapp/mq.properties")

@Configuration class IbmMqConfig {
    @Autowired
    private Environment env;

    static final MQ_TRANSPORT_TYPE = WMQConstants.WMQ_CM_CLIENT

    static final DATE_QUEUE_NAME = "PAS.DATETIME.Q"
    static final DATE_REPLY_QUEUE_NAME = "PAS.DATETIME.RTQ"
    static final CCSID=850

    @Bean(name = ["mqConnectionFactory"])
    public ConnectionFactory mqConnectionFactory() throws JMSException {
        MQConnectionFactory cf = new MQConnectionFactory()

        // the following are pssed in from command line
        cf.setHostName(env.getProperty("mq.queueManagerHost"))
        cf.setPort(env.getProperty("mq.queueManagerPort").toInteger())
        cf.setQueueManager(env.getProperty("mq.queueManagerName"))
        cf.setChannel(env.getProperty("mq.queueManagerChannel"))
        cf.setTransportType(MQ_TRANSPORT_TYPE)
        cf
    }

    @Bean(name = ["dQueue"])
    public Destination dateQueue() throws JMSException {
        MQDestination destination = new MQQueue(DATE_QUEUE_NAME)
        destination.setTargetClient(WMQConstants.WMQ_TARGET_DEST_MQ)
        destination.setCCSID(CCSID)
        destination

    }

    @Bean(name = ["dReplyQueue"])
    public Destination dateReplyQueue() throws JMSException {
        MQDestination destination = new MQQueue(DATE_REPLY_QUEUE_NAME)
        destination.setTargetClient(WMQConstants.WMQ_TARGET_DEST_MQ)
        destination.setCCSID(CCSID)
        destination
    }

}
