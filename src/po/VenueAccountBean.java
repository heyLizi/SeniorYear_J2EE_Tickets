package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 场馆账户类
 **/

@Entity
@Table(name="venue_account")
public class VenueAccountBean implements Serializable {

	private static final long serialVersionUID = -4330362626032835442L;
	
	private int vid;		//场馆编号
	private double balance;	//场馆账户金额

	@Id
	@Column(name="vid")
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	@Column(name="balance")
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
}
