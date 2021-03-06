package org.incf.atlas.ucsd.server;

import org.incf.atlas.common.server.RequestQueryStrings;
import org.incf.atlas.ucsd.resource.Capabilities;
import org.incf.atlas.ucsd.resource.CoordinateTransformationChain;
import org.incf.atlas.ucsd.resource.DescribeSRS;
import org.incf.atlas.ucsd.resource.Get2DImagesByPOI;
import org.incf.atlas.ucsd.resource.ListSRS;
import org.incf.atlas.ucsd.resource.ListTransformations;
import org.incf.atlas.ucsd.resource.NotSupported;
import org.incf.atlas.ucsd.resource.NotYetImplemented;
import org.incf.atlas.ucsd.resource.PingResource;
import org.incf.atlas.ucsd.resource.ProcessDescriptions;
import org.incf.atlas.ucsd.resource.Retrieve2DImage;
import org.incf.atlas.ucsd.resource.StructureNamesByPOI;
import org.incf.atlas.ucsd.resource.TransformPOI;
import org.incf.atlas.ucsd.resource.UnrecognizedUri;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class ServerApplication extends Application 
		implements RequestQueryStrings {

	private static final Logger logger = LoggerFactory.getLogger(
			ServerApplication.class);

	public ServerApplication() {

		// redirect restlet logging to slf4j
		// see http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/101-restlet.html
		// "Replacing default JDK logging with log4j"
		SLF4JBridgeHandler.install();

		StringBuilder buf = new StringBuilder();
		buf.append("\n");
		buf.append("       *******************************************\n");
		buf.append("       *          Starting INCF Server           *\n");
		buf.append("       *******************************************\n");
		buf.append("\n  Server version   : ");
		buf.append("\nWaiting for connections ...");
		logger.info(buf.toString());
	}

	@Override
	public Restlet createRoot() {
		Router router = new Router(getContext());

		router.attach(GET_CAPABILITIES,           Capabilities.class);
		router.attach(DESCRIBE_PROCESS,           ProcessDescriptions.class);
		router.attach(DESCRIBE_SRS,               DescribeSRS.class);
        router.attach(DESCRIBE_TRANSFORMATION,    NotYetImplemented.class);
        router.attach(GET_2D_IMAGES_BY_POI,       Get2DImagesByPOI.class);
        router.attach(GET_2D_IMAGES_BY_URI,       NotYetImplemented.class);
        router.attach(GET_CELLS_BY_POI,           NotYetImplemented.class);
        router.attach(GET_CELLS_BY_URI,           NotYetImplemented.class);
        router.attach(GET_CORRELATION_MAP_BY_POI, NotSupported.class);
        router.attach(GET_GENES_BY_POI,           NotSupported.class);
        router.attach(GET_STRUCTURE_NAMES_BY_POI, StructureNamesByPOI.class);
        router.attach(GET_TRANSFORMATION_CHAIN,   CoordinateTransformationChain.class);
		router.attach(LIST_SRS_S,                 ListSRS.class);
		router.attach(LIST_TRANSFORMATIONS,       ListTransformations.class);
		router.attach(RETRIEVE_2D_IMAGE,          Retrieve2DImage.class);
        router.attach(TRANSFORM_POI,              TransformPOI.class);
		
		router.attach("/ping/{pingType}", PingResource.class);
		
		router.attachDefault(UnrecognizedUri.class);

		return router;

	}

	// testing only
	public static void main(String[] args) {

		// redirect restlet logging to slf4j
		// see http://wiki.restlet.org/docs_1.1/13-restlet/48-restlet/101-restlet.html
		// "Replacing default JDK logging with log4j"
		SLF4JBridgeHandler.install();

		StringBuilder buf = new StringBuilder();
		buf.append("\n");
		buf.append("       *******************************************\n");
		buf.append("       *          Starting INCF Server           *\n");
		buf.append("       *******************************************\n");
		buf.append("\n  Server version   : ");
		buf.append("\nWaiting for connections ...");
		logger.info(buf.toString());
		
		try {
			Component component = new Component();
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach(new ServerApplication());
			component.start();
		} catch (Exception e) {
			logger.error("Exception in main().", e);
		}
	}

}
