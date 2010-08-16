package org.incf.atlas.emage.server;

import org.incf.atlas.emage.resource.Capabilities;
import org.incf.atlas.emage.resource.NotSupported;
import org.incf.atlas.emage.resource.NotYetImplemented;
import org.incf.atlas.emage.resource.PingResource;
import org.incf.atlas.emage.resource.ProcessDescriptions;
import org.incf.atlas.emage.resource.UnrecognizedUri;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class ServerApplication extends Application {

	private static final Logger logger = LoggerFactory.getLogger(
			ServerApplication.class);
	
	/*
	There are 4 patterns and all have service, version, request, and 
	ResponseForm (note upper case on the latter). For every Execute we will 
	also have Identifier and most, but not all, will have DataInputs (both 
	leading upper case).

	service = WPS
	version = version
	request = { GetCapabilities | DescribeProcess | Execute }
	Identifier = { ..... | .....  our standard set }
	DataInputs = { varies by identifier }
	ResponseForm = [ response format }
	 */

	// elements of Atlas GET query string
	// note query key case!
	//	service, version, request -- leading lower
	//  Identifier, DataInputs, ResponseForm -- leading upper
	private static final String SERVICE = "?service={service}";
	private static final String VERSION = "&version={version}";
	private static final String SERV_VER = SERVICE + VERSION;
	private static final String REQ_KEY = "&request=";
	private static final String EXEC_ID_KEY = REQ_KEY +"Execute&Identifier=";
	private static final String DATA_INPUTS = "&DataInputs={dataInputs}";
	
	// query string patterns for routing; each makes ResponseForm optional
	private static final String GET_CAPABILITIES =
		SERVICE + REQ_KEY + "GetCapabilities";
	private static final String DESCRIBE_PROCESS =
		SERV_VER + REQ_KEY + "DescribeProcess";
	private static final String DESCRIBE_SRS =
		SERV_VER + EXEC_ID_KEY + "DescribeSRS" + DATA_INPUTS;
    private static final String DESCRIBE_TRANSFORMATION =
        SERV_VER + EXEC_ID_KEY + "DescribeTransfomation" + DATA_INPUTS;
    private static final String GET_2D_IMAGES_BY_POI =
        SERV_VER + EXEC_ID_KEY + "Get2DImagesByPOI" + DATA_INPUTS;
    private static final String GET_2D_IMAGES_BY_URI =
        SERV_VER + EXEC_ID_KEY + "Get2DImagesByPOI" + DATA_INPUTS;
    private static final String GET_CELLS_BY_POI =
        SERV_VER + EXEC_ID_KEY + "Get2DImagesByPOI" + DATA_INPUTS;
    private static final String GET_CELLS_BY_URI =
        SERV_VER + EXEC_ID_KEY + "Get2DImagesByPOI" + DATA_INPUTS;
    private static final String GET_CORRELATION_MAP_BY_POI =
        SERV_VER + EXEC_ID_KEY + "GetCorrelationMapByPOI" + DATA_INPUTS;
    private static final String GET_GENES_BY_POI =
        SERV_VER + EXEC_ID_KEY + "GetGenesByPOI" + DATA_INPUTS;
    private static final String GET_STRUCTURE_NAMES_BY_POI =
        SERV_VER + EXEC_ID_KEY + "GetStructureNamesByPOI" + DATA_INPUTS;
    private static final String GET_TRANSFORMATION_CHAIN =
        SERV_VER + EXEC_ID_KEY + "GetTransformationChain" + DATA_INPUTS;
	private static final String LIST_SRS_S =
		SERV_VER + EXEC_ID_KEY + "ListSRSs";          // no data inputs
	private static final String LIST_TRANSFORMATIONS =
		SERV_VER + EXEC_ID_KEY + "ListTransformations" + DATA_INPUTS;
	private static final String RETRIEVE_2D_IMAGE =
		SERV_VER + EXEC_ID_KEY + "Retrieve2DImage" + DATA_INPUTS;
    private static final String TRANSFORM_POI =
        SERV_VER + EXEC_ID_KEY + "TransformPOI" + DATA_INPUTS;
	
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
		router.attach(DESCRIBE_SRS,               NotYetImplemented.class);
        router.attach(DESCRIBE_TRANSFORMATION,    NotSupported.class);
        router.attach(GET_2D_IMAGES_BY_POI,       NotSupported.class);
        router.attach(GET_2D_IMAGES_BY_URI,       NotSupported.class);
        router.attach(GET_CELLS_BY_POI,           NotSupported.class);
        router.attach(GET_CELLS_BY_URI,           NotSupported.class);
        router.attach(GET_CORRELATION_MAP_BY_POI, NotSupported.class);
        router.attach(GET_GENES_BY_POI,           NotYetImplemented.class);
        router.attach(GET_STRUCTURE_NAMES_BY_POI, NotSupported.class);
        router.attach(GET_TRANSFORMATION_CHAIN,   NotYetImplemented.class);
		router.attach(LIST_SRS_S,                 NotYetImplemented.class);
		router.attach(LIST_TRANSFORMATIONS,       NotYetImplemented.class);
		router.attach(RETRIEVE_2D_IMAGE,          NotSupported.class);
        router.attach(TRANSFORM_POI,              NotYetImplemented.class);
		
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