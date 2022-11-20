package com.tourbest.erp.chat;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Socket_thread implements Runnable {

    InputStream inputStream;
    DataInputStream dataInputStream;
    OutputStream outputStream;
    DataOutputStream dataOutputStream;
    Socket socket;

    public Socket_thread(InputStream inputStream,
                         DataInputStream dataInputStream,
                         OutputStream outputStream,
                         DataOutputStream dataOutputStream,
                         Socket socket) {
        this.inputStream = inputStream;
        this.dataInputStream = dataInputStream;
        this.outputStream = outputStream;
        this.dataOutputStream = dataOutputStream;
        this.socket = socket;

    }

    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {

//                InMessage(dataInputStream.readUTF());
                String a = dataInputStream.readUTF();
                System.out.println(a);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                try {
                    outputStream.close();
                    inputStream.close();
                    dataInputStream.close();
                    dataOutputStream.close();
                    socket.close();

                    JOptionPane.showMessageDialog(null, "서버와 접속 끊어짐", "알림", JOptionPane.ERROR_MESSAGE);
                } catch (IOException e1) {
                }
                break;

            }
        }
    }
}
