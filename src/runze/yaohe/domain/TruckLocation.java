package runze.yaohe.domain;


/**
 * TruckLocation entity. @author MyEclipse Persistence Tools
 */

public class TruckLocation implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imei;
	private Integer latitudeE6;
	private Integer longitudeE6;
	private String city;
	private String district;
	private String street;

	// Constructors

	/** default constructor */
	public TruckLocation() {
	}

	/** minimal constructor */
	public TruckLocation(String imei) {
		this.imei = imei;
	}

	/** full constructor */
	public TruckLocation(String imei, Integer latitudeE6, Integer longitudeE6,
			String city, String district, String street) {
		this.imei = imei;
		this.latitudeE6 = latitudeE6;
		this.longitudeE6 = longitudeE6;
		this.city = city;
		this.district = district;
		this.street = street;
	}

	// Property accessors

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getLatitudeE6() {
		return this.latitudeE6;
	}

	public void setLatitudeE6(Integer latitudeE6) {
		this.latitudeE6 = latitudeE6;
	}

	public Integer getLongitudeE6() {
		return this.longitudeE6;
	}

	public void setLongitudeE6(Integer longitudeE6) {
		this.longitudeE6 = longitudeE6;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}


}