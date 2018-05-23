package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 优惠券规则类
 **/

@Entity
@Table(name="coupon_rule")
public class CouponRuleBean implements Serializable {
	
	private static final long serialVersionUID = 1080694725085145152L;
	
	private int credit;	//兑换积分
	private int money;	//优惠券金额	
	
	@Id
	@Column(name="credit")
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	
	@Column(name="money")
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
}
