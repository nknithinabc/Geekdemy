package com.example.geektrust;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.geektrust.entities.Transaction;
import com.example.geektrust.entities.Transaction.Coupon;
import com.example.geektrust.service.TransactionService;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {
	
	
	@InjectMocks
	TransactionService transactionService;
	
	@Test
	public void testAddProgram_addCertification()
	{
		Transaction t = new Transaction();
		transactionService.addProgram(t, "CERTIFICATION", 2);
		assertEquals(2,t.getQtyCertification());
	}
	
	@Test
	public void testAddProgram_addDegree()
	{
		Transaction t = new Transaction();
		transactionService.addProgram(t, "DEGREE", 2);
		assertEquals(2,t.getQtyDegree());
	}
	
	@Test
	public void testAddProgram_addDiploma()
	{
		Transaction t = new Transaction();
		transactionService.addProgram(t, "DIPLOMA", 2);
		assertEquals(2,t.getQtyDiploma());
	}
	
	@Test
	public void calculateBill()
	{
		Transaction t = new Transaction();
		transactionService.printBill(t);
	}
	
	@Test
	public void testCalculateSubtotal()
	{
		Transaction t = new Transaction();
		t.setProMembershipApplied(true);
		t.setQtyCertification(3);
		t.setQtyDegree(4);
		t.setQtyDiploma(5);
		
		transactionService.calculateSubtotal(t);
		assertEquals(905.0, t.getTotalProDiscount());
	}
	
	@Test
	public void testCalculateCouponDiscount()
	{
		Transaction t = new Transaction();
		t.setProMembershipApplied(true);
		transactionService.addProgram(t, "DEGREE",2);
		transactionService.addProgram(t, "CERTIFICATION",2);
		transactionService.addProgram(t, "DIPLOMA",2);
		
		transactionService.calculateCouponDiscount(t);
		assertEquals(Coupon.B4G1, t.getFinalCoupon());
	}
	
	@Test
	public void testApplyCouponDiscount_DealG5()
	{
		Transaction t = new Transaction();
		transactionService.addProgram(t, "DIPLOMA", 3);
		t.applyCoupon(Coupon.DEAL_G5);
		t.setSubTotal(5000d);
		
		transactionService.calculateCouponDiscount(t);
		assertEquals(Coupon.DEAL_G5, t.getFinalCoupon());
	}
	
	@Test
	public void testApplyCouponDiscount_DealG20()
	{
		Transaction t = new Transaction();
		
		t.setSubTotal(12000d);
		t.applyCoupon(Coupon.DEAL_G20);
		
		transactionService.calculateCouponDiscount(t);
		assertEquals(Coupon.DEAL_G20, t.getFinalCoupon());
	}
	
	@Test
	public void testApplyCouponDiscount_DealG5_DealG20()
	{
		Transaction t = new Transaction();
		t.setSubTotal(12000d);
		t.applyCoupon(Coupon.DEAL_G20);
		t.applyCoupon(Coupon.DEAL_G5);
		transactionService.calculateCouponDiscount(t);
		assertEquals(Coupon.DEAL_G20, t.getFinalCoupon());
	}
	
	
	@Test
	public void testApplyCouponDiscount_DealG5_DealG20_2()
	{
		Transaction t = new Transaction();
		transactionService.addProgram(t,"DIPLOMA", 2);
		t.setSubTotal(1000d);
		t.applyCoupon(Coupon.DEAL_G20);
		t.applyCoupon(Coupon.DEAL_G5);
		transactionService.calculateCouponDiscount(t);
		assertEquals(Coupon.DEAL_G5, t.getFinalCoupon());
	}
	
	@Test
	public void testApplyCoupon_B4G1()
	{
		Transaction t = new Transaction();
		transactionService.applyCoupon("B4G1", t);
		assertEquals(1, t.getCouponsApplied().size());
	}
	
	@Test
	public void testApplyCoupon_DEAL_G20()
	{
		Transaction t = new Transaction();
		transactionService.applyCoupon("DEAL_G20", t);
		assertEquals(1, t.getCouponsApplied().size());
	}
	
	@Test
	public void testApplyCoupon_DEAL_G5()
	{
		Transaction t = new Transaction();
		transactionService.applyCoupon("DEAL_G5", t);
		assertEquals(1, t.getCouponsApplied().size());
	}
	
	
}
