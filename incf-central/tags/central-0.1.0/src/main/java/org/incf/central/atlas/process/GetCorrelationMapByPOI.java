package org.incf.central.atlas.process;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;
import org.deegree.commons.utils.kvp.InvalidParameterValueException;
import org.deegree.commons.utils.kvp.MissingParameterException;
import org.deegree.commons.xml.XMLAdapter;
import org.deegree.services.controller.exception.ControllerException;
import org.deegree.services.controller.ows.OWSException;
import org.deegree.services.wps.Processlet;
import org.deegree.services.wps.ProcessletException;
import org.deegree.services.wps.ProcessletExecutionInfo;
import org.deegree.services.wps.ProcessletInputs;
import org.deegree.services.wps.ProcessletOutputs;
import org.deegree.services.wps.output.ComplexOutput;
import org.incf.atlas.waxml.generated.CorrelatioMapType;
import org.incf.atlas.waxml.generated.CorrelationMapResponseDocument;
import org.incf.atlas.waxml.generated.IncfNameType;
import org.incf.atlas.waxml.generated.StructureTermType;
import org.incf.atlas.waxml.generated.StructureTermsResponseDocument;
import org.incf.atlas.waxml.generated.StructureTermsResponseType;
import org.incf.atlas.waxml.generated.CorrelatioMapType.CorrelationCollection;
import org.incf.atlas.waxml.generated.StructureTermType.Code;
import org.incf.atlas.waxml.generated.StructureTermsResponseType.StructureTerms;
import org.incf.atlas.waxml.utilities.Utilities;
import org.incf.central.atlas.util.CentralConfigurator;
import org.incf.central.atlas.util.CentralServiceVO;
import org.incf.central.atlas.util.KeyValueBean;
import org.incf.central.atlas.util.ReadXML;
import org.incf.common.atlas.exception.InvalidDataInputValueException;
import org.incf.common.atlas.util.DataInputHandler;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

public class GetCorrelationMapByPOI implements Processlet {

	private static final Logger LOG = LoggerFactory
			.getLogger(GetCorrelationMapByPOI.class);

	CentralConfigurator config = CentralConfigurator.INSTANCE;

	String abaReference = config.getValue("srsname.abareference.10");
	String abaVoxel = config.getValue("srsname.abavoxel.10");
	String agea = config.getValue("srsname.agea.10");
	String whs09 = config.getValue("srsname.whs.09");
	String whs10 = config.getValue("srsname.whs.10");
	String emap = config.getValue("srsname.emap.10");
	String paxinos = config.getValue("srsname.paxinos.10");

	// private String dataInputString;
	// private DataInputs dataInputs;
	String hostName = "";
	String portNumber = "";
	String servicePath = "";
	String responseString = "";

