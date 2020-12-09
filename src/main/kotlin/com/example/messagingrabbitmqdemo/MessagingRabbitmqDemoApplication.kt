package com.example.messagingrabbitmqdemo


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MessagingRabbitmqDemoApplication {
	companion object {
		const val topicExchangeName: String = "spring-boot-exchange"
		const val queueName: String = "springboot"
	}

	@Bean
	fun queue(): Queue = Queue(queueName, false)

	@Bean
	fun exchange(): TopicExchange = TopicExchange(topicExchangeName)

	@Bean
	fun binding(queue: Queue, exchange: TopicExchange): Binding =
		BindingBuilder.bind(queue).to(exchange).with("foo.bar.#")

	@Bean
	fun container(connectionFactory: ConnectionFactory,
				  listenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
		val container: SimpleMessageListenerContainer = SimpleMessageListenerContainer()
		container.connectionFactory = connectionFactory
		container.setQueueNames(queueName)
		container.setMessageListener(listenerAdapter)

		return container
	}

	@Bean
	fun listenerAdaptor( receiver: Receiver): MessageListenerAdapter = MessageListenerAdapter(receiver, "receiveMessage")

}

fun main(args: Array<String>) {
	runApplication<MessagingRabbitmqDemoApplication>(*args)
}
