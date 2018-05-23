package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Tickets集团信息类
 **/

@Entity
@Table(name="tickets_info")
public class TicketsInfoBean implements Serializable {
	
	private static final long serialVersionUID = -2148070979151832714L;
	
	private String mgrId;	 //经理编号
	private String mgrPasswd;//经理密码
	private double balance;	 //集团余额
	
	@Id
	@Column(name="mgr_id")
	public String getMgrId() {
		return mgrId;
	}
	public void setMgrId(String mgrId) {
		this.mgrId = mgrId;
	}
	
	@Column(name="mgr_passwd")
	public String getMgrPasswd() {
		return mgrPasswd;
	}
	public void setMgrPasswd(String mgrPasswd) {
		this.mgrPasswd = mgrPasswd;
	}
	
	@Column(name="balance")
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
