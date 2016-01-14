package com.hokee.hermes.services;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.hokee.hermes.config.MessageServiceConfig;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.shared.Contact;
import com.hokee.shared.Message;
import com.hokee.shared.SendMessageResult;
import com.hokee.shared.User;

public class MessageService implements IMessageService {
	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	private MessageServiceConfig _config;
	private RestTemplate _restTemplate;

	public MessageService(final MessageServiceConfig config, final RestTemplate restTemplate) {

		_config = config;
		_restTemplate = restTemplate;
	}

	@Override
	public SendMessageResult sendMessage(final User sender, final Contact recipient, final String message) {
		log.info("sendMessage sender={}, recipient={}, message={}", sender.getName(), recipient.getName(), message);

		// TODO: need to secure API endpoints and use headers for Auth
		final Message body = new Message(sender.getId(), recipient.getId(), message);
		HttpEntity<Message> request = new HttpEntity<>(body, new HttpHeaders());
		final SendMessageResult result = _restTemplate.postForEntity(_config.getSendMessageAPIEndpoint(), request, SendMessageResult.class).getBody();

		log.info("message sent status: {} - {}", result.isSuccess(), result.getMessage());

		return result;
	}

	@Override
	public List<Message> getMessages(final User user) {

		// TODO: use rest template
		final ArrayList<Message> messages = new ArrayList<>();
		messages.add(new Message("0001", "0000", "message 1"));
		messages.add(new Message("0003", "0000", "message 2"));

		return messages;
	}

	@Override
	public boolean deleteMessage(final String messageId) {

		// TODO: use rest template
		return true;
	}
}