	@Override
	public void process(ProcessletInputs in, ProcessletOutputs out,
			ProcessletExecutionInfo info) throws ProcessletException {

		try {

			System.out.println(" Inside GetCorrelationMapByPOI... ");

			URL processDefinitionUrl = this.getClass().getResource(
					"/" + this.getClass().getSimpleName() + ".xml");
			DataInputHandler dataInputHandler = new DataInputHandler(new File(
					processDefinitionUrl.toURI()));
			String srsName = dataInputHandler.getValidatedStringValue(in,
					"srsName");
			String x = String.valueOf(DataInputHandler.getDoubleInputValue(in,
					"x"));
			String y = String.valueOf(DataInputHandler.getDoubleInputValue(in,
					"y"));
			String z = String.valueOf(DataInputHandler.getDoubleInputValue(in,
					"z"));
			String filter = dataInputHandler.getValidatedStringValue(in,
					"filter");
			String vocabulary = dataInputHandler.getValidatedStringValue(in,
					"vocabulary");

			CentralServiceVO vo = new CentralServiceVO();

			vo.setFromSRSCodeOne(srsName);
			vo.setFromSRSCode(srsName);
			vo.setFilter(filter);
			vo.setVocabulary(vocabulary);

			System.out.println("From SRS Code: " + vo.getFromSRSCodeOne());
			System.out.println("Filter: " + vo.getFilter());

			vo.setOriginalCoordinateX(x);
			vo.setOriginalCoordinateY(y);
			vo.setOriginalCoordinateZ(z);

			// Start - FIXME - Uncomment below two lines and comment the
			// other three lines
			// String hostName = uri.getHost();
			// String portNumber = delimitor + uri.getPort();
			String delimitor = config.getValue("incf.deploy.port.delimitor");
			hostName = config.getValue("incf.deploy.host.name");
			portNumber = config.getValue("incf.aba.port.number");
			portNumber = delimitor + portNumber;
			// End - FIXME

			System.out.println("Coordinate X: " + vo.getOriginalCoordinateX());

			// Step 1 - Call GetProcessByIdentifier(identifier) - I am ignoring
			// this method right now due to short of time

			// Step 2 -
			ArrayList completeCorrelationMapList = new ArrayList();
			ReadXML readXML = new ReadXML();

			// 2a - Call the method from ABA Hub
			String abaURL = "http://"
					+ hostName
					+ portNumber
					+ "/aba/atlas?service=WPS&version=1.0.0&request=Execute&Identifier=GetCorrelationMapByPOI&DataInputs=srsName="
					+ vo.getFromSRSCode() + ";x=" + vo.getOriginalCoordinateX()
					+ ";y=" + vo.getOriginalCoordinateY() + ";z="
					+ vo.getOriginalCoordinateZ() + ";filter=" + vo.getFilter();
			completeCorrelationMapList = readXML.getCorrelationData(abaURL,
											completeCorrelationMapList);

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			java.util.Date date = new java.util.Date();
			String currentTime = dateFormat.format(date);
			vo.setCurrentTime(currentTime);

			// Generating 2 random number to be used as GMLID
			int randomGMLID1 = 0;
			Random randomGenerator1 = new Random();
			for (int idx = 1; idx <= 10; ++idx) {
				randomGMLID1 = randomGenerator1.nextInt(100);
			}

			XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
			opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
			opt.setSaveNamespacesFirst();
			opt.setSaveAggressiveNamespaces();
			opt.setUseDefaultNamespace();

			/*
			 * QueryInfoType query = rootDoc.addNewQueryInfo();
			 * 
			 * Utilities.addMethodNameToQueryInfo(query,
			 * "GetStructureNamesByPOI  ", uri.toString());
			 * 
			 * Criteria criterias = query.addNewCriteria();
			 * 
			 * InputStringType srsCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * srsCriteria.setName("srsName");
			 * srsCriteria.setValue(vo.getFromSRSCode());
			 * 
			 * InputStringType xCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * 
			 * xCriteria.setName("x");
			 * xCriteria.setValue(vo.getOriginalCoordinateX().replace(";x=",
			 * ""));
			 * 
			 * InputStringType yCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * yCriteria.setName("y");
			 * yCriteria.setValue(vo.getOriginalCoordinateY().replace(";y=",
			 * ""));
			 * 
			 * InputStringType zCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * zCriteria.setName("z");
			 * zCriteria.setValue(vo.getOriginalCoordinateZ().replace(";z=",
			 * ""));
			 * 
			 * InputStringType srsCodeCriteria = (InputStringType)
			 * criterias.addNewInput().changeType(InputStringType.type);
			 * srsCodeCriteria.setName("vocabulary");
			 * srsCodeCriteria.setValue(vo.getVocabulary()); InputStringType
			 * filterCodeCriteria = (InputStringType)
			 * criterias.addNewInput().changeType(InputStringType.type);
			 * filterCodeCriteria.setName("filter");
			 * filterCodeCriteria.setValue(vo.getFilter());
			 * 
			 * query.addNewQueryUrl();
			 * query.getQueryUrl().setName("GetStructureNamesByPOI");
			 * query.getQueryUrl().setStringValue(uri.toString());
			 * query.setTimeCreated(Calendar.getInstance());
			 */
			Iterator iterator = completeCorrelationMapList.iterator();
			KeyValueBean keyValue = null;

			CorrelationMapResponseDocument document = CorrelationMapResponseDocument.Factory
			.newInstance();

			CorrelatioMapType imagesRes = document
			.addNewCorrelationMapResponse();
			
			while (iterator.hasNext()) {

				keyValue = (KeyValueBean) iterator.next();
			/*
			 * QueryInfoType query = imagesRes.addNewQueryInfo();
			 * 
			 * Utilities.addMethodNameToQueryInfo(query,
			 * "GetCorrelationMapByPOI", uri.toString());
			 * 
			 * Criteria criterias = query.addNewCriteria();
			 * 
			 * Utilities.addInputStringCriteria(criterias, "srsName",
			 * vo.getFromSRSCode());
			 * 
			 * InputStringType xCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * xCriteria.setName("x"); xCriteria.setValue(coordinateX);
			 * 
			 * InputStringType yCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * yCriteria.setName("y"); yCriteria.setValue(coordinateY);
			 * 
			 * InputStringType zCriteria = (InputStringType)
			 * criterias.addNewInput() .changeType(InputStringType.type);
			 * zCriteria.setName("z"); zCriteria.setValue(coordinateZ);
			 * 
			 * InputStringType filterCodeCriteria = (InputStringType)
			 * criterias.addNewInput().changeType(InputStringType.type);
			 * filterCodeCriteria.setName("filter");
			 * filterCodeCriteria.setValue("maptype:coronal");
			 */
			//imagesRes.setCorrelationUrl(keyValue.getValue());
			CorrelationCollection coll1 = imagesRes.addNewCorrelationCollection();
			coll1.setHubCode("ABA");
			coll1.setCorrelationUrl(keyValue.getValue());

			ArrayList errorList = new ArrayList();
			opt.setErrorListener(errorList);
			boolean isValid = document.validate(opt);

			}

			ComplexOutput complexOutput = (ComplexOutput) out
			.getParameter("GetCorrelationMapByPOIOutput");

			// get reader on document; reader --> writer
			XMLStreamReader reader = document.newXMLStreamReader();
			XMLStreamWriter writer = complexOutput.getXMLStreamWriter();
			XMLAdapter.writeElement(writer, reader);

		} catch (MissingParameterException e) {
			LOG.error(e.getMessage(), e);
			throw new ProcessletException(new OWSException(e));
		} catch (InvalidParameterValueException e) {
			LOG.error(e.getMessage(), e);
			throw new ProcessletException(new OWSException(e));
		} catch (InvalidDataInputValueException e) {
			LOG.error(e.getMessage(), e);
			throw new ProcessletException(e); // is already OWSException
		} catch (OWSException e) {
			LOG.error(e.getMessage(), e);
			throw new ProcessletException(e); // is already OWSException
		} catch (Throwable e) {
			String message = "Unexpected exception occurred: " + e.getMessage();
			LOG.error(message, e);
			throw new ProcessletException(new OWSException(message, e,
					ControllerException.NO_APPLICABLE_CODE));
		}

	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

	// Retrieves the unique names for brain regions
	private String xmlQueryStringToGetBrainRegionNames(
			String transformedCoordinateX, String transformedCoordinateY,
			String transformedCoordinateZ) {

		StringBuffer xmlQuery = new StringBuffer();
		xmlQuery.append("<request>");
		xmlQuery.append("<coordinateX>").append(transformedCoordinateX.trim())
				.append("</coordinateX>");
		xmlQuery.append("<coordinateY>").append(transformedCoordinateY.trim())
				.append("</coordinateY>");
		xmlQuery.append("<coordinateZ>").append(transformedCoordinateZ.trim())
				.append("</coordinateZ>");
		xmlQuery.append("</request>");

		return xmlQuery.toString();

	}

	/*
	 * This method will parse the xmlString coming as a parameter and prepare
	 * the data model which then returns back to the calling method.
	 */
	private CentralServiceVO parseResponseStringForBrainRegionNames(
			String xmlData, CentralServiceVO vo) {

		// Make the xml document from the xml string
		org.jdom.Document document;

		try {

			SAXBuilder builder = new SAXBuilder(
					"org.apache.xerces.parsers.SAXParser", false);

			document = builder.build(new InputSource(new ByteArrayInputStream(
					xmlData.getBytes())));

			// Get all the childrens
			List structuresList = document.getRootElement().getChildren();
			Iterator iterator = structuresList.iterator();

			// Start - xml parsing
			org.jdom.Element requestElement;

			// We know the structure name is going to be only one, so not
			// running the loop
			for (int i = 0; iterator.hasNext(); i++) {

				requestElement = (org.jdom.Element) iterator.next();
				vo.setStructureName(requestElement.getChildText(
						"brainRegionCode").trim());
				vo.setStructureDescription(requestElement.getChildText(
						"brainRegionDescription").trim());
				// list.add(vo);

			}
			// End - xml parsing

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vo;

	}

}
