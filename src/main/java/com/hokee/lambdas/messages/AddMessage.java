package com.hokee.lambdas.messages;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokee.shared.Message;
import com.hokee.shared.AddMessageResult;

public class AddMessage {

	private static final Logger logger = LoggerFactory.getLogger(AddMessage.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String BUCKET = "hermes-inboxes";
	private static final String ACCESS_KEY = "AKIAJUAIJL5GS25C54MA";
	private static final String SECRET_KEY = "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";

	public static AddMessageResult handleRequest(final Message input, final Context context) {

		final AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		try {
			final byte[] messageBytes = mapper.writeValueAsBytes(input);
			final AmazonS3Client s3 = new AmazonS3Client(awsCredentials);

			try (InputStream objStream = new ByteArrayInputStream(messageBytes)) {
				final ObjectMetadata metadata = new ObjectMetadata();
				metadata.setContentLength(messageBytes.length);

				final String key = input.getRecipient() + "/" + input.getId();
				s3.putObject(BUCKET, key, objStream, metadata);

				logger.debug("Successfully uploaded message `{}`", key);
			} catch (IOException e) {
				logger.error("Error sending message", e);
				return AddMessageResult.Failure(e.getMessage());
			}

			return AddMessageResult.Success();
		} catch (JsonProcessingException | AmazonClientException e) {
			logger.error("Error sending message", e);
			return AddMessageResult.Failure(e.getMessage());
		}
	}
}
