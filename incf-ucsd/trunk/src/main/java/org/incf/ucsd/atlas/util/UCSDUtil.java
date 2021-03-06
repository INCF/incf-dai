package org.incf.ucsd.atlas.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlOptions;
import org.deegree.commons.xml.XMLAdapter;
import org.deegree.services.controller.exception.ControllerException;
import org.deegree.services.controller.ows.OWSException;
import org.deegree.services.wps.output.ComplexOutput;

import org.incf.atlas.waxml.generated.CoordinateChainTransformType;
import org.incf.atlas.waxml.generated.CoordinateTransformationChainResponseDocument;
import org.incf.atlas.waxml.generated.CoordinateTransformationChainResponseType.CoordinateTransformationChain;
import org.incf.atlas.waxml.generated.CoordinateTransformationInfoType;
import org.incf.atlas.waxml.generated.ListTransformationsResponseDocument;
import org.incf.atlas.waxml.generated.ListTransformationsResponseType.TransformationList;
import org.incf.atlas.waxml.generated.impl.CoordinateTransformationInfoTypeImpl;
import org.incf.atlas.waxml.utilities.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ucsd.ncmir.insighttransform.InsightTransform;

public class UCSDUtil {

	UCSDConfigurator config = UCSDConfigurator.INSTANCE;

	private static final Logger LOG = LoggerFactory
	.getLogger(UCSDUtil.class);

	String abaReference = config.getValue("srsname.abareference.10");
	String abaVoxel = config.getValue("srsname.abavoxel.10");
	String agea = config.getValue("srsname.agea.10");
	String whs09 = config.getValue("srsname.whs.09");
	String whs10 = config.getValue("srsname.whs.10");
	String emap = config.getValue("srsname.emap.10");
	String paxinos = config.getValue("srsname.paxinos.10");
	String ucsdSrsName = config.getValue("srsname.ucsdnewsrs.10");

