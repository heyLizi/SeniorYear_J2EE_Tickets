package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 场馆类
 **/

@Entity
@Table(name="venue")
public class VenueBean implements Serializable {

	private static final long serialVersionUID = 1204535038965011933L;
	
	private int vid;			//场馆编号
	private String vname;		//场馆名称
	private String vpasswd;		//场馆账号密码
	private String province;	//场馆所在省
	private String city;		//场馆所在市
	private String area;		//场馆所在区
	private String address;		//场馆地址
	private int seatVipRowNum;	//VIP类座位排数
	private int seatVipColNum;	//VIP类座位列数
	private int seatARowNum;	//A类座位排数
	private int seatAColNum;	//A类座位列数
	private int seatBRowNum;	//B类座位排数
	private int seatBColNum;	//B类座位列数
	private int seatCRowNum;	//C类座位排数
	private int seatCColNum;	//C类座位列数
	private int seatDRowNum;	//D类座位排数
	private int seatDColNum;	//D类座位列数
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="vid")
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	@Column(name="vname")
	public String getVname() {
		return vname;
	}
	public void setVname(String vname) {
		this.vname = vname;
	}
	
	@Column(name="vpasswd")
	public String getVpasswd() {
		return vpasswd;
	}
	public void setVpasswd(String vpasswd) {
		this.vpasswd = vpasswd;
	}
	
	@Column(name="province")
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@Column(name="city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name="area")
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	@Column(name="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="seat_vip_row_num")
	public int getSeatVipRowNum() {
		return seatVipRowNum;
	}
	public void setSeatVipRowNum(int seatVipRowNum) {
		this.seatVipRowNum = seatVipRowNum;
	}
	
	@Column(name="seat_vip_col_num")
	public int getSeatVipColNum() {
		return seatVipColNum;
	}
	public void setSeatVipColNum(int seatVipColNum) {
		this.seatVipColNum = seatVipColNum;
	}
	
	@Column(name="seat_A_row_num")
	public int getSeatARowNum() {
		return seatARowNum;
	}
	public void setSeatARowNum(int seatARowNum) {
		this.seatARowNum = seatARowNum;
	}
	
	@Column(name="seat_A_col_num")
	public int getSeatAColNum() {
		return seatAColNum;
	}
	public void setSeatAColNum(int seatAColNum) {
		this.seatAColNum = seatAColNum;
	}
	
	@Column(name="seat_B_row_num")
	public int getSeatBRowNum() {
		return seatBRowNum;
	}
	public void setSeatBRowNum(int seatBRowNum) {
		this.seatBRowNum = seatBRowNum;
	}
	
	@Column(name="seat_B_col_num")
	public int getSeatBColNum() {
		return seatBColNum;
	}
	public void setSeatBColNum(int seatBColNum) {
		this.seatBColNum = seatBColNum;
	}
	
	@Column(name="seat_C_row_num")
	public int getSeatCRowNum() {
		return seatCRowNum;
	}
	public void setSeatCRowNum(int seatCRowNum) {
		this.seatCRowNum = seatCRowNum;
	}
	
	@Column(name="seat_C_col_num")
	public int getSeatCColNum() {
		return seatCColNum;
	}
	public void setSeatCColNum(int seatCColNum) {
		this.seatCColNum = seatCColNum;
	}
	
	@Column(name="seat_D_row_num")
	public int getSeatDRowNum() {
		return seatDRowNum;
	}
	public void setSeatDRowNum(int seatDRowNum) {
		this.seatDRowNum = seatDRowNum;
	}
	
	@Column(name="seat_D_col_num")
	public int getSeatDColNum() {
		return seatDColNum;
	}
	public void setSeatDColNum(int seatDColNum) {
		this.seatDColNum = seatDColNum;
	}
	
}
