package service.impl;

import java.util.List;

import dao.VenueAccountDao;
import dao.VenueApplicantDao;
import dao.VenueApplicationDao;
import dao.VenueDao;
import dao.VenueUpdateDao;
import po.VenueAccountBean;
import po.VenueApplicantBean;
import po.VenueApplicationBean;
import po.VenueBean;
import po.VenueUpdateBean;
import service.VenueService;
import util.Result;

/**
 * 场馆服务实现
 **/

public class VenueServiceImpl implements VenueService {

	private VenueDao venueDao;
	private VenueAccountDao venueAccountDao;
	private VenueApplicantDao venueApplicantDao;
	private VenueApplicationDao venueApplicationDao;
	private VenueUpdateDao venueUpdateDao;
	
	public VenueDao getVenueDao() {
		return venueDao;
	}

	public void setVenueDao(VenueDao venueDao) {
		this.venueDao = venueDao;
	}
	
	public VenueAccountDao getVenueAccountDao() {
		return venueAccountDao;
	}

	public void setVenueAccountDao(VenueAccountDao venueAccountDao) {
		this.venueAccountDao = venueAccountDao;
	}

	public VenueApplicantDao getVenueApplicantDao() {
		return venueApplicantDao;
	}

	public void setVenueApplicantDao(VenueApplicantDao venueApplicantDao) {
		this.venueApplicantDao = venueApplicantDao;
	}

	public VenueApplicationDao getVenueApplicationDao() {
		return venueApplicationDao;
	}

	public void setVenueApplicationDao(VenueApplicationDao venueApplicationDao) {
		this.venueApplicationDao = venueApplicationDao;
	}

	public VenueUpdateDao getVenueUpdateDao() {
		return venueUpdateDao;
	}

