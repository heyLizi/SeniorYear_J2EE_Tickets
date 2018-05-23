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
 * 退款类
 **/
@Entity
@Table(name="refund")
public class RefundBean implements Serializable {

	private static final long serialVersionUID = -2573881357587225603L;

	private int rid;			//退款编号
	private int oid;			//被退款的订单编号
	private Timestamp refundTime;	//退款时间
	private double refundMoney;	//退款金额
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rid")
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	@Column(name="oid")
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	@Column(name="refund_time")
	public Timestamp getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Timestamp refundTime) {
		this.refundTime = refundTime;
	}
	
	@Column(name="refund_money")
	public double getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(double refundMoney) {
		this.refundMoney = refundMoney;
	}
	
}
