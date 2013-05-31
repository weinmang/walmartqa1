package com.cloudbees.walmartqa1.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cloudbees.walmartqa1.cvo.ItemStockComposite;
import com.cloudbees.walmartqa1.dao.ItemDAO;
import com.cloudbees.walmartqa1.dao.StoreStockDAO;
import com.cloudbees.walmartqa1.dto.Item;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@Path("/services/items")
public class ItemResource {

	StoreStockDAO dao = new StoreStockDAO();
	ObjectMapper mapper = new ObjectMapper();

	//TODO learn security and implement
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getItem(@PathParam("id") String id) {

		Item item = (new ItemDAO().findById(id));
		ItemStockComposite itemComposite = dao.findById(id, item.getLocaleId());
		PrettyPrinter pretty = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer(pretty);
		String display = null;
		try {
			display = writer.writeValueAsString(itemComposite);
			return Response.ok(display).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@POST
	@Path("create/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createItem(InputStream is) {
		Item item = readItem(is);
		ItemDAO dao = new ItemDAO();
		Item newItem = dao.createItem(item);
		PrettyPrinter pretty = new DefaultPrettyPrinter();
		ObjectWriter writer = mapper.writer(pretty);
		String display = null;
		try {
			display = writer.writeValueAsString(newItem);
			return Response.created(URI.create("/services/items/"
					+ item.getItemId()))
					.entity(display)
					.build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	public Item readItem(InputStream is) {

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

}