	public void setVenueUpdateDao(VenueUpdateDao venueUpdateDao) {
		this.venueUpdateDao = venueUpdateDao;
	}
	
	
	/**申请者注册
	 * 
	 * @param telephone 联系电话
	 * @param vaerPasswd 预设密码
	 * @return 注册结果:Result的result属性若为true，表示注册成功，description属性表示新的申请者编号；
	 * 				 Result的result属性若为false，表示注册失败，description属性表示失败原因（联系方式已存在等）
	 */
	@Override
	public Result register(String telephone, String vaerPasswd) {

		Result result;
		
		//添加场馆申请者
		int addApplicantResult = venueApplicantDao.addVenueApplicant(telephone, vaerPasswd);
		if(addApplicantResult > 0) {//添加场馆申请者成功，返回成功结果+场馆申请者编号 
			result = new Result(true, addApplicantResult+"");
		}
		else {//添加场馆申请者失败，返回错误信息
			result = new Result(false, "添加场馆申请者失败");
		}
		
		return result;
	}
	
	
	/**申请者登录
	 * 
	 * @param vaerid 申请者编号
	 * @param vaerPasswd 申请者密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误等）
	 */
	@Override
	public Result applicantLogin(int vaerid, String vaerPasswd) {

		Result result;

		//查询场馆申请者信息
		VenueApplicantBean venueApplicant = venueApplicantDao.showVenueApplicantDetail(vaerid);
		if(venueApplicant != null) {//场馆申请者对象存在
			String truePasswd = venueApplicant.getVaerPasswd(); //真正的密码
			if(vaerPasswd.equals(truePasswd)) {//密码正确，返回成功结果
				result = new Result(true);
			}
			else {//密码错误，返回错误信息
				result = new Result(false, "密码错误");
			}
		}
		else {//场馆申请者对象不存在，返回错误信息
			result = new Result(false, "申请者编号不存在");
		}	
		
		return result;
	}
	
	
	/**显示申请者信息
	 * 
	 * @param vaerid 申请者编号
	 * @return 申请者对象:若申请者编号不存在，则返回null
	 */
	@Override
	public VenueApplicantBean showApplicantDetail(int vaerid) {
		
		return venueApplicantDao.showVenueApplicantDetail(vaerid);
		
	}
	
	
	/**重置申请者密码
	 * 
	 * @param vaerid 场馆编号
	 * @param newPasswd 新密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（场馆编号不存在等）
	 */
	@Override
	public Result resetApplicantPassword(int vaerid, String newPasswd) {

		Result result;
		
		//查询申请者信息
		VenueApplicantBean venueApplicant = venueApplicantDao.showVenueApplicantDetail(vaerid);
		if(venueApplicant != null) {//场馆对象存在，更新场馆
			venueApplicant.setVaerPasswd(newPasswd);
			
			int updateApplicantResult = venueApplicantDao.updateVenueApplicant(venueApplicant);
			if(updateApplicantResult > 0) {//更新申请者成功，返回成功信息
				result = new Result(true);
			}
			else {//更新申请者失败，返回错误信息
				result = new Result(false, "更新申请者失败");
			}
		}
		else {//申请者对象不存在，返回错误信息
			result = new Result(false, "申请者编号不存在");
		}
		
		return result;
	}
	
	
	/**提交场馆申请
	 * 
	 * @param vaerid 场馆申请者编号
	 * @param vname 场馆名称
	 * @param vpasswd 场馆账号密码
	 * @param province 场馆所在省
	 * @param city 场馆所在市
	 * @param area 场馆所在区
	 * @param address 场馆地址
	 * @param seatVipRowNum VIP类座位排数
	 * @param seatVipColNum VIP类座位列数
	 * @param seatARowNum A类座位排数
	 * @param seatAColNum A类座位列数
	 * @param seatBRowNum B类座位排数
	 * @param seatBColNum B类座位列数
	 * @param seatCRowNum C类座位排数
	 * @param seatCColNum C类座位列数
	 * @param seatDRowNum D类座位排数
	 * @param seatDColNum D类座位列数
	 * @return 提交结果:Result的result属性若为true，表示提交成功，description属性表示提交成功后的场馆申请编号；
	 * 				 Result的result属性若为false，表示提交失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result applyVenue(int vaerid, String vname, String vpasswd, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum) {
		
		Result result;
		
		//添加场馆申请
		int addApplicationResult = venueApplicationDao.addVenueApplication(vaerid, vname, vpasswd, province, city, area, address, 
													   					   seatVipRowNum, seatVipColNum, seatARowNum, seatAColNum, seatBRowNum,
													   					   seatBColNum, seatCRowNum, seatCColNum, seatDRowNum, seatDColNum);
		if(addApplicationResult > 0) {//添加场馆申请成功，返回成功结果+场馆申请编号 
			result = new Result(true, addApplicationResult+"");
		}
		else {//添加场馆申请失败，返回错误信息
			result = new Result(false, "添加场馆申请失败");
		}
		
		return result;
	}
	
	
	/**审核场馆申请
	 * 
	 * @param vaid 场馆申请编号
	 * @param isPass 是否通过审核
	 * @return 审核结果:Result的result属性若为true，表示审核成功，如果审核通过，description属性（若有）表示新的场馆编号；
	 * 				 Result的result属性若为false，表示审核失败，description属性表示失败原因（场馆申请编号不存在等）
	 */
	@Override
	public Result checkApplication(int vaid, boolean isPass) {
		
		Result result;
		
		//查询场馆申请信息
		VenueApplicationBean venueApplication = venueApplicationDao.showVenueApplicationDetail(vaid);
		if(venueApplication != null) {//场馆申请对象存在
			
			boolean addResult = true;//初始化不同条件下需要用到的变量
			
			if(isPass == true) {
				venueApplication.setVaState('A');//审核通过，审核状态为'A'
				
				//添加场馆
				int addVenueResult = venueDao.addVenue(venueApplication.getVname(), venueApplication.getVpasswd(), venueApplication.getProvince(), venueApplication.getCity(), 
													   venueApplication.getArea(), venueApplication.getAddress(), venueApplication.getSeatVipRowNum(), venueApplication.getSeatVipColNum(), 
													   venueApplication.getSeatARowNum(), venueApplication.getSeatAColNum(), venueApplication.getSeatBRowNum(), venueApplication.getSeatBColNum(), 
													   venueApplication.getSeatCRowNum(), venueApplication.getSeatCColNum(), venueApplication.getSeatDRowNum(), venueApplication.getSeatDColNum());
				if(addVenueResult > 0) {//添加场馆成功，添加该场馆对应的场馆账户
					int addVenueAccountResult = venueAccountDao.addVenueAccount(addVenueResult);
					if(addVenueAccountResult > 0) {//添加场馆账户成功，返回成功信息+场馆编号
						//将新的场馆编号更新到场馆申请中
						venueApplication.setVid(addVenueResult);
						result = new Result(true, addVenueResult+"");
					}
					else {//添加场馆账户失败，返回错误信息
						addResult = false;
						result = new Result(false, "添加场馆账户失败");
					}
				}		
				else {//添加场馆失败，返回错误信息
					addResult = false;
					result = new Result(false, "添加场馆失败");
				}
			}
			else {
				venueApplication.setVaState('R');//审核不通过，审核状态为'R'
				result = new Result(true);
			}
			
			if(addResult == true) {//不同条件下的前期结果未出错，更新场馆申请
				int updateApplicationResult = venueApplicationDao.updateVenueApplication(venueApplication);
				if(updateApplicationResult <= 0) {//更新场馆申请失败，返回错误信息
					result = new Result(false, "更新场馆申请");
				}
			}
		}
		else {//场馆申请对象不存在，返回错误信息
			result = new Result(false, "场馆申请编号不存在");
		}
	
		return result;
	}
	
	
	/**登录
	 * 
	 * @param vid 场馆编号
	 * @param vpasswd 场馆密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误等）
	 */
	@Override
	public Result venueLogin(int vid, String vpasswd) {
		
		Result result;
		
		//查询场馆信息
		VenueBean venue = venueDao.showVenueDetail(vid);
		if(venue != null) {//场馆对象存在
			String truePasswd = venue.getVpasswd(); //真正的密码
			if(vpasswd.equals(truePasswd)) {//密码正确，返回成功结果
				result = new Result(true);
			}
			else {//密码错误，返回错误信息
				result = new Result(false, "密码错误");
			}
		}
		else {//场馆对象不存在，返回错误信息
			result = new Result(false, "场馆编号不存在");
		}	
		
		return result;
	}
	
	
	/**提交场馆更新
	 * 
	 * @param vid 场馆编号
	 * @param vname 场馆名称
	 * @param province 场馆所在省
	 * @param city 场馆所在市
	 * @param area 场馆所在区
	 * @param address 场馆地址
	 * @param seatVipRowNum VIP类座位排数
	 * @param seatVipColNum VIP类座位列数
	 * @param seatARowNum A类座位排数
	 * @param seatAColNum A类座位列数
	 * @param seatBRowNum B类座位排数
	 * @param seatBColNum B类座位列数
	 * @param seatCRowNum C类座位排数
	 * @param seatCColNum C类座位列数
	 * @param seatDRowNum D类座位排数
	 * @param seatDColNum D类座位列数
	 * @return 提交结果:Result的result属性若为true，表示提交成功，description属性表示提交成功后的场馆更新编号；
	 * 				 Result的result属性若为false，表示提交失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result updateVenue(int vid, String vname, String province, String city, String area, String address,
						 int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						 int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum) {

		Result result;
		
		//查询场馆信息
		VenueBean venue = venueDao.showVenueDetail(vid);
		if(venue != null) {//场馆对象存在，添加场馆更新
			int addUpdateResult = venueUpdateDao.addVenueUpdate(vid, vname, province, city, area, address, 
													   		    seatVipRowNum, seatVipColNum, seatARowNum, seatAColNum, seatBRowNum,
													   			seatBColNum, seatCRowNum, seatCColNum, seatDRowNum, seatDColNum);
		
			if(addUpdateResult > 0) {//添加场馆更新成功，返回成功结果+场馆更新编号 
				result = new Result(true, addUpdateResult+"");
			}
			else {//添加场馆更新失败，返回错误信息
				result = new Result(false, "添加场馆更新失败");
			}
		}
		else {//场馆对象不存在，返回错误信息
			result = new Result(false, "场馆编号不存在");
		}	
		
		return result;
	}

	
	/**审核场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @param isPass 是否通过审核
	 * @return 审核结果:Result的result属性若为true，表示审核成功，description属性无意义；
	 * 				 Result的result属性若为false，表示审核失败，description属性表示失败原因（场馆更新编号不存在等）
	 */
	@Override
	public Result checkUpdate(int vuid, boolean isPass) {

		Result result;
		
		//查询场馆更新信息
		VenueUpdateBean venueUpdate = venueUpdateDao.showVenueUpdateDetail(vuid);
		if(venueUpdate != null) {//场馆更新对象不存在，查询对应的场馆信息
			VenueBean venue = venueDao.showVenueDetail(venueUpdate.getVid());
			if(venue != null) {//场馆对象存在
				if(isPass == true) {
					venueUpdate.setVuState('A');//审核通过，审核状态为'A'
				}
				else {
					venueUpdate.setVuState('R');//审核不通过，审核状态为'R'
				}
				
				//更新场馆更新
				int updateUpdateResult = venueUpdateDao.updateVenueUpdate(venueUpdate);
				if(updateUpdateResult > 0) {//更新场馆更新成功，更新对应的场馆
					venue.setVname(venueUpdate.getVname());
					venue.setProvince(venueUpdate.getProvince());
					venue.setCity(venueUpdate.getCity());
					venue.setArea(venueUpdate.getArea());
					venue.setAddress(venueUpdate.getAddress());
					venue.setSeatVipRowNum(venueUpdate.getSeatVipRowNum());
					venue.setSeatVipColNum(venueUpdate.getSeatVipColNum());
					venue.setSeatARowNum(venueUpdate.getSeatARowNum());
					venue.setSeatAColNum(venueUpdate.getSeatAColNum());
					venue.setSeatBRowNum(venueUpdate.getSeatBRowNum());
					venue.setSeatBColNum(venueUpdate.getSeatBColNum());
					venue.setSeatCRowNum(venueUpdate.getSeatCRowNum());
					venue.setSeatCColNum(venueUpdate.getSeatCColNum());
					venue.setSeatDRowNum(venueUpdate.getSeatDRowNum());
					venue.setSeatDColNum(venueUpdate.getSeatDColNum());
					
					int updateVenueResult = venueDao.updateVenue(venue);
					if(updateVenueResult > 0) {//更新场馆成功，返回成功信息
						result = new Result(true);
					}
					else {//更新场馆失败，返回错误信息
						result = new Result(false, "更新场馆失败");
					}
				}
				else {//更新场馆更新失败，返回错误信息
					result = new Result(false, "审核更新失败");
				}
			}
			else {//场馆对象不存在，返回错误信息
				result = new Result(false, "场馆编号不存在");
			}
		}
		else {//场馆申请对象不存在，返回错误信息
			result = new Result(false, "场馆申请编号不存在");
		}
	
		return result;
	}
	
	
	/**重置场馆密码
	 * 
	 * @param vid 场馆编号
	 * @param newPasswd 新密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（场馆编号不存在等）
	 */
	@Override
	public Result resetVenuePassword(int vid, String newPasswd) {

		Result result;
		
		//查询场馆信息
		VenueBean venue = venueDao.showVenueDetail(vid);
		if(venue != null) {//场馆对象存在，更新场馆
			venue.setVpasswd(newPasswd);
			
			int updateVenueResult = venueDao.updateVenue(venue);
			if(updateVenueResult > 0) {//更新场馆成功，返回成功信息
				result = new Result(true);
			}
			else {//更新场馆失败，返回错误信息
				result = new Result(false, "更新场馆失败");
			}
		}
		else {//场馆对象不存在，返回错误信息
			result = new Result(false, "场馆编号不存在");
		}
		
		return result;
	}
	

