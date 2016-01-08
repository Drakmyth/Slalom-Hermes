package com.hokee.sendmessage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokee.shared.SendMessageInput;
import com.hokee.shared.SendMessageOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class App {

	private static final Logger logger = LoggerFactory.getLogger(App.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String BUCKET = "inboxes";
	private static final String ACCESS_KEY = "<ACCESS_KEY_HERE>";
	private static final String SECRET_KEY = "<SECRET_KEY_HERE>";

	public static SendMessageOutput handleRequest(final SendMessageInput input, final Context context) {

		final AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		try {
			final byte[] messageBytes = mapper.writeValueAsBytes(input);
			final AmazonS3Client s3 = new AmazonS3Client(awsCredentials);

			try (InputStream objStream = new ByteArrayInputStream(messageBytes)) {
				final ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentLength(messageBytes.length);

				final String key = input.getRecipient() + "/" + UUID.randomUUID();
				s3.putObject(BUCKET, key, objStream, metadata);

				logger.debug("Successfully uploaded message `{}`", key);
			} catch (IOException e) {
				logger.error("Error sending message", e);
				return SendMessageOutput.Failure(e.getMessage());
			}

			return SendMessageOutput.Success();
		} catch (JsonProcessingException | AmazonClientException e) {
			logger.error("Error sending message", e);
			return SendMessageOutput.Failure(e.getMessage());
		}
	}
}
