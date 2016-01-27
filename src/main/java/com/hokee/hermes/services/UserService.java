package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import com.hokee.hermes.config.UserServiceConfig;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetUserForPinRequest;
import com.hokee.shared.requests.GetUserRequest;
import com.hokee.shared.results.GetUserResult;

public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private UserServiceConfig _config;
	private RestTemplate _restTemplate;

	public UserService(final UserServiceConfig config, final RestTemplate restTemplate) {
		_config = config;
		_restTemplate = restTemplate;
	}

	@Override
	public User getUser(final String id) {
		
		log.info("getUser userId={}", id);

		final HttpEntity<GetUserRequest> request = new HttpEntity<>(new GetUserRequest(id), new HttpHeaders());
		final GetUserResult result = _restTemplate.postForEntity(
				_config.getGetUserAPIEndpoint(), request, GetUserResult.class).getBody();

		if (result.isSuccess()) {
			log.info("getUser status: {} - {}", result.isSuccess(), result.getErrorMessage());

			return result.getUser();
		}

		log.info("getUser status: {} - {}", result.isSuccess(), result.getErrorMessage());
		return null;
	}

	@Override
	public User getUserForPin(final String pin) {
		
		log.info("getUser pin={}", pin);

		final HttpEntity<GetUserForPinRequest> request = new HttpEntity<>(new GetUserForPinRequest(pin), new HttpHeaders());
		final GetUserResult result = _restTemplate.postForEntity(
				_config.getGetUserForPinAPIEndpoint(), request, GetUserResult.class).getBody();

		if (result.isSuccess()) {
			log.info("getUser status: {} - {}", result.isSuccess(), result.getErrorMessage());

			return result.getUser();
		}

		log.info("getUser status: {} - {}", result.isSuccess(), result.getErrorMessage());
		return null;
	}
}
