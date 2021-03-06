package org.incf.atlas.waxml.examples;

import static org.junit.Assert.assertTrue;


import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import net.opengis.gml.x32.PointType;

import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.values.XmlObjectBase;

import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.XmlDate;
import org.apache.xmlbeans.XmlCalendar;

import org.incf.atlas.waxml.generated.*;
import org.incf.atlas.waxml.generated.DescribeSRSResponseType.Fiducials;
import org.incf.atlas.waxml.generated.DescribeSRSResponseType.Slices;
import org.incf.atlas.waxml.generated.ListSRSResponseType.SRSCollection.Orientations;
import org.incf.atlas.waxml.generated.ListSRSResponseType.SRSCollection.SRSList;
import org.incf.atlas.waxml.generated.ListSRSResponseType.*;
import org.incf.atlas.waxml.generated.QueryInfoType.*;
import org.incf.atlas.waxml.generated.SRSType.*;
import org.incf.atlas.waxml.generated.impl.ListSRSResponseTypeImpl.SRSCollectionImpl.SRSListImpl;
import org.isotc211.x2005.gmd.CIResponsiblePartyType;
import org.incf.atlas.waxml.utilities.*;
import org.junit.Ignore;
import org.junit.Test;

import org.incf.atlas.waxml.examples.ListSRSResponse;
import org.incf.atlas.waxml.examples.ListSRSResponse.*;

public class DescribeSrsResponse   {
	
	@Test 
	public void validFullResponse()
	{
		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		XmlObject co = completeResponse();
		ArrayList errorList = new ArrayList();
		boolean validXml =  Utilities.validateXml(opt, co, errorList);
		 assertTrue(errorList.toString(), validXml);
		
	}
	
	public String  AsXml(){
		XmlOptions opt = (new XmlOptions()).setSavePrettyPrint();
		opt.setSaveSuggestedPrefixes(Utilities.SuggestedNamespaces());
		opt.setSaveNamespacesFirst();
		opt.setSaveAggressiveNamespaces();
		opt.setUseDefaultNamespace();
		
		DescribeSRSResponseDocument document = completeResponse();
		
		
		ArrayList errorList = new ArrayList();
		 opt.setErrorListener(errorList);
		 boolean isValid = document.validate(opt);
		 
		 // If the XML isn't valid, loop through the listener's contents,
		 // printing contained messages.
		 if (!isValid)
		 {
		      for (int i = 0; i < errorList.size(); i++)
		      {
		          XmlError error = (XmlError)errorList.get(i);
		          
		          System.out.println("\n");
		          System.out.println("Message: " + error.getMessage() + "\n");
		          System.out.println("Location of invalid XML: " + 
		              error.getCursorLocation().xmlText() + "\n");
		      }
		 }
			return document.xmlText(opt);
	}

	public DescribeSRSResponseDocument completeResponse() {
		DescribeSRSResponseDocument document =	DescribeSRSResponseDocument.Factory.newInstance(); 
		
		DescribeSRSResponseType rootDoc =	document.addNewDescribeSRSResponse();
			
			rootDoc.newCursor().insertComment("Generated " + Calendar.getInstance().getTime());
			ListSRSResponse.QueryInfoSrs(rootDoc.addNewQueryInfo(), "URL");
			rootDoc.getQueryInfo().getQueryUrl().setName("DescribeSRS");
			Utilities.addInputStringCriteria(rootDoc.getQueryInfo().addNewCriteria(),
					"srsName", "Mouse_ABAreference_1.0");	
			SRSCollection coll1 = rootDoc.addNewSRSCollection();
			coll1.setHubCode("HUBA");

		SRSList srsList = coll1.addNewSRSList();
			SRSType srs1 =  srsList.addNewSRS();
			ListSRSResponse.SrsExample1(srs1);
			
			Orientations o = coll1.addNewOrientations();
			OrientationType orientaiton1 = o.addNewOrientation();
			//orientation(orientaiton1,code,name);
			ListSRSResponse.orientation(orientaiton1,"Left","Left");
			orientaiton1 = o.addNewOrientation();
			ListSRSResponse.orientation(orientaiton1,"Right","Right");
			orientaiton1 = o.addNewOrientation();
			ListSRSResponse.orientation(orientaiton1,"Ventral","Ventral");
			orientaiton1 = o.addNewOrientation();
			ListSRSResponse.orientation(orientaiton1,"Dorsal","Dorsal");
			orientaiton1 = o.addNewOrientation();
			ListSRSResponse.orientation(orientaiton1,"Posterior","Posterior");
			orientaiton1 = o.addNewOrientation();
			ListSRSResponse.orientation(orientaiton1,"Anterior","Anterior");
			
			Slices s = rootDoc.addNewSlices();
			exampleSlice(	s.addNewSlice(), 1);
Fiducials f = rootDoc.addNewFiducials();
exampleFiducial(f.addNewFiducial(), 1);

			return document;
		}
	
	public static void exampleSlice(SliceType slice, double identifier){
		slice.newCursor().insertComment("orientation {coronal|sagittal|horizontal}");
		slice.setOrientation(SliceType.Orientation.HORIZONTAL);
		slice.setXOrientation("positive dorsal");
		slice.setYOrientation("positive coronal"); 
//		slice.setConstant(1.0);
		slice.setConstant(1);
				slice.setCode("Reference Number for Slice");
				
	}
	
	public static void exampleFiducial(FiducialType fiducial, int identifier){
		fiducial.setCode("Reference Number for fiducial");
		fiducial.setName("Fiducial Name 1");
		fiducial.setFiducialType("anatomic");
		fiducial.setCertaintyLevel("optional value");
		fiducial.setDerivedFrom("optional value");
		fiducial.setModality(1);
		
	AuthorType author=	fiducial.addNewAuthor();
	author.setAuthorCode("aCode");
	//author.setDateSubmitted(Calendar.getInstance());
	author.setDateSubmitted(new XmlCalendar("2004-07-04"));
	author.setStringValue("Author Name");
	
	Incfdescription desc = 	fiducial.addNewDescription();
	desc.setStringValue("Deescription");
	
	PointType pnt=	 fiducial.addNewPoint();
	pnt.addNewPos().setStringValue("0 0 0");
	pnt.setSrsName("Mouse_ABAReference_1.0");
	pnt.setId("a35");

	}
	
	
}
