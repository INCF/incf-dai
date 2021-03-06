package org.incf.central.atlas.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;
import org.deegree.commons.xml.XMLAdapter;
import org.deegree.services.wps.output.ComplexOutput;

/*import org.incf.atlas.generated.transformationchain.CoordinateTransformationChain;
import org.incf.atlas.generated.transformationchain.CoordinateTransformationChainResponse;
import org.incf.atlas.generated.transformationchain.ObjectFactory;
import org.incf.atlas.generated.transformationchain.QueryInfo;
import org.incf.atlas.generated.transformationchain.CoordinateTransformationChain.CoordinateTransformation;
import org.incf.atlas.generated.transformationchain.QueryInfo.QueryURL;
*/
import org.incf.atlas.waxml.generated.CoordinateChainTransformType;
import org.incf.atlas.waxml.generated.CoordinateTransformationChainResponseDocument;
import org.incf.atlas.waxml.generated.CoordinateTransformationInfoType;
import org.incf.atlas.waxml.generated.InputStringType;
import org.incf.atlas.waxml.generated.InputType;
import org.incf.atlas.waxml.generated.ListTransformationsResponseDocument;
import org.incf.atlas.waxml.generated.QueryInfoType;
import org.incf.atlas.waxml.generated.CoordinateTransformationChainResponseType.CoordinateTransformationChain;
import org.incf.atlas.waxml.generated.ListTransformationsResponseType.TransformationList;
import org.incf.atlas.waxml.generated.QueryInfoType.Criteria;
import org.incf.atlas.waxml.generated.QueryInfoType.QueryUrl;
import org.incf.atlas.waxml.utilities.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CentralUtil {

	CentralConfigurator config = CentralConfigurator.INSTANCE;

	private static final Logger LOG = LoggerFactory
	.getLogger(CentralUtil.class);

	String abaReference = config.getValue("srsname.abareference.10");
	String abaVoxel = config.getValue("srsname.abavoxel.10");
	String agea = config.getValue("srsname.agea.10");
	String whs09 = config.getValue("srsname.whs.09");
	String whs10 = config.getValue("srsname.whs.10");
	String emap = config.getValue("srsname.emap.10");
	String paxinos = config.getValue("srsname.paxinos.10");
	String ucsdSrsName = config.getValue("srsname.ucsdnewsrs.10");

	public String getCoordinateTransformationChain(CentralServiceVO vo, ComplexOutput co) {

		LOG.debug("Start - getCoordinateTransformationChain Method...");
		ArrayList srsCodeList = new ArrayList();
		String responseString = "";

		try { 

			LOG.debug("Start - transformation matrix process...");

			//2) Get the transformed coordinates from Steve's program
			CentralUtil util = new CentralUtil();
			CentralServiceVO vo1 = new CentralServiceVO();

			//mouse_abavoxel_1.0 to mouse_agea_1.0
			if ( vo.getFromSRSCodeOne().equalsIgnoreCase(emap) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(emap);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
		
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(emap) ) {
				
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(emap);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {
				
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(ucsdSrsName);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) {
				
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(ucsdSrsName);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
		
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {
				
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) || 
		    		    ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) ) {

				if (vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(ucsdSrsName);
					vo1.setToSRSCode(abaReference);
					srsCodeList.add(vo1);

				}

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) || 
	           			( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) ) { 

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(abaReference);
					vo1.setToSRSCode(ucsdSrsName);
					srsCodeList.add(vo1);

				}

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) || 
		    		    ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) ) {

				if (vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(ucsdSrsName);
					vo1.setToSRSCode(abaReference);
					srsCodeList.add(vo1);

				}

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
		    } else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) || 
		    			( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) ) { 


				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(abaReference);
					vo1.setToSRSCode(ucsdSrsName);
					srsCodeList.add(vo1);

				}

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

            //via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

            //via mouse_abavoxel_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) || 
		    		    ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) ) {

				if (vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(ucsdSrsName);
					vo1.setToSRSCode(abaReference);
					srsCodeList.add(vo1);

				}

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) || 
		    	        ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) ) { 

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);
				
				if (vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(abaReference);
					vo1.setToSRSCode(ucsdSrsName);
					srsCodeList.add(vo1);

				}

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

	        //via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) || 
		    		    ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) ) {

				if (vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(ucsdSrsName);
					vo1.setToSRSCode(abaReference);
					srsCodeList.add(vo1);

				}

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1); 

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) || 
					    ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) ) { 

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(abaReference);
					vo1.setToSRSCode(ucsdSrsName);
					srsCodeList.add(vo1);

				}

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
				
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) || 
					    ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) ) { 
				
				if (vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(ucsdSrsName);
					vo1.setToSRSCode(abaReference);
					srsCodeList.add(vo1);

				}
				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) || 
					    ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) ) { 
				
				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName)) {
					
					vo1 = new CentralServiceVO();
					vo1.setFromSRSCode(abaReference);
					vo1.setToSRSCode(ucsdSrsName);
					srsCodeList.add(vo1);

				}

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase("all") && vo.getToSRSCodeOne().equalsIgnoreCase("all") ) {

				LOG.debug("Inside the list transformation loop for all transformations...");
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(ucsdSrsName);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(ucsdSrsName);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(emap);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);
				vo1 = new CentralServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(emap);
				srsCodeList.add(vo1);

				
				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
				
			} else {
				responseString = "Error: No such transformation chain is supported under this hub."; 
			}

			//End

			LOG.debug("Ends getSpaceTransformationChain Method...");

		} catch ( Exception e ) {

			e.printStackTrace();

		} finally {

		}

		LOG.debug("End - spaceTransformationForm Method...");

		//4) Return response back to the client in a text/xml format
		return responseString;

	}

	public String getTransformationChain( CentralServiceVO vo, ComplexOutput complexOutput, ArrayList srsCodeList ) { 

		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		CoordinateTransformationChainResponseDocument co =   CoordinateTransformationChainResponseDocument.Factory.newInstance();
		co.addNewCoordinateTransformationChainResponse();

		//Query Info
/*		co.getCoordinateTransformationChainResponse().addNewQueryInfo();
		QueryInfoType qi = co.getCoordinateTransformationChainResponse().getQueryInfo();
		QueryUrl url = QueryUrl.Factory.newInstance();
		url.setName("GetTransformationChain");
		url.setStringValue(vo.getUrlString());
		qi.setQueryUrl(url);
		qi.setTimeCreated(Calendar.getInstance());
	    Criteria criterias = qi.addNewCriteria();

		InputType input1 =criterias.addNewInput();
		InputStringType inputSrsConstraint = (InputStringType) input1.changeType(InputStringType.type);

		//InputStringType inputSrsConstraint = InputStringType.Factory.newInstance();
		inputSrsConstraint.setName("inputSrsName");
		inputSrsConstraint.setValue(vo.getFromSRSCode());
			
		InputType input2 =criterias.addNewInput();
		InputStringType ouputSrsConstraint  = (InputStringType) input2.changeType(InputStringType.type);
		
		//InputStringType ouputSrsConstraint = InputStringType.Factory.newInstance();
		ouputSrsConstraint.setName("outputSrsName");
		ouputSrsConstraint.setValue(vo.getToSRSCode());
		
		Utilities.addInputStringCriteria(criterias, "filter", vo.getFilter());
*/
		CoordinateTransformationChain ct = co.getCoordinateTransformationChainResponse().addNewCoordinateTransformationChain();
		
/*		ObjectFactory of = new ObjectFactory();
		QueryInfo queryInfo = of.createQueryInfo();
		
		QueryURL queryURL = new QueryURL();
		queryURL.setName("GetTransformationChain");
		queryURL.setValue(vo.getUrlString());
		queryInfo.getQueryURL().add(queryURL);

		queryInfo.setTimeCreated(vo.getCurrentTime());
*/
		try { 

	 		  	//Exception handling somewhere here before going to the first transformation

	 		    String orderNumber = "";
	 		    String code = "";
	 		    String accuracy = "";
	 		    String implementingHub1 = "";
	 		    String implementingHub2 = "";
	 		    String implementingHub3 = "";
	 		    String implementingHub4 = "";
	 		    String transformationURL1 = "";
	 		    String transformationURL2 = "";
	 		    String transformationURL3 = "";
	 		    String transformationURL4 = "";

	 			String ucsdServicePath = config.getValue("ucsd.ucsd.service.path");
/*	 			String ucsdHostName = config.getValue("ucsd.host.name");
	 			String ucsdPortNumber = config.getValue("ucsd.port.number");
	 			String ucsdTransformationMatrixURLPrefix = ucsdHostName + ucsdPortNumber + ucsdServicePath;
*/
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
/*	 			String abaHostName = config.getValue("ucsd.host.name");
	 			String abaPortNumber = config.getValue("ucsd.port.number");
	 			String abaTransformationMatrixURLPrefix = abaHostName + abaPortNumber + abaServicePath;
*/
	 			String whsServicePath = config.getValue("ucsd.whs.service.path");
/*	 			String whsHostName = config.getValue("ucsd.host.name");
	 			String whsPortNumber = config.getValue("ucsd.port.number");
	 			String incfTransformationMatrixURLPrefix = whsHostName + whsPortNumber + whsServicePath;
*/
	 			String emapServicePath = config.getValue("ucsd.emap.service.path");

	 			String incfDeploymentHostName = vo.getIncfDeployHostname();
	 			
	 			//String incfDeploymentHostName = config.getValue("incf.deploy.host.name");

	 			String incfportNumber = config.getValue("incf.deploy.port.delimitor")+vo.getIncfDeployPortNumber();

	 			String incfTransformationMatrixURLPrefix = incfDeploymentHostName + incfportNumber;

	 			String incfSteveHostName = config.getValue("incf.slamont.staging.host");
	 			String incfSteveMatrixURLPrefix = incfSteveHostName + incfportNumber;

	 			LOG.debug("Inside All Transformations....");
	 			Iterator iterator = srsCodeList.iterator();
	 			vo = null;

	 			int i = 0;

	 			while ( iterator.hasNext() ) {
	 			i++;
	 			vo = (CentralServiceVO)iterator.next();
	 			if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
	 		    		 vo.getToSRSCode().equalsIgnoreCase(emap) || 
	 		    		 vo.getFromSRSCode().equalsIgnoreCase(emap) && 
	 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {

	 		  		implementingHub1 = "EMAP";
	 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + emapServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
	 		  		vo.setTransformationOneURL(transformationURL1);
	 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
	 		    	orderNumber = String.valueOf(i);

	 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
	 				ex.setCode(code);
	 				ex.setHub(implementingHub1);
	 				//ex.setOrder(Integer.parseInt(orderNumber)); 
	 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
	 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
	 				//ex.setAccuracy(Integer.parseInt(accuracy));
	 				ex.setStringValue(vo.getTransformationOneURL());
	 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs09) || 
		 		    		 vo.getFromSRSCode().equalsIgnoreCase(whs09) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {

		 		  		implementingHub1 = "WHS";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(paxinos) ||
		 		    	 vo.getToSRSCode().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();

		 		    	ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());

		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(ucsdSrsName) ||
		 		    	 vo.getToSRSCode().equalsIgnoreCase(ucsdSrsName) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();

		 		    	ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());

		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(agea) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	} else if ( vo.getFromSRSCode().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);
		 		    	
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	}
	 			}
