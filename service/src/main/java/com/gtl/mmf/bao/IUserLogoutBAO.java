package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.NotificationAdminListVO;

public interface IUserLogoutBAO {

    public int userLogout(String userType, Integer userId);

    public NotificationAdminListVO getAdminNotifications();

}
