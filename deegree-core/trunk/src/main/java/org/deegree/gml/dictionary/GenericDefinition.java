//$HeadURL: http://svn.wald.intevation.org/svn/deegree/deegree3/branches/3.0/deegree-core/src/main/java/org/deegree/gml/dictionary/GenericDefinition.java $
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
package org.deegree.gml.dictionary;

import org.deegree.commons.tom.ows.CodeType;
import org.deegree.commons.tom.ows.StringOrRef;
import org.deegree.gml.props.GMLStdProps;

/**
 * Default implementation of {@link Definition}.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 23152 $, $Date: 2010-03-18 03:20:56 -0700 (Thu, 18 Mar 2010) $
 */
public class GenericDefinition implements Definition {

    private String id;

    private GMLStdProps gmlProps;

    /**
     * Creates a new {@link GenericDefinition} instance.
     * 
     * @param id
     *            id of the definition, can be <code>null</code>
     * @param gmlProps
     *            GML standard properties (which contain description and names), must not be <code>null</code>
     */
    public GenericDefinition( String id, GMLStdProps gmlProps ) {
        this.id = id;
        this.gmlProps = gmlProps;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public StringOrRef getDescription() {
        return gmlProps.getDescription();
    }

    @Override
    public CodeType[] getNames() {
        return gmlProps.getNames();
    }

    @Override
    public GMLStdProps getGMLProperties() {
        return gmlProps;
    }
}
