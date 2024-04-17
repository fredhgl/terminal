package com.fredhgl.terminal.ssh;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;

public class Ssh {
	
    public void conectaSsh(String host, String username, String password, int port) throws Exception {
    	long timeout = 10;
        SshClient client = SshClient.setUpDefaultClient();      
        client.start();
        try (ClientSession session = client.connect(username, host, port).verify(timeout, TimeUnit.SECONDS).getSession()) {
            //session.addPasswordIdentity(password);
        	session.addPasswordIdentity(password);
            session.auth().verify(timeout, TimeUnit.SECONDS);
            ByteArrayOutputStream responseStream;
            ByteArrayOutputStream errorStream;
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
            String comando = "";
            // Reading data using readLine
            
            while (! comando.equals("exit\n")) {
            	
            
	            System.out.print("Digite o comando shell (exit para sair): ");
	            comando = reader.readLine()+"\n";
	            
	            ClientChannel channel = session.createChannel(Channel.CHANNEL_EXEC, comando);
	            responseStream = new ByteArrayOutputStream();
	            errorStream = new ByteArrayOutputStream();
	            channel.setOut(responseStream);
	            channel.setErr(errorStream);
	            channel.open().verify(timeout, TimeUnit.SECONDS);
	            channel.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), TimeUnit.SECONDS.toMillis(timeout));
	            String error = new String(errorStream.toByteArray());
	            
	            
	            if (!error.isEmpty()) {
	            	System.out.println(error.toString());
	            	errorStream.close();
	            	errorStream = null;
	            	channel.close();
	            } else
	            	System.out.println(responseStream.toString());
            }    
        }
        finally {
        	System.out.println("Bye!");
            client.stop();
        }
    }
}




        