// 		    }
 			
 			 ArrayList errorList = new ArrayList();
 			 opt.setErrorListener(errorList);
 			 
 			 // Validate the XML.
 			 boolean isValid = co.validate(opt);
 			 
 			 // If the XML isn't valid, loop through the listener's contents,
 			 // printing contained messages.
 			 if (!isValid)
 			 {
 			      for (int j = 0; j < errorList.size(); j++)
 			      {
 			          XmlError error = (XmlError)errorList.get(j);
 			          
 			          LOG.debug("\n");
 			          LOG.debug("Message: {}" , error.getMessage() + "\n");
 			          LOG.debug("Location of invalid XML: {}" , 
 			              error.getCursorLocation().xmlText() + "\n");
 			      }
 			 }


 			XMLStreamReader reader = co.newXMLStreamReader();
 			XMLStreamWriter writer = complexOutput.getXMLStreamWriter();
 			XMLAdapter.writeElement(writer, reader);

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return co.xmlText(opt);

	}

	
	public String listTransformations( CentralServiceVO vo, ComplexOutput complexOutput, ArrayList srsCodeList ) {

		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		ListTransformationsResponseDocument co =   ListTransformationsResponseDocument.Factory.newInstance();
		co.addNewListTransformationsResponse();

		//Query Info
/*		co.getCoordinateTransformationChainResponse().addNewQueryInfo();
		QueryInfoType qi = co.getCoordinateTransformationChainResponse().getQueryInfo();
		QueryUrl url = QueryUrl.Factory.newInstance();
		url.setName("GetTransformationChain");
		url.setStringValue(vo.getUrlString());
		qi.setQueryUrl(url);
		qi.setTimeCreated(Calendar.getInstance());
	    Criteria criterias = qi.addNewCriteria();

		InputType input1 =criterias.addNewInput();
		InputStringType inputSrsConstraint = (InputStringType) input1.changeType(InputStringType.type);

		//InputStringType inputSrsConstraint = InputStringType.Factory.newInstance();
		inputSrsConstraint.setName("inputSrsName");
		inputSrsConstraint.setValue(vo.getFromSRSCode());
			
		InputType input2 =criterias.addNewInput();
		InputStringType ouputSrsConstraint  = (InputStringType) input2.changeType(InputStringType.type);
		
		//InputStringType ouputSrsConstraint = InputStringType.Factory.newInstance();
		ouputSrsConstraint.setName("outputSrsName");
		ouputSrsConstraint.setValue(vo.getToSRSCode());
		
		Utilities.addInputStringCriteria(criterias, "filter", vo.getFilter());
*/
		TransformationList ct = co.getListTransformationsResponse().addNewTransformationList();
		
/*		ObjectFactory of = new ObjectFactory();
		QueryInfo queryInfo = of.createQueryInfo();
		
		QueryURL queryURL = new QueryURL();
		queryURL.setName("GetTransformationChain");
		queryURL.setValue(vo.getUrlString());
		queryInfo.getQueryURL().add(queryURL);

		queryInfo.setTimeCreated(vo.getCurrentTime());
*/
		try { 

	 		  	//Exception handling somewhere here before going to the first transformation

	 		    String orderNumber = "";
	 		    String code = "";
	 		    String accuracy = "";
	 		    String implementingHub1 = "";
	 		    String implementingHub2 = "";
	 		    String implementingHub3 = "";
	 		    String implementingHub4 = "";
	 		    String transformationURL1 = "";
	 		    String transformationURL2 = "";
	 		    String transformationURL3 = "";
	 		    String transformationURL4 = "";

	 			String ucsdServicePath = config.getValue("ucsd.ucsd.service.path");
/*	 			String ucsdHostName = config.getValue("ucsd.host.name");
	 			String ucsdPortNumber = config.getValue("ucsd.port.number");
	 			String ucsdTransformationMatrixURLPrefix = ucsdHostName + ucsdPortNumber + ucsdServicePath;
*/
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
/*	 			String abaHostName = config.getValue("ucsd.host.name");
	 			String abaPortNumber = config.getValue("ucsd.port.number");
	 			String abaTransformationMatrixURLPrefix = abaHostName + abaPortNumber + abaServicePath;
*/
	 			String whsServicePath = config.getValue("ucsd.whs.service.path");
/*	 			String whsHostName = config.getValue("ucsd.host.name");
	 			String whsPortNumber = config.getValue("ucsd.port.number");
	 			String incfTransformationMatrixURLPrefix = whsHostName + whsPortNumber + whsServicePath;
*/
	 			String incfDeploymentHostName = vo.getIncfDeployHostname();
	 			String incfportNumber = config.getValue("incf.deploy.port.delimitor")+vo.getIncfDeployPortNumber();

	 			String incfTransformationMatrixURLPrefix = incfDeploymentHostName + incfportNumber;

	 			String incfSteveHostName = config.getValue("incf.slamont.staging.host");
	 			String incfSteveMatrixURLPrefix = incfSteveHostName + incfportNumber;

	 			LOG.debug("Inside All Transformations....");
	 			Iterator iterator = srsCodeList.iterator();
	 			vo = null;

	 			int i = 0;

	 			while ( iterator.hasNext() ) {
	 			i++;
	 			vo = (CentralServiceVO)iterator.next();
	 		    	if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs09) || 
		 		    		 vo.getFromSRSCode().equalsIgnoreCase(whs09) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {

		 		  		implementingHub1 = "WHS";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber)); 
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(paxinos) ||
		 		    	 vo.getToSRSCode().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();

		 		    	ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());

		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(ucsdSrsName) ||
		 		    	 vo.getToSRSCode().equalsIgnoreCase(ucsdSrsName) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();

		 		    	ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());

		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(agea) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCode().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	} else if ( vo.getFromSRSCode().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
		 		    	orderNumber = String.valueOf(i);
		 		    	
		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				//ex.setOrder(Integer.parseInt(orderNumber));
		 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	}
	 			}
