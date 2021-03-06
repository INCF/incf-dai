//$HeadURL: http://svn.wald.intevation.org/svn/deegree/deegree3/branches/3.0/deegree-core/src/main/java/org/deegree/feature/types/property/GenericGMLObjectPropertyType.java $
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2009 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.feature.types.property;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xerces.xs.XSComplexTypeDefinition;

/**
 * The <code></code> class TODO add class documentation here.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 28044 $, $Date: 2010-11-11 09:40:04 -0800 (Thu, 11 Nov 2010) $
 */
public class GenericGMLObjectPropertyType extends GMLObjectPropertyType {

    private XSComplexTypeDefinition xsdType;

    /**
     * @param name
     * @param minOccurs
     * @param maxOccurs
     * @param isAbstract
     * @param isNillable
     * @param substitutions
     * @param representation
     */
    public GenericGMLObjectPropertyType( QName name, int minOccurs, int maxOccurs, boolean isAbstract,
                                         boolean isNillable, List<PropertyType> substitutions,
                                         ValueRepresentation representation, XSComplexTypeDefinition xsdType ) {
        super( name, minOccurs, maxOccurs, isAbstract, isNillable, substitutions, representation );
        this.xsdType = xsdType;
    }

    public XSComplexTypeDefinition getXSDValueType() {
        return xsdType;
    }

    @Override
    public String toString() {
        String s = "- generic GML object property type: '" + name + "', minOccurs=" + minOccurs + ", maxOccurs="
                   + maxOccurs + ", xsd type: " + xsdType;
        return s;
    }
}
