package com.example.geektrust.entities;

import java.util.ArrayList;
import java.util.List;

public class Transaction {

	public enum Coupon {
		B4G1, DEAL_G20, DEAL_G5
	};

	private List<Coupon> couponsApplied = new ArrayList<>();
	private List<Program> listOfPrograms = new ArrayList<>();
	private Coupon finalCoupon;

	private Integer qtyCertification = 0;
	private Integer qtyDegree = 0;
	private Integer qtyDiploma = 0;

	private Double subTotal = (double) 0;
	private Double couponDiscount = (double) 0;
	private Double totalProDiscount= (double) 0;
	private Double proMembershipFee= (double) 0;
	private Double enrollmentFee= (double) 0;
	private Double total= (double) 0;

	private Boolean proMembershipApplied = false;
	
	public Double getSubTotal() {
		return subTotal;
	}
		
	public Double getTotalProDiscount() {
		return totalProDiscount;
	}

	public Double getProMembershipFee() {
		if (proMembershipApplied)
			proMembershipFee =  (double) 200;
		else
			proMembershipFee = (double) 0;
			
		return proMembershipFee;
	}

	public Double getEnrollmentFee() {
		if (subTotal >= 6666)
			enrollmentFee = (double) 0;
		else
			enrollmentFee = (double) 500;

		return enrollmentFee;
	}

	public Double getTotal() {
		return total;
	}

	public Boolean getProMembershipApplied() {
		return proMembershipApplied;
	}

	public void setProMembershipApplied(Boolean proMembershipApplied) {
		this.proMembershipApplied = proMembershipApplied;
	}

	public void applyCoupon(Coupon c) {
		couponsApplied.add(c);
	}

	public List<Coupon> getCouponsApplied() {
		
		if(couponsApplied == null)
			return new ArrayList<>();
		else
			return couponsApplied;
	}

	public void setCouponsApplied(List<Coupon> couponsApplied) {
		this.couponsApplied = couponsApplied;
	}

	public List<Program> getListOfPrograms() {
		return listOfPrograms;
	}

	public void setListOfPrograms(List<Program> listOfPrograms) {
		this.listOfPrograms = listOfPrograms;
	}

	public Integer getQtyCertification() {
		return qtyCertification;
	}

	public void setQtyCertification(Integer qtyCertification) {
		this.qtyCertification = qtyCertification;
	}

	public Integer getQtyDegree() {
		return qtyDegree;
	}

	public void setQtyDegree(Integer qtyDegree) {
		this.qtyDegree = qtyDegree;
	}

	public Integer getQtyDiploma() {
		return qtyDiploma;
	}

	public void setQtyDiploma(Integer qtyDiploma) {
		this.qtyDiploma = qtyDiploma;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	public void setTotalProDiscount(Double totalProDiscount) {
		this.totalProDiscount = totalProDiscount;
	}

	public void setProMembershipFee(Double proMembershipFee) {
		this.proMembershipFee = proMembershipFee;
	}

	public void setEnrollmentFee(Double enrollmentFee) {
		this.enrollmentFee = enrollmentFee;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(Double couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public Coupon getFinalCoupon() {
		return finalCoupon;
	}

	public void setFinalCoupon(Coupon finalCoupon) {
		this.finalCoupon = finalCoupon;
	}


}