// 		    }
 			
 			 ArrayList errorList = new ArrayList();
 			 opt.setErrorListener(errorList);
 			 
 			 // Validate the XML.
 			 boolean isValid = co.validate(opt);
 			 
 			 // If the XML isn't valid, loop through the listener's contents,
 			 // printing contained messages.
 			 if (!isValid)
 			 {
 			      for (int j = 0; j < errorList.size(); j++)
 			      {
 			          XmlError error = (XmlError)errorList.get(j);
 			          
 			          LOG.debug("\n");
 			          LOG.debug("Message: {}" , error.getMessage() + "\n");
 			          LOG.debug("Location of invalid XML: {}" , 
 			              error.getCursorLocation().xmlText() + "\n");
 			      }
 			 }


 			XMLStreamReader reader = co.newXMLStreamReader();
 			XMLStreamWriter writer = complexOutput.getXMLStreamWriter();
 			XMLAdapter.writeElement(writer, reader);

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return co.xmlText(opt);

	}

	
	//FIXME - amemon - will eventually go to commons
	public String spaceTransformation( CentralServiceVO vo ) {

		LOG.debug("Start - spaceTransformation Method...");
		
		String xmlResponseString = "";

		try { 

			LOG.debug("Start - transformation matrix process...");

			LOG.debug("****From SRSCode - {}" , vo.getFromSRSCodeOne());
			LOG.debug("****To SRSCode - {}" , vo.getToSRSCodeOne());

			LOG.debug("Start - transformation matrix process...");

			//2) Get the transformed coordinates from Steve's program
			CentralUtil util = new CentralUtil();

			//mouse_abavoxel_1.0 to mouse_agea_1.0
			if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {
				
				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} /* else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			//Indirect Transformations
			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

            //via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

	        //via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				xmlResponseString = util.indirectSpaceTransformation( vo );
				
			} */ else {

				xmlResponseString = "NOT SUPPORTED";
				
			}

			//End

			LOG.debug( "XML Response String - {}" , xmlResponseString ); 
			LOG.debug("Ends running transformation  matrix...");

		} catch ( Exception e ) {

			e.printStackTrace();

		} finally {

		}

		LOG.debug("End - spaceTransformationForm Method...");

		//4) Return response back to the cllient in a text/xml format
		return xmlResponseString;

	}


	//FIXME - amemon - will eventually go to commons
	public String directSpaceTransformation( String fromSpace, String toSpace, String originalCoordinateX, 
			String originalCoordinateY, String originalCoordinateZ ) {

	String transformedCoordinateString = "";

	LOG.debug("DIRECT SPACE TRANSFORMATION...");

	try {

		//By Steve Lamont
		if (fromSpace.trim().equalsIgnoreCase(abaVoxel) && toSpace.trim().equalsIgnoreCase(agea)) {

			LOG.debug("Inside ABAVOX 2 mouse_agea_1.0...");
			
			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");
	
			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "atlas="+fromSpace.toLowerCase()+"&direction=forward&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - {}" , transforMatrixURL); 
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - {}",inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}

			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);

		}

		//By Steve Lamont
		else if (fromSpace.trim().equalsIgnoreCase(agea) && toSpace.trim().equalsIgnoreCase(abaVoxel)) {

			LOG.debug("Inside mouse_agea_1.0 2 ABAVOX...");

			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Coordinates needs to be divided by 25 to come out of mouse_agea_1.0 coordinates
			originalCoordinateX = String.valueOf((Math.round( Double.parseDouble(originalCoordinateX)/25)));
			originalCoordinateY = String.valueOf((Math.round( Double.parseDouble(originalCoordinateY)/25)));
			originalCoordinateZ = String.valueOf((Math.round( Double.parseDouble(originalCoordinateZ)/25)));

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "direction=inverse&atlas="+toSpace.toLowerCase()+"&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - {}" , transforMatrixURL); 
			LOG.debug("X in transformation matrix method is - {}" , originalCoordinateX);
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - {}",inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);
		}

		//By Steve Lamont
		else if (fromSpace.trim().equalsIgnoreCase(whs09) && toSpace.trim().equalsIgnoreCase(agea)) {

			LOG.debug("Inside mouse_whs_1.0 2 mouse_agea_1.0...");

			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "atlas="+fromSpace.toLowerCase()+"&direction=forward&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - {}" , transforMatrixURL); 
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - {}",inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);

		}

		//By Steve Lamont
		else if (fromSpace.trim().equalsIgnoreCase(agea) && toSpace.trim().equalsIgnoreCase(whs09)) {

			LOG.debug("Inside mouse_agea_1.0 2 mouse_whs_1.0...");

			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Coordinates needs to be divided by 25 to come out of mouse_agea_1.0 coordinates
			originalCoordinateX = String.valueOf((Math.round( Double.parseDouble(originalCoordinateX)/25)));
			originalCoordinateY = String.valueOf((Math.round( Double.parseDouble(originalCoordinateY)/25)));
			originalCoordinateZ = String.valueOf((Math.round( Double.parseDouble(originalCoordinateZ)/25)));

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "direction=inverse&atlas="+toSpace.toLowerCase()+"&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - {}" , transforMatrixURL); 
			LOG.debug("X in transformation matrix method is - {}" , originalCoordinateX);
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - {}",inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);
		}

		//By Steve Lamont
