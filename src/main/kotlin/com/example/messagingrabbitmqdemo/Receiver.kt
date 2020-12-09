package com.example.messagingrabbitmqdemo

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Receiver (val latch: CountDownLatch = CountDownLatch(1)){

    val log: Logger = LoggerFactory.getLogger(Receiver::class.java)

    fun receiveMessage(message:String) {
        log.info("Received < $message >")
        latch.countDown()
    }
}