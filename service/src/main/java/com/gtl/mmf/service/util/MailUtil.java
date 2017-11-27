/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.entity.TempRegistrationTb;
import static com.gtl.mmf.service.util.IConstants.AMPERSAND;
import static com.gtl.mmf.service.util.IConstants.EMAIL;
import static com.gtl.mmf.service.util.IConstants.EQ_SIGN;
import static com.gtl.mmf.service.util.IConstants.QUESTION_MARK;
import static com.gtl.mmf.service.util.IConstants.USERTYPE;
import static com.gtl.mmf.service.util.IConstants.VERIFICATION_MAIL_URL;
import static com.gtl.mmf.service.util.IConstants.VERIFICATION_MAIL_URL_PARAM_1;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.StringUtils;
import javax.activation.DataHandler;

/**
 *
 * @author 07960
 */
public final class MailUtil implements IConstants, IEmailUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.MailUtil");
    private static final String PDF = "pdf";

    private MailUtil() {
    }
    private static MailUtil instance;

    public static MailUtil getInstance() {
        if (instance == null) {
            synchronized (MailUtil.class) {
                instance = new MailUtil();
            }
        }
        return instance;
    }

    public Session getSession() {
        Properties emailConfigProp = new Properties();
        EmailAuthenticator emailAuthenticator = new EmailAuthenticator(
                PropertiesLoader.getPropertiesValue(MAIL_SMTP_USERNAME),
                PropertiesLoader.getPropertiesValue(MAIL_SMTP_PASSWORD));

        emailConfigProp.setProperty(MAIL_SMTP_SUBMITTER,
                emailAuthenticator.getPasswordAuthentication().getUserName());
        emailConfigProp.setProperty(MAIL_SMTP_AUTH,
                PropertiesLoader.getPropertiesValue(MAIL_SMTP_AUTH));
        emailConfigProp.setProperty(MAIL_SMTP_HOST,
                PropertiesLoader.getPropertiesValue(MAIL_SMTP_HOST));
        emailConfigProp.setProperty(MAIL_SMTP_PORT,
                PropertiesLoader.getPropertiesValue(MAIL_SMTP_PORT));
        return Session.getInstance(emailConfigProp, emailAuthenticator);
    }

    public boolean sendMail(String from, List<String> to, String subject,
            String content) throws ClassNotFoundException, UnsupportedEncodingException {
        isArgumentValid(from, to.get(ZERO), subject, content);
        return cunstructAndSend(from, to, subject, content);
    }

    private boolean cunstructAndSend(String from, List<String> to,
            String subject, String content) throws ClassNotFoundException, UnsupportedEncodingException {
        boolean status = false;
        try {
            Message message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO, converToInternetAddresses(to));
            message.addFrom(new InternetAddress[]{new InternetAddress(from, EMAIL_FROM_NAME)});
            message.setSubject(subject);
            BodyPart bodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart(RELATED);
            bodyPart.setContent(content, CONTENT_TYPE);
            multipart.addBodyPart(bodyPart);
            
            //Attaching MMF Transactional email template.
            multipart = attachTransactionalEmailTemplate(multipart);
            
            message.setContent(multipart);
            Transport.send(message);
            status = true;
        } catch (AddressException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MessagingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));;
        } catch (IOException ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    private void isArgumentValid(String from, String to, String subject,
            String content) {
        if (!StringUtils.hasLength(from) || !StringUtils.hasLength(to) || !StringUtils.hasLength(subject)
                || !StringUtils.hasLength(content)) {
            throw new IllegalArgumentException("Invalid argument: "
                    + "Values for, parameters from"
                    + "/to/subject/content is missing.");
        }
    }

    private InternetAddress[] converToInternetAddresses(List<String> addresses)
            throws AddressException {
        int counter = 0;
        InternetAddress[] addressTo = new InternetAddress[addresses.size()];
        for (String toAddress : addresses) {
            addressTo[counter] = new InternetAddress(toAddress);
            counter++;
        }
        return addressTo;
    }

    public void sendmail(String email, String password, String firstName, String reg_id) throws ClassNotFoundException {
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();

            Template template = ve.getTemplate("email.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("email", email);
            // context.put("password", password);
            context.put("registrationNo", reg_id);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            instance.sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
                    MMF_INITIAL_REGISTRATION_SUBJECT), sw.toString());
//            instance.sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress, PropertiesLoader.getPropertiesValue(
//                    MMF_INITIAL_PASSWORD_SUBJECT), sw.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void sendNotificationMail(String firstName, String email, String notificationMsg) throws ClassNotFoundException {
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();

            Template template = ve.getTemplate("NotificationMail.vm");
            VelocityContext context = TemplateUtil.getInstance().getVelocityContext();
            context.put("newline", NEW_LINE);
            context.put("name", firstName);
            context.put("content", notificationMsg);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress,
                    PropertiesLoader.getPropertiesValue(MMF_NOTIFICATION), sw.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public void senRebalanceNotification(String name, String email, String portfolioName, boolean advisorUser) throws ClassNotFoundException {
        try {
            StringWriter sw = new StringWriter();
            VelocityEngine ve = TemplateUtil.getInstance().getVelocityEngine();
            Template template;
            VelocityContext context;
            if (advisorUser) {
                template = ve.getTemplate("RebalanceAdvisor.vm");
                context = TemplateUtil.getInstance().getVelocityContext();
                context.put("portfolioName", portfolioName);
            } else {
                template = ve.getTemplate("RebalanceInvestor.vm");
                context = TemplateUtil.getInstance().getVelocityContext();
            }
            context.put("name", name);
            context.put("newline", NEW_LINE);
            template.merge(context, sw);
            List<String> toAddress = new ArrayList<String>();
            toAddress.add(email);
            MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), toAddress,
                    PropertiesLoader.getPropertiesValue(MMF_NOTIFICATION), sw.toString());
        } catch (UnsupportedEncodingException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    public boolean sendMandateFormMail(String from, List<String> to, String subject,
            String content, String regid, Boolean investor) throws ClassNotFoundException, UnsupportedEncodingException {
        isArgumentValid(from, to.get(0), subject, content);
        boolean status = false;
        try {
            Message message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO,
                    converToInternetAddresses(to));
            message.addFrom(new InternetAddress[]{new InternetAddress(from, EMAIL_FROM_NAME)});
            message.setSubject(subject);
            BodyPart bodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart(RELATED);
            bodyPart.setContent(content, CONTENT_TYPE);
            multipart.addBodyPart(bodyPart);
            
            //Attaching MMF Transactional email template.
            multipart = attachTransactionalEmailTemplate(multipart);
            
            MimeBodyPart ecsAttachment = new MimeBodyPart();
            StringBuilder mandatefilePath = new StringBuilder();
            mandatefilePath = mandatefilePath.append(LookupDataLoader.getResourcePath())
                    .append(FILE_PATH).append(regid).append(MANDATEFILE).append(DOT).append(PDF);
            ecsAttachment.attachFile(mandatefilePath.toString());
            multipart.addBodyPart(ecsAttachment);
            Transport.send(message);
            status = true;
        } catch (AddressException aex) {
            LOGGER.log(Level.SEVERE, null, aex);
        } catch (MessagingException mex) {
            LOGGER.log(Level.SEVERE, null, mex);
        } catch (IOException ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;

    }

    public boolean sendRegMail(String from, List<String> to, String subject,
            String content, String regid, Boolean investor) throws ClassNotFoundException, UnsupportedEncodingException {
        isArgumentValid(from, to.get(0), subject, content);
        boolean status = false;
        try {
            Message message = new MimeMessage(getSession());
            message.setRecipients(Message.RecipientType.TO,
                    converToInternetAddresses(to));
            message.addFrom(new InternetAddress[]{new InternetAddress(from, EMAIL_FROM_NAME)});
            message.setSubject(subject);
            BodyPart bodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart(RELATED);
            bodyPart.setContent(content, CONTENT_TYPE);
            multipart.addBodyPart(bodyPart);
            
            //Attaching MMF Transactional email template.
            multipart = attachTransactionalEmailTemplate(multipart);
            
            if (investor) {
                MimeBodyPart attachment = new MimeBodyPart();
                MimeBodyPart attachment2 = new MimeBodyPart();
                StringBuilder filePath = new StringBuilder();
                filePath = filePath.append(LookupDataLoader.getResourcePath())
                        .append(FILE_PATH).append(regid).append(DOT).append(PDF);
                StringBuilder filePath2 = new StringBuilder();
                filePath2 = filePath2.append(LookupDataLoader.getResourcePath())
                        .append(SINGLE_SLASH).append(RIGHTS_AND_OBLIGATIONS_DOCUMENT).append(DOT).append(PDF);
                attachment.attachFile(filePath.toString());
                attachment2.attachFile(filePath2.toString());
                multipart.addBodyPart(attachment);
                multipart.addBodyPart(attachment2);
                message.setContent(multipart);
            }
            Transport.send(message);
            status = true;
        } catch (AddressException aex) {
            LOGGER.log(Level.SEVERE, null, aex);
        } catch (MessagingException mex) {
            LOGGER.log(Level.SEVERE, null, mex);
        } catch (IOException ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;

    }

    /**
     * method to generate user verification URL
     *
     * @param tempRegistrationTb
     * @return
     */
    public String generateVerificationURL(TempRegistrationTb tempRegistrationTb) {
        StringBuilder url = new StringBuilder();
        String encryptedRegId = BASE64Encrption.encrypt(tempRegistrationTb.getVerificationCode());
        String encryptedEmail = BASE64Encrption.encrypt(tempRegistrationTb.getEmail());
        String userType = BASE64Encrption.encrypt(tempRegistrationTb.getUserType());
        String verificationUrl = PropertiesLoader.getPropertiesValue(VERIFICATION_MAIL_URL);
        verificationUrl = verificationUrl.replaceAll(CONTEXT_ROOT, LookupDataLoader.getContextRoot());
        url = url.append(verificationUrl)
                .append(QUESTION_MARK).append(VERIFICATION_MAIL_URL_PARAM_1)
                .append(EQ_SIGN).append(encryptedRegId).append(AMPERSAND).append(EMAIL)
                .append(EQ_SIGN).append(encryptedEmail)
                .append(AMPERSAND).append(USERTYPE).append(EQ_SIGN).append(userType);
        return url.toString();
    }

    /**
     * Method created to add MMF Transactional mail template design
     * @param multipart
     * @return 
     */
    public Multipart attachTransactionalEmailTemplate(Multipart multipart) {
        try {
            String[] imagefile = PropertiesLoader.getPropertiesValue(TRANSACTIONAL_EMAIL_TEMPLATE).split(COMA);
            for (String img : imagefile) {
                MimeBodyPart mbodyPart = new MimeBodyPart();
                StringBuilder path = new StringBuilder();
                path = path.append(LookupDataLoader.getTemplatePath()).append(DIRECTORY_EMAIL).append(SINGLE_SLASH).append(img);
                DataSource fds = new FileDataSource(path.toString());
                mbodyPart.setDataHandler(new DataHandler(fds));
                mbodyPart.setFileName(img);
                mbodyPart.setHeader(CONTENT_ID, img);
                multipart.addBodyPart(mbodyPart);
            }
        } catch (AddressException ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MailUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return multipart;
    }
}
