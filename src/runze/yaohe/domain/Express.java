package runze.yaohe.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Express implements Serializable {
	public Express() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Express(String imei, String name, String phoneNumber, String company,
			String otherMsg, Integer latitudeE6, Integer longitudeE6,
			String city, String district, String street,
			Timestamp lastTimeLocation) {
		super();
		this.imei = imei;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.company = company;
		this.otherMsg = otherMsg;
		this.latitudeE6 = latitudeE6;
		
		this.longtitudeE6 = longitudeE6;
		this.city = city;
		this.district = district;
		this.street = street;
		this.lastTimeLocation = lastTimeLocation;
	}
	
	
	private static final long serialVersionUID = 1L;
	private String imei;
	private String name;
	private String phoneNumber;
	private String company;
	private String otherMsg;
	private Integer latitudeE6;
	private Integer longtitudeE6;
	private String city;
	private String district;
	private String street;
	private Timestamp lastTimeLocation;
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOtherMsg() {
		return otherMsg;
	}
	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}
	public Integer getLatitudeE6() {
		return latitudeE6;
	}
	public void setLatitudeE6(Integer latitudeE6) {
		this.latitudeE6 = latitudeE6;
	}
	public Integer getLongitudeE6() {
		return longtitudeE6;
	}
	public void setlongtitudeE6(Integer longtitudeE6) {
		
		this.longtitudeE6 = longtitudeE6;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Timestamp getLastTimeLocation() {
		return lastTimeLocation;
	}
	public void setLastTimeLocation(Timestamp lastTimeLocation) {
		this.lastTimeLocation = lastTimeLocation;
	}


}
