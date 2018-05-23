package service;

import java.util.List;

import po.VenueAccountBean;
import po.VenueApplicantBean;
import po.VenueApplicationBean;
import po.VenueBean;
import po.VenueUpdateBean;
import util.Result;

/**
 * 场馆服务接口
 **/

public interface VenueService {

	/**申请者注册
	 * 
	 * @param telephone 联系电话
	 * @param vaerPasswd 预设密码
	 * @return 注册结果:Result的result属性若为true，表示注册成功，description属性表示新的申请者编号；
	 * 				 Result的result属性若为false，表示注册失败，description属性表示失败原因（联系方式已存在等）
	 */
	public Result register(String telephone, String vaerPasswd);
	
	
	/**申请者登录
	 * 
	 * @param vaerid 申请者编号
	 * @param vaerPasswd 申请者密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误等）
	 */
	public Result applicantLogin(int vaerid, String vaerPasswd);


	/**显示申请者信息
	 * 
	 * @param vaerid 申请者编号
	 * @return 申请者对象:若申请者编号不存在，则返回null
	 */
	public VenueApplicantBean showApplicantDetail(int vaerid);
	
	
	/**重置申请者密码
	 * 
	 * @param vaerid 场馆编号
	 * @param newPasswd 新密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（场馆编号不存在等）
	 */
	public Result resetApplicantPassword(int vaerid, String newPasswd);
	
	
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
	 * @return 提交结果:Result的result属性若为true，表示提交成功，description属性（若有）表示提交成功后的场馆申请编号；
	 * 				 Result的result属性若为false，表示提交失败，description属性表示失败原因（属性值不合理等）
	 */
	public Result applyVenue(int vaerid, String vname, String vpasswd, String province, String city, String area, String address,
						int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum);
	
	
	/**审核场馆申请
	 * 
	 * @param vaid 场馆申请编号
	 * @param isPass 是否通过审核
	 * @return 审核结果:Result的result属性若为true，表示审核成功，如果审核通过，description属性表示新的场馆编号；
	 * 				 Result的result属性若为false，表示审核失败，description属性表示失败原因（场馆申请编号不存在等）
	 */
	public Result checkApplication(int vaid, boolean isPass);
	
	
	/**场馆登录
	 * 
	 * @param vid 场馆编号
	 * @param vpasswd 场馆密码
	 * @return 登录结果:Result的result属性若为true，表示登录成功，description属性无意义；
	 * 				 Result的result属性若为false，表示登录失败，description属性表示失败原因（密码错误等）
	 */
	public Result venueLogin(int vid, String vpasswd);
	
	
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
	public Result updateVenue(int vid, String vname, String province, String city, String area, String address,
						 int seatVipRowNum, int seatVipColNum, int seatARowNum, int seatAColNum, int seatBRowNum, 
						 int seatBColNum, int seatCRowNum, int seatCColNum, int seatDRowNum,int seatDColNum);

	
	/**审核场馆更新
	 * 
	 * @param vuid 场馆更新编号
	 * @param isPass 是否通过审核
	 * @return 审核结果:Result的result属性若为true，表示审核成功，description属性无意义；
	 * 				 Result的result属性若为false，表示审核失败，description属性表示失败原因（场馆更新编号不存在等）
	 */
	public Result checkUpdate(int vuid, boolean isPass);
	
	
	/**重置场馆密码
	 * 
	 * @param vid 场馆编号
	 * @param newPasswd 新密码
	 * @return 重置结果:Result的result属性若为true，表示重置成功，description属性无意义；
	 * 				 Result的result属性若为false，表示重置失败，description属性表示失败原因（场馆编号不存在等）
	 */
	public Result resetVenuePassword(int vid, String newPasswd);
	

	/**显示场馆信息
	 * 
	 * @param vid 场馆编号
	 * @return 场馆对象:若场馆编号不存在，则返回null
	 */
	public VenueBean showVenueDetail(int vid);
	
	
	/**显示场馆账户信息
	 * 
	 * @param vid 场馆编号
	 * @return 场馆账户对象:若场馆编号不存在，则返回null
	 */
	public VenueAccountBean showVenueAccountDetail(int vid);
	
	
	/**显示所有场馆
	 * 
	 * @return 所有场馆对象列表
	 */
	public List<VenueBean> showAllVenues();
	
	
	/**显示场馆申请信息
	 * 
	 * @param vaid 场馆申请编号
	 * @return 场馆申请对象:若场馆申请编号不存在，则返回null
	 */
	public VenueApplicationBean showVenueApplicationDetail(int vaid);

	
	/**显示所有的场馆申请
	 * 
	 * @return 所有场馆申请对象列表
	 */
	public List<VenueApplicationBean> showAllVenueApplications();
	
	
	/**显示所有未审批的场馆申请
	 * 
	 * @return 所有未审批的场馆申请对象列表
	 */
	public List<VenueApplicationBean> showAllUncheckedVenueApplications();
	
	
	/**显示某申请者所有的场馆申请
	 * 
	 * @Param vaerid 申请者编号
	 * @return 该申请者的所有场馆申请对象列表
	 */
	public List<VenueApplicationBean> showAllVenueApplicationsOfAnApplicant(int vaerid);
	
	
	/**显示场馆更新信息
	 * 
	 * @param vuid 场馆更新编号
	 * @return 场馆更新对象:若场馆更新编号不存在，则返回null
	 */
	public VenueUpdateBean showVenueUpdateDetail(int vuid);

	
	/**显示所有的场馆更新
	 * 
	 * @return 所有场馆更新对象列表
	 */
	public List<VenueUpdateBean> showAllVenueUpdates();
	
	
	/**显示所有未审批的场馆更新
	 * 
	 * @return 所有未审批的场馆更新对象列表
	 */
	public List<VenueUpdateBean> showAllUncheckedVenueUpdates();
	
	
	/**更新场馆余额
	 * 
	 * @param vid 场馆编号
	 * @param income 收入
	 */
	public Result updateVenueBalance(int vid, double income);
	
}
