package runze.yaohe.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Truck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imei;
	private String weight;
	private String length;
	private String width;
	private String otherMsg;
	private String phoneNumber;
	private String name;
	private String isLoaded;
	private Integer latitudeE6;
	private Integer longitudeE6;
	private String city;
	private String district;
	private String street;
	private Timestamp lastTimeIsLoaded;
	private Timestamp lastTimeLocation;
	
	
	public Truck() {
		super();
	}
	
	
	public Truck(String imei, String weight, String length, String width,
			String otherMsg, String phoneNumber, String isLoaded,
			Integer latitudeE6, Integer longitudeE6, String city,
			String district, String street,String name) {
		super();
		this.imei = imei;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.otherMsg = otherMsg;
		this.phoneNumber = phoneNumber;
		this.name=name;
		this.isLoaded = isLoaded;
		this.latitudeE6 = latitudeE6;
		this.longitudeE6 = longitudeE6;
		this.city = city;
		this.district = district;
		this.street = street;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getOtherMsg() {
		return otherMsg;
	}
	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getIsLoaded() {
		return isLoaded;
	}
	public void setIsLoaded(String isLoaded) {
		this.isLoaded = isLoaded;
	}
	public Integer getLatitudeE6() {
		return latitudeE6;
	}
	public void setLatitudeE6(Integer latitudeE6) {
		this.latitudeE6 = latitudeE6;
	}
	public Integer getLongitudeE6() {
		return longitudeE6;
	}
	public void setLongitudeE6(Integer longitudeE6) {
		this.longitudeE6 = longitudeE6;
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


	public Timestamp getLastTimeIsLoaded() {
		return lastTimeIsLoaded;
	}


	public void setLastTimeIsLoaded(Timestamp lastTimeIsLoaded) {
		this.lastTimeIsLoaded = lastTimeIsLoaded;
	}


	public Timestamp getLastTimeLocation() {
		return lastTimeLocation;
	}


	public void setLastTimeLocation(Timestamp lastTimeLocation) {
		this.lastTimeLocation = lastTimeLocation;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
}
