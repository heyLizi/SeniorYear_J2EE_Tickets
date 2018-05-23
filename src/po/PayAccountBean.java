package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 支付账户类
 **/

@Entity
@Table(name="pay_account")
public class PayAccountBean implements Serializable {

	private static final long serialVersionUID = -1723537854638746614L;
	
	private char category;		//账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	private String acntName;	//账户名称，可以是银行卡号，支付宝账号等等
	private String acntPasswd;	//账户密码
	private double balance;		//账户余额
	
	@Id
	@Column(name="category")
	public char getCategory() {
		return category;
	}
	public void setCategory(char category) {
		this.category = category;
	}
	
	@Id
	@Column(name="acnt_name")
	public String getAcntName() {
		return acntName;
	}
	public void setAcntName(String acntName) {
		this.acntName = acntName;
	}
	
	@Column(name="acnt_passwd")
	public String getAcntPasswd() {
		return acntPasswd;
	}
	public void setAcntPasswd(String acntPasswd) {
		this.acntPasswd = acntPasswd;
	}
	
	@Column(name="balance")
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PayAccountBean) {
			PayAccountBean pab = (PayAccountBean) o;
			if (this.category == pab.getCategory() && this.acntName.equals(pab.getAcntName())) {
				return true;
			}
		}
		return false;
	}
	
}
