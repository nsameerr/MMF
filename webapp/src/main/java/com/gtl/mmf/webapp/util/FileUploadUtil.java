/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.webapp.util;

import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.util.StackTraceWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author 09607
 */
public class FileUploadUtil {

    public static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.webapp.util.FileUploadUtil");
    public static final String[] FILE_FORMAT = new String[]{"jpeg", "pdf", "PNG", "jpg", "PDF", "png", "JPEG", "JPG"};

    private static String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        String file_name = null;
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                file_name = content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        file_name = GetExtensionFile(file_name);
        return file_name;
    }

    private static String GetExtensionFile(String file_name) {
        if (file_name != null) {
            String[] file = file_name.split("\\.");
            if (file.length == 2) {
                file_name = file[1];
                LOGGER.log(Level.INFO, "Extension", file_name);
            } else if (file.length > 2) {
                file_name = file[file.length - 1];
                LOGGER.log(Level.INFO, "Extension", file_name);
            } else {
                file_name = null;
            }
        }
        return file_name;
    }

    /**
     * Method to download single file from specified location.
     *
     * @param fileName
     * @return
     */
    public static boolean downloadFile(String fileName) {
        boolean status = false;
        if (fileName == null) {
            // Do your thing if the file is not supplied.
            return false;
        }
        File file = new File(fileName);
        if (!file.exists()) {
            // if the file appears to be non-existing.
            return false;
        }
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
        response.setContentType("application/octet-stream");
        response.setContentLength((int) file.length());
        ServletOutputStream out = null;
        try {
            FileInputStream input = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            int i = 0;
            while ((i = input.read(buffer)) != -1) {
                out.write(buffer);
                out.flush();
            }
            FacesContext.getCurrentInstance().responseComplete();
            FacesContext.getCurrentInstance().renderResponse();
            status = FacesContext.getCurrentInstance().getResponseComplete();

        } catch (IOException err) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(err));
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException err) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(err));
            }
        }
        return status;
    }

    public static void deleteIfExist(File directory, String regid) {
        final String regID = regid;
        FilenameFilter beginswith = new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.startsWith(regID);
            }
        };
        File[] files = directory.listFiles(beginswith);
        for (File f : files) {
            f.delete();
            LOGGER.log(Level.INFO, "File deleted..", f.toString());
        }
    }

    //for Investor side file upload
    public static String UploadUserFile(String regNo, Part filepart, String path, String filePath) {

        LOGGER.info("*********************** File Uplading Started ********************************");
        path = PropertiesLoader.getPropertiesValue(path);
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                LOGGER.log(Level.INFO, "Folder Created", file.getName());
            } else {
                LOGGER.log(Level.INFO, "Folder not  Created", file.getName());
                return null;
            }
        }
        StringBuffer fileName = new StringBuffer();
        String file_name = getFileName(filepart);
        LOGGER.log(Level.INFO, "*********************** Uploaded file name:[{0}]", file_name);
        LOGGER.log(Level.INFO, "*********************** Location to Upload:[{0}]", path);
        if (file_name != null && Arrays.asList(FILE_FORMAT).contains(file_name)) {
            fileName = fileName.append(path).append(regNo).append(filePath).append(DOT)
                    .append(file_name);
        } else {
            fileName = null;
        }
        OutputStream out = null;
        InputStream filecontent = null;

        try {
            if (fileName != null) {
                LOGGER.log(Level.INFO, "*************Uploaded File name******************", fileName.toString());
                deleteIfExist(file, regNo);
                out = new FileOutputStream(new File(fileName.toString()));
                filecontent = filepart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                LOGGER.log(Level.INFO, "Uploaded Successfully", fileName.toString());
                LOGGER.info("*********************** File Uploading Completed ********************************");
                return fileName.toString();
            }
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
        }
        return null;
    }

    public static String WsUploadFile(String regNo, String filepart, String path, String filePath, InputStream inputStream) {

        LOGGER.info("*********************** File Uploading Started ********************************");
        path = PropertiesLoader.getPropertiesValue(path);
        File file = new File(path);
        if (!file.exists()) {
            if (file.mkdir()) {
                LOGGER.log(Level.INFO, "Folder Created", file.getName());
            } else {
                LOGGER.log(Level.INFO, "Folder not  Created", file.getName());
                return null;
            }
        }
        if(filepart.contains("data:image/png;base64")){
        	byte[] imagedata = DatatypeConverter.parseBase64Binary(filepart.substring(filepart.indexOf(",") + 1));
        	try {
        		deleteIfExist(file, regNo);
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagedata));
				StringBuffer fileName = new StringBuffer();
				fileName = fileName.append(path).append(regNo).append(filePath).append(DOT)
                        .append("png");
				ImageIO.write(bufferedImage, "png", new File(fileName.toString()));
				
				 LOGGER.log(Level.INFO, "*********************** BLOB Uploaded file name:[{0}]", fileName.toString());
				return fileName.toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error: file format not working.";
			}
        }else{
            StringBuffer fileName = new StringBuffer();
            String file_name = GetExtensionFile(filepart);
            LOGGER.log(Level.INFO, "*********************** Uploaded file name:[{0}]", file_name);
            LOGGER.log(Level.INFO, "*********************** Location to Upload:[{0}]", path);
            if (file_name != null && Arrays.asList(FILE_FORMAT).contains(file_name)) {
                fileName = fileName.append(path).append(regNo).append(filePath).append(DOT)
                        .append(file_name);
            } else {
                fileName = null;
                return "error: file format not supported.";
            }
            OutputStream out = null;
            InputStream filecontent = null;

            try {
                if (fileName != null) {
                    LOGGER.log(Level.INFO, "*************Uploaded File name******************", fileName.toString());
                    deleteIfExist(file, regNo);
                    out = new FileOutputStream(new File(fileName.toString()));
                    filecontent = inputStream;

                    int read = 0;
                    final byte[] bytes = new byte[1024];

                    while ((read = filecontent.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    LOGGER.log(Level.INFO, "Uploaded Successfully {0}", fileName.toString());
                    LOGGER.info("*********************** File Uploading Completed ********************************");
                    return fileName.toString();
                }
            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            } catch (IOException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                    }
                }
            }
        }

        return null;
    }

    /**
     * Method to download multiple files.
     *
     * @param fileList
     * @return
     */
    public static boolean downloadFile(List<String> fileList) {
        boolean status = false;
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setContentType("application/octet-stream");
        ServletOutputStream out = null;
        ZipOutputStream zos = null;
        if (fileList == null || fileList.isEmpty()) {
            // Do your thing if the file is not supplied.
            return false;
        }
        try {
            out = response.getOutputStream();
            zos = new ZipOutputStream(out);
            boolean first = true;
            for (String fileName : fileList) {
                File file = new File(fileName);
                if (!file.exists()) {
                    // if the file appears to be non-existing.
                    return false;
                }
                if (first) {
                    first = false;
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + getFileWithoutExtension(file.getName()) + ".zip\"");
                }
                ZipEntry zipEntry = new ZipEntry(file.getName());
                FileInputStream fis = new FileInputStream(file);
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];

                int data = 0;
                while ((data = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, data);
                }
                zos.flush();
                zos.closeEntry();
                fis.close();

                LOGGER.log(Level.INFO, "Finished zipping file {0}", file.getName());
            }
        } catch (IOException err) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(err));
        } finally {
            try {
                zos.close();
                if (out != null) {
                    out.close();
                }
                FacesContext.getCurrentInstance().responseComplete();
                FacesContext.getCurrentInstance().renderResponse();
                status = FacesContext.getCurrentInstance().getResponseComplete();
                LOGGER.log(Level.INFO, "Download completed");
            } catch (IOException err) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(err));
            }
        }
        return status;
    }

    private static String getFileWithoutExtension(String file_name) {
        String name = EMPTY_STRING;
        if (file_name != null) {
            String[] file = file_name.split("\\.");
            if (file.length == 2) {
                name = file[0];
                LOGGER.log(Level.INFO, "filename: ", name);
            } else if (file.length > 2) {
                for (int i = 0; i < file.length - 1; i++) {
                    name = name + file[i];
                }
                LOGGER.log(Level.INFO, "filename: ", name);
            } else {
                file_name = null;
            }
        }
        return name;
    }

}
