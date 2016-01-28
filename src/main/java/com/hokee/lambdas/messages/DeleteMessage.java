package com.hokee.lambdas.messages;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hokee.shared.requests.DeleteMessageRequest;
import com.hokee.shared.results.DeleteMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMessage {

	private static final Logger logger = LoggerFactory.getLogger(DeleteMessage.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final String BUCKET = "hermes-inboxes";
	private static final String ACCESS_KEY = "AKIAJUAIJL5GS25C54MA";
	private static final String SECRET_KEY = "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";

	public static DeleteMessageResult handleRequest(final DeleteMessageRequest request, final Context context) {

		final AWSCredentials awsCredentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);

		try {
			final AmazonS3Client s3 = new AmazonS3Client(awsCredentials);

			final String key = request.getUserId() + "/" + request.getMessageId();
			s3.deleteObject(BUCKET, key);

			logger.info("Successfully deleted message `{}`", key);
			return DeleteMessageResult.Success();
		} catch (AmazonClientException e) {
			logger.error("Error deleting message", e);
			return DeleteMessageResult.Failure(e.getMessage());
		}
	}
}
