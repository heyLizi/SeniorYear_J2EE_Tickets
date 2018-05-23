package service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Random;

import dao.MemberDao;
import dao.MemberPayDao;
import dao.VerifyEmailDao;
import po.MemberBean;
import po.MemberPayBean;
import po.PayAccountBean;
import po.VerifyEmailBean;
import service.MemberCardService;
import service.MemberService;
import service.PayAccountService;
import service.UtilityService;
import util.Result;

/**
 * 会员服务实现
 **/

public class MemberServiceImpl implements MemberService {

	private MemberCardService memberCardService;
	private PayAccountService payAccountService;
	private UtilityService utilityService;
	
	private MemberDao memberDao;
	private MemberPayDao memberPayDao;
	private VerifyEmailDao verifyEmailDao;

	public MemberCardService getMemberCardService() {
		return memberCardService;
	}
	
	public void setMemberCardService(MemberCardService memberCardService) {
		this.memberCardService = memberCardService;
	}
	
	public PayAccountService getPayAccountService() {
		return payAccountService;
	}

	public void setPayAccountService(PayAccountService payAccountService) {
		this.payAccountService = payAccountService;
	}

	public UtilityService getUtilityService() {
		return utilityService;
	}

	public void setUtilityService(UtilityService utilityService) {
		this.utilityService = utilityService;
	}
	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	public MemberPayDao getMemberPayDao() {
		return memberPayDao;
	}

	public void setMemberPayDao(MemberPayDao memberPayDao) {
		this.memberPayDao = memberPayDao;
	}
	
	public VerifyEmailDao getVerifyEmailDao() {
		return verifyEmailDao;
	}

