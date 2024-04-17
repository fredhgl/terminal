package com.fredhgl.terminal;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.fredhgl.terminal.ssh.Ssh;


class App 
{
    public static void main( String[] args ) throws Exception
    {
    	String hostname;
    	String username;
    	String passwd;
    	int port;
    	
        Ssh ssh = new Ssh();
        try {
        	
        	 BufferedReader in = new BufferedReader(
                     new InputStreamReader(System.in));
        	    System.out.print("Digite o hostname: ");
        	    hostname = in.readLine();
        	    System.out.print("Digite o username: ");
        	    username = in.readLine();
        	    System.out.print("Digite o password: ");
        	    passwd = in.readLine();
        	    System.out.print("Digite a porta: ");
        	    port = Integer.valueOf(in.readLine());
        	    
            ssh.conectaSsh(hostname, username, passwd, port);            
            
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    	
    }
}