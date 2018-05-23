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
 * 结算收入类
 **/

@Entity
@Table(name="settle_income")
public class SettleIncomeBean implements Serializable {

	private static final long serialVersionUID = -4330362626032835442L;
	
	private int siid;		//结算收入编号
	private int vid;		//场馆编号
	private Timestamp settleTime;//结算时间
	private double income;	//收入金额
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="siid")
	public int getSiid() {
		return siid;
	}
	public void setSiid(int siid) {
		this.siid = siid;
	}
	
	@Column(name="vid")
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}

	@Column(name="settle_time")
	public Timestamp getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Timestamp settleTime) {
		this.settleTime = settleTime;
	}	
	
	@Column(name="income")
	public double getIncome() {
		return income;
	}
	public void setIncome(double income) {
		this.income = income;
	}
	
}