	public void setVerifyEmailDao(VerifyEmailDao verifyEmailDao) {
		this.verifyEmailDao = verifyEmailDao;
	}
	
	
	/**注册
	 * 
	 * @param email 电子邮箱
	 * @param mpasswd 预设密码
	 * @return 注册结果:Result的result属性若为true，表示注册成功，description属性无意义；
	 * 				 Result的result属性若为false，表示注册失败，description属性表示失败原因（已存在该邮箱，或邮箱不存在等）
	 */
	@Override
	public Result register(String email, String mpasswd) {
		
		Result result;
		
		//随机产生6位数字验证码
		Random random = new Random();
		String randomVCode = "";
		for(int i=0; i<6; i++) {
			randomVCode += random.nextInt(10);
		}
		
		//生成验证邮箱对象
		VerifyEmailBean verifyEmail = new VerifyEmailBean();
		verifyEmail.setEmail(email);
		verifyEmail.setVePasswd(mpasswd);
		verifyEmail.setVerifyCode(randomVCode);
		
		//添加验证邮箱
		int addVrfyEmailResult = verifyEmailDao.addVrfyEmail(verifyEmail);
		if(addVrfyEmailResult > 0) {//添加验证邮箱成功
			//发送邮件，返回发送邮件的结果
			result = sendVerifyEmail(email, randomVCode);
		}
		else{//添加验证邮箱失败，返回错误信息
			result = new Result(false, "该邮箱已经存在");
		}
		
		return result;
	}

	
	/**验证邮箱
	 * 
	 * @param email 电子邮箱
	 * @param vCode 验证码
	 * @return 验证结果:Result的result属性若为true，表示验证成功，description属性表示验证成功后的会员编号；
	 * 				 Result的result属性若为false，表示验证失败，description属性表示失败原因（验证码错误、邮箱未注册等）
	 */
	@Override
	public Result verifyEmail(String email, String vCode) {
		
		Result result;
		
		//查询验证邮箱信息
		VerifyEmailBean verifyEmail = verifyEmailDao.showVrfyEmailDetail(email);
		if(verifyEmail != null) {//验证邮箱对象存在
			
			String trueVCode = verifyEmail.getVerifyCode(); //真正的验证码
			String mpasswd = verifyEmail.getVePasswd(); //预设密码
			
			if((vCode.equals(trueVCode))) {//用户输入的验证码正确，添加会员
				int addMemberResult = memberDao.addMember(email,mpasswd);				
				if(addMemberResult > 0) {//添加会员成功，添加该会员对应的会员卡
					Result addCardResult = memberCardService.signUpCard(addMemberResult);
					if(addCardResult.getResult()) {//添加会员卡成功，返回成功结果+会员编号
						result = new Result(true, addMemberResult+"");
					}
					else {//添加会员卡失败，返回错误信息
						result = addCardResult;
					}
				}
				else {//添加会员失败，返回错误信息
					result = new Result(false, "添加会员失败");
				}
			}
			else {//用户输入的验证码错误，返回错误信息
				result = new Result(false, "验证码错误");
			}
		}
		else {//验证邮箱对象不存在，返回错误信息
			result = new Result(false, "邮箱未被注册");
		}
		
		return result;		
	}

	
	/**重置验证码，用于邮箱未收到验证码的情况，用户选择重置验证码以重新接收邮件
	 * 
	 * @param email 电子邮箱
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（邮箱不存在等）
	 */
	@Override
	public Result resetVerifyCode(String email) {
		
		Result result;
		
		//查询验证邮箱信息
		VerifyEmailBean verifyEmail = verifyEmailDao.showVrfyEmailDetail(email);
		if(verifyEmail != null) {//验证邮箱对象存在
			//随机产生6位数字验证码
			Random random = new Random();
			String randomVCode = "";
			for(int i=0; i<6; i++) {
				randomVCode += random.nextInt(10);
			}
		
			//更新验证码
			verifyEmail.setVerifyCode(randomVCode);
			int updateVrfyEmailResult = verifyEmailDao.updateVrfyEmail(verifyEmail);
			if(updateVrfyEmailResult > 0) {//更新验证邮箱成功
				//发送邮件，返回发送邮件的结果
				result = sendVerifyEmail(email, randomVCode);
			}
			else{//更新验证邮箱失败，返回错误信息
				result = new Result(false, "更新验证邮箱失败");
			}
		}
		else {//验证邮箱对象不存在，返回错误信息
			result = new Result(false, "邮箱未被注册");
		}
		
		return result;
	}

	
	/**登录
	 * 
	 * @param mid 会员编号
	 * @param mpasswd 会员密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误、账户不存在等）
	 */
	@Override
	public Result login(int mid, String mpasswd) {

		Result result;
		
		//查询会员信息
		MemberBean member = memberDao.showMemberDetail(mid);
		if(member != null) {//会员对象存在
			String truePasswd = member.getMpasswd(); //真正的密码
			if(mpasswd.equals(truePasswd)) {//密码正确，返回成功结果
				result = new Result(true);
			}
			else {//密码错误，返回错误信息
				result = new Result(false, "密码错误");
			}
		}
		else {//会员对象不存在，返回错误信息
			result = new Result(false, "会员编号不存在");
		}
		
		return result;
	}

	
	/**重置密码
	 * 
	 * @param mid 会员编号
	 * @param newPasswd 新的密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result resetPassword(int mid, String newPasswd) {

		Result result;
		
		//查询会员信息
		MemberBean member = memberDao.showMemberDetail(mid);
		if(member != null) {//会员对象存在。更新会员
			member.setMpasswd(newPasswd);
			
			int updateMemberResult = memberDao.updateMember(member);
			if(updateMemberResult > 0) {//更新会员成功
				result = new Result(true);
			}
			else {//更新会员失败，返回错误信息
				result = new Result(false, "更新会员信息失败");
			}
		}
		else {//会员对象不存在，返回错误信息
			result = new Result(false, "会员编号不存在");
		}
		
		return result;
	}
	
	
	/**设置个人信息
	 * 
	 * @param mid 会员编号
	 * @param mname 会员名
	 * @param sex 性别：M表示男，F表示女
	 * @param birthDate 出生日期
	 * @return 设置结果:Result的result属性若为true，表示设置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示设置失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result setInfo(int mid, String mname, char sex, Date birthDate) {
		
		Result result;
		
		//查询会员信息
		MemberBean member = memberDao.showMemberDetail(mid);
		if(member != null) {//会员对象存在。更新会员
			member.setMname(mname);
			member.setSex(sex);
			member.setBirthDate(birthDate);
			
			int updateMemberResult = memberDao.updateMember(member);
			if(updateMemberResult > 0) {//更新会员成功
				result = new Result(true);
			}
			else {//更新会员失败，返回错误信息
				result = new Result(false, "更新会员信息失败");
			}
		}
		else {//会员对象不存在，返回错误信息
			result = new Result(false, "会员编号不存在");
		}
		
		return result;
	}
	
	
	/**显示会员信息
	 * 
	 * @param mid 会员编号
	 * @return 会员对象:若会员编号不存在，则返回null
	 */
	@Override
	public MemberBean showMemberDetail(int mid) {
		
		return memberDao.showMemberDetail(mid);
		
	}

	
	/**绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	 * @param acntName 账户名称
	 * @param acntPasswd 账户密码
	 * @return 绑定结果:Result的result属性若为true，表示绑定成功，description属性无意义；
	 * 				 Result的result属性若为false，表示绑定失败，description属性表示失败原因（支付账户不存在、密码错误等）
	 */
	@Override
	public Result bindPayAccount(int mid, char category, String acntName, String acntPasswd) {
		
		Result result;
		
		//查询支付账户信息
		PayAccountBean payAccount = payAccountService.showPayAcntDetail(category, acntName);
		if(payAccount != null) {//支付账户对象存在
			String trueAcntPasswd = payAccount.getAcntPasswd(); //真正的支付密码
			if(acntPasswd.equals(trueAcntPasswd)) {//支付密码正确，进行绑定
				int addMemberPayResult = memberPayDao.addMemberPay(mid, category, acntName);
				if(addMemberPayResult > 0) {//添加会员支付账户成功，返回成功结果
					result = new Result(true);
				}
				else {//添加会员支付账户失败，返回错误信息
					result = new Result(false, "添加会员支付账户失败");
				}
			}
			else {//支付账户密码错误，返回错误信息
				result = new Result(false, "支付账户密码错误");
			}
		}
		else {//支付账户对象不存在，返回错误信息
			result = new Result(false, "支付账户不存在");
		}
		
		return result;
	}
	
	
	/**解绑支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型:B表示银行卡，W表示微信账户，Z表示支付宝账户
	 * @param acntName 账户名称
	 * @return 解绑结果:Result的result属性若为true，表示解绑成功，description属性无意义；
	 * 				 Result的result属性若为false，表示解绑失败，description属性表示失败原因（支付账户不存在等）
	 */
	@Override
	public Result unbindPayAccount(int mid, char category, String acntName) {

		Result result;
		
		//删除会员支付账户
		int deleteMemberPayResult = memberPayDao.deleteMemberPay(mid, category, acntName);
		if(deleteMemberPayResult > 0) {//删除会员支付账户成功，返回成功结果
			result = new Result(true);
		}
		else {//删除会员支付账户失败，返回错误信息
			result = new Result(false, "删除会员支付账户失败");
		}
		
		return result;
	}

	
	/**显示会员绑定支付账户
	 * 
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @return 会员绑定支付账户对象
	 */
	@Override
	public MemberPayBean showMemberPay(int mid, char category, String acntName) {
		
		return memberPayDao.showMemberPay(mid, category, acntName);
		
	}
	
	
	/**显示某会员的全部支付账户
	 * 
	 * @param mid 会员编号
	 * @return 该会员的全部支付账户对象列表
	 */
	@Override
	public List<MemberPayBean> showMyPayAccounts(int mid) {
		
		return memberPayDao.showAllMemberPays(mid);
		
	}
	

	/**显示所有的会员对象
	 * 
	 * @return 所有的会员对象列表
	 */
	@Override
	public List<MemberBean> showAllMembers() {
		
		return memberDao.showAllMembers();
		
	}
	
	
	/**发送验证邮件
	 * 
	 * @param email 电子邮箱
	 * @param vCode 验证码
	 * @return 发送邮件的结果：Result的result属性若为true，表示发送成功，description属性无意义；
	 * 				 	 Result的result属性若为false，表示发送失败，description属性表示失败原因（邮箱不存在、网络不通等）
	 */
	private Result sendVerifyEmail(String email, String vCode) {
		String content = "恭喜您注册Tickets， 您的验证码是："+vCode+"。 请及时在网站上输入验证码以激活邮箱。";
		Result result = utilityService.sendEmail(email, content);
		return result;
	}
	
}
