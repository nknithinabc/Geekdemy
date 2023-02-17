package com.example.geektrust.service;

import java.util.List;

import com.example.geektrust.entities.Program;
import com.example.geektrust.entities.Transaction;
import com.example.geektrust.entities.Transaction.Coupon;

public class TransactionService {

	public void applyCoupon(String coupon, Transaction t) {
		Coupon c = null;
		if (coupon.equals("B4G1"))
			c = Coupon.B4G1;
		else if (coupon.equals("DEAL_G20"))
			c = Coupon.DEAL_G20;
		else if (coupon.equals("DEAL_G5"))
			c = Coupon.DEAL_G5;
		t.applyCoupon(c);
	}

	public void calculateSubtotal(Transaction t) {

		Double totalCost = (double) 0;
		Double subtotal = (double) 0;
		Double totalProDiscount = (double) 0;

		if (t.getProMembershipApplied()) {
			Double certificationCost = (double) (t.getQtyCertification() * 3000);
			Double certificationDiscount = certificationCost * 0.02;
			totalProDiscount += certificationDiscount;

			Double degreeCost = (double) (t.getQtyDegree() * 5000);
			Double degreeDiscount = degreeCost * 0.03;
			totalProDiscount += degreeDiscount;

			Double diplomaCost = (double) (t.getQtyDiploma() * 2500);
			Double diplomaDiscount = diplomaCost * 0.01;
			totalProDiscount += diplomaDiscount;

			totalCost = certificationCost + degreeCost + diplomaCost;

		} else {
			Double certificationCost = (double) (t.getQtyCertification() * 3000);
			Double degreeCost = (double) (t.getQtyDegree() * 5000);
			Double diplomaCost = (double) (t.getQtyDiploma() * 2500);

			totalCost = certificationCost + degreeCost + diplomaCost;
		}

		subtotal = totalCost - totalProDiscount;
		t.setSubTotal(subtotal);
		calculateEnrolmentFee(t);
		t.setTotalProDiscount(totalProDiscount);
	}

	public void calculateEnrolmentFee(Transaction t) {
		if (t.getSubTotal() >= 6666)
			t.setEnrollmentFee((double) 500);
		else
			t.setEnrollmentFee((double) 0);
	}

	public void calculateCouponDiscount(Transaction t) {

		Double couponDiscount = (double) 0;

		if (t.getQtyCertification() + t.getQtyDegree() + t.getQtyDiploma() >= 4) {

			couponDiscount = findMin(t.getListOfPrograms());
			t.setFinalCoupon(Coupon.B4G1);
		} else {
			// FETCH APPLIED COUPONS

			if (t.getCouponsApplied().size() == 1 && t.getCouponsApplied().contains(Coupon.DEAL_G5)
					&& t.getListOfPrograms().size() >= 2) {
				t.setFinalCoupon(Coupon.DEAL_G5);
				couponDiscount = getCouponDiscountOfDealG5(t);
			}

			if (t.getCouponsApplied().size() == 1 && t.getCouponsApplied().contains(Coupon.DEAL_G20)
					&& t.getSubTotal() >= 10000) {
				t.setFinalCoupon(Coupon.DEAL_G20);
				couponDiscount = getCouponDiscountOfDealG20(t);
			}

			if (t.getCouponsApplied().contains(Coupon.DEAL_G5) && t.getCouponsApplied().contains(Coupon.DEAL_G20)) {
				Double couponDiscountOfDealG5 = getCouponDiscountOfDealG5(t);
				Double couponDiscountOfDealG20 = getCouponDiscountOfDealG20(t);

				if (couponDiscountOfDealG5 > couponDiscountOfDealG20) {
					couponDiscount = couponDiscountOfDealG5;
					t.setFinalCoupon(Coupon.DEAL_G5);
				} else {
					couponDiscount = couponDiscountOfDealG20;
					t.setFinalCoupon(Coupon.DEAL_G20);
				}
			}
		}

		t.setCouponDiscount(couponDiscount);
	}

	private Double getCouponDiscountOfDealG5(Transaction t) {
		if (t.getListOfPrograms().size() >= 2)
			return 0.05 * t.getSubTotal();
		else
			return (double) 0;
	}

	public Double getCouponDiscountOfDealG20(Transaction t) {
		if (t.getSubTotal() >= 10000)
			return 0.2 * t.getSubTotal();
		else
			return (double) 0;
	}

	private Double findMin(List<Program> programs) {

		Double min = Double.MAX_VALUE;
		for (Program p : programs) {
			if (p.getPrice() < min)
				min = p.getPrice();
		}
		return min;
	}

	public void calculateTotal(Transaction t) {
		Double total = t.getSubTotal() - t.getCouponDiscount() - t.getEnrollmentFee();
		t.setTotal(total);
	}

	public void printBill(Transaction t) {

		calculateSubtotal(t);
		calculateCouponDiscount(t);
		calculateTotal(t);

		System.out.println("SUB_TOTAL " + t.getSubTotal());
		System.out.println("COUPON_DISCOUNT " + t.getFinalCoupon() + " " + t.getCouponDiscount());
		System.out.println("TOTAL_PRO_DISCOUNT " + t.getTotalProDiscount());
		System.out.println("PRO_MEMBERSHIP_FEE" + " " + t.getProMembershipFee());
		System.out.println("ENROLLMENT_FEE" + " " + t.getEnrollmentFee());
		System.out.println("TOTAL" + " " + t.getTotal());
	}

	public void addProgram(Transaction t, String program, int qty) {
		switch (program) {
		case "CERTIFICATION":
			t.setQtyCertification(qty);
			for (int i = 1; i <= qty; i++)
				t.getListOfPrograms().add(new Program("CERTIFICATION", (double) 3000));
			break;

		case "DEGREE":
			t.setQtyDegree(qty);
			for (int i = 1; i <= qty; i++)
				t.getListOfPrograms().add(new Program("DEGREE", (double) 5000));
			break;

		case "DIPLOMA":
			t.setQtyDiploma(qty);
			for (int i = 1; i <= qty; i++)
				t.getListOfPrograms().add(new Program("DIPLOMA", (double) 2500));
			break;
		}
	}

}
