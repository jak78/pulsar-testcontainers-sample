package com.streamnative.devrel;

import org.apache.pulsar.client.api.PulsarClientException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;

@Testcontainers
class DummyPulsarClientTest {

    @Container
    PulsarContainer pulsar = new PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:3.1.0"));
    private DummyPulsarClient underTest;

    @BeforeEach
    void setUp() throws PulsarClientException {
        underTest = new DummyPulsarClient(pulsar.getPulsarBrokerUrl());
    }

    @AfterEach
    public void tearDown() throws PulsarClientException {
        underTest.close();
    }

    @Test
    void shouldProduceAndReceiveAMessage() throws PulsarClientException {
        underTest.launchConsumer();
        underTest.produce();
        await().until( () -> underTest.getMessages().size(), equalTo(1));
    }

}