package com.benlevy.tools;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ActiveMQMessageSender implements MessageListener {
    private static int ackMode;
    private static String clientQueueName;

    private boolean transacted = false;
    private MessageProducer producer;

    static {
        ackMode = Session.AUTO_ACKNOWLEDGE;
    }

    public static void main(String[] args) throws IOException {

    }

    public ActiveMQMessageSender() {
    	String[] message = {"Second TEST Here"};
    	sendActiveMQMessages("tcp://localhost:61616", "orderRequest.queue.estore", true, message);
    }
    
    public static boolean sendActiveMQMessages(String tcpConnection, String name, boolean isQueue, String[] messages) {
    	return sendActiveMQMessages(tcpConnection, name, isQueue, Arrays.asList(messages));
    }
    public static boolean sendActiveMQMessages(String tcpConnection, String name, boolean isQueue, List<String> messages) {
    	clientQueueName = name;
    	boolean transacted = false;
    	int ackMode = Session.AUTO_ACKNOWLEDGE;
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(tcpConnection);
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(transacted, ackMode);
            Destination adminQueueOrTopic;
            if (isQueue)
            	adminQueueOrTopic = session.createQueue(clientQueueName);
            else
            	adminQueueOrTopic = session.createTopic(clientQueueName);

            //Setup a message producer to send message to the queue the server is consuming from
            MessageProducer producer = session.createProducer(adminQueueOrTopic);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //Create a temporary queue that this client will listen for responses on then create a consumer
            //that consumes message from this temporary queue...for a real application a client should reuse
            //the same temp queue for each message to the server...one temp queue per client
            //tempDest = session.createTemporaryQueue();
            //MessageConsumer responseConsumer = session.createConsumer(tempDest);

            //This class will handle the messages to the temp queue as well
            //responseConsumer.setMessageListener(this);

            for (String m : messages) {
			    //Now create the actual message you want to send
			    TextMessage txtMessage = session.createTextMessage();
			    txtMessage.setText(m);
			
			    //Set the reply to field to the temp queue you created above, this is the queue the server
			    //will respond to
			    //txtMessage.setJMSReplyTo(tempDest);
			
			    //Set a correlation ID so when you get a response you know which sent message the response is for
			    //If there is never more than one outstanding message to the server then the
			    //same correlation ID can be used for all the messages...if there is more than one outstanding
			    //message to the server you would presumably want to associate the correlation ID with this
			    //message somehow...a Map works good
			    String correlationId = createRandomString();
			    txtMessage.setJMSCorrelationID(correlationId);
			    producer.send(txtMessage);
			    System.out.println("Sent message: " + correlationId);
			    Thread.sleep(500L);
            }
            return true;
        } catch (JMSException e) {
        	e.printStackTrace();
        	return false;
        } catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} finally {
        	try {
        		connection.close();
        	} catch (JMSException e) {
        		e.printStackTrace();
        	}
        }
    }

    private static String createRandomString() {
        Random random = new Random(System.currentTimeMillis());
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

    public void onMessage(Message message) {
        String messageText = null;
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                messageText = textMessage.getText();
                System.out.println("Message:\n\n" + messageText);
            }
        } catch (JMSException e) {
        	e.printStackTrace();
        }
    }
}