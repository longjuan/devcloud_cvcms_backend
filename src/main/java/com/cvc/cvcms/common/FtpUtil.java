package com.cvc.cvcms.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Author: XiuGitHung
 * @Date: 2021/4/2 1:04
 * @Description:
 */
@Component
@Data
@ConfigurationProperties("ftp")
@Slf4j
public class FtpUtil {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String dir;

    public boolean uploadToFtp(String filename,FileInputStream in){
        FTPClient client = new FTPClient();
        try {
            client.connect(host,port);
            client.login(username,password);
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory(dir);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.storeFile(filename,in);
            client.logout();
        } catch (IOException e) {
            log.error("ftp上传异常",e);
            return false;
        } finally {
            if(client.isConnected()) {
                try {
                    client.disconnect();
                } catch(IOException e) {
                    log.error("ftp上传disconnect异常",e);
                }
            }
        }
        return true;
    }

    public boolean DownloadFromFtp(String filename,HttpServletResponse resp) throws UnsupportedEncodingException {
        FTPClient client = new FTPClient();
        try {
            client.connect(host,port);
            client.login(username,password);
            client.enterLocalPassiveMode();
            client.changeWorkingDirectory(dir);
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.setControlEncoding("UTF-8");
            InputStream retrieveFileStream = null;
            retrieveFileStream = client.retrieveFileStream(new String(filename.getBytes("GBK"),"iso-8859-1"));
            BufferedInputStream bis = new BufferedInputStream(retrieveFileStream);
            OutputStream os = resp.getOutputStream();
            String suffix = filename.substring(filename.lastIndexOf(".")+1);
            resp.reset();
            resp.setContentType("image/" + suffix);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            byte[] b = new byte[1024];
            int s = 0 ;
            while ((s = bis.read(b)) != -1) {
                bos.write(b, 0, s);
                bos.flush();
            }
            os.close();
            client.logout();
        } catch (IOException e) {
            log.error("ftp下载异常",e);
            return false;
        } finally {
            if(client.isConnected()) {
                try {
                    client.disconnect();
                } catch(IOException e) {
                    log.error("ftp下载disconnect异常",e);
                }
            }
        }
        return true;
    }

}
