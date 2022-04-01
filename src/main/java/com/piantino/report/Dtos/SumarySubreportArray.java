package com.piantino.report.Dtos;

import java.util.List;

public class SumarySubreportArray {
	
	 private List<SumaryDetailsDTO> taxesDetail;
	 private List<SumaryDetailsDTO> paymentDetail;
	 private List<SumaryDetailsDTO> paymentTypeDetail;
	 
	public List<SumaryDetailsDTO> getTaxesDetail() {
		return taxesDetail;
	}
	public void setTaxesDetail(List<SumaryDetailsDTO> taxesDetail) {
		this.taxesDetail = taxesDetail;
	}
	public List<SumaryDetailsDTO> getPaymentDetail() {
		return paymentDetail;
	}
	public void setPaymentDetail(List<SumaryDetailsDTO> paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	public List<SumaryDetailsDTO> getPaymentTypeDetail() {
		return paymentTypeDetail;
	}
	public void setPaymentTypeDetail(List<SumaryDetailsDTO> paymentTypeDetail) {
		this.paymentTypeDetail = paymentTypeDetail;
	}
 
 

}
