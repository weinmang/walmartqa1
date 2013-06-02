package com.cloudbees.walmartqa1.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;

import com.cloudbees.walmartqa1.ApplicationConstants;
import com.cloudbees.walmartqa1.cvo.ItemStockComposite;
import com.cloudbees.walmartqa1.dao.ItemDAO;
import com.cloudbees.walmartqa1.dao.StoreStockDAO;
import com.cloudbees.walmartqa1.dto.Item;
import com.cloudbees.walmartqa1.exception.ApiException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("/services/items")
@PermitAll
public class ItemResource {

	/**
	 * Retrieve 
	 * @param String containing an Item Id
	 * @return JSON formatted response
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@HeaderParam("Authorization") String authorization, @PathParam("id") String id) {
		if (!isAuthorized(authorization)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(ApplicationConstants.SEC_SVC_URI).build();			
		}
		if (id.length()>12) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Item Id! Did you use GET to create an Item?").build();
		}
		if (id == null || id.isEmpty()) {
			ResponseBuilder builder = Response.status(Status.NOT_FOUND);
			builder.type(MediaType.APPLICATION_JSON);
			builder.entity("Item Not Found due to null or empty Id");
			throw new WebApplicationException(builder.build());
		}
		try {
			Item item = (new ItemDAO().findById(id));
			StoreStockDAO dao = new StoreStockDAO();
			ItemStockComposite itemComposite = dao.findById(id, item.getLocaleId());
			PrettyPrinter pretty = new DefaultPrettyPrinter();
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer(pretty);
			String display = writer.writeValueAsString(itemComposite);
			return Response.ok(display).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		} catch (ApiException e) {
			e.printStackTrace();
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
		}
	}

	/**
	 * Simple insert entity
	 * @param InputStream containing JSON representation of an item
	 * @return JSON formatted response
	 */
	@POST
	@Path("create/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createItem(@HeaderParam("Authorization") String authorization, InputStream is) {
		if (!isAuthorized(authorization)) {
			return Response.status(Response.Status.UNAUTHORIZED).entity(ApplicationConstants.SEC_SVC_URI).build();			
		}
		try {
			if (is.available()<=12) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Invalid Item! Did you use POST to retrieve an Item?").build();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return Response.serverError().entity(ioe.getMessage()).build();
		}
		try {
			Item item = readItem(is);
			ItemDAO dao = new ItemDAO();
			Item newItem = dao.createItem(item);
			PrettyPrinter pretty = new DefaultPrettyPrinter();
			ObjectMapper mapper = new ObjectMapper();
			ObjectWriter writer = mapper.writer(pretty);
			String display = writer.writeValueAsString(newItem);
			return Response.created(URI.create("/services/items/"
					+ item.getItemId()))
					.entity(display)
					.build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		} catch (ApiException e) {
			e.printStackTrace();
			return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
		}
	}

	private Item readItem(InputStream is) {

		Item item = null;
		try {
			item = mapper.readValue(is, Item.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item; 

	}

	/**
	 * One of many possible outcomes from the service provider
	 * @param authorization string from authorization header
	 * @return boolean authorization status
	 */
	private boolean isAuthorized(String authorization) {
		boolean isAuthorized = false;
		if (authorization.contains("Authorization:")) {
			String token = authorization.substring("Authorization:".length());
			try {
				URL secURL = new URL(ApplicationConstants.SEC_SVC_URI + "?token=" + token);
				BufferedReader reader = new BufferedReader(new InputStreamReader(secURL.openStream()));
				String line = null;
				StringBuilder responseStrBuilder = new StringBuilder();
				while ((line=reader.readLine())!=null) {
					responseStrBuilder.append(line);
				}
				JSONObject responseJSON = new JSONObject(responseStrBuilder.toString());
				isAuthorized = responseJSON.getBoolean("authorization");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return isAuthorized;
	}

}
