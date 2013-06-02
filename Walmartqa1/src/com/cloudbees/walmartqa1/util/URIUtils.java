package com.cloudbees.walmartqa1.util;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.cloudbees.walmartqa1.ApplicationConstants;

public final class URIUtils {

	/**
	 * Base URI from constant
	 */
	public static URI getBaseURI() {
		return UriBuilder.fromUri(ApplicationConstants.BASE_URI).build();
	}
}
