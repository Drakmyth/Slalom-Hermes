package com.hokee.hermes.services;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.hokee.hermes.config.MessageServiceConfig;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.Message;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.AddMessageRequest;
import com.hokee.shared.requests.DeleteMessageRequest;
import com.hokee.shared.requests.GetMessagesRequest;
import com.hokee.shared.results.AddMessageResult;
import com.hokee.shared.results.DeleteMessageResult;
import com.hokee.shared.results.GetMessagesResult;

public class MessageService implements IMessageService {

	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	private MessageServiceConfig _config;
	private RestTemplate _restTemplate;

	public MessageService(final MessageServiceConfig config, final RestTemplate restTemplate) {

		_config = config;
		_restTemplate = restTemplate;
	}

	@Override
	public boolean sendMessage(final Contact contact, final String message) {

		log.info("xsendMessage sender={}, recipient={}, message={}", contact.getUser().getName(), contact.getContact().getName(), message);

		// TODO: need to secure API endpoints and use headers for Auth
		final Message newMessage = new Message(UUID.randomUUID().toString(), contact.getUser().getId(), contact.getContact().getId(), message);

		final HttpEntity<AddMessageRequest> request = new HttpEntity<>(new AddMessageRequest(newMessage), new HttpHeaders());
		final AddMessageResult result = _restTemplate.postForEntity(
				_config.getSendMessageAPIEndpoint(), request, AddMessageResult.class).getBody();

		log.info("message sent status: {} - {}", result.isSuccess(), result.getErrorMessage());

		return result.isSuccess();
	}

	@Override
	public Iterable<Message> getMessages(final User user) {

		log.info("getMessages user={}", user.getName());

		final HttpEntity<GetMessagesRequest> request = new HttpEntity<>(new GetMessagesRequest(user), new HttpHeaders());
		final GetMessagesResult result = _restTemplate.postForEntity(
				_config.getGetMessagesAPIEndpoint(), request, GetMessagesResult.class).getBody();

		return result.getMessages();
	}

	@Override
	public boolean deleteMessage(final User user, final String messageId) {

		log.info("deleteMessage user={}, messageId={}", user.getName(), messageId);

		final HttpEntity<DeleteMessageRequest> request = new HttpEntity<>(new DeleteMessageRequest(user.getId(), messageId), new HttpHeaders());
		final DeleteMessageResult result = _restTemplate.postForEntity(
				_config.getDeleteMessageAPIEndpoint(), request, DeleteMessageResult.class).getBody();


		log.info("message delete status: {} - {}", result.isSuccess(), result.getErrorMessage());

		return result.isSuccess();
	}
}
