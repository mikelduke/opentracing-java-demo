package com.mikelduke.vhs.catalog;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mikelduke.opentracing.sparkjava.OpenTracingSparkFilters;

import spark.Spark;

public class VHSCatalogApplication {
	private static final String CLAZZ = VHSCatalogApplication.class.getName();
	private static final Logger LOGGER = Logger.getLogger(CLAZZ);

	public static void main(String[] args) {
		int port = 4567;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		TracingConfiguration.configureGlobalTracer();

		new VHSCatalogApplication().start(port);
	}

	public void start(int port) {
		Spark.port(port);

		OpenTracingSparkFilters sparkTracingFilters = new OpenTracingSparkFilters();
		Spark.before(sparkTracingFilters.before());
		Spark.afterAfter(sparkTracingFilters.afterAfter());
		Spark.exception(Exception.class, sparkTracingFilters.exception());

		Spark.get("/", (req, res) -> "hello");
		
		Spark.awaitInitialization();
		LOGGER.logp(Level.INFO, CLAZZ, "main", "Server started on port " + Spark.port());
	}
}
