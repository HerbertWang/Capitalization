import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class CapitalizeClient {

	private BufferedReader in;
	private PrintWriter out;
	private JFrame frame=new JFrame("Capitalize Client");
	private JTextField dataField=new JTextField(40);
	private JTextArea messageArea=new JTextArea(8,60);
	
	public CapitalizeClient()
	{
		messageArea.setEditable(false);
		frame.getContentPane().add(dataField,"North");
		frame.getContentPane().add(new JScrollPane(messageArea),"Center");
		
		dataField.addActionListener(new ActionListener(){	
			public void actionPerformed(ActionEvent e) {
			out.println(dataField.getText());
			 String response;
			 try{
				 response=in.readLine();
				 if(response==null||response.equals(""))
				 {
					 System.exit(0);
				 }
			 } catch(IOException ex) {
				 response="Error :"+ex;
			 }				 
			messageArea.append(response+ "\n");
			dataField.selectAll();
		}
			});


	}

	public void connectToServer() throws IOException {
		String serverAddress =JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the Capitalization Program",
				JOptionPane.QUESTION_MESSAGE);
		
		Socket socket=new Socket(serverAddress,9898);
		in=new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		out=new PrintWriter(socket.getOutputStream(),true);
		
		for (int i=0;i<3;i++) {
			messageArea.append(in.readLine()+"\n");
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CapitalizeClient client=new CapitalizeClient();
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.frame.pack();
		client.frame.setVisible(true);
		client.connectToServer();
	}

}
