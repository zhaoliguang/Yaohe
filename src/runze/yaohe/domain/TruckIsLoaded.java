package runze.yaohe.domain;


/**
 * TruckIsLoaded entity. @author MyEclipse Persistence Tools
 */

public class TruckIsLoaded implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private String imei;
	private String isLoaded;

	// Constructors

	/** default constructor */
	public TruckIsLoaded() {
	}

	/** minimal constructor */
	public TruckIsLoaded(String imei) {
		this.imei = imei;
	}

	/** full constructor */
	public TruckIsLoaded(String imei, String isLoaded) {
		this.imei = imei;
		this.isLoaded = isLoaded;
	}

	// Property accessors

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIsLoaded() {
		return this.isLoaded;
	}

	public void setIsLoaded(String isLoaded) {
		this.isLoaded = isLoaded;
	}

	

}