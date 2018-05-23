package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员绑定支付账户类
 **/

@Entity
@Table(name="member_pay")
public class MemberPayBean implements Serializable {

	private static final long serialVersionUID = -3420358189205975572L;
	
	private int mid;		//会员编号
	private char category;  //账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	private String acntName;//账户名称

	@Id
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
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
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof MemberPayBean) {
			MemberPayBean mpb = (MemberPayBean) o;
			if (this.mid == mpb.getMid() && this.category == mpb.getCategory() && this.acntName.equals(mpb.getAcntName())) {
				return true;
			}
		}
		return false;
	}
}