	public String getCoordinateTransformationChain(UCSDServiceVO vo, ComplexOutput co) {

		LOG.debug("Start - getCoordinateTransformationChain Method...");
		String responseString = "";
		LOG.debug("Inside Method InputSrsName...{}" ,vo.getFromSRSCodeOne());
		LOG.debug("Inside Method OutputSrsName...{}" , vo.getToSRSCodeOne());

		try { 

			LOG.debug("Start - transformation matrix process...");

			//2) Get the transformed coordinates from Steve's program
			UCSDUtil util = new UCSDUtil();
			ArrayList srsCodeList = new ArrayList();
			UCSDServiceVO vo1 = new UCSDServiceVO();

			//mouse_abavoxel_1.0 to mouse_agea_1.0
			/*if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {
				LOG.debug("Inside Match..." );
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				
				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
		
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {
				
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} *//*else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} */ if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) { 

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) { 

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(ucsdSrsName);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) {

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(ucsdSrsName);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_whs_1.0
			} /* else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				vo1 = new ABAServiceVO();
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

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs10);
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
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
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
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
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
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
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
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via  mouse_whs_1.0, and then mouse_agea_1.0, then mouse_abavoxel_1.0
		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_agea_1.0
			} */ else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
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
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

            //via mouse_abavoxel_1.0
			} /*else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
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
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

            //via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(agea) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

	        //via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1); 

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
				
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(whs10);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			//via mouse_abavoxel_1.0, and then mouse_agea_1.0
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs10);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new ABAServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);

				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}
				
			} */else if ( vo.getFromSRSCodeOne().equalsIgnoreCase("all") && vo.getToSRSCodeOne().equalsIgnoreCase("all") ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 

				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(paxinos);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(paxinos);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(ucsdSrsName);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaReference);
				vo1.setToSRSCode(ucsdSrsName);
				srsCodeList.add(vo1);

/*				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(whs09);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(abaVoxel);
				vo1.setToSRSCode(abaReference);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(agea);
				vo1.setToSRSCode(abaVoxel);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
				vo1.setFromSRSCode(whs09);
				vo1.setToSRSCode(agea);
				srsCodeList.add(vo1);
				vo1 = new UCSDServiceVO();
*/
				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) {
					responseString = util.listTransformations( vo, co, srsCodeList );
				} else {
					responseString = util.getTransformationChain( vo, co, srsCodeList );
				}

			} else {
				if (vo.getFlag().equalsIgnoreCase("ListTransformations")) { 
					responseString = "Error: No such transformation is supported under this hub.";
				} else {
					responseString = "Error: No such transformation chain is supported under this hub.";
				}
				 
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

	public String getTransformationChain( UCSDServiceVO vo, ComplexOutput complexOutput, ArrayList srsCodeList ) { 

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
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
	 			String whsServicePath = config.getValue("ucsd.whs.service.path");
	 			String incfDeploymentHostName = vo.getIncfDeployHostname();
	 			String incfportNumber = config.getValue("incf.deploy.port.delimitor")+vo.getIncfDeployPortNumber();
	 			String incfTransformationMatrixURLPrefix = incfDeploymentHostName + incfportNumber;

/*	 		    if ( vo.getFromSRSCodeOne() != null ) {

	 		    	if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs10) && 
	 		    		 vo.getToSRSCodeOne().equalsIgnoreCase(whs09) || 
	 		    		 vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && 
	 		    		 vo.getToSRSCodeOne().equalsIgnoreCase(whs10) ) {
		 		  		implementingHub1 = "WHS";
		 		  		
		 		  		//transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCodeOne()+"_To_"+vo.getToSRSCodeOne()+"_v1.0;x=;y=;z=;filter=";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter="; 

		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) ||
		 		    		vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub1 = "UCSD";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter=";
		 		  		
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();

		 		    	ex.setOrder(Integer.parseInt(orderNumber));
		 				
		 		    	ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(agea) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		    } else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub1 = "ABA";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeOne()+";outputSrsName="+vo.getToSRSCodeOne()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCodeOne() + "_To_" + vo.getToSRSCodeOne(); 
		 		    	orderNumber = "1";
		 		    	
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeOne()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeOne()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
		 		  	}
	 		    }

	 		    if ( vo.getFromSRSCodeTwo() != null ) {

	 		    if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(whs10) && 
	 		    		 vo.getToSRSCodeTwo().equalsIgnoreCase(whs09) || 
	 		    		 vo.getFromSRSCodeTwo().equalsIgnoreCase(whs09) && 
	 		    		 vo.getToSRSCodeTwo().equalsIgnoreCase(whs10) ) { 
	 		    	
	 		    	implementingHub2 = "WHS";
	 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
	 		  		vo.setTransformationTwoURL(transformationURL2);
	 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
	 		    	orderNumber = "2";
	 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
	 				ex.setOrder(Integer.parseInt(orderNumber));
	 				ex.setCode(code);
	 				ex.setHub(implementingHub2);
	 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
	 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
	 				//ex.setAccuracy(Integer.parseInt(accuracy));
	 				ex.setStringValue(vo.getTransformationTwoURL());
	 		    } else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(paxinos) ||
		 		    	 vo.getToSRSCodeTwo().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub2 = "UCSD";
		 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationTwoURL(transformationURL2);
		 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";

		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub2);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationTwoURL());
		 		    } else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(whs09) ) {
		 		  	
		 		    	implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationTwoURL(transformationURL2);
		 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub2);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationTwoURL());
		 		    } else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(agea) ) {
		 		  		implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationTwoURL(transformationURL2);
		 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub2);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationTwoURL());
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub2 = "ABA";
		 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationTwoURL(transformationURL2);
		 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub2);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationTwoURL());
		 		  	} else if ( vo.getFromSRSCodeTwo().equalsIgnoreCase(abaVoxel) ) {
		 		  		transformationURL2 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeTwo()+";outputSrsName="+vo.getToSRSCodeTwo()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationTwoURL(transformationURL2);
		 		  		code = vo.getFromSRSCodeTwo() + "_To_" + vo.getToSRSCodeTwo(); 
		 		    	orderNumber = "2";
		 		  		implementingHub2 = "ABA";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub2);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeTwo()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeTwo()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationTwoURL());
		 		  	}
	 		    }

	 		    if ( vo.getFromSRSCodeThree() != null ) {

	 		    	if ( vo.getFromSRSCodeThree().equalsIgnoreCase(whs10) && 
	 		    		 vo.getToSRSCodeThree().equalsIgnoreCase(whs09) || 
	 		    		 vo.getFromSRSCodeThree().equalsIgnoreCase(whs09) && 
	 		    		 vo.getToSRSCodeThree().equalsIgnoreCase(whs10) ) { 
	 		    	
						implementingHub3 = "WHS";
				  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
				  		vo.setTransformationThreeURL(transformationURL3);
				  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
				    	orderNumber = "3";
				    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
						ex.setOrder(Integer.parseInt(orderNumber));
						ex.setCode(code);
						ex.setHub(implementingHub3);
						ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
						ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
						//ex.setAccuracy(Integer.parseInt(accuracy));
						ex.setStringValue(vo.getTransformationThreeURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(paxinos) ||
		 		    	 vo.getToSRSCodeThree().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub3 = "UCSD";
		 		  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationThreeURL(transformationURL3);
		 		  		//transformationURL3 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeThree() + "&amp;toSRSCode=" + vo.getToSRSCodeThree() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub3);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationThreeURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationThreeURL(transformationURL3);
		 		  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub3);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationThreeURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(agea) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationThreeURL(transformationURL3);
		 		  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub3);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationThreeURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationThreeURL(transformationURL3);
		 		  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub3);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationThreeURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub3 = "ABA";
		 		  		transformationURL3 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeThree()+";outputSrsName="+vo.getToSRSCodeThree()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationThreeURL(transformationURL3);
		 		  		code = vo.getFromSRSCodeThree() + "_To_" + vo.getToSRSCodeThree(); 
		 		    	orderNumber = "3";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub3);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeThree()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeThree()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationThreeURL());
		 		  	}
	 		    }
	 		    
	 		    if ( vo.getFromSRSCodeFour() != null ) {

	 		    	if ( vo.getFromSRSCodeFour().equalsIgnoreCase(whs10) && 
		 		    		 vo.getToSRSCodeFour().equalsIgnoreCase(whs09) || 
		 		    		 vo.getFromSRSCodeFour().equalsIgnoreCase(whs09) && 
		 		    		 vo.getToSRSCodeFour().equalsIgnoreCase(whs10) ) { 

	 		    		implementingHub4 = "WHS";
				  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
				  		vo.setTransformationFourURL(transformationURL4);
				  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
				    	orderNumber = "4";
				    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
						ex.setOrder(Integer.parseInt(orderNumber));
						ex.setCode(code);
						ex.setHub(implementingHub4);
						ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
						ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
						//ex.setAccuracy(Integer.parseInt(accuracy));
						ex.setStringValue(vo.getTransformationFourURL());
		 		  	} else if ( vo.getFromSRSCodeFour().equalsIgnoreCase(paxinos) || 
		 		    	 vo.getToSRSCodeFour().equalsIgnoreCase(paxinos) ) {
		 		  		implementingHub4 = "UCSD";
		 		  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + ucsdServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationFourURL(transformationURL4);
		 		  		//transformationURL4 = "http://" + ucsdTransformationMatrixURLPrefix + "request=SpaceTransformation&amp;fromSRSCode=" + vo.getFromSRSCodeFour() + "&amp;toSRSCode=" + vo.getToSRSCodeFour() + "&amp;x=&amp;y=&amp;z=&amp;output=xml";
		 		  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub4);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationFourURL());
		 		  	} else if ( vo.getFromSRSCodeFour().equalsIgnoreCase(whs09) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationFourURL(transformationURL4);
		 		  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub4);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationFourURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(agea) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationFourURL(transformationURL4);
		 		  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub4);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationFourURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaReference) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationFourURL(transformationURL4);
		 		  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub4);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationFourURL());
		 		  	} else if ( vo.getFromSRSCodeThree().equalsIgnoreCase(abaVoxel) ) {
		 		  		implementingHub4 = "ABA";
		 		  		transformationURL4 = "http://" + incfTransformationMatrixURLPrefix + abaServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=inputSrsName="+vo.getFromSRSCodeFour()+";outputSrsName="+vo.getToSRSCodeFour()+";x=;y=;z=;filter=";
		 		  		vo.setTransformationFourURL(transformationURL4);
		 		  		code = vo.getFromSRSCodeFour() + "_To_" + vo.getToSRSCodeFour(); 
		 		    	orderNumber = "4";
		 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
		 				ex.setOrder(Integer.parseInt(orderNumber));
		 				ex.setCode(code);
		 				ex.setHub(implementingHub4);
		 				ex.setInputSrsName(new QName(vo.getFromSRSCodeFour()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCodeFour()));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationFourURL());
		 		  	}
	 		    }
*/
//	 		    if ( vo.getFromSRSCodeOne().equals("all") ) {

		 		    LOG.debug("Inside All Transformations....");
		 			Iterator iterator = srsCodeList.iterator();
		 			vo = null;

		 			int i = 0;

		 			while ( iterator.hasNext() ) {
		 			i++;
		 			vo = (UCSDServiceVO)iterator.next();
		 		    	if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
			 		    		 vo.getToSRSCode().equalsIgnoreCase(whs09) || 
			 		    		 vo.getFromSRSCode().equalsIgnoreCase(whs09) && 
			 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {

			 		  		implementingHub1 = "WHS";
			 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
			 		  		vo.setTransformationOneURL(transformationURL1);
			 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; 
			 		    	orderNumber = String.valueOf(i);

			 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
			 		    	//CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
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

			 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();

			 		    	ex.setCode(code);
			 				ex.setHub(implementingHub1);
			 				//ex.setOrder(Integer.parseInt(orderNumber));
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

			 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
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

			 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
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
			 		    	
			 		    	CoordinateChainTransformType ex = ct.addNewCoordinateTransformation();
			 				ex.setCode(code);
			 				ex.setHub(implementingHub1);
			 				//ex.setOrder(Integer.parseInt(orderNumber));
			 				//ex.setInputSrsName(new QName(vo.getFromSRSCode()));
			 				//ex.setOutputSrsName(new QName(vo.getToSRSCode()));
			 				//ex.setAccuracy(Integer.parseInt(accuracy));
			 				ex.setStringValue(vo.getTransformationOneURL());
			 		  	}
		 			}
//	 		    }
	 			
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

	
	public String listTransformations( UCSDServiceVO vo, ComplexOutput complexOutput, ArrayList srsCodeList ) { 

		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		ListTransformationsResponseDocument co =   ListTransformationsResponseDocument.Factory.newInstance();
		co.addNewListTransformationsResponse();

		TransformationList ct = co.getListTransformationsResponse().addNewTransformationList();
		ct.setHubCode("UCSD");

		try { 

	 		    String orderNumber = "";
	 		    String code = "";
	 		    String accuracy = "";
	 		    String implementingHub1 = "";
	 		    String transformationURL1 = "";

	 		    LOG.debug("Inside All Transformations....");
	 		    UCSDServiceDAOImpl impl = new UCSDServiceDAOImpl();
	 		    
	 		    Set chainsList = spaceTransformationFromDB(vo.getFromSRSCode(), vo.getToSRSCode());
	 			
	 		    //Starts - Check to see if the transformation is supported in the hub or not
	 		    Iterator iter = chainsList.iterator();
	 			String chain = "";
	 			StringTokenizer token1 = null;
	 			StringTokenizer token2 = null;
	 			Set srcSet = new HashSet();
	 			Set destSet = new HashSet();
	 			
 				System.out.println("chainsList Size is: " + chainsList.size());
	 			while ( iter.hasNext()) {
	 				chain = (String)iter.next();
	 				System.out.println("Chain is: " + chain);
	 				token1 = new StringTokenizer(chain, ":");
	 				String tokenModify = token1.nextToken().replaceAll("_To_", ":");
	 				token2 = new StringTokenizer(tokenModify, ":");
	 				srcSet.add(token2.nextToken());
	 				destSet.add(token2.nextToken());
	 			}
	 			
	 			while (!destSet.contains(vo.getToSRSCode()) && !vo.getToSRSCode().equalsIgnoreCase("all")) { 
	 				String responseString = "Error: No such transformation is available under this hub.";
	 				System.out.println("Dest Not available");
	 				return responseString;
				}
	 			while (!srcSet.contains(vo.getFromSRSCode()) && !vo.getFromSRSCode().equalsIgnoreCase("all")) { 
	 				String responseString = "Error: No such transformation is available under this hub.";
	 				System.out.println("Src Not available");
	 				return responseString;
	 			}
	 			//Ends
	 			
	 		    Iterator iterator = chainsList.iterator();
	 			//vo = null;

	 			String ucsdServicePath = config.getValue("ucsd.ucsd.service.path");
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
	 			String whsServicePath = config.getValue("ucsd.whs.service.path");
	 			String incfDeploymentHostName = vo.getIncfDeployHostname();
	 			String incfportNumber = config.getValue("incf.deploy.port.delimitor")+vo.getIncfDeployPortNumber();
	 			String incfSteveHostName = config.getValue("incf.slamont.staging.host");
	 			String incfSteveMatrixURLPrefix = incfSteveHostName + incfportNumber;

	 			String incfTransformationMatrixURLPrefix = incfDeploymentHostName + incfportNumber;

	 			int i = 0;
	 			StringTokenizer tokens = null;
	 			String chainString = "";
	 			String transformationCode = "";
	 			String prefixURL = "";
	 			String servicePath = "";
	 			
	 			while ( iterator.hasNext() ) {
	 			i++;
	 			/*	 			vo = (UCSDServiceVO)iterator.next();
	 		    	if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs09) || 
		 		    		 vo.getFromSRSCode().equalsIgnoreCase(whs09) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {
*/

	 			chainString = (String)iterator.next();
				System.out.println("Chain is: " + chainString);
				tokens = new StringTokenizer(chainString, ":");
				transformationCode = tokens.nextToken();
				implementingHub1 = tokens.nextToken();
				System.out.println("Implementing Hub is: " + implementingHub1);

				if (implementingHub1.equalsIgnoreCase("ucsd")) {
					servicePath = ucsdServicePath; 
				} else if (implementingHub1.equalsIgnoreCase("aba")) {
					servicePath = abaServicePath; 
				} else if (implementingHub1.equalsIgnoreCase("whs")) {
					servicePath = whsServicePath; 
				} 
				
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + servicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+transformationCode+"_v1.0;x=;y=;z=";
						System.out.println("transformationURL1: " + transformationURL1); 

		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		//code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; // got it
		 		    	orderNumber = String.valueOf(i); // got it

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(transformationCode+"_v1.0");
		 				ex.setHub(implementingHub1);

		 				String transformations = "";
		 				transformations = transformationCode.replaceAll("_To_", ":");
		 				StringTokenizer tokens1 = new StringTokenizer(transformations,":");
		 				String fromSRSCode = tokens1.nextToken();
		 				String toSRSCode = tokens1.nextToken();

		 				ex.setInputSrsName(new QName(fromSRSCode));
		 				ex.setOutputSrsName(new QName(toSRSCode));
		 				//ex.setAccuracy(Integer.parseInt(accuracy));
		 				ex.setStringValue(vo.getTransformationOneURL());
	 			}
 			
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


/*	public String listTransformations( UCSDServiceVO vo, ComplexOutput complexOutput, ArrayList srsCodeList ) { 

		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		ListTransformationsResponseDocument co =   ListTransformationsResponseDocument.Factory.newInstance();
		co.addNewListTransformationsResponse();

		TransformationList ct = co.getListTransformationsResponse().addNewTransformationList();
		ct.setHubCode("UCSD");

		try { 

	 		    String orderNumber = "";
	 		    String code = "";
	 		    String accuracy = "";
	 		    String implementingHub1 = "";
	 		    String transformationURL1 = "";

	 			String ucsdServicePath = config.getValue("ucsd.ucsd.service.path");
	 			String abaServicePath = config.getValue("ucsd.aba.service.path");
	 			String whsServicePath = config.getValue("ucsd.whs.service.path");
	 			String incfDeploymentHostName = vo.getIncfDeployHostname();
	 			String incfportNumber = config.getValue("incf.deploy.port.delimitor")+vo.getIncfDeployPortNumber();
	 			String incfSteveHostName = config.getValue("incf.slamont.staging.host");
	 			String incfSteveMatrixURLPrefix = incfSteveHostName + incfportNumber;

	 			String incfTransformationMatrixURLPrefix = incfDeploymentHostName + incfportNumber;

	 		    LOG.debug("Inside All Transformations....");
	 			Iterator iterator = srsCodeList.iterator();
	 			vo = null;

	 			int i = 0;

	 			while ( iterator.hasNext() ) {
	 			i++;
	 			vo = (UCSDServiceVO)iterator.next();
	 		    	if ( vo.getFromSRSCode().equalsIgnoreCase(whs10) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs09) || 
		 		    		 vo.getFromSRSCode().equalsIgnoreCase(whs09) && 
		 		    		 vo.getToSRSCode().equalsIgnoreCase(whs10) ) {

		 		  		implementingHub1 = "WHS";
		 		  		transformationURL1 = "http://" + incfTransformationMatrixURLPrefix + whsServicePath + "service=WPS&version=1.0.0&request=Execute&Identifier=TransformPOI&DataInputs=transformationCode="+vo.getFromSRSCode()+"_To_"+vo.getToSRSCode()+"_v1.0;x=;y=;z=";
		 		  		vo.setTransformationOneURL(transformationURL1);
		 		  		code = vo.getFromSRSCode() + "_To_" + vo.getToSRSCode()+"_v1.0"; // got it
		 		    	orderNumber = String.valueOf(i); // got it

		 		    	CoordinateTransformationInfoType ex = ct.addNewCoordinateTransformation();
		 				ex.setCode(code);
		 				ex.setHub(implementingHub1);
		 				
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
		 				ex.setInputSrsName(new QName(vo.getFromSRSCode()));
		 				ex.setOutputSrsName(new QName(vo.getToSRSCode()));
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
*/
	

	public String calculateAccuracy(UCSDServiceVO vo) {

/*		String transformationCode = "Mouse_Paxinos_1.0_To_Mouse_WHS_0.9_v1.0";
		String originalCoordinateX = "1";
		String originalCoordinateY = "4.3";
		String originalCoordinateZ = "1.78";
*/
		String transformationCode = vo.getTransformationCode();
		String originalCoordinateX = vo.getOriginalCoordinateX();
		String originalCoordinateY = vo.getOriginalCoordinateY();
		String originalCoordinateZ = vo.getOriginalCoordinateZ();

		double accuracy = 0;
		
		//Parsing transformationCode
		String[] transformationNameArray;
		String delimiter = "_To_";
		transformationNameArray = transformationCode.split(delimiter);
		String fromSRSCode = transformationNameArray[0];
		String toSRSCode = transformationNameArray[1].replace("_v1.0", "");

		String transformedCoordinateX1 = "";
		String transformedCoordinateY1 = "";
		String transformedCoordinateZ1 = "";

		String transformedCoordinateX2 = "";
		String transformedCoordinateY2 = "";
		String transformedCoordinateZ2 = "";

		String transformedCoordinateX3 = "";
		String transformedCoordinateY3 = "";
		String transformedCoordinateZ3 = "";

		try {

			UCSDUtil util = new UCSDUtil();
			
			//Transformation 1
			String completeCoordinatesString = util.directSpaceTransformation(fromSRSCode, toSRSCode, originalCoordinateX, originalCoordinateY, originalCoordinateZ);
			if (completeCoordinatesString.equalsIgnoreCase("NOT SUPPORTED")) {
				throw new OWSException(
						"No Such Transformation is available under UCSD Hub.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			vo = util.splitCoordinatesFromStringToVO(vo, completeCoordinatesString);

			if (vo.getTransformedCoordinateX().equalsIgnoreCase("out")) {
				throw new OWSException("Coordinates - Out of Range.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			transformedCoordinateX1 = vo.getTransformedCoordinateX();
			transformedCoordinateY1 = vo.getTransformedCoordinateY();
			transformedCoordinateZ1 = vo.getTransformedCoordinateZ();
			System.out.println("transformedCoordinateX1: " + transformedCoordinateX1);
			System.out.println("transformedCoordinateY1: " + transformedCoordinateY1);
			System.out.println("transformedCoordinateZ1: " + transformedCoordinateZ1);
			
			//Transformation 2
			completeCoordinatesString = "";
			completeCoordinatesString = util.directSpaceTransformation(toSRSCode, fromSRSCode, vo.getTransformedCoordinateX(), vo.getTransformedCoordinateY(), vo.getTransformedCoordinateZ());
			if (completeCoordinatesString.equalsIgnoreCase("NOT SUPPORTED")) {
				throw new OWSException(
						"No Such Transformation is available under UCSD Hub.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			vo = util.splitCoordinatesFromStringToVO(vo, completeCoordinatesString);

			if (vo.getTransformedCoordinateX().equalsIgnoreCase("out")) {
				throw new OWSException("Coordinates - Out of Range.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			transformedCoordinateX2 = vo.getTransformedCoordinateX();
			transformedCoordinateY2 = vo.getTransformedCoordinateY();
			transformedCoordinateZ2 = vo.getTransformedCoordinateZ();

			System.out.println("transformedCoordinateX2: " + transformedCoordinateX2);
			System.out.println("transformedCoordinateY2: " + transformedCoordinateY2);
			System.out.println("transformedCoordinateZ2: " + transformedCoordinateZ2);

			//Transformation 3
			completeCoordinatesString = "";
			completeCoordinatesString = util.directSpaceTransformation(fromSRSCode, toSRSCode, vo.getTransformedCoordinateX(), vo.getTransformedCoordinateY(), vo.getTransformedCoordinateZ());
			if (completeCoordinatesString.equalsIgnoreCase("NOT SUPPORTED")) {
				throw new OWSException(
						"No Such Transformation is available under UCSD Hub.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			vo = util.splitCoordinatesFromStringToVO(vo, completeCoordinatesString);

			if (vo.getTransformedCoordinateX().equalsIgnoreCase("out")) {
				throw new OWSException("Coordinates - Out of Range.",
						ControllerException.NO_APPLICABLE_CODE);
			}

			transformedCoordinateX3 = vo.getTransformedCoordinateX();
			transformedCoordinateY3 = vo.getTransformedCoordinateY();
			transformedCoordinateZ3 = vo.getTransformedCoordinateZ();

			System.out.println("transformedCoordinateX3: " + transformedCoordinateX3);
			System.out.println("transformedCoordinateY3: " + transformedCoordinateY3);
			System.out.println("transformedCoordinateZ3: " + transformedCoordinateZ3);

			//Apply the Euclidean Formula to get the accuracy and divide it by 3
			double doubleCoordinateX = (Double.parseDouble(transformedCoordinateX3))-(Double.parseDouble(transformedCoordinateX1));
			double doubleCoordinateY = (Double.parseDouble(transformedCoordinateY3))-(Double.parseDouble(transformedCoordinateY1));
			double doubleCoordinateZ = (Double.parseDouble(transformedCoordinateZ3))-(Double.parseDouble(transformedCoordinateZ1));
			accuracy = Math.sqrt((doubleCoordinateX*doubleCoordinateX)+(doubleCoordinateY*doubleCoordinateY)+(doubleCoordinateZ*doubleCoordinateZ))/3;
		
			System.out.println("Math without sqrt: "+transformedCoordinateX1);
			System.out.println("Math with sqrt: "+Math.sqrt((doubleCoordinateX*doubleCoordinateX)+(doubleCoordinateY*doubleCoordinateY)+(doubleCoordinateZ*doubleCoordinateZ)));
			System.out.println("Accuracy actual value is: " + accuracy);

		} catch ( Exception e ) {
		e.printStackTrace();
		}

		return String.valueOf(accuracy);

	}


	//FIXME - amemon - will eventually go to commons
	public String spaceTransformation( UCSDServiceVO vo ) {

		LOG.debug("Start - spaceTransformation Method...");
		
		String xmlResponseString = "";

		try { 

			LOG.debug("Start - transformation matrix process...");

			LOG.debug("****From SRSCode - {}" , vo.getFromSRSCodeOne());
			LOG.debug("****To SRSCode - {}" , vo.getToSRSCodeOne());

			LOG.debug("Start - transformation matrix process...");

			//2) Get the transformed coordinates from Steve's program
			UCSDUtil util = new UCSDUtil();

			if ( vo.getFromSRSCodeOne().equalsIgnoreCase(paxinos) && vo.getToSRSCodeOne().equalsIgnoreCase(whs09) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(whs09) && vo.getToSRSCodeOne().equalsIgnoreCase(paxinos) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(ucsdSrsName) && vo.getToSRSCodeOne().equalsIgnoreCase(abaReference) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());
			
			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase(abaReference) && vo.getToSRSCodeOne().equalsIgnoreCase(ucsdSrsName) ) {

				xmlResponseString = util.directSpaceTransformation(vo.getFromSRSCodeOne(), vo.getToSRSCodeOne(), vo.getOriginalCoordinateX(), vo.getOriginalCoordinateY(), vo.getOriginalCoordinateZ());

			} else {

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


	public String directSpaceTransformation( String fromSpace, String toSpace, String originalCoordinateX, 
			String originalCoordinateY, String originalCoordinateZ ) { 

	String transformedCoordinateString = "";

	LOG.debug("DIRECT SPACE TRANSFORMATION...");

	try {

		LOG.debug("X: {}",originalCoordinateX);
		LOG.debug("Y: {}",originalCoordinateY);
		LOG.debug("Z: {}",originalCoordinateZ);

		UCSDServiceVO vo = new UCSDServiceVO();
		UCSDServiceDAOImpl impl = new UCSDServiceDAOImpl();
		ArrayList list = impl.getUCSDSpaceTransformationData(vo);
		Iterator iterator = list.iterator();

		while (iterator.hasNext()) { 
		
		vo = (UCSDServiceVO) iterator.next();
		LOG.debug("DB From..." +vo.getTransformationSource() + " and From User..." +fromSpace);
		LOG.debug("DB To..." +vo.getTransformationDestination()+ " and To User..." +toSpace);
		LOG.debug("DB URL..." +vo.getTransformationURL());
		
		if ( vo.getTransformationSource().equalsIgnoreCase(fromSpace.trim()) && vo.getTransformationDestination().equalsIgnoreCase(toSpace.trim()) 
			&& vo.getTransformationURL().equalsIgnoreCase("paxinos12whs09")) { 

			LOG.debug("Inside PAXINOS 2 WHS...");

			Paxinos2WHS paxinos2whs = new Paxinos2WHS();
			//client.getTransformation( 0.00, 4.29, -1.94 );
			transformedCoordinateString = paxinos2whs.getTransformation( Double.parseDouble(originalCoordinateX),
			Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ) );

			LOG.debug("Paxinos to WHS - TransformedCoordinateString - {}",transformedCoordinateString);

		//Alex
		} else if ( vo.getTransformationSource().equalsIgnoreCase(fromSpace.trim()) && vo.getTransformationDestination().equalsIgnoreCase(toSpace.trim()) 
										&& vo.getTransformationURL().equalsIgnoreCase("whs092paxinos1")) { 

			LOG.debug("Hello - Inside WHS 2 PAXINOS...");
			LOG.debug("Coordinate X: {}" , originalCoordinateX); 
			LOG.debug("Coordinate Y: {}" , originalCoordinateY); 
			LOG.debug("Coordinate Z: {}" , originalCoordinateZ); 
			
			WHS2Paxinos whs2paxinos = new WHS2Paxinos();
			transformedCoordinateString = whs2paxinos.getTransformation( Long.parseLong(originalCoordinateX.replace(".0", "")), 
					Long.parseLong(originalCoordinateY.replace(".0", "")), Long.parseLong(originalCoordinateZ.replace(".0", "")) );

			LOG.debug("WHS to PAXINOS - TransformedCoordinateString - {}",transformedCoordinateString);

		//Steve
			/*		
			} else if ( fromSpace.trim().equalsIgnoreCase(ucsdSrsName) && toSpace.trim().equalsIgnoreCase(abaReference) ) { 

			LOG.debug("Inside ucsd_srs_1.0 2 mouse_abaReference_1.0...");

			String transformationHostName = config.getValue("incf.slamont.staging.host");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "inputsrsname=ucsd_srs_1.0&outputsrsname=mouse_abareference_1.0&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
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

		//Steve
		} else if ( fromSpace.trim().equalsIgnoreCase(abaReference) && toSpace.trim().equalsIgnoreCase(ucsdSrsName) ) { 

			LOG.debug("Inside mouse_abaReference_1.0 2 ucsd_srs_1.0...");

			String transformationHostName = config.getValue("incf.slamont.staging.host");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "inputsrsname=mouse_abareference_1.0&outputsrsname=ucsd_srs_1.0&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
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

		} */
		
		} else if ( vo.getTransformationSource().equalsIgnoreCase(fromSpace.trim()) && vo.getTransformationDestination().equalsIgnoreCase(toSpace.trim()) 
				&& vo.getTransformationURL().equalsIgnoreCase("yuko12whs09")) { 

			LOG.debug("Hello - Inside Yuko 2 WHS09..."); 
			//InsightTransform transform = new InsightTransform("C:\\Asif\\work\\projects\\birn\\smartatlas\\itk\\tools\\ITKData\\Yuko\\images\\output\\canon_to_user_Affine.txt");
			InsightTransform transform = new InsightTransform("/home/amemon/canon_to_user_Affine.txt");
			LOG.debug("Hello - InsightTransform..." + transform);
			double[] forward = transform.transformForward(Double.parseDouble(originalCoordinateX), Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ));
			System.out.println("X: "+forward[0]);
			System.out.println("Y: "+forward[1]);
			System.out.println("Z: "+forward[2]);
			
			transformedCoordinateString = originalCoordinateX +" "+ originalCoordinateY +" "+ originalCoordinateZ +" "+ String.valueOf(forward[0])+" "+ String.valueOf(forward[1])+" "+ String.valueOf(forward[2]);
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);
			
		} else if ( vo.getTransformationSource().equalsIgnoreCase(fromSpace.trim()) && vo.getTransformationDestination().equalsIgnoreCase(toSpace.trim()) 
				&& vo.getTransformationURL().equalsIgnoreCase("whs092yuko1")) { 
			
			LOG.debug("Hello - Inside WHS09 2 Yuko1...");
			InsightTransform transform = new InsightTransform("C:\\Asif\\work\\projects\\birn\\smartatlas\\itk\\tools\\ITKData\\Yuko\\images\\output\\canon_to_user_Affine.txt");
			double[] inverse = transform.transformInverse(Double.parseDouble(originalCoordinateX), Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ));
			System.out.println("X: "+inverse[0]);
			System.out.println("Y: "+inverse[1]);
			System.out.println("Z: "+inverse[2]);
			
			transformedCoordinateString = originalCoordinateX +" "+ originalCoordinateY +" "+ originalCoordinateZ +" "+ String.valueOf(inverse[0])+" "+ String.valueOf(inverse[1])+" "+ String.valueOf(inverse[2]);
			LOG.debug("TransformedCoordinateString - {}",transformedCoordinateString);
			
		} else if ( vo.getTransformationSource().equalsIgnoreCase(fromSpace.trim()) && vo.getTransformationDestination().equalsIgnoreCase(toSpace.trim()) 
				&& !vo.getTransformationURL().equalsIgnoreCase("")) { 
			
			LOG.debug("Hello - Inside Dynamic transformation...");
			String transforMatrixURL = vo.getTransformationURL()+"&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
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
			
		} /*else {
			transformedCoordinateString = "No such transformation is available at this point under ABA hub.";
		}*/
		
		}
		
	LOG.debug("Ends running transformation  matrix...");

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return transformedCoordinateString;

	}

	
	//FIXME - amemon - will eventually go to commons
/*	public String directSpaceTransformation( String fromSpace, String toSpace, String originalCoordinateX, 
			String originalCoordinateY, String originalCoordinateZ ) { 

	String transformedCoordinateString = "";

	LOG.debug("DIRECT SPACE TRANSFORMATION...");

	try {

		LOG.debug("X: {}",originalCoordinateX);
		LOG.debug("Y: {}",originalCoordinateY);
		LOG.debug("Z: {}",originalCoordinateZ);

		if ( fromSpace.trim().equalsIgnoreCase(paxinos) && toSpace.trim().equalsIgnoreCase(whs09) ) { 

			LOG.debug("Inside PAXINOS 2 WHS...");

			Paxinos2WHS paxinos2whs = new Paxinos2WHS();
			//client.getTransformation( 0.00, 4.29, -1.94 );
			transformedCoordinateString = paxinos2whs.getTransformation( Double.parseDouble(originalCoordinateX),
			Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ) );

			LOG.debug("Paxinos to WHS - TransformedCoordinateString - {}",transformedCoordinateString);

		//Alex
		} else if ( fromSpace.trim().equalsIgnoreCase(whs09) && toSpace.trim().equalsIgnoreCase(paxinos) ) { 

			LOG.debug("Hello - Inside WHS 2 PAXINOS...");
			LOG.debug("Coordinate X: {}" , originalCoordinateX); 
			LOG.debug("Coordinate Y: {}" , originalCoordinateY); 
			LOG.debug("Coordinate Z: {}" , originalCoordinateZ); 
			
			WHS2Paxinos whs2paxinos = new WHS2Paxinos();
			transformedCoordinateString = whs2paxinos.getTransformation( Long.parseLong(originalCoordinateX.replace(".0", "")), 
					Long.parseLong(originalCoordinateY.replace(".0", "")), Long.parseLong(originalCoordinateZ.replace(".0", "")) );

			LOG.debug("WHS to PAXINOS - TransformedCoordinateString - {}",transformedCoordinateString);

		//Steve
		} else if ( fromSpace.trim().equalsIgnoreCase(ucsdSrsName) && toSpace.trim().equalsIgnoreCase(abaReference) ) { 

			LOG.debug("Inside ucsd_srs_1.0 2 mouse_abaReference_1.0...");

			String transformationHostName = config.getValue("incf.slamont.staging.host");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "inputsrsname=ucsd_srs_1.0&outputsrsname=mouse_abareference_1.0&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
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

		//Steve
		} else if ( fromSpace.trim().equalsIgnoreCase(abaReference) && toSpace.trim().equalsIgnoreCase(ucsdSrsName) ) { 

			LOG.debug("Inside mouse_abaReference_1.0 2 ucsd_srs_1.0...");

			String transformationHostName = config.getValue("incf.slamont.staging.host");
			String transformationPortNumber = config.getValue("incf.transformationservice.port.number");
			String transformationServicePath = config.getValue("incf.transformationservice.atlas.path");

			//Start - Create and run URL, and read the string from the webpage
			String transforMatrixURL = "http://" + transformationHostName + transformationPortNumber + transformationServicePath + "inputsrsname=mouse_abareference_1.0&outputsrsname=ucsd_srs_1.0&x=" + originalCoordinateX + "&y=" + originalCoordinateY + "&z=" + originalCoordinateZ;
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

		} else if ( fromSpace.trim().equalsIgnoreCase(abaReference) && toSpace.trim().equalsIgnoreCase(whs09) ) { 

			LOG.debug("Inside ABAReference 2 WHS...");

			Paxinos2WHS paxinos2whs = new Paxinos2WHS();
			//client.getTransformation( 0.00, 4.29, -1.94 );
			transformedCoordinateString = paxinos2whs.getTransformation( Double.parseDouble(originalCoordinateX),
			Double.parseDouble(originalCoordinateY), Double.parseDouble(originalCoordinateZ) );

			LOG.debug("Paxinos to WHS - TransformedCoordinateString - "+transformedCoordinateString);

		//Alex
		} else if ( fromSpace.trim().equalsIgnoreCase(whs09) && toSpace.trim().equalsIgnoreCase(abaReference) ) { 

			LOG.debug("Hello - Inside WHS 2 ABAReference...");
			LOG.debug("Coordinate X: " + originalCoordinateX); 
			LOG.debug("Coordinate Y: " + originalCoordinateY); 
			LOG.debug("Coordinate Z: " + originalCoordinateZ); 
			
			WHS2Paxinos whs2paxinos = new WHS2Paxinos();
			transformedCoordinateString = whs2paxinos.getTransformation( Long.parseLong(originalCoordinateX.replace(".0", "")), 
					Long.parseLong(originalCoordinateY.replace(".0", "")), Long.parseLong(originalCoordinateZ.replace(".0", "")) );

			LOG.debug("WHS to PAXINOS - TransformedCoordinateString - "+transformedCoordinateString);

		} else {
		transformedCoordinateString = "No such transformation is available at this point under ABA hub.";
		return transformedCoordinateString;
	} 

	LOG.debug("Ends running transformation  matrix...");

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		return transformedCoordinateString;

	}
*/
	
	//FIXME - amemon - will eventually go to commons
	public String indirectSpaceTransformation( UCSDServiceVO vo ) {

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

			UCSDUtil util = new UCSDUtil();

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

			} else if ( vo.getFromSRSCodeOne().equalsIgnoreCase("all") && vo.getToSRSCodeOne().equalsIgnoreCase("all") ) {

				//First convert from mouse_paxinos_1.0 to mouse_whs_1.0 
				vo.setFromSRSCodeOne(abaReference);
				vo.setToSRSCodeOne(abaVoxel);
				vo.setFromSRSCodeTwo(abaVoxel);
				vo.setToSRSCodeTwo(agea);
				vo.setFromSRSCodeThree(agea);
				vo.setToSRSCodeThree(whs09);
				vo.setFromSRSCodeFour(whs09);
				vo.setToSRSCodeFour(whs10);
				vo.setFromSRSCodeFive(abaReference);
				vo.setToSRSCodeFive(abaVoxel);

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
	public String getSpaceTransformationInfoXMLResponseString( UCSDServiceVO vo ) { 

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
	public String errorSpaceTransformationInfoXMLResponse( UCSDServiceVO vo ) { 

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
		UCSDUtil util = new UCSDUtil();

		UCSDServiceVO vo = new UCSDServiceVO();
		String accuracy = util.calculateAccuracy(vo);
		
		System.out.println("Accuracy: " + accuracy);
		
/*		StringTokenizer tokens = new StringTokenizer("Fine Structure Name: DG"); 
		while ( tokens.hasMoreTokens() ) {
			String structureName = tokens.nextToken();
			LOG.debug("Structure Name is - {}" , structureName);
		}
*/		
		//util.splitCoordinatesFromStringToVO(new ABAServiceVO(), "13 12 3 4 5 6");

	}

	public UCSDServiceVO splitCoordinatesFromStringToVO(UCSDServiceVO vo, String completeCoordinatesString ) {

		System.out.println("completeCoordinatesString: "+completeCoordinatesString);
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
	public String getFineStructureNameByPOI( UCSDServiceVO vo ) {

		LOG.debug("Start - getFineStructureNameByPOI Method...");

		UCSDUtil util = new UCSDUtil();
		
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
	public String getAnatomicStructureNameByPOI( UCSDServiceVO vo ) {

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
			UCSDUtil util = new UCSDUtil();

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
					, responseString.toString());

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

	
	public Set spaceTransformationFromDB( String fromSRS, String toSRS ) {

		Set chainsList = new HashSet();
		
		UCSDServiceDAOImpl impl = new UCSDServiceDAOImpl();
		UCSDServiceVO vo1 = new UCSDServiceVO();
		UCSDServiceVO vo2 = new UCSDServiceVO();
		ArrayList list = impl.getUCSDSpaceTransformationData(vo1);

		Iterator iterator1 = list.iterator();
		Iterator iterator2 = list.iterator();
		String oldToSRS = " ";
		String chain = "";

		//Check for direct transformation
		try {

		for ( int i=0; iterator1.hasNext(); i++ ) {
			
			vo1 = (UCSDServiceVO) iterator1.next();
			
			//System.out.println("fromSRS from List: " + vo1.getTransformationSource() + ", toSRS from List: " + vo1.getTransformationDestination());
			
			if ( fromSRS == null || fromSRS.equals("") || toSRS == null ||
				  toSRS.equals("") || fromSRS.equals("all") || toSRS.equals("all")) {
				chain = vo1.getTransformationSource() + "_To_" + vo1.getTransformationDestination()+":"+vo1.getTransformationHub();
				//System.out.println("Inside empty from.." + chain);
				chainsList.add(chain);
			}
		}
		//return chainsList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set srcSet = new HashSet();
		while (iterator2.hasNext()) {
			vo2 = (UCSDServiceVO) iterator2.next();
			srcSet.add(vo2.getTransformationSource().toLowerCase());
		}

		iterator2 = list.iterator();
		Set destSet = new HashSet();
		while (iterator2.hasNext()) {
			vo2 = (UCSDServiceVO) iterator2.next();
			destSet.add(vo2.getTransformationDestination().toLowerCase());
		}

		if ( !srcSet.contains(fromSRS.toLowerCase()) && !fromSRS.equalsIgnoreCase("all") ) {
			//System.out.println("*****************************NOT PRESENT in FROM***************************************");
			chain = "Error: No such transformation is available under this hub.";
			chainsList.add(chain);
		}

		if ( !destSet.contains(toSRS.toLowerCase()) && !toSRS.equalsIgnoreCase("all") ) {
			//System.out.println("*****************************NOT PRESENT in TO***************************************");
			chain = "Error: No such transformation is available under this hub.";
			chainsList.add(chain);
		}


		iterator1 = list.iterator();
		//Check for direct transformation
		for ( int i=0; iterator1.hasNext(); i++ ) {

			vo1 = (UCSDServiceVO) iterator1.next();

			if ( vo1.getTransformationSource().equalsIgnoreCase(fromSRS) &&
				 vo1.getTransformationDestination().equalsIgnoreCase(toSRS) ) {
				System.out.println("Inside Direct... fromSRS from List: " + vo1.getTransformationSource() + ", toSRS from List: " + vo1.getTransformationDestination());
				//System.out.println("Inside Direct Transformation..");
				chain = vo1.getTransformationSource() + "_To_" + vo1.getTransformationDestination()+":"+vo1.getTransformationHub();
				chainsList.add(chain);
				chain = vo1.getTransformationDestination() + "_To_" + vo1.getTransformationSource()+":"+vo1.getTransformationHub();
				chainsList.add(chain);
				return chainsList;
			}
			
		}

		//Check for indirect transformation
		iterator1 = list.iterator();
		for ( int i=0; iterator1.hasNext(); i++ ) {
			vo1 = (UCSDServiceVO) iterator1.next();
			
			
			//System.out.println("fromSRS from List: " + vo1.getTransformationSource() + ", toSRS from List: " + vo1.getTransformationDestination());
			
		if ( vo1.getTransformationDestination().equalsIgnoreCase(toSRS) ) {

					System.out.println("Inside ToSRS match..");
					if (vo1.getTransformationSource().equalsIgnoreCase(oldToSRS)) {
						System.out.println("Inside OldToSRS match. So ignore this and move on.");
						
					} else if (vo1.getTransformationSource().equalsIgnoreCase(fromSRS)) {
						System.out.println("Closing step to build chain.");
						chain = vo1.getTransformationSource() + "_To_" + vo1.getTransformationDestination()+":"+vo1.getTransformationHub();
						chainsList.add(chain);
						
						//Reverse Chain
						chain = vo1.getTransformationDestination() + "_To_" + vo1.getTransformationSource()+":"+vo1.getTransformationHub();
						chainsList.add(chain);

						System.out.println("****DONE CHAIN***" + chain);
						//break;
					} else {
						//System.out.println("Ongoing step to build chain.");
						chain = vo1.getTransformationSource() + "_To_" + vo1.getTransformationDestination()+":"+vo1.getTransformationHub();
						chainsList.add(chain);
						
						//Reverse Chain
						chain = vo1.getTransformationDestination() + "_To_" + vo1.getTransformationSource()+":"+vo1.getTransformationHub();
						chainsList.add(chain);

						toSRS = vo1.getTransformationSource();
						oldToSRS = vo1.getTransformationDestination();

						//Rewrite the list to default to go against the new toSRS value
						iterator1 = list.iterator();

						System.out.println("****ONGOING CHAIN ***" + chain);	 
					}

					//chainsList.add(chain);
	
				}

		}

		return chainsList;
	}

	
}
