package org.squidmin.spring.gradle.pubsub.service;

import com.google.cloud.spring.pubsub.core.subscriber.PubSubSubscriberTemplate;
import com.google.pubsub.v1.PubsubMessage;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class SubService {

    private final PubSubSubscriberTemplate subscriberTemplate;

    public SubService(PubSubSubscriberTemplate subscriberTemplate) {
        this.subscriberTemplate = subscriberTemplate;
    }

    @PostConstruct
    public void subscribe() {
        this.subscriberTemplate.subscribe("test-subscription", (message) -> {
            // Correctly get the PubsubMessage
            PubsubMessage pubsubMessage = message.getPubsubMessage();

            // Decode the message data from ByteString to String
            String messageData = pubsubMessage.getData().toStringUtf8();
            System.out.println("Message received: " + messageData);

            // Acknowledge the message
            message.ack();
        });
    }

}
