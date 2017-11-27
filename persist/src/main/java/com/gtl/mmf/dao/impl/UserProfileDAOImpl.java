package com.gtl.mmf.dao.impl;

import com.gtl.mmf.dao.IUserProfileDAO;
import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.CitiesTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.EcsTransmittalSheetTb;
import com.gtl.mmf.entity.ExchangeHolidayTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.entity.InvProofFileDetailsTb;
import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorQualificationTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MmfSettingsTb;
import com.gtl.mmf.entity.TempAdv;
import com.gtl.mmf.entity.TempInv;
import com.gtl.mmf.entity.TempRegistrationTb;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserProfileDAOImpl implements IUserProfileDAO {

    static final Logger logger = Logger.getLogger("com.gtl.mmf.dao.impl.UserProfileDAOImpl");
    private static final Integer ONE = 1;

    @Autowired
    private SessionFactory sessionFactory;

    public boolean saveInvestorProfile(MasterApplicantTb investor) {
        boolean status = false;
        Integer id = (Integer) sessionFactory.getCurrentSession().save(investor);
        if (id != null) {
            status = true;
        }
        logger.log(Level.INFO, "id {0},staus {1}", new Object[]{id, status});
//        String hql = " UPDATE  TempRegistrationTb "
//                + " SET mailVerified =:mailVerified"
//                + " where email =:email";
//        Query query = sessionFactory.getCurrentSession().createQuery(hql);
//        query.setString("email", investor.getEmail());
//        query.setBoolean("mailVerified", true);
//        query.executeUpdate();
        return status;
    }

    public int saveAdvisorProfile(MasterApplicantTb advisor) {
        boolean status = false;
        Integer id = (Integer) sessionFactory.getCurrentSession().save(advisor);
//        String hql = " UPDATE  TempRegistrationTb "
//                + " SET mailVerified =:mailVerified"
//                + " where email =:email";
//        Query query = sessionFactory.getCurrentSession().createQuery(hql);
//        query.setString("email", advisor.getEmail());
//        query.setBoolean("mailVerified", true);
//        query.executeUpdate();
        logger.info("id " + id + ",staus " + status);
        return id;
    }

    public long sizeOfUsersList(String userType, String searchText) {
        String HQL;
        if (userType.equalsIgnoreCase("Investor")) {
            HQL = " SELECT COUNT(*) From MasterApplicantTb"
                    + " WHERE advisor=false AND (name LIKE :searchText or email LIKE :searchText"
                    + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText) AND isActiveUser = :Active"
                    + " ORDER BY registerDatetime,status";
        } else if (userType.equalsIgnoreCase("Advisor")) {
            HQL = " SELECT COUNT(*) From MasterApplicantTb"
                    + " WHERE advisor=true AND (name LIKE :searchText or email LIKE :searchText"
                    + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText) AND isActiveUser = :Active"
                    + " ORDER BY registerDatetime ,status";
        } else {
            HQL = " SELECT COUNT(*) From MasterApplicantTb"
                    + " WHERE name LIKE :searchText or email LIKE :searchText"
                    + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText AND isActiveUser = :Active"
                    + " ORDER BY registerDatetime DESC,status";
        }
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setParameter("searchText", searchText + "%");
        query.setBoolean("Active", Boolean.TRUE);
        List list = query.list();
        Long get = (Long) list.get(0);
        return get.longValue();
    }

    public List<MasterApplicantTb> listNewUsers(String userType, String searchText,
            int firstResultIndex, int maxResults, int userSelected) {
        String HQL;
        if (userType.equalsIgnoreCase("Investor")) {
            if (userSelected == -1) {
                HQL = " From MasterApplicantTb"
                        + " WHERE advisor=false AND (name LIKE :searchText or email LIKE :searchText"
                        + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText)"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY status DESC";
            } else {
                HQL = " From MasterApplicantTb"
                        + " WHERE advisor=false AND (name LIKE :searchText or email LIKE :searchText"
                        + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText)"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY registerDatetime DESC";
            }
        } else if (userType.equalsIgnoreCase("Advisor")) {
            if (userSelected == -1) {
                HQL = " From MasterApplicantTb "
                        + " WHERE advisor=true AND (name LIKE :searchText or email LIKE :searchText"
                        + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText)"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY status DESC";
            } else {
                HQL = " From MasterApplicantTb"
                        + " WHERE advisor=true AND (name LIKE :searchText or email LIKE :searchText"
                        + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText)"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY registerDatetime DESC";
            }
        } else {
            if (userSelected == -1) {
                HQL = " From MasterApplicantTb"
                        + " WHERE name LIKE :searchText or email LIKE :searchText"
                        + " or registrationId LIKE :searchText or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY status DESC";
            } else {
                HQL = " From MasterApplicantTb WHERE (name LIKE :searchText or email LIKE :searchText or registrationId LIKE :searchText"
                        + " or date_format(registerDatetime,'%d/%m/%Y') LIKE :searchText)"
                        + " AND isActiveUser = :Active"
                        + " AND isMailVerified = 1"
                        + " ORDER BY registerDatetime DESC";
            }
        }
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setParameter("searchText", searchText + "%");
        query.setBoolean("Active", Boolean.TRUE);
        query.setFirstResult(firstResultIndex);
        query.setMaxResults(maxResults);
        return query.list();
    }

    public List getAdvisorDetails(String regid, String username, boolean fromAdmin) {
        List responseList = new ArrayList();
        if (fromAdmin) {
            String HQL = "From MasterAdvisorTb where registrationId=:reg_id AND isActiveUser = :Active";
            Query query = sessionFactory.getCurrentSession().createQuery(HQL);
            query.setString("reg_id", regid);
            query.setBoolean("Active", Boolean.TRUE);
            return query.list();
        } else {
            String HQL = "From MasterAdvisorTb WHERE email=:username AND isActiveUser = :Active";
            Query query = sessionFactory.getCurrentSession().createQuery(HQL);
            query.setString("username", username);
            query.setBoolean("Active", Boolean.TRUE);
            responseList.add(query.list());

            String HQL2 = "From TempRegistrationTb WHERE email=:username";
            Query query2 = sessionFactory.getCurrentSession().createQuery(HQL2);
            query2.setString("username", username);
            responseList.add(query2.list());

            return responseList;
        }
    }

    public List getInvestorDetails(String regid, String username, boolean fromAdmin) {
        List responseList = new ArrayList();
        if (fromAdmin) {
            String HQL = "From MasterCustomerTb where registrationId=:reg_id AND isActiveUser = :Active";
            Query query = sessionFactory.getCurrentSession().createQuery(HQL);
            query.setString("reg_id", regid);
            query.setBoolean("Active", Boolean.TRUE);
            return query.list();
        } else {
            String HQL = "From MasterCustomerTb WHERE email=:username AND isActiveUser = :Active";
            Query query = sessionFactory.getCurrentSession().createQuery(HQL);
            query.setString("username", username);
            query.setBoolean("Active", Boolean.TRUE);
            responseList.add(query.list());

            String HQL2 = "From TempRegistrationTb WHERE email=:username";
            Query query2 = sessionFactory.getCurrentSession().createQuery(HQL2);
            query2.setString("username", username);
            responseList.add(query2.list());

            return responseList;
        }

    }

    public List getEmailStatus(String mail, String regId) {
        String HQL = "From MasterApplicantTb where email=:email and registrationId!=:regId AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", mail);
        query.setString("regId", regId);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public List getEmailStatus(String mail) {
        String HQL = "From MasterApplicantTb where email=:email AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", mail);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public List getPanNoStatusInvestor(String panNo, String regId) {
//        String HQL = "From MasterCustomerTb where panNo=:panNo and registrationId!=:regId AND isActiveUser = :Active";
        String HQL = "From MasterApplicantTb where pan=:panNo and registrationId!=:regId";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("panNo", panNo);
        query.setString("regId", regId);
//        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public List getPanNoStatusAdvisor(String panNo, String regId) {
        String HQL = "From MasterAdvisorTb where panNo=:panNo and registrationId!=:regId AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("panNo", panNo);
        query.setString("regId", regId);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Object[] getLinkedinUserStatus(String linkedinMemberId) {
        String HQL = " SELECT COUNT(linkedinMemberId),linkedinUser, email From MasterApplicantTb"
                + " where linkedinMemberId =:linkedinMemberId AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("linkedinMemberId", linkedinMemberId);
        query.setBoolean("Active", Boolean.TRUE);
        return (Object[]) query.uniqueResult();
    }

    public int updateExpireInDet(String username, String expirein, Date linkedinExpireInDate, String accesstoken) {
        String HQL = "UPDATE MasterApplicantTb SET  linkedinExpireIn =:expirein,linkedinExpireDate =:linkedinExpireInDate,linkedinPassword =:accesstoken where email=:username ";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("username", username);
        query.setString("expirein", expirein);
        query.setDate("linkedinExpireInDate", linkedinExpireInDate);
        query.setString("accesstoken", accesstoken);
        return query.executeUpdate();

    }

    public List<MasterCustomerTb> getAllUsers() {
        String HQL = "From MasterCustomerTb WHERE isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setBoolean("Active", Boolean.TRUE);
        return query.list();
    }

    public List<CitiesTb> getCityList(String countryCode) {
        String hql = "From CitiesTb WHERE countryCode = :CountryCode ORDER BY cityName ASC";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("CountryCode", countryCode);
        return query.list();
    }

    public Integer checkEmailVerified(MasterApplicantTb masterApplicantTb) {
        Integer rowCount;
        String hql = " SELECT COUNT(*) FROM MasterApplicantTb"
                + " WHERE verificationCode = :VerficationCode AND dob = :DOB"
                + " AND mobile = :Mobile AND email = :Email AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("VerficationCode", masterApplicantTb.getVerificationCode());
        query.setDate("DOB", masterApplicantTb.getDob());
        query.setString("Mobile", masterApplicantTb.getMobile());
        query.setString("Email", masterApplicantTb.getEmail());
        query.setBoolean("Active", Boolean.TRUE);
        rowCount = Integer.parseInt(query.uniqueResult().toString());
        if (rowCount.equals(ONE)) {
            hql = "UPDATE MasterApplicantTb"
                    + " SET isMailVerified = :IsMailVerified,"
                    + " status = :Status"
                    + " WHERE verificationCode = :VerficationCode AND dob = :DOB"
                    + " AND mobile = :Mobile AND email = :Email";
            query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setString("VerficationCode", masterApplicantTb.getVerificationCode());
            query.setBoolean("IsMailVerified", masterApplicantTb.isIsMailVerified());
            query.setInteger("Status", masterApplicantTb.getStatus());
            query.setDate("DOB", masterApplicantTb.getDob());
            query.setString("Mobile", masterApplicantTb.getMobile());
            query.setString("Email", masterApplicantTb.getEmail());
            query.executeUpdate();
        }
        return rowCount;
    }

    public List<Object> getUserProfile(Integer userId, Boolean isAdvisor) {
        String hql;
        Query query;
        if (isAdvisor) {
            hql = " FROM MasterAdvisorTb"
                    + " WHERE advisorId = :AdvisorId AND isActiveUser = :Active";
            query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("AdvisorId", userId);
            query.setBoolean("Active", Boolean.TRUE);
            return query.list();
        } else {
            hql = " FROM MasterCustomerTb"
                    + " WHERE customerId = :CustomerId AND isActiveUser = :Active";
            query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("CustomerId", userId);
            query.setBoolean("Active", Boolean.TRUE);
            return query.list();
        }
    }

    public Integer deleteAdvisor(MasterAdvisorTb masterAdvisorTb, Boolean isHardDelete) throws Exception {
        String hql;
        Query query;
        try {
            if (isHardDelete) {

                String sql = " CALL delete_advisor_proc(:UserId,:MasterApplicantRegId)";
                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
                sqlQuery.setInteger("UserId", masterAdvisorTb.getAdvisorId());
                sqlQuery.setString("MasterApplicantRegId", masterAdvisorTb.getMasterApplicantTb().getRegistrationId());
                return sqlQuery.executeUpdate();
            } else {
                hql = " UPDATE  MasterAdvisorTb"
                        + " SET isActiveUser = :IsActiveUser,status=:Status"
                        + " WHERE advisorId = :AdvisorId";
                query = sessionFactory.getCurrentSession().createQuery(hql);
                query.setInteger("AdvisorId", masterAdvisorTb.getAdvisorId());
                query.setBoolean("IsActiveUser", masterAdvisorTb.isIsActiveUser());
                query.setInteger("Status", masterAdvisorTb.getMasterApplicantTb().getStatus());
                query.executeUpdate();

                hql = " UPDATE  MasterApplicantTb"
                        + " SET isActiveUser = :IsActiveUser,status=:Status"
                        + " WHERE id = :Id";
                query = sessionFactory.getCurrentSession().createQuery(hql);
                query.setInteger("Id", masterAdvisorTb.getMasterApplicantTb().getId());
                query.setBoolean("IsActiveUser", masterAdvisorTb.isIsActiveUser());
                query.setInteger("Status", masterAdvisorTb.getMasterApplicantTb().getStatus());
                return query.executeUpdate();

            }
        } catch (HibernateException ex) {
            throw new Exception(ex);
        } catch (NullPointerException ex) {
            throw new Exception(ex);
        }
    }

    public Integer deleteCustomer(MasterCustomerTb masterCustomerTb, Boolean isHardDelete) throws Exception {
        String hql;
        Query query;
        try {

            if (isHardDelete) {

                String sql = " CALL delete_customer_proc(:UserId,:MasterApplicantRegId,:IsFromAdvisorDelete)";
                SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
                sqlQuery.setInteger("UserId", masterCustomerTb.getCustomerId());
                sqlQuery.setString("MasterApplicantRegId", masterCustomerTb.getMasterApplicantTb().getRegistrationId());
                sqlQuery.setBoolean("IsFromAdvisorDelete", Boolean.FALSE);
                return sqlQuery.executeUpdate();
            } else {
                hql = " UPDATE  MasterCustomerTb"
                        + " SET isActiveUser = :IsActiveUser,status=:Status"
                        + " WHERE customerId = :CustomerId";
                query = sessionFactory.getCurrentSession().createQuery(hql);
                query.setInteger("CustomerId", masterCustomerTb.getCustomerId());
                query.setInteger("Status", masterCustomerTb.getMasterApplicantTb().getStatus());
                query.setBoolean("IsActiveUser", masterCustomerTb.isIsActiveUser());
                query.executeUpdate();

                hql = " UPDATE  MasterApplicantTb"
                        + " SET isActiveUser = :IsActiveUser,status=:Status"
                        + " WHERE id = :Id";
                query = sessionFactory.getCurrentSession().createQuery(hql);
                query.setInteger("Id", masterCustomerTb.getMasterApplicantTb().getId());
                query.setBoolean("IsActiveUser", masterCustomerTb.isIsActiveUser());
                query.setInteger("Status", masterCustomerTb.getMasterApplicantTb().getStatus());
                return query.executeUpdate();

            }
        } catch (HibernateException ex) {
            throw new Exception(ex);
        } catch (NullPointerException ex) {
            throw new Exception(ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean saveTempUserProfile(TempRegistrationTb tempRegistration) {
        boolean status = false;
        Integer id = (Integer) sessionFactory.getCurrentSession().save(tempRegistration);
        if (id != null) {
            status = true;
        }
        logger.info("id " + id + ",staus " + status);
        return status;
    }

    public Integer emailVerification(String verificationCode, String email) {
        Integer rowCount;
        String hql = " SELECT COUNT(*) FROM TempRegistrationTb"
                + " WHERE verificationCode = :VerficationCode AND email = :Email"
                + " AND mailVerified = :IsMailVerified";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("VerficationCode", verificationCode);
        query.setBoolean("IsMailVerified", false);
        query.setString("Email", email);
        rowCount = Integer.parseInt(query.uniqueResult().toString());
        if (rowCount.equals(ONE)) {
            hql = "UPDATE TempRegistrationTb"
                    + " SET mailVerified = :IsMailVerified"
                    + " WHERE verificationCode = :VerficationCode"
                    + " AND email = :Email";
            query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setString("VerficationCode", verificationCode);
            query.setBoolean("IsMailVerified", true);
            query.setString("Email", email);
            query.executeUpdate();

        }
        return rowCount;
    }

    public List getEmailStatusTempUser(String mail) {
        String HQL = "From TempRegistrationTb where email=:email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", mail);
        return query.list();
    }

    public Object getTemporaryUserDetails(String email) {
        String HQL = "FROM MasterApplicantTb WHERE email =:email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", email);
        return (Object) query.uniqueResult();

    }

    public boolean updateProfile(MasterApplicantTb master) {
        boolean status = false;
        try {
            sessionFactory.getCurrentSession().update(master);
            status = true;
        } catch (HibernateException ex) {
            status = false;
        }
        return status;
    }

    public void updateMailSendStatus(String tablename, String email, String mailStatus) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List getSendMailFailedList(String tableName, String searchText) {
        if (tableName.equalsIgnoreCase("ALL")) {
            String hql = "FROM TempRegistrationTb"
                    + " WHERE email NOT IN (SELECT T2.email"
                    + " FROM MasterApplicantTb T2 GROUP BY T2.email) ORDER BY id DESC";//AND mailSendSuccess = true
            Query sqlqry = sessionFactory.getCurrentSession().createQuery(hql);
            return sqlqry.list();
        } else {
            String sql = "SELECT * FROM " + tableName + " WHERE email LIKE :searchText ORDER BY CAST(`registration_id` AS UNSIGNED) DESC ";//+ " WHERE mailSendSuccess=:status";
            Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            query.setParameter("searchText", searchText + "%");
            return query.list();
        }
    }

    public void saveMandateForm(MandateFormTb mandate) {
        boolean status = false;
        try {
            Integer id = (Integer) sessionFactory.getCurrentSession().save(mandate);
            if (id != null) {
                status = true;
            }
        } catch (HibernateException ex) {
            status = false;
        }
    }

    public List<Object> getMandateTbForRegId(String regId, boolean flag) {
        List<Object> resultSet = new ArrayList<Object>();
        String HQL = "FROM MandateFormTb WHERE registrationId =:regId";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("regId", regId);
        resultSet.add((MandateFormTb) query.uniqueResult());

        if (flag) {
            HQL = "FROM EcsTransmittalSheetTb WHERE registrationId =:regId";
            query = sessionFactory.getCurrentSession().createQuery(HQL);
            query.setString("regId", regId);
            resultSet.add((EcsTransmittalSheetTb) query.uniqueResult());
        }

        return resultSet;
    }

    public List<Object> getUserDetails(String email, Boolean isAdvisor) {

        List<Object> resultSet = new ArrayList<Object>();

        String hql = "FROM MasterApplicantTb WHERE email =:email";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("email", email);
        resultSet.add(query.uniqueResult());
        if (isAdvisor) {
            String hql2 = " FROM MasterAdvisorTb WHERE email = :email";
            Query query2 = sessionFactory.getCurrentSession().createQuery(hql2);
            query2.setString("email", email);
            resultSet.add(query2.uniqueResult());
        } else {
            String hql3 = " FROM MasterCustomerTb WHERE email = :email ";
            Query query3 = sessionFactory.getCurrentSession().createQuery(hql3);
            query3.setString("email", email);
            resultSet.add(query3.uniqueResult());
        }
        return resultSet;
    }

    public void updateLinkedInData(List<Object> linkedIndata, boolean isAdvisor) {
        MasterApplicantTb master = (MasterApplicantTb) linkedIndata.get(0);
        sessionFactory.getCurrentSession().update(master);
        if (isAdvisor) {
            MasterAdvisorTb masterAdvisor = (MasterAdvisorTb) linkedIndata.get(1);
            sessionFactory.getCurrentSession().update(masterAdvisor);
        } else {
            MasterCustomerTb masterCustomer = (MasterCustomerTb) linkedIndata.get(1);
            sessionFactory.getCurrentSession().update(masterCustomer);
        }
    }

    public void updateLinkedInStatus(String email, Boolean advisor) {
        String hql = " UPDATE  MasterApplicantTb"
                + " SET linkedInConnected = :linkedin,linkedInSkipped =:linkedInSkipped"
                + " WHERE email = :email AND  advisor = :advisor";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("email", email);
        query.setBoolean("linkedin", false);
        query.setBoolean("linkedInSkipped", true);
        query.setBoolean("advisor", advisor);
        query.executeUpdate();
    }

    public boolean saveInvestorNomineeProfile(InvestorNomineeDetailsTb nominee) {
        boolean status = false;
        Integer id = (Integer) sessionFactory.getCurrentSession().save(nominee);
        if (id != null) {
            status = true;
        }
        return status;
    }

    /**
     * retrieve investor nomination details
     *
     * @param registrationId
     * @return
     */
    public InvestorNomineeDetailsTb getNominationDeatails(String registrationId) {
        String HQL = "From InvestorNomineeDetailsTb"
                + " WHERE registrationId=:registrationId";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("registrationId", registrationId);
        return (InvestorNomineeDetailsTb) query.uniqueResult();
    }

    public boolean saveAdvisorQualification(MasterAdvisorQualificationTb qualificationT) {
        boolean status = false;
        Integer id = (Integer) sessionFactory.getCurrentSession().save(qualificationT);
        if (id != null) {
            status = true;
        }
        return status;
    }

    public MasterAdvisorQualificationTb getMasterAdvisorQualificationDeatails(String registrationId) {
        String HQL = "From MasterAdvisorQualificationTb"
                + " WHERE registrationId=:registrationId";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("registrationId", registrationId);
        return (MasterAdvisorQualificationTb) query.uniqueResult();
    }

    public Object getMasterApplicant(String regNo) {
        String HQL = "FROM MasterApplicantTb WHERE registrationId =:regNo";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("regNo", regNo);
        return (Object) query.uniqueResult();

    }

    public void saveTempAdv(TempAdv tempmaster) {
        sessionFactory.getCurrentSession().saveOrUpdate(tempmaster);
    }

    public TempAdv getTempAdvDetails(String email) {
        String HQL = "From TempAdv"
                + " WHERE email=:email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", email);
        return (TempAdv) query.uniqueResult();
    }

    public TempInv getTempInvDetails(String email) {
        String HQL = "From TempInv"
                + " WHERE email=:email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("email", email);
        return (TempInv) query.uniqueResult();
    }

    public void saveTempInvestor(TempInv investor) {
        sessionFactory.getCurrentSession().saveOrUpdate(investor);
    }

    public List<Object> getAllAllocation() {
        List<Object> responseList = new ArrayList<Object>();
        Integer kitNmbr = 0;
        String sql = "SELECT MAX(T1.kit_number) FROM master_applicant_tb T1,kit_allocation_tb T2"
                + " WHERE T2.`kit_status` = :status AND "
                + " T1.kit_number BETWEEN T2.fromValue AND T2.toValue";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setString("status", "IN USE");
        if (query.uniqueResult() != null) {
            kitNmbr = (Integer) query.uniqueResult();
        }

        String hql = "FROM KitAllocationTb";
        Query hqlQry = sessionFactory.getCurrentSession().createQuery(hql);
        responseList.add(hqlQry.list());
        responseList.add(kitNmbr);
        return responseList;
    }

    public void updateKitAllocationStatus(Integer regKitId) {
        Integer rowId = 0;
        String sql = "SELECT id FROM `kit_allocation_tb` "
                + " WHERE id > :firstId ORDER BY id ASC LIMIT 1";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("firstId", regKitId);
        if (query.uniqueResult() != null) {
            rowId = Integer.parseInt(query.uniqueResult().toString());
        }

        if (rowId != 0) {
            String sql2 = "UPDATE `kit_allocation_tb` SET `kit_status` = CASE "
                    + " WHEN id = :firstId THEN :status1 "
                    + " WHEN id = :secondId THEN :status2 "
                    + " END  WHERE id  IN (:firstId,:secondId)";
            query = sessionFactory.getCurrentSession().createSQLQuery(sql2);
            query.setString("status1", "CLOSED");
            query.setString("status2", "IN USE");
            query.setInteger("firstId", regKitId);
            query.setInteger("secondId", rowId);
            query.executeUpdate();
        } else {
            String sql3 = "UPDATE `kit_allocation_tb` SET `kit_status` = :closed "
                    + " WHERE id = :kitId";
            query = sessionFactory.getCurrentSession().createSQLQuery(sql3);
            query.setString("closed", "CLOSED");
            query.setInteger("kitId", regKitId);
            query.executeUpdate();

            //notifying admin only if no new kits series are available.
            sendNotification(100);
        }

    }

    public List<Object> getAllRegistrationData(Integer kitNumber) {
        List<Object> responseList = new ArrayList<Object>();
        String hql = "FROM MasterApplicantTb where advisor = :status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("status", false);
        responseList.add(query.list());
        return responseList;
    }

    public void sendNotification(Integer status) {
        String hql = "UPDATE AdminNotificationTb SET adminViewed = :adminViewed,"
                + " notifyAdmin = :notifyAdmin, notificationDate = :date"
                + " WHERE statusCode = :status";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setBoolean("adminViewed", false);
        query.setBoolean("notifyAdmin", true);
        query.setInteger("status", status);
        query.setDate("date", new Date());
        query.executeUpdate();
    }

    public int updateSettings(MmfSettingsTb settingsTb, boolean save) {
        Integer rowId = 0;
        if (save) {
            rowId = (Integer) sessionFactory.getCurrentSession().save(settingsTb);
            if (rowId == null) {
                rowId = 0;
            }
        } else {
            String sql = "UPDATE mmf_settings_tb SET fieldvalue = :value"
                    + " WHERE fieldname = :key";
            SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
            query.setString("key", settingsTb.getFieldname());
            query.setString("value", settingsTb.getFieldvalue());
            rowId = query.executeUpdate();
        }

        return rowId;
    }

    public List<ExchangeHolidayTb> getHolidayCalender() {
        Query hqlQry;
        String hql3 = "FROM ExchangeHolidayTb";
        hqlQry = sessionFactory.getCurrentSession().createQuery(hql3);
        return hqlQry.list();
    }

    public void saveHoliday(ExchangeHolidayTb holidayTb) {
        sessionFactory.getCurrentSession().saveOrUpdate(holidayTb);
    }

    public ExchangeHolidayTb CheckForUpdationHoliday(Date hdate) {
        SQLQuery sqlq;
        String hql4 = "SELECT * FROM exchange_holiday_tb WHERE DATE_FORMAT(hdate,'%Y-%m-%d')= DATE_FORMAT(:date,'%Y-%m-%d')";
        sqlq = sessionFactory.getCurrentSession().createSQLQuery(hql4);
        Calendar cal = Calendar.getInstance();
        cal.setTime(hdate);
        sqlq.setDate("date", cal.getTime());
        return (ExchangeHolidayTb) sqlq.uniqueResult();
    }

    public void deleteHoliday(ExchangeHolidayTb holidayTb) {
        sessionFactory.getCurrentSession().delete(holidayTb);
        sessionFactory.getCurrentSession().flush();
    }

    public TempRegistrationTb getTempRegUserDetails(String email, String userType) {
        String hql3 = "FROM TempRegistrationTb WHERE email =:email AND userType =:type";
        Query hqlQry = sessionFactory.getCurrentSession().createQuery(hql3);
        hqlQry.setString("email", email);
        hqlQry.setString("type", userType);
        return (TempRegistrationTb) hqlQry.uniqueResult();
    }

    /*public List<Object[]> getBankDetails(String searchText) {
        String sql = "SELECT a.*,b.micr FROM (SELECT * FROM bank_tb WHERE ifsc LIKE :searchText) AS a LEFT JOIN ifc_micr_mapping_tb AS b ON a.ifsc = b.ifsc";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setString("searchText", searchText + "%");
        return query.list();
    }*/

    public List hasPortfolioMapped(String registrationId) {
        String SQL = "SELECT b.`customer_id`  FROM  `customer_portfolio_tb` b,`master_customer_tb` a "
                + "WHERE  b.`customer_id` = a.`customer_id` AND a.`registration_id` = :registrationId";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
        sqlQuery.setString("registrationId", registrationId);
        return sqlQuery.list();
    }

    public List<Object[]> getCustomerPortfolioDetails(Integer customerid) {
        String SQL = "SELECT a.`current_value`,a.`cash_amount`,SUM(b.`current_price`* b.`exe_units`) AS holdingValue "
                + "FROM `customer_portfolio_tb` a,`customer_portfolio_securities_tb` b "
                + "WHERE a.`customer_id` = :customerid   AND  a.`customer_portfolio_id` = b.`customer_portfolio_id`";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
        sqlQuery.setParameter("customerid", customerid);
        return sqlQuery.list();
    }

    public List getCustomerDetails(String registrationId) {
        String SQL = "SELECT `total_funds` FROM `master_customer_tb` WHERE `registration_id` = :registrationId";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
        sqlQuery.setParameter("registrationId", registrationId);
        return sqlQuery.list();
    }

    public String getStateCode(String stateName) {
        String statecode = "";
        String SQL = "SELECT `stateCode` FROM `state_tb` WHERE`stateName` = :stateName LIMIT 1";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(SQL);
        sqlQuery.setString("stateName", stateName);
        statecode = sqlQuery.list().get(0).toString();
        return statecode;
    }

    public boolean getPanNoStatus(String pan) {
        String HQL = "From MasterApplicantTb where pan=:panNo AND isActiveUser = :Active";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setString("panNo", pan);
        query.setBoolean("Active", Boolean.TRUE);
        if (!query.list().isEmpty()) {
            //pan already used
            return true;
        } else {
            return false;
        }

    }

    public boolean isEmailAlreadyRegistered(String email) {
        String hql3 = "FROM TempRegistrationTb WHERE email =:email";
        Query hqlQry = sessionFactory.getCurrentSession().createQuery(hql3);
        hqlQry.setString("email", email);
        if (hqlQry.list().isEmpty()) {
            //email not registered
            return false;
        } else {
            return true;
        }
    }

    public boolean isApplicantDetailsSubmitted(String email, String userType) {
        boolean advisor = userType.equals("INVESTOR") ? false : true;
        String HQL = " From MasterApplicantTb"
                + " WHERE advisor=:Advisor "
                + " AND isActiveUser = :Active"
                + " AND isMailVerified = :mailVerified"
                + " AND email = :Email";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setBoolean("Advisor", advisor);
        query.setBoolean("mailVerified", Boolean.FALSE);
        query.setBoolean("Active", Boolean.TRUE);
        query.setString("Email", email);
        if (query.list().isEmpty()) {
            //details not submitted
            return false;
        } else {
            return true;
        }
    }

    public void updateApplicantMailVerifed(String email, String userType) {
        boolean advisor = userType.equals("INVESTOR") ? false : true;
        String hql = " UPDATE  MasterApplicantTb "
                + " SET isMailVerified =:mailVerified"
                + " where email =:email"
                + " AND advisor =:advisor";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("email", email);
        query.setBoolean("advisor", advisor);
        query.setBoolean("mailVerified", true);
        query.executeUpdate();
    }

    public Integer getRelationIdOfUser(int userId) {
        String sql = "SELECT `relation_id` FROM `customer_advisor_mapping_tb` WHERE `relation_status` NOT IN(2,403,310,300,9,15,200,210) AND `customer_id` =:userId";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("userId", userId);
        return (Integer) query.uniqueResult();
    }

    public Integer updateContractTerminateStatus(int relationId) {
        String sql = "UPDATE `customer_advisor_mapping_tb` SET `contract_terminate_status` =:contractteminateStatus WHERE `relation_id`=:relationId";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setInteger("contractteminateStatus", 1);
        query.setInteger("relationId", relationId);
        return query.executeUpdate();
    }
    
    public CustomerAdvisorMappingTb getTerminationDetailsOfUser(int relationId){
        String hql = "FROM CustomerAdvisorMappingTb WHERE relationId =:relationId";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setInteger("relationId", relationId);
        return (CustomerAdvisorMappingTb) query.uniqueResult();
    }
    
    public void saveInvestorDocumentDetails(MasterApplicantTb master){
       if(master != null) {
           String hql = "UPDATE MasterApplicantTb SET identityProofPath = :id_proof_path,"
                   + "correspondenceAddressPath =:caddresspath,permanentAddressPath =:paddresspth "
                   + "WHERE email=:Email_Id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id_proof_path", master.getIdentityProofPath());
        query.setString("caddresspath", master.getCorrespondenceAddressPath());
        query.setString("paddresspth", master.getPermanentAddressPath());
        query.setString("Email_Id", master.getEmail());
        query.executeUpdate();
       }
    }

	/**
	 * @author Sumeet 
	 * save file path in db with email
	 */
	public void saveProofFileToDb(InvProofFileDetailsTb ipfd) {
		sessionFactory.getCurrentSession().saveOrUpdate(ipfd);
		
	}

	public InvProofFileDetailsTb getInvProofFileDetails(String email) {
		String hql = "FROM InvProofFileDetailsTb where email = :email";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("email", email);
		if(query.list().isEmpty()){
        	return null;
        }
        else{
        	return (InvProofFileDetailsTb) query.list().get(0);
        }
	}
}
