package com.gtl.mmf.dao;

import com.gtl.mmf.entity.AdminNotificationTb;
import com.gtl.mmf.entity.AdminuserTb;
import java.util.List;

public interface IUserLogoutDAO {

    public int advisorLogout(Integer advisorId);

    public int investorLogout(Integer customerId);

    public AdminuserTb getAdminNotificationDetails();

    public List<AdminNotificationTb> getAllAdminNotification();
}
