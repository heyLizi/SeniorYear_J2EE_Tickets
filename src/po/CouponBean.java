package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 优惠券类
 **/

@Entity
@Table(name="coupon")
public class CouponBean implements Serializable {
	
	private static final long serialVersionUID = 297369475308524152L;
	
	private int cpnId;			//优惠券编号
	private int mid;			//会员编号
	private double cpnMoney;	//优惠券金额
	private char cpnState;		//优惠券状态:A表示可用，U表示不可用
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cpn_id")
	public int getCpnId() {
		return cpnId;
	}
	public void setCpnId(int cpnId) {
		this.cpnId = cpnId;
	}
	
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	@Column(name="cpn_money")
	public double getCpnMoney() {
		return cpnMoney;
	}
	public void setCpnMoney(double cpnMoney) {
		this.cpnMoney = cpnMoney;
	}
	
	@Column(name="cpn_state")
	public char getCpnState() {
		return cpnState;
	}
	public void setCpnState(char cpnState) {
		this.cpnState = cpnState;
	}
	
}

