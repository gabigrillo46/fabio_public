package com.piantino.report.Dtos;


public class RentalAgreement {

    private Long rental_agreement_id;
    private String hirer_name;
    // DOB IS DATE
    private String dob;
    private String licence_no;
    private Integer issued_in;
    // fecha_vencimiento_lic IS DATE
    private String expiry_date;
    private String address;
    private String phone;
    private String mobile;
    private String email;
    // Vehicle Details
    private String make;
    private String model;
    private String licence_plate;

    private Double kms_out;
    private Double kms_in;

    private String fuel_type;
    private Integer fuel_out;
    private Integer fuel_in;
    // RENTAIL DETAIL
    private String start_date_time;
    private String pickup_location;
    private String return_date_time;
    private String return_location;

    // RATES & FEES
    private Integer rental_fees_days;
    private Double rental_fees_rate;
    private Double rental_fees_total;

    private Double first_payment_qty;
    private Double first_payment_rate;
    private Double first_payment_total;

    private Double total;

    // BALANCE DUE
    private Double balance_due;

    public Long getRental_agreement_id() {
        return rental_agreement_id;
    }

    public void setRental_agreement_id(Long rental_agreement_id) {
        this.rental_agreement_id = rental_agreement_id;
    }

    public String getHirer_name() {
        return hirer_name;
    }

    public void setHirer_name(String hirer_name) {
        this.hirer_name = hirer_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public void setLicence_no(String licence_no) {
        this.licence_no = licence_no;
    }

    public Integer getIssued_in() {
        return issued_in;
    }

    public void setIssued_in(Integer issued_in) {
        this.issued_in = issued_in;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public void setLicence_plate(String licence_plate) {
        this.licence_plate = licence_plate;
    }

    public Double getKms_out() {
        return kms_out;
    }

    public void setKms_out(Double kms_out) {
        this.kms_out = kms_out;
    }

    public Double getKms_in() {
        return kms_in;
    }

    public void setKms_in(Double kms_in) {
        this.kms_in = kms_in;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public Integer getFuel_out() {
        return fuel_out;
    }

    public void setFuel_out(Integer fuel_out) {
        this.fuel_out = fuel_out;
    }

    public Integer getFuel_in() {
        return fuel_in;
    }

    public void setFuel_in(Integer fuel_in) {
        this.fuel_in = fuel_in;
    }

    public String getStart_date_time() {
        return start_date_time;
    }

    public void setStart_date_time(String start_date_time) {
        this.start_date_time = start_date_time;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getReturn_date_time() {
        return return_date_time;
    }

    public void setReturn_date_time(String return_date_time) {
        this.return_date_time = return_date_time;
    }

    public String getReturn_location() {
        return return_location;
    }

    public void setReturn_location(String return_location) {
        this.return_location = return_location;
    }

    public Integer getRental_fees_days() {
        return rental_fees_days;
    }

    public void setRental_fees_days(Integer rental_fees_days) {
        this.rental_fees_days = rental_fees_days;
    }

    public Double getRental_fees_rate() {
        return rental_fees_rate;
    }

    public void setRental_fees_rate(Double rental_fees_rate) {
        this.rental_fees_rate = rental_fees_rate;
    }

    public Double getRental_fees_total() {
        return rental_fees_total;
    }

    public void setRental_fees_total(Double rental_fees_total) {
        this.rental_fees_total = rental_fees_total;
    }

    public Double getFirst_payment_qty() {
        return first_payment_qty;
    }

    public void setFirst_payment_qty(Double first_payment_qty) {
        this.first_payment_qty = first_payment_qty;
    }

    public Double getFirst_payment_rate() {
        return first_payment_rate;
    }

    public void setFirst_payment_rate(Double first_payment_rate) {
        this.first_payment_rate = first_payment_rate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getBalance_due() {
        return balance_due;
    }

    public void setBalance_due(Double balance_due) {
        this.balance_due = balance_due;
    }

    public Double getFirst_payment_total() {
        return first_payment_total;
    }

    public void setFirst_payment_total(Double first_payment_total) {
        this.first_payment_total = first_payment_total;
    }
}
