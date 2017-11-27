/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.BACKUP_FOLDER;
import static com.gtl.mmf.service.util.IConstants.FTP_SERVER_PROPERTIES;
import static com.gtl.mmf.service.util.IConstants.SFTP;
import static com.gtl.mmf.service.util.IConstants.SFTP_SERVER_PROPERTIES;
import static com.gtl.mmf.service.util.IConstants.SINGLE_SLASH;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.util.StackTraceWriter;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

/**
 * This class is created for transfer files to ftp
 *
 * @author trainee8
 */
public class FtpFileTranferUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.FtpFileTranferUtil");
    private static final String STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    private static final String NO = "no";

    /**
     * This method is used to write data into a file in ftp.
     *
     * @param fileContent
     * @param fileLocation
     * @return
     */
    public static boolean writeFileToFTP(String fileContent, String fileLocation) {
        boolean done = false;
        FTPClient ftpClient = new FTPClient();
        try {
            String[] ftpFilePath = PropertiesLoader.getPropertiesValue(FTP_SERVER_PROPERTIES).split("\\|");
            String server = ftpFilePath[0];
            int port = Integer.parseInt(ftpFilePath[1]);
            String user = ftpFilePath[2];
            String pass = ftpFilePath[3];
            ftpClient.connect(server, port);
            boolean loginFlag = ftpClient.login(user, pass);
            LOGGER.log(Level.INFO, "Login into FTP{0}", loginFlag);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            LOGGER.log(Level.INFO, "Writing Debt file:{0}", fileLocation);
            //Constructing inputstream from string
            InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
            done = ftpClient.storeFile(fileLocation, inputStream);
            inputStream.close();
            if (done) {
                LOGGER.log(Level.INFO, "The file is uploaded successfully.{0}", done);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            }
        }
        return done;
    }

    /**
     * This method is used to check whether the mandate file is created for the
     * current day by matching with file name
     *
     * @param root - path to directory where file is storing
     * @param regex - regular expression to match the file name
     * @return file name if exist else null
     */
    public static String[] listFilesMatching(String root, final String regex, String downloadLocation) {
        FTPClient ftpClient = new FTPClient();
        String[] fileName = null;
        boolean fileExist = false;
        int i = 0;
        try {
            String[] ftpFilePath = PropertiesLoader.getPropertiesValue(FTP_SERVER_PROPERTIES).split("\\|");
            String server = ftpFilePath[0];
            int port = Integer.parseInt(ftpFilePath[1]);
            String user = ftpFilePath[2];
            String pass = ftpFilePath[3];
            ftpClient.connect(server, port);
            boolean loginFlag = ftpClient.login(user, pass);
            LOGGER.log(Level.INFO, "Login to FTP{0}", loginFlag);
            final Pattern p = Pattern.compile(regex);
            FTPFileFilter filter = new FTPFileFilter() {
                @Override
                public boolean accept(FTPFile ftpFile) {
                    return p.matcher(ftpFile.getName()).matches();
                }
            };
            LOGGER.log(Level.INFO, "Listing files in FTP{0}", loginFlag);
            FTPFile[] result = ftpClient.listFiles(root, filter);
            LOGGER.log(Level.INFO, "Listing files in FTP{0}", result);
            if (result.length > ZERO) {
                fileName = new String[result.length];
                for (FTPFile todayFile : result) {
                    LOGGER.log(Level.INFO, "Transfering file to MMF from FTP:{0}", todayFile.getName());
                    StringBuilder debtCsvFile = new StringBuilder();
                    debtCsvFile.append(LookupDataLoader.getResourcePath()).append(downloadLocation)
                            .append(todayFile.getName());
                    File downloadFile1 = new File(debtCsvFile.toString());
                    OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile1));
                    StringBuffer dwonlodFile = new StringBuffer();
                    dwonlodFile.append(root).append(SINGLE_SLASH).append(todayFile.getName());
                    LOGGER.log(Level.INFO, "Transfering File{0} from FTP to MMF:{1}", new Object[]{i, dwonlodFile});
                    boolean success = ftpClient.retrieveFile(dwonlodFile.toString(), outputStream);
                    LOGGER.log(Level.INFO, "Completed transfer file:{0} {1}", new Object[]{i, success});
                    outputStream.close();
                    LOGGER.log(Level.INFO, "Transfering file to MMF from FTP is completed:{0}", success);
                    if (new File(debtCsvFile.toString()).exists()) {
                        fileName[i] = new File(debtCsvFile.toString()).getAbsolutePath();
                        String destinationFile = root + BACKUP_FOLDER + "/" + (todayFile.getName());
                        String source = root + "/" + (todayFile.getName());
                        boolean copied = ftpClient.rename(source, destinationFile);
                        if (copied) {
                            LOGGER.log(Level.INFO, "File copied :{0} {1}", new Object[]{i, copied});
                            boolean deleted = ftpClient.deleteFile(dwonlodFile.toString());
                            if (deleted) {
                                LOGGER.log(Level.INFO, "File is deleted :{0} {1}", new Object[]{i, deleted});
                            } else {
                                LOGGER.log(Level.INFO, "File is not deleted{0} {1}", new Object[]{i, deleted});
                            }
                        } else {
                            LOGGER.log(Level.INFO, "File is not copied :{0} {1}", new Object[]{i, copied});
                        }
                    }
                    i++;
                }

            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            }
        }
        return fileName;

    }

    /**
     * Method to write file to SFTP server.
     *
     * @param fileContent
     * @param fileLocation
     * @return
     */
    public static boolean writeFileToSFTP(String fileContent, String fileLocation) {
        boolean done = false;
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        JSch jsch = new JSch();
        try {
            String[] sftpFilePath = PropertiesLoader.getPropertiesValue(SFTP_SERVER_PROPERTIES).split("\\|");
            String server = sftpFilePath[0];
            int port = Integer.parseInt(sftpFilePath[1]);
            String user = sftpFilePath[2];
            String pass = sftpFilePath[3];
            session = jsch.getSession(user, server, port);
            session.setPassword(pass);
            session.setConfig(STRICT_HOST_KEY_CHECKING, NO);
            LOGGER.log(Level.INFO, "Login into SFTP..");
            session.connect();
            LOGGER.log(Level.INFO, "Connected to {0}.", session.getHost());
            channel = session.openChannel(SFTP);
            channel.connect();
            LOGGER.log(Level.INFO, "Writing Debt file:{0}", fileLocation);
            InputStream baos = new ByteArrayInputStream(fileContent.getBytes());
            channelSftp = (ChannelSftp) channel;
            channelSftp.put(baos, fileLocation);
            done = true;
            if (done) {
                LOGGER.log(Level.INFO, "The file {} upload status :.{0}", new Object[]{fileLocation, done});
            }
        } catch (JSchException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } catch (SftpException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
                channelSftp.quit();
            }
        }
        return done;
    }

    /**
     * Method to transfer data from SFTP server to MMF database.
     *
     * @param sftpPath
     * @param regex
     * @param downloadLocation
     * @return
     */
    public static String[] listFilesMatchingInSFTP(String sftpPath, final String regex, String downloadLocation) {
        String flileMovingPath = sftpPath.concat(BACKUP_FOLDER);
        boolean deleted = false;
        boolean success = false;
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        String[] fileName = null;
        JSch jsch = new JSch();
        int i = 0;
        try {
            String[] sftpFilePath = PropertiesLoader.getPropertiesValue(SFTP_SERVER_PROPERTIES).split("\\|");
            String server = sftpFilePath[0];
            int port = Integer.parseInt(sftpFilePath[1]);
            String user = sftpFilePath[2];
            String pass = sftpFilePath[3];
            session = jsch.getSession(user, server, port);
            session.setPassword(pass);
            session.setConfig(STRICT_HOST_KEY_CHECKING, NO);
            LOGGER.log(Level.INFO, "Login into SFTP..");
            session.connect();
            LOGGER.log(Level.INFO, "Connected to {0}.", session.getHost());
            channel = session.openChannel(SFTP);
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            System.out.println("Reading files from SFTP ");
            Vector<String> list = channelSftp.ls(sftpPath + "/*");
            System.out.println("Listing files in FTP{0}" + list);
            if (list.size() > ZERO) {
                fileName = new String[list.size()];
                for (Object obj : list) {
                    if (obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry) {
                        ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) obj;
                        System.out.println("Transfering file to MMF from FTP " + entry.getFilename());
                        StringBuilder debtCsvFile = new StringBuilder();
                        debtCsvFile.append(LookupDataLoader.getResourcePath()).append(downloadLocation)
                                .append(entry.getFilename());
                        File downloadFile1 = new File(debtCsvFile.toString());
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile1));
                        StringBuffer dwonlodFile = new StringBuffer();
                        dwonlodFile.append(sftpPath).append(SINGLE_SLASH).append(entry.getFilename());
                        LOGGER.log(Level.INFO, "Transfering File {0} from FTP to MMF:{1}", new Object[]{i, dwonlodFile});
                        channelSftp.get(dwonlodFile.toString(), outputStream);
                        success = true;
                        LOGGER.log(Level.INFO, "Completed transfer file:{0}", success);
                        outputStream.close();
                        LOGGER.log(Level.INFO, "Transfering file to MMF from FTP is completed:{0}", success);
                        if (new File(debtCsvFile.toString()).exists()) {
                            LOGGER.log(Level.INFO, "File is deleteing :{0}", dwonlodFile);
                            fileName[i] = new File(debtCsvFile.toString()).getAbsolutePath();
                            channelSftp.rename(dwonlodFile.toString(), flileMovingPath + "/" + entry.getFilename());
                            deleted = true;
                            if (deleted) {
                                LOGGER.log(Level.INFO, "File is deleted :{0} {1}", new Object[]{i, deleted});
                            } else {
                                LOGGER.log(Level.INFO, "File is not deleted{0} {1}", new Object[]{i, deleted});
                            }
                        }
                        i++;
                    }
                }
            }
        } catch (JSchException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } catch (SftpException ex) {
            Logger.getLogger(FtpFileTranferUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FtpFileTranferUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FtpFileTranferUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
                channelSftp.quit();
            }
        }
        return fileName;
    }

    public static boolean writeExcelToFTP(InputStream fileContent, String fileLocation) {
        boolean done = false;
        FTPClient ftpClient = new FTPClient();
        try {
            String[] ftpFilePath = PropertiesLoader.getPropertiesValue(FTP_SERVER_PROPERTIES).split("\\|");
            String server = ftpFilePath[0];
            int port = Integer.parseInt(ftpFilePath[1]);
            String user = ftpFilePath[2];
            String pass = ftpFilePath[3];
            ftpClient.connect(server, port);
            boolean loginFlag = ftpClient.login(user, pass);
            LOGGER.log(Level.INFO, "Login into FTP{0}", loginFlag);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            LOGGER.log(Level.INFO, "Writing  file:{0}", fileLocation);
            done = ftpClient.storeFile(fileLocation, fileContent);
            fileContent.close();
            if (done) {
                LOGGER.log(Level.INFO, "The file is uploaded successfully.{0}", done);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            }
        }
        return done;
    }

    public static boolean writeExcelToSFTP(InputStream fileContent, String fileLocation) {
        boolean done = false;
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        JSch jsch = new JSch();
        try {
            String[] sftpFilePath = PropertiesLoader.getPropertiesValue(SFTP_SERVER_PROPERTIES).split("\\|");
            String server = sftpFilePath[0];
            int port = Integer.parseInt(sftpFilePath[1]);
            String user = sftpFilePath[2];
            String pass = sftpFilePath[3];
            session = jsch.getSession(user, server, port);
            session.setPassword(pass);
            session.setConfig(STRICT_HOST_KEY_CHECKING, NO);
            LOGGER.log(Level.INFO, "Login into SFTP..");
            session.connect();
            LOGGER.log(Level.INFO, "Connected to {0}.", session.getHost());
            channel = session.openChannel(SFTP);
            channel.connect();
            LOGGER.log(Level.INFO, "Writing  file:{0}", fileLocation);
            channelSftp = (ChannelSftp) channel;
            channelSftp.put(fileContent, fileLocation);
            fileContent.close();
            done = true;
            if (done) {
                LOGGER.log(Level.INFO, "The file {} upload status :.{0}", new Object[]{fileLocation, done});
            }
        } catch (JSchException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } catch (SftpException ex) {
            LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
        } catch (IOException ex) {
            Logger.getLogger(FtpFileTranferUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
                channelSftp.quit();
            }
        }
        return done;
    }
}
