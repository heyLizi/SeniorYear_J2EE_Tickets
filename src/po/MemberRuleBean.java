package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员规则类
 **/

@Entity
@Table(name="member_rule")
public class MemberRuleBean implements Serializable {
	
	private static final long serialVersionUID = 1080694753085145152L;
	
	private int level;			//会员等级
	private double boundMin;	//消费总金额界限最小值
	private double boundMax;	//消费总金额界限最大值
	private double discount;	//折扣比例
	private double creditRule;	//积分规则（每消费1元积多少分）
	
	@Id
	@Column(name="level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column(name="bound_min")
	public double getBoundMin() {
		return boundMin;
	}
	public void setBoundMin(double boundMin) {
		this.boundMin = boundMin;
	}
	
	@Column(name="bound_max")
	public double getBoundMax() {
		return boundMax;
	}
	public void setBoundMax(double boundMax) {
		this.boundMax = boundMax;
	}
	
	@Column(name="discount")
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	
	@Column(name="credit_rule")
	public double getCreditRule() {
		return creditRule;
	}
	public void setCreditRule(double creditRule) {
		this.creditRule = creditRule;
	}
	
}
