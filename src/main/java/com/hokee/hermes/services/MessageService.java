package com.hokee.hermes.services;

import com.hokee.hermes.config.MessageServiceConfig;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.models.Contact;
import com.hokee.hermes.models.User;
import com.hokee.shared.SendMessageInput;
import com.hokee.shared.SendMessageOutput;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class MessageService implements IMessageService {

	private MessageServiceConfig _config;
	private RestTemplate _restTemplate;

	public MessageService(final MessageServiceConfig config, final RestTemplate restTemplate) {

		_config = config;
		_restTemplate = restTemplate;
	}

	@Override
	public void sendMessage(final User sender, final Contact recipient, final String message) {

		final SendMessageInput body = new SendMessageInput(sender.getId(), recipient.getId(), message);
		HttpEntity<SendMessageInput> request = new HttpEntity<>(body, buildHeaders());
		final SendMessageOutput result = _restTemplate.postForEntity(_config.getSendMessageAPIEndpoint(), request, SendMessageOutput.class).getBody();
	}

	private HttpHeaders buildHeaders() {

		return new HttpHeaders();
	}
}
