package com.webapp.integration

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jms.core.JmsTemplate
import org.springframework.jms.core.MessageCreator
import org.springframework.stereotype.Service

import javax.inject.Inject
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Message
import javax.jms.Session
import javax.jms.TextMessage
import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference


/**
 * Created with IntelliJ IDEA.
 * User: u1cc32
 * Date: 8/15/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

@Service
@Slf4j
class MqSyncClient implements Callable<String> {

    //int id

    @Inject
    Destination dQueue
    @Inject
    Destination dReplyQueue

    @Inject
    @Qualifier("dJmsTemplate")
    JmsTemplate dJmsTemplate

    @Inject
    @Qualifier("dReplyJmsTemplate")
    JmsTemplate dReplyJmsTemplate

    //final static PAY_LOAD = "<DateTimeRequest><RacfUserId>U68P26</RacfUserId></DateTimeRequest>"
    final static PAY_LOAD = "U68P26"

    static int workerCount
    static AtomicInteger unfinishedCount = new AtomicInteger()
    static int simulatedResponseDelay

    @Override
    String call() throws Exception {
        queryMainframe(PAY_LOAD)
    }


    public String queryMainframe(final String pMessage) {
        final String lCorrelationId = String.format("%s::%s", "dateQueueName", UUID.randomUUID().toString());
        final AtomicReference<TextMessage> lJmsMessageRef = new AtomicReference<TextMessage>()

        String id = ++workerCount;

        dJmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(pMessage)
                message.setJMSCorrelationID(lCorrelationId)
                message.setJMSReplyTo(dReplyQueue)
                lJmsMessageRef.set(message)
                message
            }
        })

        log.info "--------------> #" + id + " sent message: [" + lJmsMessageRef.get().getText() +"]"
        //println "lCorrelationId: " + lCorrelationId

        final Message lSentMessage = lJmsMessageRef.get()
        final String lMessageId = lSentMessage.getJMSMessageID()
        String lMessageSelector = String.format("JMSCorrelationID IN ('%s','%s')", lMessageId, lCorrelationId)

        if (simulatedResponseDelay>0)
            Thread.currentThread().sleep(simulatedResponseDelay)

        TextMessage lResponseMessage = (TextMessage) dReplyJmsTemplate.receiveSelected(lMessageSelector)

        log.info "<============= #" + id + " received message: [" + lResponseMessage.getText() + "]"
        int uf = unfinishedCount.decrementAndGet()
        log.info " " + unfinishedCount + " : unfinishedCount ++++++++++++++++++++++++++++++++++++++++++ "
        if (uf == 0) {
            log.info ""
            log.info "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
            log.info "|                       FINISHED NORMAL                         |"
            log.info "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        }

        lResponseMessage.getText()


    }
}
