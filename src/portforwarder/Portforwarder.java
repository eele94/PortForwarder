/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portforwarder;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author your user name
 */
public class Portforwarder {
    public static void main(String[] args){
        String[] servers = args[0].split(",");
        
        MultiThreadedServer server = new MultiThreadedServer(9000, servers);
        new Thread(server).start();
      
    }
}
