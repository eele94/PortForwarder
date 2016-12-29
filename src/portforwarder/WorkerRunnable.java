/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portforwarder;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import static jdk.nashorn.tools.ShellFunctions.input;
import sun.misc.IOUtils;

/**
 *
 */
public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;

    public WorkerRunnable(Socket clientSocket, String serverText) {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    public void run() {

        try {
            URL oracle = new URL("http://"+this.serverText+"/");
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            
            String inputLine;
            String outputLine = "";
            while ((inputLine = in.readLine()) != null) {
                outputLine += inputLine;
            }
            System.out.println(outputLine);
            in.close();
            OutputStream output = clientSocket.getOutputStream();
            
            output.write(("HTTP/1.1 200 OK\n\n" + inputLine).getBytes());
            output.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {

        }
    }
}
