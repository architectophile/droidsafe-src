package android.media;

import java.io.IOException;

import droidsafe.annotations.DSC;
import droidsafe.annotations.DSModeled;
/*
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
*/

public class ExifInterface {
	
	@DSModeled() //Decided to go with SPEC because in theory be used for covert data storage
	private void loadAttributes() throws IOException {
		addTaint("string attribute taint".getTaint()); //Implicit taint for getAttribute
		//addTaint(-1.getTaint()); //Implicit taint for getAttributeInt
		//addTaint(-2.getTaint()); //Implicit taint for getAttributeDouble
	}
	
	@DSModeled(value = DSC.SAFE)
	public ExifInterface(String filename) throws IOException {
		addTaint(filename.getTaint());
        //mFilename = filename;
		/*
		 * DSFIXME:  loadAttributes will parse and load values obtained from a
		 * native call that pulls image attributes from the file itself and stores
		 * them in the HashMap mAttributes.  Effectively this is causing an implicit
		 * taint on attributes, since they are being loaded through a native call
		 * which is returning back a series of properties serialized as a space
		 * delimited string (ghetto!).  Access to values stored in
		 * mAttributes is controlled through the getAttribute* series of methods.
		 * This is a first stab at trying to actually taint the attributes themselves
		 */
        loadAttributes();
    }
	
	@DSModeled(value = DSC.SPEC)
	public String getAttribute(String tag) {
        String str = new String();
        str.addTaint(getTaint());
        return str;
        //return mAttributes.get(tag);
    }
	
	public int getAttributeInt(String tag, int defaultValue) {
		return getTaintInt();
	}
	
	/*
	 * Not quite sure how to model this, but nothing is calling it (yet) so
	 * we'll hold off on this.
	public double getAttributeDouble(String tag, double defaultValue) {
		return (double)getTaintInt();
	}
	*/
}
