package com.gtl.mmf.bao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IUserLogoutBAO;
import com.gtl.mmf.common.EnumAdminNotificationStatus;
import com.gtl.mmf.dao.IUserLogoutDAO;
import com.gtl.mmf.entity.AdminNotificationTb;
import com.gtl.mmf.entity.AdminuserTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.BTN_VIEW;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.vo.AdminNotificationVO;
import com.gtl.mmf.service.vo.NotificationAdminListVO;
import java.util.ArrayList;
import java.util.List;

public class UserLogoutBAOImpl implements IUserLogoutBAO, IConstants {

    @Autowired
    private IUserLogoutDAO userLogoutDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int userLogout(String userType, Integer userId) {
        int updatedRows = ZERO;
        if (userType.equalsIgnoreCase(ADVISOR)) {
            updatedRows = userLogoutDAO.advisorLogout(userId);
        } else if (userType.equalsIgnoreCase(INVESTOR)) {
            updatedRows = userLogoutDAO.investorLogout(userId);
        }
        return updatedRows;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public NotificationAdminListVO getAdminNotifications() {
        NotificationAdminListVO notificationAdminListVO = new NotificationAdminListVO();
        List<AdminNotificationVO> adminNotificationsList = new ArrayList<AdminNotificationVO>();
        List<AdminNotificationVO> adminNotificationsListPopup = new ArrayList<AdminNotificationVO>();
        List<AdminNotificationTb> adminNotificationTbList = userLogoutDAO.getAllAdminNotification();

        for (AdminNotificationTb entry : adminNotificationTbList) {
            if (entry.isNotifyAdmin()) {
                EnumAdminNotificationStatus adminStatus = EnumAdminNotificationStatus.fromInt(entry.getStatusCode());
                if (getAdminNotificationStatusList().contains(adminStatus)) {
                    AdminNotificationVO notification = new AdminNotificationVO();
                    String notificationMsg = InvestorNotificationMsgService.getAdminNotificationMessage(entry.getStatusCode());
                    notification.setStatusCode(ZERO);
                    notification.setNotificationStatus(entry.getNotificationStatus());
                    notification.setStatusCode(entry.getStatusCode());
                    notification.setButtonText(BTN_VIEW);
                    notification.setNotificationMessage(notificationMsg); 
                    notification.setNotificationDate(entry.getNotificationDate());
                    notification.setEnumAdminNotificationStatus(adminStatus); 
                    //Setting elapsed notification time to display in the popup
                    notification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(notification.getNotificationDate()));
                    adminNotificationsList.add(notification);
                    if (entry.isNotifyAdmin() && !entry.isAdminViewed()) {
                        adminNotificationsListPopup.add(notification);
                    }
                    notificationAdminListVO.setNotifications(adminNotificationsList);
                    notificationAdminListVO.setNotificationsPopup(adminNotificationsListPopup);
                }
            }
        }
        return notificationAdminListVO;
    }

    /**
     * This method returns a list which contains the status of Admin
     * notifications to be displayed on the popup
     *
     * @return List of customer notification
     */
    private static List<EnumAdminNotificationStatus> getAdminNotificationStatusList() {
        List<EnumAdminNotificationStatus> adminNotificationStatuses = new ArrayList<EnumAdminNotificationStatus>();
        adminNotificationStatuses.add(EnumAdminNotificationStatus.REG_KIT_INADEQUATE);
        adminNotificationStatuses.add(EnumAdminNotificationStatus.REG_KIT_OUT_OF_STOCK);
        adminNotificationStatuses.add(EnumAdminNotificationStatus.UPDATE_HOLIDAY_CALENDER);
        return adminNotificationStatuses;
    }
}