	/**显示场馆信息
	 * 
	 * @param vid 场馆编号
	 * @return 场馆对象:若场馆编号不存在，则返回null
	 */
	@Override
	public VenueBean showVenueDetail(int vid) {
		
		return venueDao.showVenueDetail(vid);
	
	}
	
	
	/**显示场馆账户信息
	 * 
	 * @param vid 场馆编号
	 * @return 场馆账户对象:若场馆编号不存在，则返回null
	 */
	@Override
	public VenueAccountBean showVenueAccountDetail(int vid) {
		
		return venueAccountDao.showVenueAccountDetail(vid);
	
	}
	
	
	/**显示所有场馆
	 * 
	 * @return 所有场馆对象列表
	 */
	@Override
	public List<VenueBean> showAllVenues() {
		
		return venueDao.showAllVenues();
		
	}
	
	
	/**显示场馆申请信息
	 * 
	 * @param vaid 场馆申请编号
	 * @return 场馆申请对象:若场馆申请编号不存在，则返回null
	 */
	@Override
	public VenueApplicationBean showVenueApplicationDetail(int vaid) {
		
		return venueApplicationDao.showVenueApplicationDetail(vaid);
		
	}

	
	/**显示所有的场馆申请
	 * 
	 * @return 所有场馆申请对象列表
	 */
	@Override
	public List<VenueApplicationBean> showAllVenueApplications() {
		
		return venueApplicationDao.showAllVenueApplications();
		
	}
	
	
	/**显示所有未审批的场馆申请
	 * 
	 * @return 所有未审批的场馆申请对象列表
	 */
	@Override
	public List<VenueApplicationBean> showAllUncheckedVenueApplications() {
		
		return venueApplicationDao.showAllUncheckedVenueApplications();
		
	}

