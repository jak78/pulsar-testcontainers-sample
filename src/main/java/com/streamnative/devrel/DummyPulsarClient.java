package com.streamnative.devrel;

import org.apache.pulsar.client.api.*;

import java.util.ArrayList;
import java.util.Collection;

public class DummyPulsarClient {
    private PulsarClient client;
    private Collection<byte[]> messages;

    public DummyPulsarClient(String serviceUrl) throws PulsarClientException {
        client = PulsarClient.builder()
//                .serviceUrl("pulsar://localhost:6650")
                .serviceUrl(serviceUrl)
                .build();
        messages = new ArrayList<>();
    }
    public void produce() throws PulsarClientException {
        
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();
        
        // You can then send messages to the broker and topic you specified:
        producer.send("My message".getBytes());
        
        producer.close();
    }
    
    public void launchConsumer() throws PulsarClientException {
        MessageListener myMessageListener = (consumer, msg) -> {
          try {
              System.out.println("Message received: " + new String(msg.getData()));
              messages.add(msg.getData());
              consumer.acknowledge(msg);
          } catch (Exception e) {
              consumer.negativeAcknowledge(msg);
          }
        };
        
        Consumer consumer = client.newConsumer()
             .topic("my-topic")
             .subscriptionName("my-subscription")
             .messageListener(myMessageListener)
             .subscribe();
    }
    
    public void close() throws PulsarClientException {
        client.close();
    }

    public Collection<byte[]> getMessages() {
        return messages;
    }
}
