package ar.edu.iua;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import ar.edu.iua.rest.Constantes;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/iw3"); // topic
												
		
		// registry.setApplicationDestinationPrefixes("/wsin"); // recepciones de datos

	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(Constantes.URL_WEBSOCKET_ENPOINT); // Habilita protocolo STOMP Spring
		registry.addEndpoint(Constantes.URL_WEBSOCKET_ENPOINT).withSockJS(); // Habilita SockJS para aportar
																				// flexibilidad
		// Cuando conectamos con un cliente. usamos: new SockJS('/ws');
		// En el caso anterior Constantes.URL_WEBSOCKET_ENPOINT='/ws'
	}

}