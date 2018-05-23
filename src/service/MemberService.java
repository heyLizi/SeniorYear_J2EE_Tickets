package service;

import java.sql.Date;
import java.util.List;

import po.MemberBean;
import po.MemberPayBean;
import util.Result;

/**
 * 会员服务接口
 **/

public interface MemberService {

	/**注册
	 * 
	 * @param email 电子邮箱
	 * @param mpasswd 预设密码
	 * @return 注册结果:Result的result属性若为true，表示注册成功，description属性无意义；
	 * 				 Result的result属性若为false，表示注册失败，description属性表示失败原因（已存在该邮箱，或邮箱不存在等）
	 */
	public Result register(String email, String mpasswd);
	
	
	/**验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @param vCode 验证码
	 * @return 验证结果:Result的result属性若为true，表示验证成功，description属性表示验证成功后的会员编号；
	 * 				 Result的result属性若为false，表示验证失败，description属性表示失败原因（验证码错误、邮箱未注册等）
	 */
	public Result verifyEmail(String email, String vCode);
	
	
	/**重置验证码，用于邮箱未收到验证码的情况，用户选择重置验证码以重新接收邮件
	 * 
	 * @param email 电子邮箱
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（邮箱不存在等）
	 */
	public Result resetVerifyCode(String email);

	
	/**登录
	 * 
	 * @param mid 会员编号
	 * @param mpasswd 会员密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误、账户不存在等）
	 */
	public Result login(int mid, String mpasswd);
	
	
	/**重置密码
	 * 
	 * @param mid 会员编号
	 * @param newPasswd 新的密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result resetPassword(int mid, String newPasswd);
	
	
	/**设置个人信息
	 * 
	 * @param mid 会员编号
	 * @param mname 会员名
	 * @param sex 性别:M表示男，F表示女
	 * @param birthDate 出生日期
	 * @return 设置结果:Result的result属性若为true，表示设置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示设置失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result setInfo(int mid, String mname, char sex, Date birthDate);

	
	/**显示会员信息
	 * 
	 * @param mid 会员编号
	 * @return 会员对象:若会员编号不存在，则返回null
	 */
	public MemberBean showMemberDetail(int mid);
	
	
	/**绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	 * @param acntName 账户名称
	 * @param acntPasswd 账户密码
	 * @return 绑定结果:Result的result属性若为true，表示绑定成功，description属性无意义；
	 * 				 Result的result属性若为false，表示绑定失败，description属性表示失败原因（支付账户不存在、密码错误等）
	 */
	public Result bindPayAccount(int mid, char category, String acntName, String acntPasswd);
	
	
	/**解绑支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	 * @param acntName 账户名称
	 * @return 解绑结果:Result的result属性若为true，表示解绑成功，description属性无意义；
	 * 				 Result的result属性若为false，表示解绑失败，description属性表示失败原因（支付账户不存在等）
	 */
	public Result unbindPayAccount(int mid, char category, String acntName);

	
	/**显示会员绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 会员绑定支付账户对象
	 */
	public MemberPayBean showMemberPay(int mid, char category, String acntName);

	
	/**显示某会员的全部支付账户
	 * 
	 * @param mid 会员编号
	 * @return 该会员的全部支付账户对象列表
	 */
	public List<MemberPayBean> showMyPayAccounts(int mid);
	
	
	/**显示所有的会员对象
	 * 
	 * @return 所有的会员对象列表
	 */
	public List<MemberBean> showAllMembers();
	
}
