//$HeadURL: http://svn.wald.intevation.org/svn/deegree/deegree3/branches/3.0/deegree-core/src/main/java/org/deegree/filter/sql/expression/SQLOperationBuilder.java $
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
package org.deegree.filter.sql.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for building {@link SQLOperation} expressions.
 * 
 * @author <a href="mailto:schneider@lat-lon.de">Markus Schneider</a>
 * @author last edited by: $Author: mschneider $
 * 
 * @version $Revision: 24519 $, $Date: 2010-05-20 11:35:27 -0700 (Thu, 20 May 2010) $
 */
public class SQLOperationBuilder {

    private int sqlType;    
    
    private boolean matchCase = true;

    private List<Object> particles = new ArrayList<Object>();

    public SQLOperationBuilder( int sqlType ) {
        this.sqlType = sqlType;
    }

    public SQLOperationBuilder() {
        // nothing to do
    }

    public SQLOperationBuilder( boolean matchCase ) {
        this.matchCase = matchCase;
    }

    public void add( String s ) {
        particles.add( s );
    }

    public void add( SQLExpression expr ) {
        particles.add( expr );
    }

    public SQLOperation toOperation() {
        return new SQLOperation( particles );
    }
}
