package com.piantino.report.DtosMios;


import com.piantino.report.Dtos.PaymentDetailsDTO;
import com.piantino.report.Dtos.RentalAgreement;
import com.piantino.report.Dtos.SumaryDetailsDTO;
import com.piantino.report.Dtos.SumaryHeaderDTO;
import com.piantino.report.Dtos.SumarySubreportArray;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class ReportServiceMio {

	public String generateReport() {
		
		/*PRINT RESULT IN THIS VARIABLE*/
		String responseReturn = ""; 
		
		responseReturn += generateReportConr();
		responseReturn += " <br> " + generateReportSumary();
		
		return responseReturn;
	}

	/**
	 * GENERATE SUMARY REPORT
	 * DATA ENTRY RESOURCE (QUERIES)
	 * @return
	 * Path of generate file
	 */
	private String generateReportSumary() {
		
		SumaryHeaderDTO sumaryHeaderDTO = new SumaryHeaderDTO();
		/* GET DATA FROM QUERY HERE*/	
		sumaryHeaderDTO.setDate_printed((new SimpleDateFormat("dd/MM/yy hh:mm:ss a")).format(new Date()));//Enviamos fecha actual
		sumaryHeaderDTO.setNombre_cliente("Gabriel Grillo");
		sumaryHeaderDTO.setReservation_no(343);
		sumaryHeaderDTO.setBooking_status("Hired by Gabriel Grillo");
		sumaryHeaderDTO.setVehicle("Volkswagen Golf - Black");
		sumaryHeaderDTO.setAuto_rego("kam013");
		sumaryHeaderDTO.setPickup("Bexley 2020-10-26 09:00");
		sumaryHeaderDTO.setDropoff("Bexley 2021-10-05 09:00");
		sumaryHeaderDTO.setEntered_by("grillo gabriel");
		sumaryHeaderDTO.setDate_entered("2020-10-26");
		sumaryHeaderDTO.setSource_by("Internet search");
		sumaryHeaderDTO.setRate_detail("49 days x @$1  $49");
		sumaryHeaderDTO.setRate_total("$1.469,00");
		sumaryHeaderDTO.setKms_out(15564);
		sumaryHeaderDTO.setBalance_due(62);
		sumaryHeaderDTO.setRenter_name("Gabriel Grillo");
		sumaryHeaderDTO.setRenter_email("gabigrillo46@gmail.com");
		sumaryHeaderDTO.setLicence_no("321321");
		sumaryHeaderDTO.setExpiry_date("2024-10-16");
		sumaryHeaderDTO.setDob("1986-08-09");
		sumaryHeaderDTO.setAddress("10 / 20 Monomeeth");
		sumaryHeaderDTO.setPhone("0400913385");
		sumaryHeaderDTO.setMobile("");
		
		/* 
		 *  * FOR TAXES
		 */
		List<SumaryDetailsDTO> sumaryDetailsTaxes = new ArrayList<SumaryDetailsDTO>();
		/* GET DATA FROM QUERY HERE*/	
		SumaryDetailsDTO p1 = new SumaryDetailsDTO();
		p1.setSumary_detail_name("First Payment");
		p1.setSumary_detail_detail("Daily @ $1400");
		p1.setSumary_detail_subtotal(1400d);
		sumaryDetailsTaxes.add(p1);
		SumaryDetailsDTO p2 = new SumaryDetailsDTO();
		p2.setSumary_detail_name("Imp Toll");
		p2.setSumary_detail_detail("Daily @ $20");
		p2.setSumary_detail_subtotal(20d);
		sumaryDetailsTaxes.add(p2);
		
		
		/* 
		 *  * FOR PAYMENT DETAIL
		 */
		List<SumaryDetailsDTO> sumaryDetailsPayment = new ArrayList<SumaryDetailsDTO>();
		
		SumaryDetailsDTO pay1 = new SumaryDetailsDTO();
		pay1.setSumary_detail_name("EFTPOS");
		pay1.setSumary_detail_detail("2020-10-26 Central");
		pay1.setSumary_detail_subtotal(1400d);
		sumaryDetailsPayment.add(pay1);
		SumaryDetailsDTO pay2 = new SumaryDetailsDTO();
		pay2.setSumary_detail_name("DIRECT DEBIT");
		pay2.setSumary_detail_detail("2020-10-26 Central");
		pay2.setSumary_detail_subtotal(5d);
		sumaryDetailsPayment.add(pay2);
		SumaryDetailsDTO pay3 = new SumaryDetailsDTO();
		pay3.setSumary_detail_name("DIRECT DEBIT");
		pay3.setSumary_detail_detail("2020-10-26 Central");
		pay3.setSumary_detail_subtotal(2d);
		sumaryDetailsPayment.add(pay3);
		
		/* 
		 *  * FOR PAYMENT TYPE
		 */
		List<SumaryDetailsDTO> sumaryDetailsType = new ArrayList<SumaryDetailsDTO>();
		/* GET DATA FROM QUERY HERE*/	
		SumaryDetailsDTO type1 = new SumaryDetailsDTO();
		type1.setSumary_detail_name("Toll");
		type1.setSumary_detail_detail("654654654");
		sumaryDetailsType.add(type1);
		
		SumaryDetailsDTO type2 = new SumaryDetailsDTO();
		type2.setSumary_detail_name("toll");
		type2.setSumary_detail_detail("8885546641");
		sumaryDetailsType.add(type2);
		
		List<SumarySubreportArray> arraysDetails = new ArrayList<SumarySubreportArray>();
		SumarySubreportArray sumarySubReportArray = new SumarySubreportArray();
		sumarySubReportArray.setTaxesDetail(sumaryDetailsTaxes);
		sumarySubReportArray.setPaymentDetail(sumaryDetailsPayment);
		sumarySubReportArray.setPaymentTypeDetail(sumaryDetailsType);
		arraysDetails.add(sumarySubReportArray);
		
		try {
			
			File sumary = new File("src/main/resources/sumary.jrxml");
			File sumary_taxes = new File("src/main/resources/sumary-taxes.jrxml");
			
			String reportPath_base = sumary.getAbsolutePath().replace("sumary.jrxml", "");
			String reportPath = sumary.getAbsolutePath();
			String reportPath_taxes = sumary_taxes.getAbsolutePath();
			
			JasperReport jasperMasterReport = JasperCompileManager.compileReport(reportPath);
			JasperReport jasperSubreportReport_taxes = JasperCompileManager.compileReport(reportPath_taxes);
			
			//Complete Params
			Map<String, Object> params = new HashMap<>();
			params.put("date_printed", sumaryHeaderDTO.getDate_printed());
			params.put("nombre_cliente", sumaryHeaderDTO.getNombre_cliente());
			params.put("reservation_no", sumaryHeaderDTO.getReservation_no());
			params.put("booking_status", sumaryHeaderDTO.getBooking_status());
			params.put("vehicle", sumaryHeaderDTO.getVehicle());
			params.put("auto_rego", sumaryHeaderDTO.getAuto_rego());
			params.put("pickup", sumaryHeaderDTO.getPickup());
			params.put("dropoff", sumaryHeaderDTO.getDropoff());
			params.put("entered_by", sumaryHeaderDTO.getEntered_by());
			params.put("date_entered", sumaryHeaderDTO.getDate_entered());
			params.put("source_by", sumaryHeaderDTO.getSource_by());
			params.put("rate_detail", sumaryHeaderDTO.getRate_detail());
			params.put("rate_total", sumaryHeaderDTO.getRate_total());
			params.put("kms_out", sumaryHeaderDTO.getKms_out());
			params.put("balance_due", sumaryHeaderDTO.getBalance_due());
			params.put("renter_name", sumaryHeaderDTO.getRenter_name());
			params.put("renter_email", sumaryHeaderDTO.getRenter_email());
			params.put("licence_no", sumaryHeaderDTO.getLicence_no());
			params.put("expiry_date", sumaryHeaderDTO.getExpiry_date());
			params.put("dob", sumaryHeaderDTO.getDob());
			params.put("address", sumaryHeaderDTO.getAddress());
			params.put("phone", sumaryHeaderDTO.getPhone());
			params.put("mobile", sumaryHeaderDTO.getMobile());

			//SUBREPORT PARAMS
			params.put("subReportBeanList", jasperSubreportReport_taxes);
			params.put("SUBREPORT_DIR",reportPath_base);
			
			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperMasterReport, params,
					jrBeanCollectionDataSource);

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath.replace("sumary.jrxml", "") + "sumary.pdf");

			System.out.println("Done");

			return "Report successfully generated @path= " + reportPath.replace("sumary.jrxml", "");

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
	}

	/**
	 * GENERATE CONR REPORT
	 * DATA ENTRY RESOURCE (QUERIES)
	 * @return
	 * Path of generate file
	 */
	private String generateReportConr() {
		
		
		RentalAgreement rental = new RentalAgreement();
		/* GET DATA FROM QUERY HERE*/	
		rental.setRental_agreement_id(343l);
		rental.setHirer_name("Gabriel Grillo");
		rental.setDob("1986-08-09");
		rental.setLicence_no("321321");
		rental.setIssued_in(10);
		rental.setExpiry_date("2024-10-16");
		rental.setAddress("10 / 20 Monomeeth");
		rental.setPhone("0400913385");
		rental.setMobile("");
		rental.setEmail("gabigrillo46@gmail.com");
		rental.setMake("Volkswagen");
		rental.setModel("Golf");
		rental.setLicence_plate("kam013");
		rental.setKms_out(15564d);
		rental.setKms_in(0d);
		rental.setFuel_type("Diesel");
		rental.setFuel_out(0);
		rental.setFuel_in(0);
		rental.setStart_date_time("2020-10-26 09:00");
		rental.setPickup_location("Bexley");
		rental.setReturn_date_time("2021-10-05 09:00");
		rental.setReturn_location("Bexley");
		rental.setRental_fees_days(344);
		rental.setRental_fees_rate(1d);
		rental.setRental_fees_total(49d);
		rental.setFirst_payment_qty(1d);
		rental.setFirst_payment_rate(1420d);
		rental.setFirst_payment_total(1420d);
		rental.setTotal(1469d);
		rental.setBalance_due(62d);
		

		List<PaymentsDetailsDTO> paymentDetails = new ArrayList<PaymentsDetailsDTO>();
		/* GET DATA FROM QUERY HERE*/	
		PaymentsDetailsDTO p1 = new PaymentsDetailsDTO();
	
                
                List<ImpuestoDetailsDTO> impuestosDetails = new ArrayList<ImpuestoDetailsDTO>();
                
                ImpuestoDetailsDTO imp1 = new ImpuestoDetailsDTO();
                imp1.setNombre_impuesto("impuesto1");
                impuestosDetails.add(imp1);
                ImpuestoDetailsDTO imp2 = new ImpuestoDetailsDTO();
                imp2.setNombre_impuesto("impuesto2");
                impuestosDetails.add(imp2);
                


		// PUT PARAMETERS (WARNING, THIS IS REALLY NECESARY)
		Map<String, Object> params = new HashMap<>();
		params.put("rental_agreement_id", rental.getRental_agreement_id());
		params.put("hirer_name", rental.getHirer_name());
		params.put("dob", rental.getDob());
		params.put("licence_no", rental.getLicence_no() );
		params.put("issued_in", rental.getIssued_in() );
		params.put("expiry_date", rental.getExpiry_date() );
		params.put("address", rental.getAddress() );
		params.put("phone", rental.getPhone() );
		params.put("mobile", rental.getMobile() );
		params.put("email", rental.getEmail() );
		params.put("make", rental.getMake() );
		params.put("model", rental.getModel() );
		params.put("licence_plate", rental.getLicence_plate() );
		params.put("kms_out", rental.getKms_out() );
		params.put("kms_in", rental.getKms_in() );
		params.put("fuel_type", rental.getFuel_type() );
		params.put("fuel_out", rental.getFuel_out() );
		params.put("fuel_in", rental.getFuel_in() );
		params.put("start_date_time", rental.getStart_date_time() );
		params.put("pickup_location", rental.getPickup_location() );
		params.put("return_date_time", rental.getReturn_date_time() );
		params.put("return_location", rental.getReturn_location() );
		params.put("rental_fees_days", rental.getRental_fees_days() );
		params.put("rental_fees_rate", rental.getRental_fees_rate() );
		params.put("rental_fees_total", rental.getRental_fees_total() );
		params.put("first_payment_qty", rental.getFirst_payment_qty() );
		params.put("first_payment_rate", rental.getFirst_payment_rate() );
		params.put("first_payment_total", rental.getFirst_payment_total() );
		params.put("total", rental.getTotal() );
		params.put("balance_due", rental.getBalance_due() );
		/* GABRIEL: Esto es importante (cuenta el numero de detalles, para imprimir texto fijo)
		 * Si no hay detalles, es necesario que se env�e al menos un elemento de detalle con valores vacios o en cero
		 *  */
		params.put("counter_record", paymentDetails != null ? paymentDetails.size() : 0 );
                
                List<ContratoSubreports> arraysDetails = new ArrayList<ContratoSubreports>();
		ContratoSubreports contratosSubreportes = new ContratoSubreports();
                contratosSubreportes.setListaImpuestos(impuestosDetails);
                contratosSubreportes.setListaPagos(paymentDetails);
		arraysDetails.add(contratosSubreportes);
		
		try {
			
			/*SEARCH THE FILE (if has other path please change it!)*/
			File f = new File("D:\\Gabriel\\Proyectos\\fabio\\reportes\\conrMio.jrxml");
                        File subreport = new File("D:\\Gabriel\\Proyectos\\fabio\\reportes\\SubreporteImpuestosjrxml.jrxml");
			
                        String reportPath_base = f.getAbsolutePath().replace("conrMio.jrxml", "");
			String reportPath = f.getAbsolutePath();
			String reportPathBase = f.getAbsolutePath().replace("conr.jrxml", "");
			
			JasperReport jasperReport = JasperCompileManager.compileReport(f.getAbsolutePath());
                        JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport.getAbsolutePath());
                        
                        params.put("subReportBeanList", jasperSubReport);
			params.put("SUBREPORT_DIR",reportPath_base);
			
			// Get data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(arraysDetails);

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,
					jrBeanCollectionDataSource);

			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, reportPathBase + "conr.pdf");

			System.out.println("Done");

			return "Report successfully generated @path= " + reportPathBase;

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		
	}

	
}
