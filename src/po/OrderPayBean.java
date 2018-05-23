package po;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单支付类
 **/

@Entity
@Table(name="order_pay")
public class OrderPayBean implements Serializable {

	private static final long serialVersionUID = 892276383353709018L;

	private int oid;			//订单编号
	private int mid;			//会员编号
	private char isPayAcnt; 	//是否使用会员支付账户进行付款：若为Y，表示使用会员账户；若为N，表示使用现金
	private char payCategory;	//支付账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	private String payAcntName;	//支付账户名称
	private Timestamp payTime;	//支付时间
	private double payMoney;	//支付金额
	
	@Id
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
	
	@Column(name="is_pay_acnt")
	public char getIsPayAcnt() {
		return isPayAcnt;
	}
	public void setIsPayAcnt(char isPayAcnt) {
		this.isPayAcnt = isPayAcnt;
	}
	
	@Column(name="pay_category")
	public char getPayCategory() {
		return payCategory;
	}
	public void setPayCategory(char payCategory) {
		this.payCategory = payCategory;
	}
	
	@Column(name="pay_acnt_name")
	public String getPayAcntName() {
		return payAcntName;
	}
	public void setPayAcntName(String payAcntName) {
		this.payAcntName = payAcntName;
	}
	
	@Column(name="pay_time")
	public Timestamp getPayTime() {
		return payTime;
	}
	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	
	@Column(name="pay_money")
	public double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(double payMoney) {
		this.payMoney = payMoney;
	}
	
}
