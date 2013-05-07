package com.smoopay.sts.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.codec.Base64;

/**
 * http://krishnasblog.com/2013/02/10/spring-test-mvc-junit-testing-spring-
 * security-layer-with-inmemorydaoimpl-2/
 * http://java.dzone.com/articles/spring-test-mvc-junit-testing
 * http://static.springsource
 * .org/spring-security/site/docs/3.2.x/reference/ns-config
 * .html#ns-getting-started
 * https://github.com/SpringSource/spring-integration-samples
 * /blob/master/intermediate
 * /rest-http/src/test/java/org/springframework/integration
 * /samples/rest/RestHttpClientTest.java
 * 
 * http://www.petrikainulainen.net/programming/spring-framework/integration-
 * testing-of-spring-mvc-applications-security/
 * 
 * @author decad
 * 
 */
public class HttpBasicCredentialCreator {

	public static HttpHeaders createBasicCredential(String username, String password) {
		HttpHeaders headers = new HttpHeaders();
		String combinedUsernamePassword = username + ":" + password;
		byte[] base64Token = Base64.encode(combinedUsernamePassword.getBytes());
		String base64EncodedToken = new String(base64Token);
		// adding Authorization header for HTTP Basic authentication
		headers.add("Authorization", "Basic " + base64EncodedToken);
		return headers;
	}

	public static HttpHeaders createBasicSpringCredential() {
		return createBasicCredential("adam1_login", "adam1_pass");
	}

	public static void main(String... args) {
		HttpBasicCredentialCreator.createBasicCredential("adam", "adam");
	}
}