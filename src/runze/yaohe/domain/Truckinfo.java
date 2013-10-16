package runze.yaohe.domain;

/**
 * Truckinfo entity. @author MyEclipse Persistence Tools
 */

public class Truckinfo implements java.io.Serializable {

	// Fields

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
	// Constructors

	/** default constructor */
	public Truckinfo() {
	}

	/** minimal constructor */
	public Truckinfo(String imei) {
		this.imei = imei;
	}

	/** full constructor */
	public Truckinfo(String imei, String weight, String length, String width,
			String otherMsg, String phoneNumber,String name) {
		this.imei = imei;
		this.weight = weight;
		this.length = length;
		this.width = width;
		this.otherMsg = otherMsg;
		this.phoneNumber = phoneNumber;
        this.name=name;
	}

	// Property accessors

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return this.width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getOtherMsg() {
		return this.otherMsg;
	}

	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}