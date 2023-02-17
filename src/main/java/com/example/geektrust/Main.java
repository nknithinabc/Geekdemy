package com.example.geektrust;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import com.example.geektrust.entities.Transaction;
import com.example.geektrust.service.TransactionService;

public class Main {

	public static void main(String[] args) {
		
		TransactionService transactionService = new TransactionService();
       
        try {
            // the file to be opened for reading
      
        	Scanner sc = new Scanner(new File("sample_input/input1.txt")); // file to be scanned
            // returns true if there is another line to read
            Transaction t = new Transaction();
            while (sc.hasNextLine()) {
               //Add your code here to process input commands
            	String input = sc.nextLine();
            	String[] tokens = input.split(" ");
            	
            	switch(tokens[0])
            	{
            		case "ADD_PROGRAMME":
            						  String program = tokens[1];
            						  int qty = Integer.parseInt(tokens[2]);
            						  transactionService.addProgram(t, program, qty);
            		                  break;  
            		case "APPLY_COUPON":
            						   String coupon = tokens[1];
            						   transactionService.applyCoupon(coupon, t);
            			               break;
            						   
            		case "ADD_PRO_MEMBERSHIP":
            						   t.setProMembershipApplied(true);
            		                   break;
            		                   
            		case "PRINT_BILL":
            						   transactionService.printBill(t);
            		                   break; 
            	}
            	
            }
            sc.close(); // closes the scanner
        } 
        
        catch (Exception e) {
        	System.out.println(e);
        }
        
    }
}
