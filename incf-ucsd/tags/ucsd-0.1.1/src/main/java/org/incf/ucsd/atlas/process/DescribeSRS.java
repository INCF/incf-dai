package org.incf.ucsd.atlas.process;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.opengis.gml.x32.PointType;
import net.opengis.gml.x32.UnitOfMeasureType;

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
import org.incf.atlas.waxml.generated.AuthorType;
import org.incf.atlas.waxml.generated.DescribeSRSResponseDocument;
import org.incf.atlas.waxml.generated.DescribeSRSResponseType;
import org.incf.atlas.waxml.generated.FiducialType;
import org.incf.atlas.waxml.generated.DescribeSRSResponseType.Fiducials;
import org.incf.atlas.waxml.generated.DescribeSRSResponseType.Slices;
import org.incf.atlas.waxml.generated.IncfCodeType;
import org.incf.atlas.waxml.generated.IncfUriSliceSource;
import org.incf.atlas.waxml.generated.Incfdescription;
import org.incf.atlas.waxml.generated.NeurodimensionType;
import org.incf.atlas.waxml.generated.NeurodimensionsType;
import org.incf.atlas.waxml.generated.OrientationType;
import org.incf.atlas.waxml.generated.ListSRSResponseType.SRSCollection;
import org.incf.atlas.waxml.generated.ListSRSResponseType.SRSCollection.Orientations;
import org.incf.atlas.waxml.generated.ListSRSResponseType.SRSCollection.SRSList;
import org.incf.atlas.waxml.generated.OrientationType.Author;
import org.incf.atlas.waxml.generated.QueryInfoType;
import org.incf.atlas.waxml.generated.SRSType;
import org.incf.atlas.waxml.generated.SRSType.Area;
import org.incf.atlas.waxml.generated.SRSType.DerivedFrom;
import org.incf.atlas.waxml.generated.SRSType.Name;
import org.incf.atlas.waxml.generated.SliceType;
import org.incf.atlas.waxml.utilities.Utilities;
import org.incf.ucsd.atlas.util.UCSDConfigurator;
import org.incf.ucsd.atlas.util.UCSDServiceDAOImpl;
import org.incf.ucsd.atlas.util.UCSDServiceVO;
import org.incf.ucsd.atlas.util.UCSDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.incf.common.atlas.exception.InvalidDataInputValueException;
import org.incf.common.atlas.util.AllowedValuesValidator;
import org.incf.common.atlas.util.DataInputHandler;
import org.incf.common.atlas.util.Util;


public class DescribeSRS implements Processlet {

	private static final Logger LOG = LoggerFactory
			.getLogger(DescribeSRS.class);

	UCSDConfigurator config = UCSDConfigurator.INSTANCE;

	String abaReference = config.getValue("srsname.abareference.10");
	String abaVoxel = config.getValue("srsname.abavoxel.10");
	String agea = config.getValue("srsname.agea.10");
	String whs09 = config.getValue("srsname.whs.09");
	String whs10 = config.getValue("srsname.whs.10");
	String emap = config.getValue("srsname.emap.10");
	String paxinos = config.getValue("srsname.paxinos.10");

	String abavoxel2agea = config.getValue("code.abavoxel2agea.v1");
	String agea2abavoxel = config.getValue("code.agea2abavoxel.v1");
	String whs092agea = config.getValue("code.whs092agea.v1");
	String agea2whs09 = config.getValue("code.agea2whs09.v1");
	String whs092whs10 = config.getValue("code.whs092whs10.v1");
	String whs102whs09 = config.getValue("code.whs102whs09.v1");
	String abareference2abavoxel = config
			.getValue("code.abareference2abavoxel.v1");
	String abavoxel2abareference = config
			.getValue("code.abavoxel2abareference.v1");
	String paxinos2whs09 = config.getValue("code.paxinos2whs09.v1");
	String whs092paxinos = config.getValue("code.whs092paxinos.v1");

	String hostName = "";
	String portNumber = "";
	String servicePath = "";
	String responseString = "";
	int randomGMLID = 0;
	int randomGMLID1 = 0;
	int randomGMLID2 = 0;

