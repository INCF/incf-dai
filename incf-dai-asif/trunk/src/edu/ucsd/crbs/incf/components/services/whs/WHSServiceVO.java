/**
 * <p>Title: Image Assembly VO</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: BIRN-CC, UCSD</p>
 * @author Asif Memon
 * @version 1.0
 */

package edu.ucsd.crbs.incf.components.services.whs;


/**
 * @author Asif Memon
 * 
 */
public class WHSServiceVO {

	/**
	 * The helper class to store the image assembly related data
	 */

	public WHSServiceVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String category;
	private String imageServiceWSURL;
	
	    
	//-------------------Public methods---------------------

	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getImageServiceWSURL() {
		return imageServiceWSURL;
	}
	
	public void setImageServiceWSURL(String imageServiceWSURL) {
		this.imageServiceWSURL = imageServiceWSURL;
	}

	
}
