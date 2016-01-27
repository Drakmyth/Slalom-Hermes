package com.hokee.hermes;

import static org.mockito.Mockito.mock;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;
import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;

public class HermesSpeechletCheckMessagesTest {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private ISessionService sessionService;
	private IMessageService messageService;
	private IContactService contactService;
	private IUserService userService;

	private HermesSpeechlet speechlet;

	@Before
	public void setUp() throws Exception {
		sessionService = mock(ISessionService.class);
		messageService = mock(IMessageService.class);
		contactService = mock(IContactService.class);
		userService = mock(IUserService.class);

		speechlet = new HermesSpeechlet(messageService, contactService, userService);
	}

	@Test
	public void testOnSessionStarted() {

		try (final InputStream is = new FileInputStream("new CheckMessageIntent.json")) {


			final SpeechletRequestEnvelope envelope = SpeechletRequestEnvelope.fromJson(is);

			// call startSession

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}