package com.piantino.report.Dtos;

public class SumaryDetailsDTO {
	
	private String sumary_detail_name;
	private String sumary_detail_detail;
	private double sumary_detail_subtotal;
	
	/*GABRIEL: esta variable es para identificar donde imprimir en el pdf
	 * type = 1 --> Taxes
	 * type = 2 --> Payment Details
	 * type = 3 --> Payment Type
	 * */
	private int sumary_detail_band;
	public String getSumary_detail_name() {
            
		return sumary_detail_name;
	}
	public void setSumary_detail_name(String sumary_detail_name) {
		this.sumary_detail_name = sumary_detail_name;
	}
	public String getSumary_detail_detail() {
		return sumary_detail_detail;
	}
	public void setSumary_detail_detail(String sumary_detail_detail) {
		this.sumary_detail_detail = sumary_detail_detail;
	}
	public double getSumary_detail_subtotal() {
		return sumary_detail_subtotal;
	}
	public void setSumary_detail_subtotal(double sumary_detail_subtotal) {
		this.sumary_detail_subtotal = sumary_detail_subtotal;
	}
	
	
	
	

}
