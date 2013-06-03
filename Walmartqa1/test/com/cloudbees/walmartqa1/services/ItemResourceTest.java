package com.cloudbees.walmartqa1.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cloudbees.walmartqa1.ApplicationConstants;
import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.provider.CsvDataProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class ItemResourceTest {

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions = {
			ClientHandlerException.class, UniformInterfaceException.class,
			JSONException.class })
	public void getItemGoodTest(Map<String, String> parms)
			throws ClientHandlerException, UniformInterfaceException,
			JSONException {
		String itemId = parms.get("itemId");
		String userId = parms.get("userId");
		String authorization = this.getAuthorization(
				ApplicationConstants.GET_ITEM_URI + itemId, userId);
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.GET_ITEM_URI + itemId);
		ClientResponse response = webResource
				.header("Authorization", "Token " + authorization)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Assert.assertEquals(response.getStatus(), Response.Status.OK,
				"Failed : HTTP error code : " + response.getStatus());
		JSONObject responseJSON = new JSONObject(
				response.getEntity(String.class));
		Assert.assertEquals(responseJSON.getString("itemId"), itemId,
				"Returned item inconsistant with search");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void getItemBadTest(Map<String, String> parms) {
		String itemId = parms.get("itemId");
		String userId = parms.get("userId");
		String authorization = this.getAuthorization(
				ApplicationConstants.GET_ITEM_URI + itemId, userId);
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.GET_ITEM_URI + itemId);
		ClientResponse response = webResource
				.header("Authorization", "Token " + authorization)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		Assert.assertNotEquals(response.getStatus(), Response.Status.OK,
				"Unexpected HTTP status code : " + response.getStatus());
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class)
	public void getItemNoAuthTest(Map<String, String> parms) {
		String itemId = parms.get("itemId");
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.GET_ITEM_URI + itemId);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED,
				"Expected unauthorized found " + response.getStatus());
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions = {
			JsonProcessingException.class, ClientHandlerException.class,
			UniformInterfaceException.class, JSONException.class })
	public void createItemGoodTest(Map<String, String> parms)
			throws JsonProcessingException, ClientHandlerException,
			UniformInterfaceException, JSONException {
		Item item = this.setItem(parms);
		String userId = parms.get("userId");
		String authorization = this.getAuthorization(
				ApplicationConstants.GET_ITEM_URI + item.toString(), userId);

		ObjectMapper mapper = new ObjectMapper();
		String resource = mapper.writeValueAsString(item);
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.CREATE_ITEM_URI);
		ClientResponse response = webResource
				.header("Authorization", "Token " + authorization)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, resource);

		Assert.assertEquals(response.getStatus(), Response.Status.CREATED,
				"Failed : HTTP error code : " + response.getStatus());
		JSONObject responseJSON = new JSONObject(
				response.getEntity(String.class));
		Assert.assertEquals(responseJSON.getString("itemId"), item.getItemId(),
				"Item creation failure");
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions = JsonProcessingException.class)
	public void createItemBadTest(Map<String, String> parms)
			throws JsonProcessingException {
		Item item = this.setItem(parms);
		String userId = parms.get("userId");
		String authorization = this.getAuthorization(
				ApplicationConstants.GET_ITEM_URI + item.toString(), userId);

		ObjectMapper mapper = new ObjectMapper();
		String resource = mapper.writeValueAsString(item);
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.CREATE_ITEM_URI);
		ClientResponse response = webResource
				.header("Authorization", "Token " + authorization)
				.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, resource);

		Assert.assertNotEquals(response.getStatus(), Response.Status.CREATED,
				"Unexpected HTTP status code : " + response.getStatus());
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions = JsonProcessingException.class)
	public void createItemNoAuthTest(Map<String, String> parms)
			throws JsonProcessingException {
		Item item = this.setItem(parms);

		ObjectMapper mapper = new ObjectMapper();
		String resource = mapper.writeValueAsString(item);
		Client client = Client.create();
		WebResource webResource = client
				.resource(ApplicationConstants.CREATE_ITEM_URI);
		ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, resource);
		Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED,
				"Expected unauthorized found " + response.getStatus());
	}

	@Test(dataProvider = "csvReader", dataProviderClass = CsvDataProvider.class, expectedExceptions = {
			JsonProcessingException.class, ClientHandlerException.class,
			UniformInterfaceException.class, JSONException.class })
	public void createThenGetGoodTest(Map<String, String> parms)
			throws JsonProcessingException, ClientHandlerException,
			UniformInterfaceException, JSONException {
		this.createItemGoodTest(parms);
		this.getItemGoodTest(parms);
	}

	/**
	 * One of many possible outcomes from the service provider
	 * 
	 * @return String authorization
	 */
	private String getAuthorization(String request, String userId) {
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
				.format(new Date());
		String authHash = new String(
				Base64.encode(request + userId + timeStamp));
		String authorized = null;
		try {
			URL secURL = new URL(ApplicationConstants.SEC_SVC_URI + authHash);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					secURL.openStream()));
			String line = null;
			StringBuilder responseStrBuilder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				responseStrBuilder.append(line);
			}
			JSONObject responseJSON = new JSONObject(
					responseStrBuilder.toString());
			authorized = responseJSON.getString("authorization");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authorized;
	}

	/**
	 * One of many possible outcomes from the service provider
	 * 
	 * @return String authorization
	 */
	private Item setItem(Map<String, String> parms) {
		Item item = new Item();
		String itemId = parms.get("itemId");
		String itemDescr = parms.get("itemDescr");
		String itemPriceStr = parms.get("itemPrice");
		Double itemPrice = Double.valueOf(itemPriceStr);
		String localeId = parms.get("localeId");
		item.setItemId(itemId);
		item.setItemDescr(itemDescr);
		item.setItemPrice(itemPrice);
		item.setLocaleId(localeId);
		return item;
	}
}
