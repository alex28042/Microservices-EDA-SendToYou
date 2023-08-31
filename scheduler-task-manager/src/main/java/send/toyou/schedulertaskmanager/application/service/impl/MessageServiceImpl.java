package send.toyou.schedulertaskmanager.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import send.toyou.schedulertaskmanager.application.service.MessageService;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Autowired
    private StreamBridge streamBridge;

    @Override
    public boolean send(String key, Object message, String destination) {
        var messageBuilder = MessageBuilder.withPayload(message);
        messageBuilder.setHeader(KafkaHeaders.KEY, key);

        var output = messageBuilder.build();

        return this.streamBridge.send(destination, output);
    }
}
