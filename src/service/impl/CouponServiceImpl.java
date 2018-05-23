package service.impl;

import java.util.List;

import dao.CouponDao;
import po.CouponBean;
import po.MemberBean;
import service.CouponService;
import service.MemberService;
import util.Result;

/**
 * 优惠券服务实现
 **/

public class CouponServiceImpl implements CouponService {

	private MemberService memberService;

	private CouponDao couponDao;
	
	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public CouponDao getCouponDao() {
		return couponDao;
	}
	
	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}
	
	
	/**兑换优惠券
	 * 
	 * @param mid 会员编号
	 * @param cpnMoney 优惠券金额
	 * @return 兑换结果:Result的result属性若为true，表示兑换成功，description属性表示兑换而来的优惠券编号；
	 * 				 Result的result属性若为false，表示兑换失败，description属性表示失败原因（会员编号不存在等）
	 */
	@Override
	public Result redeemCoupon(int mid, int cpnMoney) {
		
		Result result;
		
		//查询该会员编号对应的会员是否存在
		MemberBean member = memberService.showMemberDetail(mid);
		if(member != null) {//会员对象存在，添加优惠券
			int addCouponResult = couponDao.addCoupon(mid, cpnMoney);
			if(addCouponResult > 0) {//添加优惠券成功，返回成功结果+优惠券编号
				result = new Result(true, addCouponResult+"");
			}
			else {//添加优惠券失败，返回错误信息
				result = new Result(false, "添加优惠券失败");
			}
		}
		else {//会员对象不存在，无法兑换优惠券，返回错误信息
			result = new Result(false, "会员编号不存在");
		}
		return result;
	}
	
	
	/**使用优惠券（优惠券状态从可用(A)变为不可用(U)）
	 * 
	 * @param cpnId 优惠券编号
	 * @return 使用结果:Result的result属性若为true，表示使用成功，description属性无意义；
	 * 				 Result的result属性若为false，表示使用失败，description属性表示失败原因（优惠券编号不存在等）
	 */
	@Override
	public Result useCoupon(int cpnId) {

		Result result;
		
		//查询优惠券信息
		CouponBean coupon = couponDao.showCouponDetail(cpnId);
		if(coupon != null) {//优惠券对象存在，更新优惠券
			coupon.setCpnState('U');
			
			int updateCouponResult = couponDao.updateCoupon(coupon);
			if(updateCouponResult > 0) {//更新优惠券成功，返回成功结果
				result = new Result(true);
			}
			else {//更新优惠券失败，返回错误信息
				result = new Result(false, "更新优惠券失败");
			}
		}
		else {//优惠券对象不存在，无法使用优惠券，返回错误信息
			result = new Result(false, "优惠券编号不存在");
		}
		return result;
		
	}
	
	
	/**显示优惠券信息
	 * 
	 * @param cpnId 优惠券编号
	 * @return 优惠券对象:若优惠券编号不存在，则返回null
	 */
	@Override
	public CouponBean showCouponDetail(int cpnId) {
		
		return couponDao.showCouponDetail(cpnId);
		
	}
	
	
	/**显示某用户的所有可用优惠券
	 * 
	 * @param mid 会员编号
	 * @return 该用户的所有可用优惠券对象列表
	 */
	@Override
	public List<CouponBean> showAllAvaiCoupons(int mid) {
		
		return couponDao.showAllAvailableCoupons(mid);
		
	}
	
}
