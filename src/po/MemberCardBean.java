package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员卡类
 **/

@Entity
@Table(name="member_card")
public class MemberCardBean implements Serializable {

	private static final long serialVersionUID = -3420358189504975572L;
	
	private int mid;		//会员编号
	private int level;		//会员等级
	private double totalPay;//会员总消费金额
	private double credit;	//会员积分

	@Id
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	@Column(name="level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column(name="total_pay")
	public double getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(double totalPay) {
		this.totalPay = totalPay;
	}
	
	@Column(name="credit")
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	
}