	/**显示某申请者所有的场馆申请
	 * 
	 * @Param vaerid 申请者编号
	 * @return 该申请者的所有场馆申请对象列表
	 */
	@Override
	public List<VenueApplicationBean> showAllVenueApplicationsOfAnApplicant(int vaerid) {
		
		return venueApplicationDao.showAllVenueApplicationsOfAnApplicant(vaerid);
		
	}
	
	
	/**显示场馆更新信息
	 * 
	 * @param vuid 场馆更新编号
	 * @return 场馆更新对象:若场馆更新编号不存在，则返回null
	 */
	@Override
	public VenueUpdateBean showVenueUpdateDetail(int vuid) {
		
		return venueUpdateDao.showVenueUpdateDetail(vuid);
		
	}

	
	/**显示所有的场馆更新
	 * 
	 * @return 所有场馆更新对象列表
	 */
	@Override
	public List<VenueUpdateBean> showAllVenueUpdates() {
		
		return venueUpdateDao.showAllVenueUpdates();
		
	}
	
	
	/**显示所有未审批的场馆更新
	 * 
	 * @return 所有未审批的场馆更新对象列表
	 */
	@Override
	public List<VenueUpdateBean> showAllUncheckedVenueUpdates() {
		
		return venueUpdateDao.showAllUncheckedVenueUpdates();
		
	}
	
	
	/**更新场馆余额
	 * 
	 * @param vid 场馆编号
	 * @param income 收入
	 */
	@Override
	public Result updateVenueBalance(int vid, double income) {
		
		Result result;
		
		//查询场馆账户信息
		VenueAccountBean venueAccount = venueAccountDao.showVenueAccountDetail(vid);
		if(venueAccount != null) {//场馆账户对象存在，更新场馆账户
			venueAccount.setBalance(venueAccount.getBalance() + income);
			
			int updateVenueAcntResult = venueAccountDao.updateVenueAccount(venueAccount);
			if(updateVenueAcntResult > 0) {//更新场馆账户成功，返回成功信息
				result = new Result(true);
			}
			else {//更新场馆账户失败，返回错误信息
				result = new Result(false, "更新场馆账户失败");
			}
		}
		else {//场馆账户对象不存在，返回错误信息
			result = new Result(false, "场馆编号不存在");
		}
		
		return result;
	}
	
}
