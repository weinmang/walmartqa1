package com.cloudbees.walmartqa1;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.cloudbees.walmartqa1.services.ItemResourceTest;

public class ItemCatalogApplication extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public ItemCatalogApplication() {
		singletons.add(new ItemResourceTest());
	}

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
