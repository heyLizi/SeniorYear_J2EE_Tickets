package service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import dao.OrderDao;
import dao.OrderPayDao;
import dao.SoldSeatDao;
import po.CouponBean;
import po.MemberPayBean;
import po.OrderBean;
import po.OrderPayBean;
import po.PayAccountBean;
import po.PerformancePlanBean;
import po.SoldSeatBean;
import po.VenueBean;
import service.CouponService;
import service.MemberCardService;
import service.MemberService;
import service.OrderService;
import service.PayAccountService;
import service.PerformancePlanService;
import service.RefundService;
import service.TicketsCompanyInfoService;
import service.VenueService;
import util.Result;
import vo.SeatVO;

/**
 * 订单服务实现
 **/

public class OrderServiceImpl implements OrderService {

	private CouponService couponService;
	private MemberCardService memberCardService;
	private MemberService memberService;
	private PayAccountService payAccountService;
	private PerformancePlanService performancePlanService;
	private RefundService refundService;
	private TicketsCompanyInfoService ticketsCompanyInfoService;
	private VenueService venueService;
	
	private OrderDao orderDao;
	private OrderPayDao orderPayDao;
	private SoldSeatDao soldSeatDao;

	public CouponService getCouponService() {
		return couponService;
	}
	
	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
	
	public MemberCardService getMemberCardService() {
		return memberCardService;
	}
	
	public void setMemberCardService(MemberCardService memberCardService) {
		this.memberCardService = memberCardService;
	}

