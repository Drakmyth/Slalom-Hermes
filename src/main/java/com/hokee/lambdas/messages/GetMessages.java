package com.hokee.lambdas.messages;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokee.shared.models.Message;
import com.hokee.shared.requests.GetMessagesRequest;
import com.hokee.shared.results.GetMessagesResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetMessages {

	private static final Logger logger = LoggerFactory.getLogger(GetMessages.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String BUCKET = "hermes-inboxes";
	private static final String ACCESS_KEY = "AKIAJUAIJL5GS25C54MA";
	private static final String SECRET_KEY = "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";

	public static GetMessagesResult handleRequest(final GetMessagesRequest request, final Context context) {

		final AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		try {
			final AmazonS3Client s3 = new AmazonS3Client(awsCredentials);

			List<Message> userMessages = new ArrayList<>();
			ObjectListing listing = s3.listObjects(BUCKET, request.getUser().getId());
			for (S3ObjectSummary obj : listing.getObjectSummaries()) {
				S3Object s3obj = s3.getObject(BUCKET, obj.getKey());
				Message message = mapper.readValue(s3obj.getObjectContent(), Message.class);
				userMessages.add(message);
			}

			return GetMessagesResult.Success(userMessages);
		} catch (IOException | AmazonClientException e) {
			logger.error("Error sending message", e);
			return GetMessagesResult.Failure(e.getMessage());
		}
	}
}