	@Override
	public void process(ProcessletInputs in, ProcessletOutputs out,
			ProcessletExecutionInfo info) throws ProcessletException {

		try {

			UCSDServiceVO vo = new UCSDServiceVO();

			URL processDefinitionUrl = this.getClass().getResource(
					"/" + this.getClass().getSimpleName() + ".xml");
			DataInputHandler dataInputHandler = new DataInputHandler(new File(
					processDefinitionUrl.toURI()));

			String srsName = dataInputHandler.getValidatedStringValue(in,
					"srsName");

			XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
			opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
			opt.setSaveNamespacesFirst();
			opt.setSaveAggressiveNamespaces();
			opt.setUseDefaultNamespace();

			DescribeSRSResponseDocument document = completeResponse(srsName);

			ArrayList errorList = new ArrayList();
			opt.setErrorListener(errorList);
			boolean isValid = document.validate(opt);

			// If the XML isn't valid, loop through the listener's contents,
			// printing contained messages.
			if (!isValid) {
				for (int i = 0; i < errorList.size(); i++) {
					XmlError error = (XmlError) errorList.get(i);

					System.out.println("\n");
					System.out.println("Message: " + error.getMessage() + "\n");
					System.out.println("Location of invalid XML: "
							+ error.getCursorLocation().xmlText() + "\n");
				}
			}

			ComplexOutput complexOutput = (ComplexOutput) out
					.getParameter("DescribeSRSsOutput");

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
		} catch (Throwable e) {
			String message = "Unexpected exception occured";
			LOG.error(message, e);
			OWSException owsException = new OWSException(message, e,
					ControllerException.NO_APPLICABLE_CODE);
			throw new ProcessletException(owsException);
		}

	}

	public static void createNueroDimentions(NeurodimensionType dimension,
			String name, float maxValue, String xRef) {
		dimension.setStringValue(name);
		dimension.setMaxValue(maxValue);
		// dimension.setHref(xRef);
	}

	public static void QueryInfoSrs(QueryInfoType queryInfo, String callUrl) {
		queryInfo.addNewQueryUrl().setStringValue(callUrl);

		return;
	}

	public static OrientationType orientation(OrientationType orient,
			String code, String name, String gmlID, String authorCode,
			String authorName, String orientationDescription) {

		orient.setCode(code);
		orient.setId(gmlID); // this is what is linked, to
		orient.setName(name);
		Author author = orient.addNewAuthor();

		Calendar calendar = new GregorianCalendar(2000, // year
	            10, // month
	            1); // day of month
	        java.sql.Date date = new java.sql.Date(calendar.getTimeInMillis());
	        
		author.setDateSubmitted(Calendar.getInstance());
		author.setAuthorCode(authorCode);
		author.setStringValue(authorName);

		Incfdescription desc = orient.addNewDescription();
		desc.setStringValue(orientationDescription);

		return orient;

	}