	public MemberService getMemberService() {
		return memberService;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	public PayAccountService getPayAccountService() {
		return payAccountService;
	}
	
	public void setPayAccountService(PayAccountService payAccountService) {
		this.payAccountService = payAccountService;
	}

	public PerformancePlanService getPerformancePlanService() {
		return performancePlanService;
	}
	
	public void setPerformancePlanService(PerformancePlanService performancePlanService) {
		this.performancePlanService = performancePlanService;
	}
	
	public RefundService getRefundService() {
		return refundService;
	}
	
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	public TicketsCompanyInfoService getTicketsCompanyInfoService() {
		return ticketsCompanyInfoService;
	}

	public void setTicketsCompanyInfoService(TicketsCompanyInfoService ticketsCompanyInfoService) {
		this.ticketsCompanyInfoService = ticketsCompanyInfoService;
	}
	
	public VenueService getVenueService() {
		return venueService;
	}
	
	public void setVenueService(VenueService venueService) {
		this.venueService = venueService;
	}
	
	public OrderDao getOrderDao() {
		return orderDao;
	}
	
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	public OrderPayDao getOrderPayDao() {
		return orderPayDao;
	}
	
	public void setOrderPayDao(OrderPayDao orderPayDao) {
		this.orderPayDao = orderPayDao;
	}
	
	public SoldSeatDao getSoldSeatDao() {
		return soldSeatDao;
	}
	public void setSoldSeatDao(SoldSeatDao soldSeatDao) {
		this.soldSeatDao = soldSeatDao;
	}
	
	
	/**显示某场演出某个座位类型的所剩空余座位
	 * 
	 * @param ppid 演出编号
	 * @param seatCategory 座位类型
	 * @return 该场演出该座位类型的所有空余座位对象列表
	 */
	@Override
	public List<SeatVO> showEmptySeats(int ppid, char seatCategory) {

		//得到该演出对应的场馆编号
		int vid = performancePlanService.showPerformancePlanDetail(ppid).getVid();
		
		//获取该场馆该座位类型的开始排数（VIP类座位从第1排开始，接下来依次是A类、B类、C类、D类座位，每一类座位都占据整排）
		int thisCategorySeatRowStart = 0;
		
		//获取该场馆该座位类型的座位总数
		VenueBean thisVenue = venueService.showVenueDetail(vid);
		int thisCategorySeatRowNum = 0;
		int thisCategorySeatColNum = 0;
		switch(seatCategory) {
		case 'A': {	thisCategorySeatRowStart = 1 + thisVenue.getSeatVipRowNum();
					thisCategorySeatRowNum = thisVenue.getSeatARowNum(); thisCategorySeatColNum = thisVenue.getSeatAColNum(); break;}
		case 'B': {	thisCategorySeatRowStart = 1 + thisVenue.getSeatVipRowNum() + thisVenue.getSeatARowNum();
					thisCategorySeatRowNum = thisVenue.getSeatBRowNum(); thisCategorySeatColNum = thisVenue.getSeatBColNum(); break;}
		case 'C': {	thisCategorySeatRowStart = 1 + thisVenue.getSeatVipRowNum() + thisVenue.getSeatARowNum() + thisVenue.getSeatBRowNum();
					thisCategorySeatRowNum = thisVenue.getSeatCRowNum(); thisCategorySeatColNum = thisVenue.getSeatCColNum(); break;}
		case 'D': {	thisCategorySeatRowStart = 1 + thisVenue.getSeatVipRowNum() + thisVenue.getSeatARowNum() + thisVenue.getSeatBRowNum() + thisVenue.getSeatCRowNum();
					thisCategorySeatRowNum = thisVenue.getSeatDRowNum(); thisCategorySeatColNum = thisVenue.getSeatDColNum(); break;}
		case 'V': {	thisCategorySeatRowStart = 1; 
					thisCategorySeatRowNum = thisVenue.getSeatVipRowNum(); thisCategorySeatColNum = thisVenue.getSeatVipColNum(); break;}
		
		}
		
		//初始化空座位列表
		List<SeatVO> emptySeatsList = new ArrayList<SeatVO>();
		
		//循环该座位类型的所有座位直至找到所有的空座位
		for(int i=thisCategorySeatRowStart; i<thisCategorySeatRowStart+thisCategorySeatRowNum; i++) {
			for(int j=1; j<=thisCategorySeatColNum; j++) {
				//查询该场馆该座位该演出的售出信息
				SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, i, j, ppid);
				if(soldSeat == null) {//该座位在该场演出中还未售出,添加该座位至空座位列表中
					SeatVO tempSeat = new SeatVO(i, j);
					emptySeatsList.add(tempSeat);
				}
			}			
		}
		
		return emptySeatsList;
	}
	
	
	/**选座购买演出票（会员用）
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seats 选择的座位列表
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result buyTicketsWithChoice(int mid, int ppid, char seatCategory, List<SeatVO> seats) {
		
		Result result;
		
		//初始化循环中需要用到的变量
		boolean seatHasSold = false;
		
		//得到该演出对应的场馆编号
		int vid = performancePlanService.showPerformancePlanDetail(ppid).getVid();
		
		int seatNum = seats.size();
		for(int i=0; i<seatNum; i++) {
			SeatVO tempSeat = seats.get(i);
			SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, tempSeat.getRow(), tempSeat.getCol(), ppid);
			if(soldSeat != null) {//用户选定的座位中有已售出的，修改循环变量的值，便于结果的返回
				seatHasSold = true;
			}
		}
		
		if(seatHasSold) {//如果用户选定的座位中有已售出的票，返回错误信息
			result = new Result(false, "选择的座位已售出");
		}
		else {//如果用户选定的座位中没有已售出的票，可以下订单
			
			char orderType = 'S';//订单类型为选座购买
			Timestamp orderTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
			
			//获取该座位类型下每个座位的售价
			PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);
			int perSeatPrice = 0;
			switch(seatCategory) {
				case 'A': {	perSeatPrice = performancePlan.getSeatAPrice(); break;}
				case 'B': {	perSeatPrice = performancePlan.getSeatBPrice(); break;}
				case 'C': {	perSeatPrice = performancePlan.getSeatCPrice(); break;}
				case 'D': {	perSeatPrice = performancePlan.getSeatDPrice(); break;}
				case 'V': {	perSeatPrice = performancePlan.getSeatVipPrice(); break;}
			}
			double totalMoney = seatNum * perSeatPrice;//得到订单总金额
			double realMoney = memberCardService.getRealCost(mid, totalMoney);//得到订单实际金额
			
			//添加订单
			int addOrderResult = orderDao.addOrder(mid, ppid, orderType, orderTime, seatCategory, seatNum, totalMoney, realMoney);
			if(addOrderResult > 0) {//添加订单成功，添加售出座位
				//初始化循环中需要用到的变量
				boolean seatFailed = false;
				
				for(int i=0; i<seatNum; i++) {
					SeatVO tempSeat = seats.get(i);
					int addThisSoldSeatResult = soldSeatDao.addSoldSeat(vid, tempSeat.getRow(), tempSeat.getCol(), ppid, addOrderResult, mid, seatCategory);
					if(addThisSoldSeatResult <= 0) {//添加该售出座位失败，修改循环中的变量，便于结果的返回
						seatFailed = true;
					}
				}
				
				if(!seatFailed) {//添加售出座位成功，返回成功信息+订单编号
					result = new Result(true, addOrderResult+"");
				}
				else {//添加售出座位失败，返回错误信息
					result = new Result(false, "添加售出座位失败");
				}
			}
			else {//添加订单失败，返回错误信息
				result = new Result(false, "添加订单失败");
			}
		}
		
		return result;
	}
	
	
	/**不选票购买演出票（会员用）
	 * 
	 * @param mid 会员编号
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seatNum 演出票张数
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result buyTicketsWithoutChoice(int mid, int ppid, char seatCategory, int seatNum) {

		Result result;

		//得到该演出对应的场馆编号
		int vid = performancePlanService.showPerformancePlanDetail(ppid).getVid();
		
		//获取该场馆该座位类型的座位总数
		VenueBean thisVenue = venueService.showVenueDetail(vid);
		int thisCategorySeatRowNum = 0;
		int thisCategorySeatColNum = 0;
		switch(seatCategory) {
		case 'A': {	thisCategorySeatRowNum = thisVenue.getSeatARowNum(); thisCategorySeatColNum = thisVenue.getSeatAColNum(); break;}
		case 'B': {	thisCategorySeatRowNum = thisVenue.getSeatBRowNum(); thisCategorySeatColNum = thisVenue.getSeatBColNum(); break;}
		case 'C': {	thisCategorySeatRowNum = thisVenue.getSeatCRowNum(); thisCategorySeatColNum = thisVenue.getSeatCColNum(); break;}
		case 'D': {	thisCategorySeatRowNum = thisVenue.getSeatDRowNum(); thisCategorySeatColNum = thisVenue.getSeatDColNum(); break;}
		case 'V': {	thisCategorySeatRowNum = thisVenue.getSeatVipRowNum(); thisCategorySeatColNum = thisVenue.getSeatVipColNum(); break;}
		}
		int thisCategorySeatTotalNum = thisCategorySeatRowNum * thisCategorySeatColNum;

		//获取该场馆该演出该座位类型的已售座位列表和座位总数
		List<SoldSeatBean> soldSeatList = soldSeatDao.showCategorySoldSeatsOfAPerformanceOfAVenue(vid, ppid, seatCategory);
		int thisCategorySoldSeatsNum = soldSeatList.size();
		
		if(thisCategorySoldSeatsNum + seatNum > thisCategorySeatTotalNum) {//用户想购买的票数超过了该演出该座位类型的剩余票数，返回错误信息
			result = new Result(false, "该座位类型的余票不足");
		}
		else {//用户想购买的票数没有超过该演出该座位类型的剩余票数，可以下订单
			
			//初始化这次将要售出的座位列表
			List<SeatVO> thisTimeSoldSeatsList = new ArrayList<SeatVO>();
			
			//循环该座位类型的所有座位直至找到满足购买张数的未售座位
			int thisTimeSoldNum = 0;
			for(int i=1; i<=thisCategorySeatRowNum; i++) {
				for(int j=1; j<=thisCategorySeatColNum; j++) {
					//查询该场馆该座位该演出的售出信息
					SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, i, j, ppid);
					if(soldSeat == null) {//该座位在该场演出中还未售出,添加该座位至这次将要售出的座位列表中
						SeatVO tempSeat = new SeatVO(i, j);
						thisTimeSoldSeatsList.add(tempSeat);
						thisTimeSoldNum += 1;
					}
					if(thisTimeSoldNum == seatNum) {//如果这次将要售出的座位总数=购票张数，跳出循环(注意：此时仅跳出了内层循环)
						break;
					}
				}
				if(thisTimeSoldNum == seatNum) {//如果这次将要售出的座位总数=购票张数，跳出循环(注意：此时已跳出了外层循环)
					break;
				}
			}
			
			char orderType = 'R';//订单类型为不选座购买
			Timestamp orderTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
			
			//获取该座位类型下每个座位的售价
			PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);
			int perSeatPrice = 0;
			switch(seatCategory) {
				case 'A': {	perSeatPrice = performancePlan.getSeatAPrice(); break;}
				case 'B': {	perSeatPrice = performancePlan.getSeatBPrice(); break;}
				case 'C': {	perSeatPrice = performancePlan.getSeatCPrice(); break;}
				case 'D': {	perSeatPrice = performancePlan.getSeatDPrice(); break;}
				case 'V': {	perSeatPrice = performancePlan.getSeatVipPrice(); break;}
			}
			double totalMoney = seatNum * perSeatPrice;//得到订单总金额
			double realMoney = memberCardService.getRealCost(mid, totalMoney);//得到订单实际金额
			
			//添加订单
			int addOrderResult = orderDao.addOrder(mid, ppid, orderType, orderTime, seatCategory, seatNum, totalMoney, realMoney);
			if(addOrderResult > 0) {//添加订单成功，添加售出座位
				//初始化循环中需要用到的变量
				boolean seatFailed = false;
				
				for(int i=0; i<seatNum; i++) {
					SeatVO tempSeat = thisTimeSoldSeatsList.get(i);
					int addThisSoldSeatResult = soldSeatDao.addSoldSeat(vid, tempSeat.getRow(), tempSeat.getCol(), ppid, addOrderResult, mid, seatCategory);
					if(addThisSoldSeatResult <= 0) {//添加该售出座位失败，修改循环中的变量，便于结果的返回
						seatFailed = true;
					}
				}
				
				if(!seatFailed) {//添加售出座位成功，返回成功信息+订单编号
					result = new Result(true, addOrderResult+"");
				}
				else {//添加售出座位失败，返回错误信息
					result = new Result(false, "添加售出座位失败");
				}
			}
			else {//添加订单失败，返回错误信息
				result = new Result(false, "添加订单失败");
			}
			
		}
		
		return result;
	}
	
	
	/**选座出售演出票（场馆用）
	 * 
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seats 选择的座位列表
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result sellTicketsWithChoice(int mid, int ppid, char seatCategory, List<SeatVO> seats) {

		Result result;

		//判断是否存在该会员账号
		if(mid > 0 && memberService.showMemberDetail(mid) == null) {
			result = new Result(false, "会员账号不存在");
		}
		else {
			//初始化循环中需要用到的变量
			boolean seatHasSold = false;
			
			//得到该演出对应的场馆编号
			int vid = performancePlanService.showPerformancePlanDetail(ppid).getVid();
			
			int seatNum = seats.size();
			for(int i=0; i<seatNum; i++) {
				SeatVO tempSeat = seats.get(i);
				SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, tempSeat.getRow(), tempSeat.getCol(), ppid);
				if(soldSeat != null) {//用户选定的座位中有已售出的，修改循环变量的值，便于结果的返回
					seatHasSold = true;
				}
			}
			
			if(seatHasSold) {//如果用户选定的座位中有已售出的票，返回错误信息
				result = new Result(false, "选择的座位已售出");
			}
			else {//如果用户选定的座位中没有已售出的票，可以下订单
				
				char orderType = 'S';//订单类型为选座购买
				Timestamp orderTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
				
				//获取该座位类型下每个座位的售价
				PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);
				int perSeatPrice = 0;
				switch(seatCategory) {
					case 'A': {	perSeatPrice = performancePlan.getSeatAPrice(); break;}
					case 'B': {	perSeatPrice = performancePlan.getSeatBPrice(); break;}
					case 'C': {	perSeatPrice = performancePlan.getSeatCPrice(); break;}
					case 'D': {	perSeatPrice = performancePlan.getSeatDPrice(); break;}
					case 'V': {	perSeatPrice = performancePlan.getSeatVipPrice(); break;}
				}
				double totalMoney = seatNum * perSeatPrice;//得到订单总金额
				double realMoney = totalMoney;//如果不是会员，订单实际金额则为订单总金额
				if(mid > 0) {//如果是会员，得到订单实际金额
					realMoney = memberCardService.getRealCost(mid, totalMoney);
				}
				
				//添加订单
				int addOrderResult = orderDao.addOrder(mid, ppid, orderType, orderTime, seatCategory, seatNum, totalMoney, realMoney);
				if(addOrderResult > 0) {//添加订单成功，添加售出座位
					//初始化循环中需要用到的变量
					boolean seatFailed = false;
					
					for(int i=0; i<seatNum; i++) {
						SeatVO tempSeat = seats.get(i);
						int addThisSoldSeatResult = soldSeatDao.addSoldSeat(vid, tempSeat.getRow(), tempSeat.getCol(), ppid, addOrderResult, mid, seatCategory);
						if(addThisSoldSeatResult <= 0) {//添加该售出座位失败，修改循环中的变量，便于结果的返回
							seatFailed = true;
						}
					}
					
					if(!seatFailed) {//添加售出座位成功，返回成功信息+订单编号
						result = new Result(true, addOrderResult+"");
					}
					else {//添加售出座位失败，返回错误信息
						result = new Result(false, "添加售出座位失败");
					}
				}
				else {//添加订单失败，返回错误信息
					result = new Result(false, "添加订单失败");
				}
			}
		}
		
		return result;
	}
	
	
	/**不选票出售演出票（场馆用）
	 * 
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @param ppid 演出计划编号
	 * @param seatCategory 座位类型
	 * @param seatNum 演出票张数
	 * @return 购买结果:Result的result属性若为true，表示购买成功，description属性表示购买成功后的订单号；
	 * 				 Result的result属性若为false，表示购买失败，description属性表示失败原因（属性值不合理等）
	 */
	@Override
	public Result sellTicketsWithoutChoice(int mid, int ppid, char seatCategory, int seatNum) {

		Result result;
		
		//判断是否存在该会员账号
		if(mid > 0 && memberService.showMemberDetail(mid) == null) {
			result = new Result(false, "会员账号不存在");
		}
		else {
			//得到该演出对应的场馆编号
			int vid = performancePlanService.showPerformancePlanDetail(ppid).getVid();
			
			//获取该场馆该座位类型的座位总数
			VenueBean thisVenue = venueService.showVenueDetail(vid);
			int thisCategorySeatRowNum = 0;
			int thisCategorySeatColNum = 0;
			switch(seatCategory) {
			case 'A': {	thisCategorySeatRowNum = thisVenue.getSeatARowNum(); thisCategorySeatColNum = thisVenue.getSeatAColNum(); break;}
			case 'B': {	thisCategorySeatRowNum = thisVenue.getSeatBRowNum(); thisCategorySeatColNum = thisVenue.getSeatBColNum(); break;}
			case 'C': {	thisCategorySeatRowNum = thisVenue.getSeatCRowNum(); thisCategorySeatColNum = thisVenue.getSeatCColNum(); break;}
			case 'D': {	thisCategorySeatRowNum = thisVenue.getSeatDRowNum(); thisCategorySeatColNum = thisVenue.getSeatDColNum(); break;}
			case 'V': {	thisCategorySeatRowNum = thisVenue.getSeatVipRowNum(); thisCategorySeatColNum = thisVenue.getSeatVipColNum(); break;}
			}
			int thisCategorySeatTotalNum = thisCategorySeatRowNum * thisCategorySeatColNum;
	
			//获取该场馆该演出该座位类型的已售座位列表和座位总数
			List<SoldSeatBean> soldSeatList = soldSeatDao.showCategorySoldSeatsOfAPerformanceOfAVenue(vid, ppid, seatCategory);
			int thisCategorySoldSeatsNum = soldSeatList.size();
			
			if(thisCategorySoldSeatsNum + seatNum > thisCategorySeatTotalNum) {//用户想购买的票数超过了该演出该座位类型的剩余票数，返回错误信息
				result = new Result(false, "该座位类型的余票不足");
			}
			else {//用户想购买的票数没有超过该演出该座位类型的剩余票数，可以下订单
				
				//初始化这次将要售出的座位列表
				List<SeatVO> thisTimeSoldSeatsList = new ArrayList<SeatVO>();
				
				//循环该座位类型的所有座位直至找到满足购买张数的未售座位
				int thisTimeSoldNum = 0;
				for(int i=1; i<=thisCategorySeatRowNum; i++) {
					for(int j=1; j<=thisCategorySeatColNum; j++) {
						//查询该场馆该座位该演出的售出信息
						SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, i, j, ppid);
						if(soldSeat == null) {//该座位在该场演出中还未售出,添加该座位至这次将要售出的座位列表中
							SeatVO tempSeat = new SeatVO(i, j);
							thisTimeSoldSeatsList.add(tempSeat);
							thisTimeSoldNum += 1;
						}
						if(thisTimeSoldNum == seatNum) {//如果这次将要售出的座位总数=购票张数，跳出循环(注意：此时仅跳出了内层循环)
							break;
						}
					}
					if(thisTimeSoldNum == seatNum) {//如果这次将要售出的座位总数=购票张数，跳出循环(注意：此时已跳出了外层循环)
						break;
					}
				}
				
				char orderType = 'R';//订单类型为不选座购买
				Timestamp orderTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为订单时间
				
				//获取该座位类型下每个座位的售价
				PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);
				int perSeatPrice = 0;
				switch(seatCategory) {
					case 'A': {	perSeatPrice = performancePlan.getSeatAPrice(); break;}
					case 'B': {	perSeatPrice = performancePlan.getSeatBPrice(); break;}
					case 'C': {	perSeatPrice = performancePlan.getSeatCPrice(); break;}
					case 'D': {	perSeatPrice = performancePlan.getSeatDPrice(); break;}
					case 'V': {	perSeatPrice = performancePlan.getSeatVipPrice(); break;}
				}
				double totalMoney = seatNum * perSeatPrice;//得到订单总金额
				double realMoney = totalMoney;//如果不是会员，订单实际金额则为订单总金额
				if(mid > 0) {//如果是会员，得到订单实际金额
					realMoney = memberCardService.getRealCost(mid, totalMoney);
				}
				
				//添加订单
				int addOrderResult = orderDao.addOrder(mid, ppid, orderType, orderTime, seatCategory, seatNum, totalMoney, realMoney);
				if(addOrderResult > 0) {//添加订单成功，添加售出座位
					//初始化循环中需要用到的变量
					boolean seatFailed = false;
					
					for(int i=0; i<seatNum; i++) {
						SeatVO tempSeat = thisTimeSoldSeatsList.get(i);
						int addThisSoldSeatResult = soldSeatDao.addSoldSeat(vid, tempSeat.getRow(), tempSeat.getCol(), ppid, addOrderResult, mid, seatCategory);
						if(addThisSoldSeatResult <= 0) {//添加该售出座位失败，修改循环中的变量，便于结果的返回
							seatFailed = true;
						}
					}
					
					if(!seatFailed) {//添加售出座位成功，返回成功信息+订单编号
						result = new Result(true, addOrderResult+"");
					}
					else {//添加售出座位失败，返回错误信息
						result = new Result(false, "添加售出座位失败");
					}
				}
				else {//添加订单失败，返回错误信息
					result = new Result(false, "添加订单失败");
				}
				
			}
		}
		
		return result;
	}
	
	
	/**使用优惠券来付款（会员购票时，使用支付账户付款用）
	 * 
	 * @param oid 订单编号
	 * @param cpnId 优惠券编号
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param passwd 账户密码
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	@Override
	public Result payOrderWithCoupon(int oid, int cpnId, int mid, char category, String acntName, String passwd) {
		
		Result result;
		
		//查询会员支付账户
		MemberPayBean memberPay = memberService.showMemberPay(mid, category, acntName);
		if(memberPay == null) {//会员支付账户对象不存在，返回错误信息
			result = new Result(false, "会员支付账户不存在");
		}
		else {//会员支付账户对象存在，查询支付账户
			PayAccountBean payAccount = payAccountService.showPayAcntDetail(category, acntName);
			if(payAccount == null) {//支付账户对象不存在，返回错误信息
				result = new Result(false, "支付账户不存在");
			}
			else {//支付账户对象存在，查询优惠券
				CouponBean coupon = couponService.showCouponDetail(cpnId);
				if(coupon == null) {//优惠券对象不存在，返回错误信息
					result = new Result(false, "优惠券不存在");
				}
				else {//优惠券对象存在，获取使用优惠券后的实际支付金额
					double couponMoney = coupon.getCpnMoney();
					double orderMoney = orderDao.showOrderDetail(oid).getRealMoney();
					double realPayMoney = orderMoney - couponMoney;
					
					//检查用户输入密码是否正确
					if(!passwd.equals(payAccount.getAcntPasswd())) {//用户输入密码不正确，返回错误信息
						result = new Result(false, "支付密码错误");
					}
					else {//用户输入密码正确，进行支付（更新支付账户）
						Result updatePayAccountResult = payAccountService.pay(category, acntName, realPayMoney);
						if(updatePayAccountResult.getResult() == true) {//支付（更新支付账户）成功，更新Tickets集团信息
							Result updateTicketsInfoResult = ticketsCompanyInfoService.acquireMoney(realPayMoney); 
							if(updateTicketsInfoResult.getResult() == true) {//更新Tickets集团信息成功，更新优惠券
								Result updateCouponResult = couponService.useCoupon(cpnId);
								if(updateCouponResult.getResult() == true) {//更新优惠券成功，更新会员卡
									Result updateMemberCardResult = memberCardService.updateCardAfterCost(mid, realPayMoney);
									if(updateMemberCardResult.getResult() == true) {//更新会员卡成功，添加订单支付
										Timestamp payTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为支付时间
										int addOrderPayResult = orderPayDao.addOrderPay(oid, mid, 'Y', category, acntName, payTime, realPayMoney);
										if(addOrderPayResult > 0) {//添加订单支付成功，，更新订单状态
											OrderBean order = orderDao.showOrderDetail(oid);
											order.setOrderState('P');
											
											int updateOrderResult = orderDao.updateOrder(order);
											if(updateOrderResult > 0) {//更新订单状态成功，返回成功信息
												result = new Result(true);
											}
											else {//更新订单状态失败，返回错误信息
												result = new Result(false, "更新订单状态失败");
											}
										}
										else {//添加订单支付失败，返回错误信息
											result = new Result(false, "添加订单支付失败");
										}
									}
									else {//更新会员卡失败，返回错误信息
										result = updateMemberCardResult;
									}
								}
								else {//更新优惠券失败，返回错误信息
									result = updateCouponResult;
								}
							}
							else {//更新Tickets集团信息失败，返回错误信息
								result = updateTicketsInfoResult;
							}
						}
						else {//支付（更新支付账户）失败，返回错误信息
							result = updatePayAccountResult;
						}
					}
				}
			}
		}
	
		return result;
	}
	
	
	/**不使用优惠券来付款（会员购票时，使用支付账户付款用）
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号
	 * @param category 账户类型
	 * @param acntName 账户名称
	 * @param passwd 账户密码
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	@Override
	public Result payOrderWithoutCoupon(int oid, int mid, char category, String acntName, String passwd) {

		Result result;
		
		//查询会员支付账户
		MemberPayBean memberPay = memberService.showMemberPay(mid, category, acntName);
		if(memberPay == null) {//会员支付账户对象不存在，返回错误信息
			result = new Result(false, "会员支付账户不存在");
		}
		else {//会员支付账户对象存在，查询支付账户
			PayAccountBean payAccount = payAccountService.showPayAcntDetail(category, acntName);
			if(payAccount == null) {//支付账户对象不存在，返回错误信息
				result = new Result(false, "支付账户不存在");
			}
			else {//支付账户对象存在，获取订单金额，即实际支付金额
				double realPayMoney = orderDao.showOrderDetail(oid).getRealMoney();
					
				//检查用户输入密码是否正确
				if(!passwd.equals(payAccount.getAcntPasswd())) {//用户输入密码不正确，返回错误信息
					result = new Result(false, "支付密码错误");
				}
				else {//用户输入密码正确，进行支付（更新支付账户）
					Result updatePayAccountResult = payAccountService.pay(category, acntName, realPayMoney);
					if(updatePayAccountResult.getResult() == true) {//支付（更新支付账户）成功，更新Tickets集团信息
						Result updateTicketsInfoResult = ticketsCompanyInfoService.acquireMoney(realPayMoney); 
						if(updateTicketsInfoResult.getResult() == true) {//更新Tickets集团信息成功，更新会员卡
							Result updateMemberCardResult = memberCardService.updateCardAfterCost(mid, realPayMoney);
							if(updateMemberCardResult.getResult() == true) {//更新会员卡成功，添加订单支付
								Timestamp payTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为支付时间
								int addOrderPayResult = orderPayDao.addOrderPay(oid, mid, 'Y', category, acntName, payTime, realPayMoney);
								if(addOrderPayResult > 0) {//添加订单支付成功，，更新订单状态
									OrderBean order = orderDao.showOrderDetail(oid);
									order.setOrderState('P');
									
									int updateOrderResult = orderDao.updateOrder(order);
									if(updateOrderResult > 0) {//更新订单状态成功，返回成功信息
										result = new Result(true);
									}
									else {//更新订单状态失败，返回错误信息
										result = new Result(false, "更新订单状态失败");
									}
								}
								else {//添加订单支付失败，返回错误信息
									result = new Result(false, "添加订单支付失败");
								}
							}
							else {//更新会员卡失败，返回错误信息
								result = updateMemberCardResult;
							}
						}
						else {//更新Tickets集团信息失败，返回错误信息
							result = updateTicketsInfoResult;
						}
					}
					else {//支付（更新支付账户）失败，返回错误信息
						result = updatePayAccountResult;
					}
				}
			}
		}
	
		return result;
	}
	
	
	/**使用现金付款（场馆售票时用）
	 * 
	 * @param oid 订单编号
	 * @param mid 会员编号:会员编号若为0，表示非会员
	 * @return 支付结果:Result的result属性若为true，表示支付成功，description属性无意义；
	 * 				 Result的result属性若为false，表示支付失败，description属性表示失败原因（订单编号不存在、优惠券编号不存在、会员编号不存在等）
	 */
	@Override
	public Result payOrderUseCurrency(int oid, int mid) {

		Result result;
		
		//获取订单金额，即实际支付金额
		double realPayMoney = orderDao.showOrderDetail(oid).getRealMoney();
		
		//支付（由于使用现金支付，所以不需要更新支付账户），更新Tickets集团信息
		Result updateTicketsInfoResult = ticketsCompanyInfoService.acquireMoney(realPayMoney); 
		if(updateTicketsInfoResult.getResult() == true) {//更新Tickets集团信息成功
			//检查一下是否为会员，如果是会员，更新会员卡
			Result updateMemberCardResult = new Result(true);
			if(mid > 0) {
				updateMemberCardResult = memberCardService.updateCardAfterCost(mid, realPayMoney);
			}
			if(updateMemberCardResult.getResult() == true) {//更新会员卡成功，添加订单支付
				Timestamp payTime = new java.sql.Timestamp(System.currentTimeMillis());//获取当前时间，即为支付时间
				int addOrderPayResult = orderPayDao.addOrderPay(oid, mid, 'N', ' ', "", payTime, realPayMoney);
				if(addOrderPayResult > 0) {//添加订单支付成功，，更新订单状态
					OrderBean order = orderDao.showOrderDetail(oid);
					order.setOrderState('P');
	
					int updateOrderResult = orderDao.updateOrder(order);
					if(updateOrderResult > 0) {//更新订单状态成功，返回成功信息
						result = new Result(true);
					}
					else {//更新订单状态失败，返回错误信息
						result = new Result(false, "更新订单状态失败");
					}
				}
				else {//添加订单支付失败，返回错误信息
					result = new Result(false, "添加订单支付失败");
				}
			}
			else {//更新会员卡失败，返回错误信息
				result = updateMemberCardResult;
			}
		}
		else {//更新Tickets集团信息失败，返回错误信息
			result = updateTicketsInfoResult;
		}
		
		return result;
	}
		
	
	/**为订单退款
	 * 
	 * @param oid 订单编号
	 * @return 退款结果:Result的result属性若为true，表示退款成功，description属性表示退款比例和实际退款金额；
	 * 				 Result的result属性若为false，表示退款失败，description属性表示失败原因（订单编号不存在等）
	 */
	@Override
	public Result refundOrder(int oid) {

		Result result;
		
		//查询订单支付
		OrderPayBean orderPay = orderPayDao.showOrderPayDetail(oid);
		if(orderPay == null) {//订单支付对象不存在，返回错误信息
			result = new Result(false, "订单支付不存在");
		}
		else {//订单支付对象存在
			if(orderPay.getIsPayAcnt() == 'N') {//订单支付对象是用现金付款，不予退款
				result = new Result(false, "使用现金付款的订单暂不支持退款");
			}
			else {//订单支付对象是用会员账户付款的，获得支付账户的账户类型和账户名称
				char category = orderPay.getPayCategory();
				String acntName = orderPay.getPayAcntName();
				
				//查询支付账户
				PayAccountBean payAccount = payAccountService.showPayAcntDetail(category, acntName);
				if(payAccount == null) {//支付账户对象不存在，返回错误信息
					result = new Result(false, "支付账户不存在");
				}
				else {//支付账户对象存在
					
					//添加退款
					Result refundResult = refundService.refund(oid);
					if(refundResult.getResult() == true) {//添加退款成功
						
						//获取实际退款金额
						double realRefundMoney = Double.parseDouble(refundResult.getDescription());
						//获取对应订单的实际支付金额
						double realPayMoney = orderPay.getPayMoney();
						
						//退款（更新支付账户）
						Result updatePayAccountResult = payAccountService.refund(category, acntName, realRefundMoney);
						if(updatePayAccountResult.getResult() == true) {//退款（更新支付账户）成功，更新Tickets集团信息
							Result updateTicketsInfoResult = ticketsCompanyInfoService.spendMoney(realRefundMoney);
							if(updateTicketsInfoResult.getResult() == true) {//更新Tickets集团信息成功，更新会员卡
								int mid = orderDao.showOrderDetail(oid).getMid();
								Result updateMemberCardResult = memberCardService.updateCardAfterRefund(mid, realPayMoney);
								if(updateMemberCardResult.getResult() == true) {//更新会员卡成功，更新订单状态
									OrderBean order = orderDao.showOrderDetail(oid);
									order.setOrderState('R');
									
									int updateOrderResult = orderDao.updateOrder(order);
									if(updateOrderResult > 0) {//更新订单状态成功，返回成功信息+退款比例和实际退款金额
										result = new Result(true, "退款百分比为："+getStringDouble(realRefundMoney * 100 / realPayMoney)+"%，实际退款金额为："+getStringDouble(realRefundMoney)+"元");
									}
									else {//更新订单状态失败，返回错误信息
										result = new Result(false, "更新订单状态失败");
									}
								}
								else {//更新会员卡失败，返回错误信息
									result = updateMemberCardResult;
								}
							}
							else {//更新Tickets集团信息失败，返回错误信息
								result = updateTicketsInfoResult;
							}
						}
						else {//退款（更新支付账户）失败，返回错误信息
							result = updatePayAccountResult;
						}
					}
					else {//添加退款失败，返回错误信息
						result = refundResult;
					}
				}
			}
		}
		
		return result;
	}

	
	/**检票入场
	 * 
	 * @param ppid 演出计划编号
	 * @param vid 场馆编号
	 * @param seatRow 座位排号
	 * @param seatCol 座位列号
	 * @return 检票结果:Result的result属性若为true，表示检票成功，description属性无意义；
	 * 				 Result的result属性若为false，表示检票失败，description属性表示失败原因（会员编号不存在、演出计划编号不存在、场馆编号不存在等）
	 */
	@Override
	public Result checkin(int ppid, int vid, int seatRow, int seatCol) {
		
		Result result;
		
		//查询演出计划
		PerformancePlanBean performancePlan = performancePlanService.showPerformancePlanDetail(ppid);
		if(performancePlan == null) {//演出计划对象不存在，返回错误信息
			result = new Result(false, "演出计划不存在");
		}
		else {//演出计划对象存在，查询场馆
			VenueBean venue = venueService.showVenueDetail(vid);
			if(venue == null) {//场馆对象不存在，返回错误信息
				result = new Result(false, "场馆不存在");
			}
			else {//场馆对象存在，查询售出座位
				SoldSeatBean soldSeat = soldSeatDao.showSoldSeatDetail(vid, seatRow, seatCol, ppid);
				if(soldSeat == null) {//售出座位对象不存在，返回错误信息
					result = new Result(false, "售出座位不存在");
				}
				else {//售出座位对象存在，检查座位状态
					if(soldSeat.getSeatState() == 'O') {//该座位已经检票就座，返回错误信息
						result = new Result(false, "该座位已经检票");
					}
					else {//该座位尚未检票，更新售出座位
						soldSeat.setSeatState('O');
						
						int updateSoldSeatResult = soldSeatDao.updateSoldSeat(soldSeat);
						if(updateSoldSeatResult > 0) {//更新售出座位成功，返回成功信息
							result = new Result(true);
						}
						else {//更新售出座位失败，返回错误信息
							result = new Result(false, "更新售出座位失败");
						}
					}
				}
			}
		}
		
		return result;
	}
	
	
	/**删除过期订单
	 * 
	 * @param time 当前时间
	 * @return
	 */
	@Override
	public Result deleteUnpaidOrder() {
		return new Result(true);
	}
	
	
	/**查看订单信息
	 * 
	 * @param oid 订单编号
	 * @return 订单对象
	 */
	@Override
	public OrderBean showOrderDetail(int oid) {
		
		return orderDao.showOrderDetail(oid);
		
	}
	
	
	/**查看订单支付信息
	 * 
	 * @param oid 订单支付编号
	 * @return 订单支付对象
	 */
	@Override
	public OrderPayBean showOrderPayDetail(int oid) {
		
		return orderPayDao.showOrderPayDetail(oid);
		
	}
	
	
	/**查看某会员的所有订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有订单对象列表
	 */
	@Override
	public List<OrderBean> showAllOrdersOfAMember(int mid) {

		return orderDao.showAllOrdersOfAMember(mid);
		
	}
	
	
	/**查看某会员的所有退款订单
	 * 
	 * @param mid 会员编号
	 * @return 该会员的所有退款订单对象列表
	 */
	@Override
	public List<OrderBean> showAllRefundOrdersOfAMember(int mid) {

		return orderDao.showAllRefundOrdersOfAMember(mid);
		
	}
	
	
	/**查看某场馆的所有订单
	 * 
	 * @param mid 场馆编号
	 * @return 该场馆的所有订单对象列表
	 */
	@Override
	public List<OrderBean> showAllOrdersOfAVenue(int vid) {

		return orderDao.showAllOrdersOfAVenue(vid);
		
	}
	
	
	/**查看某场馆的所有退款订单
	 * 
	 * @param mid 场馆编号
	 * @return 该场馆的所有退款订单对象列表
	 */
	@Override
	public List<OrderBean> showAllRefundOrdersOfAVenue(int vid) {
		
		return orderDao.showAllRefundOrdersOfAVenue(vid);
		
	}
	
	
	/**查看某场馆从某段时间开始的所有订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有订单对象列表
	 */
	@Override
	public List<OrderBean> showAllOrdersOfAVenueFromATime(int vid, Timestamp time) {
		
		return orderDao.showAllOrdersOfAVenueFromATime(vid, time);
		
	}
	
	
	/**查看某场馆从某段时间开始的所有退款订单
	 * 
	 * @param vid 场馆编号
	 * @param time 指定时间
	 * @return 该场馆从这段时间开始的所有退款订单对象列表
	 */
	@Override
	public List<OrderBean> showAllRefundOrdersOfAVenueFromATime(int vid, Timestamp time) {
		
		return orderDao.showAllRefundOrdersOfAVenueFromATime(vid, time);
		
	}
	

	/**查看某个订单的所有售出座位
	 * 
	 * @param oid 订单编号
	 * @return 该订单的所有售出座位
	 */
	@Override
	public List<SoldSeatBean> showSoldSeatsOfAOrder(int oid) {
	
		return soldSeatDao.showSoldSeatsOfAOrder(oid);
		
	}

	public String getStringDouble(double num) {
		BigDecimal bd = new BigDecimal(num);
		String numStr = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
		return numStr;
	}
	
}
