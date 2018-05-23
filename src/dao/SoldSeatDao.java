package dao;

import java.util.List;

import po.SoldSeatBean;

/**
 * 售出座位数据访问接口
 **/

public interface SoldSeatDao {
	
	/**根据订单编号增加售出座位
	 * 
	 * @param vid 场馆编号
	 * @param seatRow 座位排号
	 * @param seatCol 座位列号
	 * @param ppid 演出计划编号
	 * @param oid 订单编号
	 * @param mid 会员编号
	 * @param seatCategory 座位类型
	 * @return 添加结果：若结果大于0，则添加成功；若结果小于等于0，则添加失败
	 */
	public int addSoldSeat(int vid, int seatRow, int seatCol, int ppid, int oid, int mid, char seatCategory);
	
	/**删除售出座位
	 * 
	 * @param vid 场馆编号
	 * @param seatRow 座位排号
	 * @param seatCol 座位列号
	 * @param ppid 演出计划编号
	 * @return 删除结果，若结果大于0，则删除成功；若结果小于等于0，则删除失败
	 */
	public int deleteSoldSeat(int vid, int seatRow, int seatCol, int ppid);
	
	/**更新售出座位
	 * 
	 * @param newSoldSeat 新的售出座位
	 * @return 更新结果：若结果大于0，则更新成功；若结果小于等于0，则更新失败
	 */
	public int updateSoldSeat(SoldSeatBean newSoldSeat);
	
	/**查看售出座位
	 * 
	 * @param vid 场馆编号
	 * @param seatRow 座位排号
	 * @param seatCol 座位列号
	 * @param ppid 演出计划编号
	 * @return 售出座位对象
	 */
	public SoldSeatBean showSoldSeatDetail(int vid, int seatRow, int seatCol, int ppid);
	
	/**查看某场演出某个场馆某个座位类型的所有售出座位
	 * 
	 * @param vid 场馆编号
	 * @param ppid 演出计划编号
	 * @param category 座位类型
	 * @return 该场演出该场馆指定座位类型的所有售出座位对象列表
	 */
	public List<SoldSeatBean> showCategorySoldSeatsOfAPerformanceOfAVenue(int vid, int ppid, char category);
	
	/**查看某个订单的所有售出座位
	 * 
	 * @param oid 订单编号
	 * @return 该订单的所有售出座位
	 */
	public List<SoldSeatBean> showSoldSeatsOfAOrder(int oid);
	
}
