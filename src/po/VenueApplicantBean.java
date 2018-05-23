package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 场馆申请者类
 **/

@Entity
@Table(name="venue_applicant")
public class VenueApplicantBean implements Serializable {

	private static final long serialVersionUID = 1167144450767455540L;

	private int vaerid;			//申请者编号
	private String telephone;	//申请者联系电话
	private String vaerPasswd;	//申请者密码
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="vaerid")
	public int getVaerid() {
		return vaerid;
	}
	public void setVaerid(int vaerid) {
		this.vaerid = vaerid;
	}
	
	@Column(name="telephone")
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@Column(name="vaer_passwd")
	public String getVaerPasswd() {
		return vaerPasswd;
	}
	public void setVaerPasswd(String vaerPasswd) {
		this.vaerPasswd = vaerPasswd;
	}
	
}
