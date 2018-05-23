package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 验证邮箱类
 **/

@Entity
@Table(name="verify_email")
public class VerifyEmailBean implements Serializable {

	private static final long serialVersionUID = 1743550167115648656L;

	private String email;		//邮箱地址
	private String vePasswd;	//预设密码
	private String verifyCode;	//验证码
	
	@Id
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name="ve_passwd")
	public String getVePasswd() {
		return vePasswd;
	}
	public void setVePasswd(String vePasswd) {
		this.vePasswd = vePasswd;
	}
	
	@Column(name="verify_code")
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
}