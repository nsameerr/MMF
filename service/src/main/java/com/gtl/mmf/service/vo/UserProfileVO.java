/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

/**
 *
 * @author 07960
 */
public class UserProfileVO {

    private String name;
    private String address;
    private String city;
    private String pincode;
    private String country;
    private String telephone;
    private String mobile;
    private String sebi_reg_no;
    private String email;
    private boolean advisor;
    private int user_status;
    private String access_token;
    private String expire_in;
    private String linkedin_member_id;
    private String linkedin_user;
    private String middleName;
    private String lastName;
    private String pictureUrl;
    private Date dob;
    private Date expire_in_date;
    private String workOrganization;
    private String jobTitle;
    Integer Id;

    private String address1_Line1;
    private String address1_Street;
    private String address1_Landmark;
    private String address1_Zipcode;

    private String address2_Line1;
    private String address2_Street;
    private String address2_Landmark;
    private String address2_Zipcode;
    private String state;
    private String regId;
    private boolean linkedInConnected;
    private String profileUrl;

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSebi_reg_no() {
        return sebi_reg_no;
    }

    public void setSebi_reg_no(String sebi_reg_no) {
        this.sebi_reg_no = sebi_reg_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedin_member_id() {
        return linkedin_member_id;
    }

    public void setLinkedin_member_id(String linkedin_member_id) {
        this.linkedin_member_id = linkedin_member_id;
    }

    public String getLinkedin_user() {
        return linkedin_user;
    }

    public void setLinkedin_user(String linkedin_user) {
        this.linkedin_user = linkedin_user;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getExpire_in_date() {
        return expire_in_date;
    }

    public void setExpire_in_date(Date expire_in_date) {
        this.expire_in_date = expire_in_date;
    }

    public String getWorkOrganization() {
        return workOrganization;
    }

    public void setWorkOrganization(String workOrganization) {
        this.workOrganization = workOrganization;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getAddress1_Line1() {
        return address1_Line1;
    }

    public void setAddress1_Line1(String address1_Line1) {
        this.address1_Line1 = address1_Line1;
    }

    public String getAddress1_Street() {
        return address1_Street;
    }

    public void setAddress1_Street(String address1_Street) {
        this.address1_Street = address1_Street;
    }

    public String getAddress1_Landmark() {
        return address1_Landmark;
    }

    public void setAddress1_Landmark(String address1_Landmark) {
        this.address1_Landmark = address1_Landmark;
    }

    public String getAddress1_Zipcode() {
        return address1_Zipcode;
    }

    public void setAddress1_Zipcode(String address1_Zipcode) {
        this.address1_Zipcode = address1_Zipcode;
    }

    public String getAddress2_Line1() {
        return address2_Line1;
    }

    public void setAddress2_Line1(String address2_Line1) {
        this.address2_Line1 = address2_Line1;
    }

    public String getAddress2_Street() {
        return address2_Street;
    }

    public void setAddress2_Street(String address2_Street) {
        this.address2_Street = address2_Street;
    }

    public String getAddress2_Landmark() {
        return address2_Landmark;
    }

    public void setAddress2_Landmark(String address2_Landmark) {
        this.address2_Landmark = address2_Landmark;
    }

    public String getAddress2_Zipcode() {
        return address2_Zipcode;
    }

    public void setAddress2_Zipcode(String address2_Zipcode) {
        this.address2_Zipcode = address2_Zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
