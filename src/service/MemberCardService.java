package service;

import po.MemberCardBean;
import po.MemberRuleBean;
import util.Result;

/**
 * 会员卡服务接口
 **/

public interface MemberCardService {

	/**办理会员卡
	 * 
	 * @param mid 会员编号
	 * @return 办理结果:Result的result属性若为true，表示办理成功，description属性无意义；
	 * 				 Result的result属性若为false，表示办理失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result signUpCard(int mid);
	
	
	/**停用、取消会员卡
	 * 
	 * @param mid 会员编号
	 * @return 取消结果:Result的result属性若为true，表示取消成功，description属性无意义；
	 * 				 Result的result属性若为false，表示取消失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result stopCard(int mid);

	
	/**更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param level 会员等级
	 * @param totalPay 总消费金额
	 * @param credit 会员积分
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result updateCard(int mid, int level, double totalPay, double credit);
	
	
	/**显示会员卡信息
	 * 
	 * @param mid 会员编号
	 * @return 会员卡对象:若会员编号不存在，则返回null
	 */
	public MemberCardBean showCardInfo(int mid);
	
	
	/**显示会员规则信息
	 * 
	 * @param level 会员等级
	 * @return 会员规则对象:若会员等级不存在，则返回null
	 */
	public MemberRuleBean showRuleInfo(int level);

	
	/**获取折扣后的实际消费金额
	 * 
	 * @param mid 会员编号
	 * @param originCostMoney 原始消费金额
	 * @return 折扣后的实际消费金额
	 */
	public double getRealCost(int mid, double originCostMoney);
	

	/**在消费之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param costMoney 消费金额
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result updateCardAfterCost(int mid, double costMoney);
	
	
	/**在退款之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param refundMoney 退款金额
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result updateCardAfterRefund(int mid, double refundMoney);

	
	/**在兑换优惠券之后更新会员卡
	 * 
	 * @param mid 会员编号
	 * @param redeemedCredit 被兑换的积分
	 * @return 更新结果:Result的result属性若为true，表示更新成功，description属性无意义；
	 * 				 Result的result属性若为false，表示更新失败，description属性表示失败原因（会员编号不存在等）
	 */
	public Result updateCardAfterRedeem(int mid, double redeemedCredit);
	
}
