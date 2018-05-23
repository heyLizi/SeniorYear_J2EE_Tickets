package po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 演出计划类
 **/

@Entity
@Table(name="performance_plan")
public class PerformancePlanBean implements Serializable {

	private static final long serialVersionUID = 3365383535948046650L;
	
	private int ppid;			//演出计划编号
	private String ppname;		//演出计划名称
	private int vid;			//场馆编号
	private Timestamp startTime;//演出开始时间
	private Timestamp endTime;	//演出结束时间
	private char category;		//演出类型:M表示音乐会，C表示演唱会，D表示话剧
	private String briefIntro;	//演出简要介绍
	private int seatVipPrice;	//VIP类座位的价格
	private int seatAPrice;		//A类座位的价格
	private int seatBPrice;		//B类座位的价格
	private int seatCPrice;		//C类座位的价格
	private int seatDPrice;		//D类座位的价格
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ppid")
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	
	@Column(name="ppname")
	public String getPpname() {
		return ppname;
	}
	public void setPpname(String ppname) {
		this.ppname = ppname;
	}
	
	@Column(name="vid")
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	@Column(name="start_time")
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	@Column(name="end_time")
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	
	@Column(name="category")
	public char getCategory() {
		return category;
	}
	public void setCategory(char category) {
		this.category = category;
	}
	
	@Column(name="brief_intro")
	public String getBriefIntro() {
		return briefIntro;
	}
	public void setBriefIntro(String briefIntro) {
		this.briefIntro = briefIntro;
	}
	
	@Column(name="seat_vip_price")
	public int getSeatVipPrice() {
		return seatVipPrice;
	}
	public void setSeatVipPrice(int seatVipPrice) {
		this.seatVipPrice = seatVipPrice;
	}
	
	@Column(name="seat_A_price")
	public int getSeatAPrice() {
		return seatAPrice;
	}
	public void setSeatAPrice(int seatAPrice) {
		this.seatAPrice = seatAPrice;
	}
	
	@Column(name="seat_B_price")
	public int getSeatBPrice() {
		return seatBPrice;
	}
	public void setSeatBPrice(int seatBPrice) {
		this.seatBPrice = seatBPrice;
	}
	
	@Column(name="seat_C_price")
	public int getSeatCPrice() {
		return seatCPrice;
	}
	public void setSeatCPrice(int seatCPrice) {
		this.seatCPrice = seatCPrice;
	}
	
	@Column(name="seat_D_price")
	public int getSeatDPrice() {
		return seatDPrice;
	}
	public void setSeatDPrice(int seatDPrice) {
		this.seatDPrice = seatDPrice;
	}
	
}