/*		else if (fromSpace.trim().equalsIgnoreCase(abaReference) && toSpace.trim().equalsIgnoreCase(abaVoxel)) {

			LOG.debug("Inside ABAREF 2 ABAVOX...");

			int[] abar2abav = ABATransform.convertReferenceToVoxel(Double.parseDouble(originalCoordinateX), 
					Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ));

			transformedCoordinateString = originalCoordinateX + " " + originalCoordinateY + " "+ originalCoordinateZ + " " + abar2abav[0] + " " + abar2abav[1]  + " " + abar2abav[2];

			LOG.debug("ABAR to ABAV - TransformedCoordinateString - "+transformedCoordinateString);

		}

		//By Steve Lamont
		else if ( fromSpace.trim().equalsIgnoreCase(abaVoxel) && toSpace.trim().equalsIgnoreCase(abaReference) ) { 

			LOG.debug("Inside ABAVOX 2 ABAREF...");

			double[] abav2abar = ABATransform.convertVoxelToReference(Integer.parseInt(originalCoordinateX), 
					Integer.parseInt(originalCoordinateY), Integer.parseInt(originalCoordinateZ)); 

			transformedCoordinateString = originalCoordinateX + " " + originalCoordinateY + " "+ originalCoordinateZ + " " + abav2abar[0] + " " + abav2abar[1]  + " " + abav2abar[2];

			LOG.debug("ABAV to ABAR - TransformedCoordinateString - "+transformedCoordinateString);

		} */else {
		transformedCoordinateString = "No such transformation is available at this point under ABA hub.";
		return transformedCoordinateString;
	} 

	LOG.debug("Ends running transformation  matrix...");

	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return transformedCoordinateString;

	}

	
	//FIXME - amemon - will eventually go to commons
	public String indirectSpaceTransformation( CentralServiceVO vo ) {

		LOG.debug("Start - INDIRECT SPACE TRANSFORMATION METHOD...");

		//1) Define and Get parameters from URL
		//Define Properties
		LOG.debug(" Parameters... " );

		String hostName = config.getValue("ucsd.host.name");
		String servicePath = config.getValue("ucsd.ucsd.service.path");
		String portNumber = config.getValue("ucsd.port.number");
		String transformationMatrixURLPrefix = hostName + portNumber + servicePath;
		
		LOG.debug(" X... {}" , vo.getOriginalCoordinateX() );
		LOG.debug(" Y... {}" , vo.getOriginalCoordinateY() );
		LOG.debug(" Z... {}" , vo.getOriginalCoordinateZ() );

		StringBuffer responseString = new StringBuffer();

		StringBuffer transformedCoordinates = new StringBuffer();
		String transformedCoordinateString = "";
		String xmlResponseString = "";

		String rawTransformationStringOne = "";
		String rawTransformationStringTwo = "";
		String rawTransformationStringThree = "";
		String rawTransformationStringFour = "";
		StringBuffer transformationOne = new StringBuffer();
		StringBuffer transformationTwo = new StringBuffer();
		StringBuffer transformationThree = new StringBuffer();
		StringBuffer transformationFour = new StringBuffer();
		String[] arrayOfTransformedCoordinatesOne = new String[3];
		String[] arrayOfTransformedCoordinatesTwo = new String[3];
		String[] arrayOfTransformedCoordinatesThree = new String[3];
		String[] arrayOfTransformedCoordinatesFour = new String[3];
		
		try { 

			LOG.debug("Start - transformation matrix process...");

			CentralUtil util = new CentralUtil();

			//via mouse_whs_1.0
			if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(paxinos);
				vo.setFromSRSCodeTwo(whs09);
				vo.setToSRSCodeOne(whs09);
				vo.setToSRSCodeTwo(agea);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];

				LOG.debug("mouse_paxinos_1.0 to mouse_agea_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);
				
			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(agea);
				vo.setToSRSCodeOne(whs09);
				vo.setFromSRSCodeTwo(whs09);
				vo.setToSRSCodeTwo(paxinos);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
				
				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];

				LOG.debug("mouse_agea_1.0 to mouse_paxinos_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(paxinos);
				vo.setToSRSCodeOne(whs09);
				vo.setFromSRSCodeTwo(whs09);
				vo.setToSRSCodeTwo(agea);
				vo.setFromSRSCodeThree(agea);
				vo.setToSRSCodeThree(abaVoxel);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
				
				//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
				rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

				arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
								 .append(vo.getToSRSCodeThree()).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
				vo.setTransformationThree(transformationThree.toString());
				LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

				//Setting the transformation URL
				vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesThree[0] + " " + arrayOfTransformedCoordinatesThree[1]  + " " + arrayOfTransformedCoordinatesThree[2];

				LOG.debug("mouse_paxinos_1.0 to mouse_abavoxel_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via  mouse_whs_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(abaVoxel);
				vo.setToSRSCodeOne(agea);
				vo.setFromSRSCodeTwo(agea);
				vo.setToSRSCodeTwo(whs09);
				vo.setFromSRSCodeThree(whs09);
				vo.setToSRSCodeThree(paxinos);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");
				
				//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
				rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

				arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
								 .append(vo.getToSRSCodeThree()).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
				vo.setTransformationThree(transformationThree.toString());
				LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

				//Setting the transformation URL
				vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesThree[0] + " " + arrayOfTransformedCoordinatesThree[1]  + " " + arrayOfTransformedCoordinatesThree[2];
				LOG.debug("mouse_abavoxel_1.0 TO mouse_paxinos_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

				//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 

				vo.setFromSRSCodeOne(abaReference);
				vo.setToSRSCodeOne(abaVoxel);
				vo.setFromSRSCodeTwo(abaVoxel);
				vo.setToSRSCodeTwo(agea);
				vo.setFromSRSCodeThree(agea);
				vo.setToSRSCodeThree(whs09);
				vo.setFromSRSCodeFour(whs09);
				vo.setToSRSCodeFour(paxinos);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
				
				//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
				rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

				arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
								 .append(vo.getToSRSCodeThree()).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
				vo.setTransformationThree(transformationThree.toString());
				LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

				//Setting the transformation URL
				vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

				//Fourth convert - mouse_abavoxel_1.0 to mouse_abareference_1.0
				rawTransformationStringFour = util.directSpaceTransformation(vo.getFromSRSCodeFour(), vo.getToSRSCodeFour(), arrayOfTransformedCoordinatesThree[0], arrayOfTransformedCoordinatesThree[1], arrayOfTransformedCoordinatesThree[2] );

				arrayOfTransformedCoordinatesFour = util.getTabDelimNumbers(rawTransformationStringFour);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesFour[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationFour.append(vo.getFromSRSCodeFour()).append(" ")
								 .append(vo.getToSRSCodeFour()).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesFour[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesFour[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesFour[2]).append(" ");
				vo.setTransformationFour(transformationFour.toString());
				LOG.debug("TransformationFour - {}" , vo.getTransformationFour());

				//Setting the transformation URL
				vo.setTransformationFourURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x="+arrayOfTransformedCoordinatesThree[0]+"&amp;y="+arrayOfTransformedCoordinatesThree[1]+"&amp;z="+arrayOfTransformedCoordinatesThree[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesFour[0] + " " + arrayOfTransformedCoordinatesFour[1]  + " " + arrayOfTransformedCoordinatesFour[2];
				LOG.debug("mouse_abareference_1.0 TO mouse_paxinos_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

				//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

					//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
					vo.setFromSRSCodeOne(paxinos);
					vo.setToSRSCodeOne(whs09);
					vo.setFromSRSCodeTwo(whs09);
					vo.setToSRSCodeTwo(agea);
					vo.setFromSRSCodeThree(agea);
					vo.setToSRSCodeThree(abaVoxel);
					vo.setFromSRSCodeFour(abaVoxel);
					vo.setToSRSCodeFour(abaReference);

					//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
					rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

					arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

					//Exception Handling
					if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
						transformedCoordinateString = "Out of Range";
						return transformedCoordinateString;
					}

					// replace this with fromspace tospace x y z tx ty tz
					transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
									 .append(vo.getToSRSCodeOne()).append(" ")
									 .append(vo.getOriginalCoordinateX()).append(" ")
									 .append(vo.getOriginalCoordinateY()).append(" ")
									 .append(vo.getOriginalCoordinateZ()).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
					vo.setTransformationOne(transformationOne.toString());
					LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

					//Setting the transformation URL
					vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

					//Second convert - mouse_whs_1.0 to mouse_agea_1.0
					rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

					arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

					//Exception Handling
					if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
						transformedCoordinateString = "Out of Range";
						return transformedCoordinateString;
					}

					// replace this with fromspace tospace x y z tx ty tz
					transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
									 .append(vo.getToSRSCodeTwo()).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
					vo.setTransformationTwo(transformationTwo.toString());
					LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

					//Setting the transformation URL
					vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
					
					//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
					rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

					arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

					//Exception Handling
					if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
						transformedCoordinateString = "Out of Range";
						return transformedCoordinateString;
					}

					// replace this with fromspace tospace x y z tx ty tz
					transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
									 .append(vo.getToSRSCodeThree()).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
					vo.setTransformationThree(transformationThree.toString());
					LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

					//Setting the transformation URL
					vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

					//Fourth convert - mouse_abavoxel_1.0 to mouse_abareference_1.0
					rawTransformationStringFour = util.directSpaceTransformation(vo.getFromSRSCodeFour(), vo.getToSRSCodeFour(), arrayOfTransformedCoordinatesThree[0], arrayOfTransformedCoordinatesThree[1], arrayOfTransformedCoordinatesThree[2] );

					arrayOfTransformedCoordinatesFour = util.getTabDelimNumbers(rawTransformationStringFour);

					//Exception Handling
					if ( arrayOfTransformedCoordinatesFour[0].trim().equalsIgnoreCase("out") ) { 
						transformedCoordinateString = "Out of Range";
						return transformedCoordinateString;
					}

					// replace this with fromspace tospace x y z tx ty tz
					transformationFour.append(vo.getFromSRSCodeFour()).append(" ")
									 .append(vo.getToSRSCodeFour()).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesThree[2]).append(" ")
									 .append(arrayOfTransformedCoordinatesFour[0]).append(" ")
									 .append(arrayOfTransformedCoordinatesFour[1]).append(" ")
									 .append(arrayOfTransformedCoordinatesFour[2]).append(" ");
					vo.setTransformationFour(transformationFour.toString());
					LOG.debug("TransformationFour - {}" , vo.getTransformationFour());

					//Setting the transformation URL
					vo.setTransformationFourURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x="+arrayOfTransformedCoordinatesThree[0]+"&amp;y="+arrayOfTransformedCoordinatesThree[1]+"&amp;z="+arrayOfTransformedCoordinatesThree[2]+"&amp;output=html");

					//Get and Set xml response string for transformation info
					vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

					//Return A transformation string
					transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesFour[0] + " " + arrayOfTransformedCoordinatesFour[1]  + " " + arrayOfTransformedCoordinatesFour[2];
					LOG.debug("mouse_paxinos_1.0 TO mouse_abareference_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(abaVoxel);
				vo.setToSRSCodeOne(agea);
				vo.setFromSRSCodeTwo(agea);
				vo.setToSRSCodeTwo(whs09);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];
				LOG.debug("mouse_abavoxel_1.0 TO mouse_whs_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(whs09);
				vo.setToSRSCodeOne(agea);
				vo.setFromSRSCodeTwo(agea);
				vo.setToSRSCodeTwo(abaVoxel);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];
				LOG.debug("mouse_whs_1.0 TO mouse_abavoxel_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

            //via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(abaReference);
				vo.setToSRSCodeOne(abaVoxel);
				vo.setFromSRSCodeTwo(abaVoxel);
				vo.setToSRSCodeTwo(agea);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];
				LOG.debug("mouse_abareference_1.0 TO mouse_agea_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(agea);
				vo.setToSRSCodeOne(abaVoxel);
				vo.setFromSRSCodeTwo(abaVoxel);
				vo.setToSRSCodeTwo(abaReference);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesTwo[0] + " " + arrayOfTransformedCoordinatesTwo[1]  + " " + arrayOfTransformedCoordinatesTwo[2];
				LOG.debug("mouse_agea_1.0 TO mouse_abareference_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

	        //via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(abaReference);
				vo.setToSRSCodeOne(abaVoxel);
				vo.setFromSRSCodeTwo(abaVoxel);
				vo.setToSRSCodeTwo(agea);
				vo.setFromSRSCodeThree(agea);
				vo.setToSRSCodeThree(whs09);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
				
				//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
				rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

				arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
								 .append(vo.getToSRSCodeThree()).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
				vo.setTransformationThree(transformationThree.toString());
				LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

				//Setting the transformation URL
				vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));

				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesThree[0] + " " + arrayOfTransformedCoordinatesThree[1]  + " " + arrayOfTransformedCoordinatesThree[2];
				LOG.debug("mouse_abareference_1.0 TO mouse_whs_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(whs09);
				vo.setToSRSCodeOne(agea);
				vo.setFromSRSCodeTwo(agea);
				vo.setToSRSCodeTwo(abaVoxel);
				vo.setFromSRSCodeThree(abaVoxel);
				vo.setToSRSCodeThree(abaReference);

				//First convert - mouse_paxinos_1.0 to mouse_whs_1.0
				rawTransformationStringOne = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ() );

				arrayOfTransformedCoordinatesOne = util.getTabDelimNumbers(rawTransformationStringOne);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesOne[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationOne.append(vo.getFromSRSCodeOne()).append(" ")
								 .append(vo.getToSRSCodeOne()).append(" ")
								 .append(vo.getOriginalCoordinateX()).append(" ")
								 .append(vo.getOriginalCoordinateY()).append(" ")
								 .append(vo.getOriginalCoordinateZ()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ");
				vo.setTransformationOne(transformationOne.toString());
				LOG.debug("TransformationOne - {}" , vo.getTransformationOne());

				//Setting the transformation URL
				vo.setTransformationOneURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x="+vo.getOriginalCoordinateX()+"&amp;y="+vo.getOriginalCoordinateY()+"&amp;z="+vo.getOriginalCoordinateZ()+"&amp;output=html");

				//Second convert - mouse_whs_1.0 to mouse_agea_1.0
				rawTransformationStringTwo = util.directSpaceTransformation(vo.getFromSRSCodeTwo(), vo.getToSRSCodeTwo(), arrayOfTransformedCoordinatesOne[0], arrayOfTransformedCoordinatesOne[1], arrayOfTransformedCoordinatesOne[2] );

				arrayOfTransformedCoordinatesTwo = util.getTabDelimNumbers(rawTransformationStringTwo);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesTwo[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationTwo.append(vo.getFromSRSCodeTwo()).append(" ")
								 .append(vo.getToSRSCodeTwo()).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesOne[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ");
				vo.setTransformationTwo(transformationTwo.toString());
				LOG.debug("TransformationTwo - {}" , vo.getTransformationTwo());

				//Setting the transformation URL
				vo.setTransformationTwoURL("http://"+transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x="+arrayOfTransformedCoordinatesOne[0]+"&amp;y="+arrayOfTransformedCoordinatesOne[1]+"&amp;z="+arrayOfTransformedCoordinatesOne[2]+"&amp;output=html");
				
				//Third convert - mouse_agea_1.0 to mouse_abavoxel_1.0
				rawTransformationStringThree = util.directSpaceTransformation(vo.getFromSRSCodeThree(), vo.getToSRSCodeThree(), arrayOfTransformedCoordinatesTwo[0], arrayOfTransformedCoordinatesTwo[1], arrayOfTransformedCoordinatesTwo[2] );

				arrayOfTransformedCoordinatesThree = util.getTabDelimNumbers(rawTransformationStringThree);

				//Exception Handling
				if ( arrayOfTransformedCoordinatesThree[0].trim().equalsIgnoreCase("out") ) { 
					transformedCoordinateString = "Out of Range";
					return transformedCoordinateString;
				}

				// replace this with fromspace tospace x y z tx ty tz
				transformationThree.append(vo.getFromSRSCodeThree()).append(" ")
								 .append(vo.getToSRSCodeThree()).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesTwo[2]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[0]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[1]).append(" ")
								 .append(arrayOfTransformedCoordinatesThree[2]).append(" ");
				vo.setTransformationThree(transformationThree.toString());
				LOG.debug("TransformationThree - {}" , vo.getTransformationThree());

				//Setting the transformation URL
				vo.setTransformationThreeURL("http://" + transformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x="+arrayOfTransformedCoordinatesTwo[0]+"&amp;y="+arrayOfTransformedCoordinatesTwo[1]+"&amp;z="+arrayOfTransformedCoordinatesTwo[2]+"&amp;output=html");

				//Get and Set xml response string for transformation info
				vo.setXmlStringForTransformationInfo(util.getSpaceTransformationInfoXMLResponseString( vo ));
				
				//Return A transformation string
				transformedCoordinateString = vo.getOriginalCoordinateX() + " " + vo.getOriginalCoordinateY() + " "+ vo.getOriginalCoordinateZ() + " " + arrayOfTransformedCoordinatesThree[0] + " " + arrayOfTransformedCoordinatesThree[1]  + " " + arrayOfTransformedCoordinatesThree[2];
				LOG.debug("mouse_whs_1.0 TO mouse_abareference_1.0 - TransformedCoordinateString - {}",transformedCoordinateString);

			} else {
				transformedCoordinateString = "No such transformation is available at this point. Sorry for the inconvinience";
				return transformedCoordinateString;
			} 

			LOG.debug( "Transformed Coordinate String - {}" , transformedCoordinateString ); 
			LOG.debug( "Ends running transformation  matrix..." );

		} catch ( Exception e ) {

			e.printStackTrace();

		} finally {

		}

		LOG.debug("End - spaceTransformationForm Method...");

		//4) Return response back to the cllient in a text/xml format
		return transformedCoordinateString;

	}

	

	
	//FIXME - amemon - will eventually go to commons
	public String getSpaceTransformationInfoXMLResponseString( CentralServiceVO vo ) { 

		StringBuffer sb = new StringBuffer();

		try { 
			  
	 		  sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
			    .append("<coordinateTransformationInfoResponse xmlns:gml=\"http://www.opengis.net/gml\">\n ")

			    .append("<queryinfo>\n")
				//<queryURL name= GetTransformationInfo>URL</queryURL>
				.append("<criteria>\n");

				sb.append("<input name=\"fromSRSCode\">").append(vo.getFromSRSCode()).append("</input>\n");//fromSpaceName
				sb.append("<input name=\"toSRSCode\">").append(vo.getToSRSCode()).append("</input>\n");//vo.getToSRSCodeOne()

				sb.append("</criteria>\n")
				.append("</queryinfo>\n")
				.append("<coordinateTransformationInfo>\n");

	 		  	//Exception handling somewhere here before going to the first transformation

	 		    String orderNumber = "";
	 		    String code = "";
	 		    String implementingHub1 = "";
	 		    String implementingHub2 = "";
	 		    String implementingHub3 = "";
	 		    String implementingHub4 = "";
	 		    String transformationURL1 = "";
	 		    String transformationURL2 = "";
	 		    String transformationURL3 = "";
	 		    String transformationURL4 = "";
	 		    	
	 			String ucsdHostName = config.getValue("ucsd.host.name");
	 			String ucsdServicePath = config.getValue("ucsd.ucsd.service.path");
	 			String ucsdPortNumber = config.getValue("ucsd.port.number");
	 			String ucsdTransformationMatrixURLPrefix = ucsdHostName + ucsdPortNumber + ucsdServicePath;

	 			String abaHostName = config.getValue("ucsd.host.name");
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
	 			String abaPortNumber = config.getValue("ucsd.port.number");
	 			String abaTransformationMatrixURLPrefix = abaHostName + abaPortNumber + abaServicePath;


	 		    //order Number, fromSRSCode2toSRSCode, condition about UCSD or ABA, fromSRSCode, toSRSCode, transformationURL
	 		    if ( vo.getFromSRSCodeOne() != null ) {
		 		    if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeOne() + "2" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub1).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeOne()).append("\" toSRSCode=\"").append(vo.getToSRSCodeOne()).append("\">").append(transformationURL1).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeOne() + "2" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub1).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeOne()).append("\" toSRSCode=\"").append(vo.getToSRSCodeOne()).append("\">").append(transformationURL1).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeOne() + "2" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub1).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeOne()).append("\" toSRSCode=\"").append(vo.getToSRSCodeOne()).append("\">").append(transformationURL1).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeOne() + "2" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub1).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeOne()).append("\" toSRSCode=\"").append(vo.getToSRSCodeOne()).append("\">").append(transformationURL1).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeOne() + "&amp;toSRSCode=" + vo.getToSRSCodeOne() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeOne() + "2" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub1).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeOne()).append("\" toSRSCode=\"").append(vo.getToSRSCodeOne()).append("\">").append(transformationURL1).append("</coordinateTransformation>\n");
		 		  	}
	 		    }

	 		    if ( vo.getFromSRSCodeTwo() != null ) {
		 		    if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub2 = "UCSD";
		 		  		transformationURL2 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(transformationURL2).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(transformationURL2).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(agea) ) {
		 		  		implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(transformationURL2).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(transformationURL2).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(abaVoxel) ) {
		 		  		transformationURL2 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeTwo() + "&amp;toSRSCode=" + vo.getToSRSCodeTwo() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(transformationURL2).append("</coordinateTransformation>\n");
		 		  		implementingHub2 = "ABA";
		 		  	}
	 		    }

	 		    if ( vo.getFromSRSCodeThree() != null ) {
		 		    if ( vo.getFromSRSCodeThree().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub3 = "UCSD";
		 		  		transformationURL3 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(transformationURL3).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(transformationURL3).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(agea) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(transformationURL3).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(transformationURL3).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(transformationURL3).append("</coordinateTransformation>\n");
		 		  	}
	 		    }
	 		    
	 		    if ( vo.getFromSRSCodeFour() != null ) {
		 		    if ( vo.getFromSRSCodeFour().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub4 = "UCSD";
		 		  		transformationURL4 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "2" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub4).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeFour()).append("\" toSRSCode=\"").append(vo.getToSRSCodeFour()).append("\">").append(transformationURL4).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "2" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub4).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeFour()).append("\" toSRSCode=\"").append(vo.getToSRSCodeFour()).append("\">").append(transformationURL4).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(agea) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "2" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub4).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeFour()).append("\" toSRSCode=\"").append(vo.getToSRSCodeFour()).append("\">").append(transformationURL4).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "2" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub4).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeFour()).append("\" toSRSCode=\"").append(vo.getToSRSCodeFour()).append("\">").append(transformationURL4).append("</coordinateTransformation>\n");
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + abaTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "2" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub4).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeFour()).append("\" toSRSCode=\"").append(vo.getToSRSCodeFour()).append("\">").append(transformationURL4).append("</coordinateTransformation>\n");
		 		  	}
	 		    }
	 		    
