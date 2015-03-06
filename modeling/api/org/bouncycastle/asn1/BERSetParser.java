/*
 * Copyright (C) 2015,  Massachusetts Institute of Technology
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2 of the License, or (at your
 * option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * Please email droidsafe@lists.csail.mit.edu if you need additional
 * information or have any questions.
 */


/***** THIS FILE HAS BEEN MODIFIED FROM THE ORIGINAL BY THE DROIDSAFE PROJECT. *****/


package org.bouncycastle.asn1;

// Droidsafe Imports
import droidsafe.runtime.*;
import droidsafe.helpers.*;
import droidsafe.annotations.*;
import java.io.IOException;

public class BERSetParser implements ASN1SetParser {
@DSGeneratedField(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 13:00:28.748 -0500", hash_original_field = "8EE68094F784B2C8EC928CABAEA021F0", hash_generated_field = "76949B28A5103E4F545C65F9E6322200")

    private ASN1StreamParser _parser;

    @DSComment("Package priviledge")
    @DSBan(DSCat.DEFAULT_MODIFIER)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 13:00:28.751 -0500", hash_original_method = "EF8530238239035799CD0A162B856113", hash_generated_method = "EF8530238239035799CD0A162B856113")
    
BERSetParser(ASN1StreamParser parser)
    {
        this._parser = parser;
    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 13:00:28.753 -0500", hash_original_method = "C77FFAC7A6753577CB275ABB55D17A64", hash_generated_method = "7E945A4F85D137716A9450ED5D0C4580")
    
public DEREncodable readObject()
        throws IOException
    {
        return _parser.readObject();
    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 13:00:28.755 -0500", hash_original_method = "1B045C000E117296EF92C9846060EDEF", hash_generated_method = "36F6270156FC4136535AFCDB82B9B5A1")
    
public DERObject getLoadedObject()
        throws IOException
    {
        return new BERSet(_parser.readVector(), false);
    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2013-12-30 13:00:28.757 -0500", hash_original_method = "C25B7E6814F316F8AC8F55D830DF862A", hash_generated_method = "C5BF662686CC510B0E8AAA77A95B6DA6")
    
public DERObject getDERObject()
    {
        try
        {
            return getLoadedObject();
        }
        catch (IOException e)
        {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
    
}

