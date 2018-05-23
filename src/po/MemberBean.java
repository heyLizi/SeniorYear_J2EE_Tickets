package po;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员类
 **/

@Entity
@Table(name="member")
public class MemberBean implements Serializable {
	
	private static final long serialVersionUID = 2973694753085145152L;
	
	private int mid;			//会员编号
	private String email;		//会员邮箱
	private String mpasswd;		//会员密码
	private String mname;		//会员名称
	private char sex;			//会员性别:M表示男，F表示女
	private Date birthDate;		//会员生日
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="mpasswd")
	public String getMpasswd() {
		return mpasswd;
	}
	public void setMpasswd(String mpasswd) {
		this.mpasswd = mpasswd;
	}
	
	@Column(name="mname")
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	
	@Column(name="sex")
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	
	@Column(name="birth_date")
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
}