/*	 		    if ( vo.getTransformationOne() != null ) {
				} 
	 		    
	 		    if ( vo.getTransformationTwo() != null ) { 
	 		    	String transformationTwo = vo.getTransformationTwo();
	 		    	StringTokenizer tokensTwo = new StringTokenizer(transformationTwo, " ");
	 		    	String[] transformationTwoArray = new String[tokensTwo.countTokens()];
	 		    	int i = 0;
	 		    	while ( tokensTwo.hasMoreTokens() ) {
	 		    		transformationTwoArray[i] = tokensTwo.nextToken();
	 		    	}
	 		    	code = vo.getFromSRSCodeTwo() + "2" + vo.getToSRSCodeTwo(); 
					orderNumber = "2";
	 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub2).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeTwo()).append("\" toSRSCode=\"").append(vo.getToSRSCodeTwo()).append("\">").append(vo.getTransformationTwoURL()).append("</coordinateTransformation>\n");
				} 
	 		    
	 		    if ( vo.getTransformationThree() != null ) { 
	 		    	String transformationThree = vo.getTransformationThree();
	 		    	StringTokenizer tokensThree = new StringTokenizer(transformationThree, " ");
	 		    	String[] transformationThreeArray = new String[tokensThree.countTokens()];
	 		    	int i = 0;
	 		    	while ( tokensThree.hasMoreTokens() ) {
	 		    		transformationThreeArray[i] = tokensThree.nextToken();
	 		    	}
	 		    	code = vo.getFromSRSCodeThree() + "2" + vo.getToSRSCodeThree(); 
					orderNumber = "3";
	 		    	sb.append("<coordinateTransformation order=\"").append(orderNumber).append("\" code=\"").append(code).append("\" implementingHub=\"").append(implementingHub3).append("\" fromSRSCode=\"").append(vo.getFromSRSCodeThree()).append("\" toSRSCode=\"").append(vo.getToSRSCodeThree()).append("\">").append(vo.getTransformationThreeURL()).append("</coordinateTransformation>\n");
				}
*/
	 		    sb.append("</coordinateTransformationInfo>\n");
				sb.append("</coordinateTransformationInfoResponse>\n");

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return sb.toString();

		}

	//FIXME - amemon - will eventually go to commons
	public String errorSpaceTransformationInfoXMLResponse( CentralServiceVO vo ) { 

		StringBuffer sb = new StringBuffer();

		try { 
			  
	 		  sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
			    .append("<coordinateTransformationInfoResponse xmlns:gml=\"http://www.opengis.net/gml\">\n ")

			    .append("<queryinfo>\n")
				//<queryURL name= GetTransformationInfo>URL</queryURL>
				.append("<criteria>\n");

				sb.append("<input name=\"fromSRSCode\">").append(vo.getFromSRSCode()).append("</input>\n");//fromSpaceName
				sb.append("<input name=\"toSRSCode\">").append(vo.getToSRSCode()).append("</input>\n");//vo.getToSRSCodeOne()

				sb.append("</criteria>\n")
				.append("</queryinfo>\n")
				.append("<errormessage>\n")
				.append(vo.getErrorMessage());
	 		    sb.append("</errormessage>\n");
				sb.append("</coordinateTransformationInfoResponse>\n");

		} catch ( Exception e ) {
			e.printStackTrace();
		}

		return sb.toString();

		}

	
	//FIXME - amemon - will eventually go to commons
	public String[] getTabDelimNumbers(String tabLimNumbers ) {

		StringTokenizer tokens = new StringTokenizer(tabLimNumbers, " ");
		int tokensSize = tokens.countTokens();

		String[] coordinateString = new String[tokensSize]; 
		String[] transformedCoordinates = new String[3]; //Returned coordinates are 3
		LOG.debug( " tokens - {}" ,tokensSize);

		int i = 0;
		while ( tokens.hasMoreTokens() ) {
			coordinateString[i] = tokens.nextToken(); 
			i++;
		}

		
		if (coordinateString.length > 3) { 
			transformedCoordinates[0] = coordinateString[3];
			LOG.debug( " transformedCoordinates x - {}" , transformedCoordinates[0] );
			if (tokensSize >= 5 ) {
			transformedCoordinates[1] = coordinateString[4];
			LOG.debug( " transformedCoordinates y - {}" , transformedCoordinates[1] );
			}
			if (tokensSize >= 6 ) {
			transformedCoordinates[2] = coordinateString[5];
			LOG.debug( " transformedCoordinates z - {}" , transformedCoordinates[2] );
			}
		} else if (coordinateString.length == 3) {
			transformedCoordinates[0] = coordinateString[0];
			LOG.debug( " transformedCoordinates x - {}" , transformedCoordinates[0] );
			transformedCoordinates[1] = coordinateString[1];
			transformedCoordinates[2] = coordinateString[2];
		}
			
			
		return transformedCoordinates;

	}


	public static void main ( String args[] ) {
		CentralUtil util = new CentralUtil();
		
		StringTokenizer tokens = new StringTokenizer("Fine Structure Name: DG"); 
		while ( tokens.hasMoreTokens() ) {
			String structureName = tokens.nextToken();
			LOG.debug("Structure Name is - {}" , structureName);
		}
		
		//util.splitCoordinatesFromStringToVO(new ABAServiceVO(), "13 12 3 4 5 6");

	}

	public CentralServiceVO splitCoordinatesFromStringToVO(CentralServiceVO vo, String completeCoordinatesString ) {

		StringTokenizer tokens = new StringTokenizer(completeCoordinatesString, " ");
		int tokensSize = tokens.countTokens();

		String[] coordinateString = new String[tokensSize]; 
		String[] transformedCoordinates = new String[6]; //Returned coordinates are 3
		LOG.debug( " tokens - {}" ,tokensSize);

		int i = 0;
		while ( tokens.hasMoreTokens() ) {
			coordinateString[i] = tokens.nextToken(); 
			LOG.debug( " Token Name - {}" , coordinateString[i]);
			i++;
		}

/*		vo.setOriginalCoordinateX(coordinateString[0]);
		vo.setOriginalCoordinateY(coordinateString[1]);
		vo.setOriginalCoordinateZ(coordinateString[2]);
*/		vo.setTransformedCoordinateX(coordinateString[3]);
		vo.setTransformedCoordinateY(coordinateString[4]);
		vo.setTransformedCoordinateZ(coordinateString[5]);

		return vo;
		
	}

	
	// http://132.239.131.188:8080/incf-services/service/wbc?request=GetFineStructureNameByPOI&atlasSpaceName=ABA&x=263&y=159&z=227
	public String getFineStructureNameByPOI( CentralServiceVO vo ) {

		LOG.debug("Start - getFineStructureNameByPOI Method...");

		CentralUtil util = new CentralUtil();
		
		// http://mouse.brain-map.org/mouse_agea_1.0/all_coronal/slice_correlation_image?plane=coronal&index=7525&blend=0&width=217&height=152&loc=7525,4075,6300&lowerRange=0.5&upperRange=1
		// 1) Define and Get parameters from URL
		LOG.debug(" Parameters... ");
		String fromSpaceName = vo.getFromSRSCode();
		String coordinateX = vo.getTransformedCoordinateX();
		String coordinateY = vo.getTransformedCoordinateY();
		String coordinateZ = vo.getTransformedCoordinateZ();
		String vocabulary = vo.getVocabulary();

		// Define config Properties
		String hostName = config.getValue("incf.aba.host.name");
		String portNumber = config.getValue("incf.aba.port.number");
		String abaServicePath = config.getValue("incf.aba.service.path");

		String fineStructureName = "";
		String anatomicStructureName = "";
		String outOfRange = "";
		String responseString = "";

		try {

			LOG.debug("Starts Transformation matrix process...");

			// 2) Get the transformed coordinates from Steve's program
			// http://incf-dev-mapserver.crbs.ucsd.edu/cgi-bin/structure_lookup.cgi?atlas=aba&x=264&y=160&z=228

			// Cannot say fromSpaceName as the structure look up is supported
			// only for mouse_abavoxel_1.0
			String structureNamesString = util.getStructureNameLookup(
					abaVoxel, coordinateX, coordinateY, coordinateZ);

			String[] structureNames = util
					.getTabDelimNumbers(structureNamesString);

			fineStructureName = structureNames[1];
			anatomicStructureName = structureNames[0];
			outOfRange = structureNames[2];

			// Start - Changes
			if (fineStructureName == null || fineStructureName.equals("")) {
				responseString = "No structure found";
			} else if(outOfRange != null && outOfRange.trim().endsWith("range")) {
				responseString = "Out of range";
			} else if (fineStructureName.trim().equals("-")) {
				responseString = "No structure found";
			} else {
				responseString = "Fine Structure Name: "
					.concat(fineStructureName);
			}
			// End - Changes

			LOG.debug("Response String - {}" , responseString);

			// End
			LOG.debug("Ends running transformation  matrix...");

		} catch (Exception e) {

			e.printStackTrace();
			responseString = "Please contact the administrator to resolve this issue.";

		} finally {

		}

		LOG.debug("End - getFineStructureNameByPOI Method...");

		// 4) Return response back to the cllient in a text/xml format
		return responseString;

	}


	// http://132.239.131.188:8080/incf-services/service/wbc?request=GetFineStructureNameByPOI&atlasSpaceName=ABA&x=263&y=159&z=227
	public String getAnatomicStructureNameByPOI( CentralServiceVO vo ) {

		LOG.debug("Start - getAnatomicStructureNameByPOI Method...");

		// http://mouse.brain-map.org/agea/all_coronal/slice_correlation_image?plane=coronal&index=7525&blend=0&width=217&height=152&loc=7525,4075,6300&lowerRange=0.5&upperRange=1
		// 1) Define and Get parameters from URL
		LOG.debug(" Parameters... ");

		String fromSpaceName = vo.getFromSRSCode();
		String coordinateX = vo.getTransformedCoordinateX();
		String coordinateY = vo.getTransformedCoordinateY();
		String coordinateZ = vo.getTransformedCoordinateZ();
		String vocabulary = vo.getVocabulary();

		// Define config Properties
		String hostName = config.getValue("incf.aba.host.name");
		String portNumber = config.getValue("incf.aba.port.number");
		String abaServicePath = config.getValue("incf.aba.service.path");

		String fineStructureName = "";
		String anatomicStructureName = "";

		StringBuffer responseString = new StringBuffer();

		try {

			LOG.debug("Starts Transformation matrix process...");

			// 2) Get the transformed coordinates from Steve's program
			// http://incf-dev-mapserver.crbs.ucsd.edu/cgi-bin/structure_lookup.cgi?atlas=aba&x=264&y=160&z=228
			CentralUtil util = new CentralUtil();

			// Cannot say fromSpaceName as the structure look up is supported
			// only for mouse_abavoxel_1.0
			String structureNamesString = util.getStructureNameLookup(
					abaVoxel, coordinateX, coordinateY, coordinateZ);

			String[] structureNames = util
					.getTabDelimNumbers(structureNamesString);

			fineStructureName = structureNames[1];
			anatomicStructureName = structureNames[0];
			String outOfRange = structureNames[2];

			// Start - Changes
			if (anatomicStructureName == null || anatomicStructureName.equals("")) {
				responseString.append("No structure found");
			} else if(outOfRange != null && outOfRange.trim().endsWith("out")) {
				responseString.append("Out of range");
			} else if (anatomicStructureName.trim().equals("-")) {
				responseString.append("No structure found");
			} else {
				responseString.append("Anatomic Structure Name: "
					.concat(anatomicStructureName));
			}
			// End - Changes
			LOG.debug("Anatomic Structure - {}"
					,responseString.toString());

		} catch (Exception e) {

			e.printStackTrace();
			responseString
					.append("Please contact the administrator to resolve this issue");

		} finally {

		}

		LOG.debug("End - getAnatomicStructureNameByPOI Method...");

		// 4) Return response back to the cllient in a text/xml format
		return responseString.toString();

	}

	
	//http://incf-dev-mapserver.crbs.ucsd.edu/cgi-bin/structure_lookup.cgi?atlas=aba&x=264&y=160&z=228
	public String getStructureNameLookup( String atlasSpaceName, String originalCoordinateX, 
			String originalCoordinateY, String originalCoordinateZ ) {

	String transformedCoordinateString = "";

	try {
	
		if ( atlasSpaceName.trim().equalsIgnoreCase(abaVoxel) ) {

			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.abavoxelstructure.path");
	
			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "atlas="+atlasSpaceName+"&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - {}" , transforMatrixURL);
			LOG.debug("X in transformation matrix method is - {}" , originalCoordinateX);
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - {}",inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);

		} /*else if ( atlasSpaceName.trim().equalsIgnoreCase(whs09) ) { 

			String transformationHostName = config.getValue("incf.transformationservice.host.name");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.whsstructure.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
			LOG.debug("Transformation matrix url is - " + transforMatrixURL);
			LOG.debug("X in transformation matrix method is - " + originalCoordinateX);
			URL url = new URL(transforMatrixURL);
			URLConnection urlCon = url.openConnection();
			urlCon.setUseCaches(false);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlCon
					.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				LOG.debug("inputLine - "+inputLine);
				transformedCoordinateString = transformedCoordinateString + inputLine;
			}
			LOG.debug("TransformedCoordinateString - "+transformedCoordinateString);

		}
*/		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return transformedCoordinateString;

	}

	/**
	 * Returns a list of gene symbols from Allen Brain Atlas that express at
	 * the given location. The number returned is determined by the last 
	 * argument. The list is in order of the strength of expression.
	 *  
	 * @param x
	 * @param y
	 * @param z
	 * @param nbrStrongGenes
	 * @return
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public static List<ABAGene> retrieveStrongGenesAtAGEAPOI(double x, double y, 
			double z, int nbrStrongGenes) throws IOException, 
					XMLStreamException {
        List<ABAGene> abaGenes = new ArrayList<ABAGene>();
	    	
        // round coords to nearest xx00, where xx is even
        URL u = new URL(CentralUtil.assembleGeneFinderURI(
        		String.valueOf(CentralUtil.round200(x)), 
        		String.valueOf(CentralUtil.round200(y)), 
        		String.valueOf(CentralUtil.round200(z))));

        InputStream in = u.openStream();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = factory.createXMLStreamReader(in);

        // loop through genes found at poi
        boolean inEnergy = false;
        boolean inGeneSymbol = false;
        int event = parser.next(); 
        int i = 1;
        ABAGene abaGene = null;
        while (i <= nbrStrongGenes 
        		&& event != XMLStreamConstants.END_DOCUMENT) {
        	if (event == XMLStreamConstants.START_ELEMENT) {
        	    if (parser.getLocalName().equals("energy")) {
        	        inEnergy = true;
        	    } else if (parser.getLocalName().equals("genesymbol")) {
        			inGeneSymbol = true;
        		}
        	} else if (event == XMLStreamConstants.CHARACTERS) {
        		if (inEnergy) {
                	abaGene = new ABAGene();
                	abaGene.setId("G" + String.valueOf(i));
        			abaGene.setEnergy(parser.getText());
        			inEnergy = false;
        		} else if (inGeneSymbol) {
        			abaGene.setGenesymbol(parser.getText());
        			inGeneSymbol = false;
        			abaGenes.add(abaGene);
        			i++;
        		}
        	}
        	event = parser.next();
        } // while
        try {
        	parser.close();
        } catch (XMLStreamException e) {
        	e.printStackTrace();
        }

        // debug
        for (ABAGene gene: abaGenes) {
        	LOG.debug("abaGene symbol: {}",gene.getGenesymbol());
        }
	    return abaGenes;
	}
	
	/**
	 * Rounds a number to the nearest 200 to match Allen Brain Atlas's use of
	 * 200x200micron voxels.
	 * 
	 * @param a
	 * @return
	 */
	public static int round200(double a) {
		return ((int) ((a / 200) + 0.5)) * 200;
	}

	/**
	 * Returns a URI that will access the Allen Brain Atlas's gene finder
	 * using given coordinates.
	 * 
	 * Example: http://www.brain-map.org/agea/GeneFinder.xml?seedPoint=6600,4000,5600
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static String assembleGeneFinderURI(String x, String y, String z) {
		return String.format(
			"http://mouse.brain-map.org/agea/GeneFinder.xml?seedPoint=%s,%s,%s", 
			x, y, z);
	}
	
	/**
	 * Returns a URI that will access the Allen Brain Atlas for information
	 * about the given gene.
	 * 
	 * Example: http://www.brain-map.org/aba/api/gene/C1ql2.xml
	 * 
	 * @param geneSymbol
	 * @return
	 */
	public static String assembleGeneURI(String geneSymbol) {
		return String.format(
				"http://www.brain-map.org/aba/api/gene/%s.xml", 
				geneSymbol);
	}

}
