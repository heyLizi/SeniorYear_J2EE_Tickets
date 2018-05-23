package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 结算规则类
 **/

@Entity
@Table(name="settle_rule")
public class SettleRuleBean implements Serializable {

	private static final long serialVersionUID = -2412737938575774661L;

	private int level; 			 //结算等级
	private int boundMin;		 //界限最小值
	private int boundMax;		 //界限最大值
	private double settlePercent;//结算比例
	
	@Id
	@Column(name="level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column(name="bound_min")
	public int getBoundMin() {
		return boundMin;
	}
	public void setBoundMin(int boundMin) {
		this.boundMin = boundMin;
	}
	
	@Column(name="bound_max")
	public int getBoundMax() {
		return boundMax;
	}
	public void setBoundMax(int boundMax) {
		this.boundMax = boundMax;
	}
	
	@Column(name="settle_percent")
	public double getSettlePercent() {
		return settlePercent;
	}
	public void setSettlePercent(double settlePercent) {
		this.settlePercent = settlePercent;
	}
	
}
