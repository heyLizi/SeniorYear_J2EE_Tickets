package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 退款规则类
 **/
@Entity
@Table(name="refund_rule")
public class RefundRuleBean implements Serializable {

	private static final long serialVersionUID = -5363238306807346606L;

	private int level;				//退款等级
	private int leftMinuteBoundMin;	//剩余分钟界限最小值
	private int leftMinuteBoundMax;	//剩余分钟界限最大值
	private double refundPercent;	//退款比例
	
	@Id
	@Column(name="level")
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column(name="left_minute_bound_min")
	public int getLeftMinuteBoundMin() {
		return leftMinuteBoundMin;
	}
	public void setLeftMinuteBoundMin(int leftMinuteBoundMin) {
		this.leftMinuteBoundMin = leftMinuteBoundMin;
	}
	
	@Column(name="left_minute_bound_max")
	public int getLeftMinuteBoundMax() {
		return leftMinuteBoundMax;
	}
	public void setLeftMinuteBoundMax(int leftMinuteBoundMax) {
		this.leftMinuteBoundMax = leftMinuteBoundMax;
	}
	
	@Column(name="refund_percent")
	public double getRefundPercent() {
		return refundPercent;
	}
	public void setRefundPercent(double refundPercent) {
		this.refundPercent = refundPercent;
	}
	
}
