package po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 售出座位类
 **/

@Entity
@Table(name="sold_seat")
public class SoldSeatBean implements Serializable {

	private static final long serialVersionUID = -37856550807554351L;
	
	private int vid;			//场馆编号
	private int seatRow;		//座位排号
	private int seatCol;		//座位列号
	private int ppid;			//演出编号
	private int oid; 			//订单号
	private int mid;			//会员编号
	private char seatCategory;	//座位类型:V表示Vip类座位，A表示A类座位，B表示B类座位，C表示C类座位，D表示D类座位
	private char seatState;		//座位状态:E表示尚未检票就座，O表示已经检票就座

	@Id
	@Column(name="vid")
	public int getVid() {
		return vid;
	}
	public void setVid(int vid) {
		this.vid = vid;
	}
	
	@Id
	@Column(name="seat_row")
	public int getSeatRow() {
		return seatRow;
	}
	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}
	
	@Id
	@Column(name="seat_col")
	public int getSeatCol() {
		return seatCol;
	}
	public void setSeatCol(int seatCol) {
		this.seatCol = seatCol;
	}
	
	@Id
	@Column(name="ppid")
	public int getPpid() {
		return ppid;
	}
	public void setPpid(int ppid) {
		this.ppid = ppid;
	}
	
	@Column(name="oid")
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	
	@Column(name="mid")
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	
	@Column(name="seat_category")
	public char getSeatCategory() {
		return seatCategory;
	}
	public void setSeatCategory(char seatCategory) {
		this.seatCategory = seatCategory;
	}
	
	@Column(name="seat_state")
	public char getSeatState() {
		return seatState;
	}
	public void setSeatState(char seatState) {
		this.seatState = seatState;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof SoldSeatBean) {
			SoldSeatBean ssb = (SoldSeatBean) o;
			if (this.vid == ssb.getVid() && this.ppid == ssb.getPpid() && 
				this.seatRow == ssb.getSeatRow() && this.seatCol == ssb.getSeatCol()) {
				return true;
			}
		}
		return false;
	}
	
}
