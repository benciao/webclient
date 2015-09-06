package com.ecg.webclient.common.rabbit;

import org.springframework.amqp.core.AmqpTemplate;

public class RabbitMqUtil
{
    private AmqpTemplate webclientRabbitTemplate;

    public RabbitMqUtil(AmqpTemplate webclientRabbitTemplate)
    {
        this.webclientRabbitTemplate = webclientRabbitTemplate;
    }

    public AmqpTemplate getWebclientRabbitTemplate()
    {
        return webclientRabbitTemplate;
    }
}
