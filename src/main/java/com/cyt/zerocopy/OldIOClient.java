package com.cyt.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class OldIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        InputStream inputStream = null;
        DataOutputStream outputStream = null;
        try {
            socket = new Socket("localhost", 8899);
            String file_name = "D:\\Tomcat\\apache-tomcat-9.0.21.zip";
            inputStream = new FileInputStream(file_name);
            outputStream = new DataOutputStream(socket.getOutputStream());

            byte[] bytes = new byte[4096];
            long read_num;
            long total = 0;

            long start_time = System.currentTimeMillis();

            while ((read_num = inputStream.read(bytes)) >= 0) {
                total += read_num;
                outputStream.write(bytes);
            }
            System.out.println("发送总字节数： " + total + "，耗时：" + (System.currentTimeMillis() - start_time));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            socket.close();
            inputStream.close();
            outputStream.close();
        }
    }
}
