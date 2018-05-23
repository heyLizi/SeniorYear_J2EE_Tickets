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
 * 订单类
 **/

@Entity
@Table(name="member_order")
public class OrderBean implements Serializable {
	
	private static final long serialVersionUID = 297369475308524152L;
	
	private int oid;			//订单编号
	private int mid;			//会员编号（会员编号若为0，表示是非会员）
	private int ppid;			//演出计划编号
	private char orderType;		//订单类型:S表示选座购买，R表示不选座购买
	private char orderState;	//订单状态:S表示订单已经提交尚未付款，P表示订单已经付款，R表示订单已退款，E表示订单已经完成
	private Timestamp orderTime;//订单完成时间
	private char seatCategory;	//座位类型:V表示Vip类座位，A表示A类座位，B表示B类座位，C表示C类座位，D表示D类座位
	private int seatNum;		//座位数量
	private double totalMoney;	//订单总额
	private double realMoney;	//订单实际消费金额
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="oid")
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	@Column(name="ppid")
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	
	@Column(name="order_type")
	public char getOrderType() {
		return orderType;
	}
	public void setOrderType(char orderType) {
		this.orderType = orderType;
	}
	
	@Column(name="order_state")
	public char getOrderState() {
		return orderState;
	}
	public void setOrderState(char orderState) {
		this.orderState = orderState;
	}
	
	@Column(name="order_time")
	public Timestamp getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	
	@Column(name="seat_category")
	public char getSeatCategory() {
		return seatCategory;
	}
	public void setSeatCategory(char seatCategory) {
		this.seatCategory = seatCategory;
	}
	
	@Column(name="seat_num")
	public int getSeatNum() {
		return seatNum;
	}
	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}
	
	@Column(name="total_money")
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	@Column(name="real_money")
	public double getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(double realMoney) {
		this.realMoney = realMoney;
	}
	
}