	// First SRS
	public static void addSRS(SRSList srsList, ArrayList list, int size) {

		UCSDServiceVO vo = null;

		try {

			Iterator iterator = list.iterator();
			SRSType srs = null;

			while (iterator.hasNext()) {

				System.out
						.println("**************************Count is********************* "
								+ list.size());
				srs = srsList.addNewSRS();

				vo = (UCSDServiceVO) iterator.next();

				Name name = srs.addNewName();
				name.setStringValue(vo.getSrsName());
				name.setSrsCode(vo.getSrsCode());

				if ( vo.getSrsName().equalsIgnoreCase("Mouse_WHS_0.9") ) {  
					name.setSrsBase(vo.getSrsName().replace("Mouse_", "").replaceAll("_0.9", ""));
				} else { 
					name.setSrsBase(vo.getSrsName().replace("Mouse_", "").replaceAll("_1.0", ""));
				}
				
				name.setSrsVersion(vo.getSrsVersion());
				name.setSpecies(vo.getSpecies());
				// name.setUrn("ReferenceUrl");//Uncomment this once i find the
				// value

				Incfdescription srsdescription = srs.addNewDescription();
				srsdescription.setStringValue(vo.getSrsDescription());

				AuthorType author = srs.addNewAuthor();
				author.setAuthorCode(vo.getSrsAuthorCode());
				author.setDateSubmitted(Calendar.getInstance());

				IncfCodeType origin = srs.addNewOrigin();
				// origin.setCodeSpace("URN");
				origin.setStringValue(vo.getOrigin());

				// <Area structureName=whole brain structureURN=/>
				Area area = srs.addNewArea();
				area.setStructureName(vo.getRegionOfValidity());
				// area.setStructureURN("URN");

				UnitOfMeasureType unit = srs.addNewUnits();
				unit.setUom(vo.getUnitsAbbreviation());

				NeurodimensionsType dimensions = srs.addNewNeurodimensions();
				NeurodimensionType minusX = dimensions.addNewMinusX();
				createNueroDimentions(minusX, vo.getNeuroMinusXCode(), Float
						.parseFloat(vo.getDimensionMinX()), "#"
						+ vo.getNeuroMinusXCode());
				NeurodimensionType minusY = dimensions.addNewMinusY();
				createNueroDimentions(minusY, vo.getNeuroMinusYCode(), Float
						.parseFloat(vo.getDimensionMinY()), "#"
						+ vo.getNeuroMinusYCode());
				NeurodimensionType minusZ = dimensions.addNewMinusZ();
				createNueroDimentions(minusZ, vo.getNeuroMinusZCode(), Float
						.parseFloat(vo.getDimensionMinZ()), "#"
						+ vo.getNeuroMinusZCode());
				NeurodimensionType plusX = dimensions.addNewPlusX();
				createNueroDimentions(plusX, vo.getNeuroPlusXCode(), Float
						.parseFloat(vo.getDimensionMaxX()), "#"
						+ vo.getNeuroPlusXCode());
				NeurodimensionType plusY = dimensions.addNewPlusY();
				createNueroDimentions(plusY, vo.getNeuroPlusYCode(), Float
						.parseFloat(vo.getDimensionMaxY()), "#"
						+ vo.getNeuroPlusYCode());
				NeurodimensionType plusZ = dimensions.addNewPlusZ();
				createNueroDimentions(plusZ, vo.getNeuroPlusZCode(), Float
						.parseFloat(vo.getDimensionMaxZ()), "#"
						+ vo.getNeuroPlusZCode());

				IncfUriSliceSource cite = srs.addNewSource();
				cite.setStringValue(vo.getSourceURI());
				cite.setFormat(vo.getSourceFileFormat());// Could be null
				DerivedFrom derived = srs.addNewDerivedFrom();
				derived.setSrsName(vo.getDerivedFromSRSCode());
				// derived.setMethod("MethodName");
				//srs.setDateCreated(Calendar.getInstance());
				//srs.setDateUpdated(Calendar.getInstance());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	public DescribeSRSResponseDocument completeResponse(String srsName) {

		DescribeSRSResponseDocument document = DescribeSRSResponseDocument.Factory
				.newInstance();

		DescribeSRSResponseType rootDoc = document.addNewDescribeSRSResponse();
		// rootDoc.newCursor().insertComment("Test Comment");
		/*
		 * QueryInfoSrs(rootDoc.addNewQueryInfo(), uri.toString());
		 *///SRSList srsList = rootDoc.addNewSRSList();
		SRSCollection coll1 = rootDoc.addNewSRSCollection();
		coll1.setHubCode("UCSD");

		SRSList srsList = coll1.addNewSRSList();

		// Start - Get data from the database
		ArrayList list = new ArrayList();
		UCSDServiceDAOImpl impl = new UCSDServiceDAOImpl();
		list = impl.getDescribeSRSData(srsName);
		// End

		addSRS(srsList, list, list.size());

		ArrayList list2 = impl.getOrientationData();
		Orientations o = coll1.addNewOrientations();
		OrientationType orientation = o.addNewOrientation();

		//Orientations o = null;

		Iterator iterator2 = list2.iterator();
		UCSDServiceVO vo = null;

		orientation = o.addNewOrientation();
		Random randomGenerator = new Random();
		while (iterator2.hasNext()) {
			for (int idx = 1; idx <= 10; ++idx) {
				randomGMLID = randomGenerator.nextInt(100);
			}
			vo = (UCSDServiceVO) iterator2.next();
			orientation = o.addNewOrientation();
			orientation(orientation, vo.getOrientationName(), vo
					.getOrientationName(), String.valueOf(randomGMLID), vo
					.getOrientationAuthor(), vo.getOrientationAuthor(), vo
					.getOrientationDescription());
		}

		// Start - Get Slice data
		if (srsName.equals(abaReference)) {
			vo.setSpaceCode("ABA_ref");// FIXME - Remove the hardcoded value
		} else if (srsName.equals(paxinos)) {
			vo.setSpaceCode("M_Pax");// FIXME - Remove the hardcoded value
		} else {
			vo.setSpaceCode("");
		}
		UCSDServiceDAOImpl daoImpl = new UCSDServiceDAOImpl();
		ArrayList sliceDataList = daoImpl.getSliceData(vo);
		// Start - End Slice data

		Slices s = rootDoc.addNewSlices();
		Iterator iterator = sliceDataList.iterator();

		UCSDUtil util = new UCSDUtil();

		while (iterator.hasNext()) {
			vo = (UCSDServiceVO) iterator.next();
			sliceElements(s.addNewSlice(), 1, vo);
		}

		//Start - GetFiducials Data
		if (srsName.equals(whs09)) {
			vo.setSpaceCode(whs09);// FIXME - Remove the hardcoded value
		} else if (srsName.equals(paxinos)) {
			vo.setSpaceCode(paxinos);// FIXME - Remove the hardcoded value
		} else {
			vo.setSpaceCode("");
		}
		ArrayList fiducialsDataList = daoImpl.getFiducialsData(vo);
		
		Fiducials f = rootDoc.addNewFiducials();
		iterator = fiducialsDataList.iterator();
		int fiducialCount = 0;
		while (iterator.hasNext()) {
			fiducialCount++;
			vo = (UCSDServiceVO) iterator.next();
			FiducialType fiducialType = f.addNewFiducial();
			fiducialElements(fiducialType, fiducialCount, vo);
		}
		//End

		/*
		 * Fiducials f = rootDoc.addNewFiducials();
		 * exampleFiducial(f.addNewFiducial(), 1);
		 */
		return document;
	}

	
	public static void fiducialElements(FiducialType fiducialType, int identifier, UCSDServiceVO vo){
		
		try {

			fiducialType.setCode(vo.getFiducialCode());
			fiducialType.setFiducialType(vo.getFiducialType());
			fiducialType.setName(vo.getFiducialName());
			fiducialType.addNewDescription().setStringValue(vo.getDescription());
	
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date date = sdf.parse(vo.getDateSubmitted());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
	
			AuthorType author=	fiducialType.addNewAuthor();
			author.setAuthorCode(vo.getAuthorCode());
			author.setDateSubmitted(cal.getInstance());

			fiducialType.setCertaintyLevel(vo.getCertaintyLevel());
			fiducialType.setDerivedFrom(vo.getDerivedFrom());
			
			PointType pnt =	fiducialType.addNewPoint();
			pnt.addNewPos().setStringValue(vo.getPos());
			pnt.setSrsName(vo.getSrsName());
			pnt.setId(String.valueOf(identifier));

		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
	
	public static void sliceElements(SliceType slice, int identifier,
			UCSDServiceVO vo) {

		if (vo.getValueDirection().equalsIgnoreCase("front")) {
			slice.setOrientation(SliceType.Orientation.CORONAL);

			if (vo.getPlusX().equalsIgnoreCase("constant")) {
				slice.setXOrientation(vo.getPlusZ());
			} else {
				slice.setXOrientation(vo.getPlusX());
			}

			slice.setYOrientation(vo.getPlusY());

			// (Derived from the coordinate system from Ilya - specific to
			// ABA_REF space only)
			/*
			 * if ( vo.getPlusX().equalsIgnoreCase("left") ) {
			 * slice.setXOrientation("right"); } if (
			 * vo.getPlusY().equalsIgnoreCase("down") ) {
			 * slice.setYOrientation("ventral"); }
			 */
		} else if (vo.getValueDirection().equalsIgnoreCase("right")) {
			slice.setOrientation(SliceType.Orientation.SAGITTAL);
			if (vo.getPlusX().equalsIgnoreCase("constant")) {
				slice.setXOrientation(vo.getPlusZ());
			} else {
				slice.setXOrientation(vo.getPlusX());
			}
			slice.setYOrientation(vo.getPlusY());
		} else {
			slice.setOrientation(SliceType.Orientation.HORIZONTAL);
		}

		System.out.println("Value is - " + vo.getSlideValue());
		System.out.println("Again Value is - "
				+ Float.parseFloat(vo.getSlideValue()));

		// FIXME - Ask Dave to change the
		slice.setConstant(Double.parseDouble(vo.getSlideValue()));
		slice.setCode(vo.getSliceID());

	}

	@Override
	public void destroy() {
	}

	@Override
	public void init() {
	}

}
