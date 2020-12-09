package com.example.messagingrabbitmqdemo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class Runner(val receiver: Receiver, val rabbitTemplate: RabbitTemplate): CommandLineRunner {

    val log: Logger = LoggerFactory.getLogger(Runner::class.java)

    override fun run(vararg args: String?) {
        log.info("Sending message...")

        rabbitTemplate.convertAndSend(MessagingRabbitmqDemoApplication.topicExchangeName,
            "foo.bar.baz",
            "Hello from RabbitMQ!")

        receiver.latch.await(10000, TimeUnit.MILLISECONDS)

    }

}