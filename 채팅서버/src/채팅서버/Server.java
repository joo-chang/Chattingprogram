	package ä�ü���;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Server extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTextField port_tf;
	private JTextArea textArea = new JTextArea();
	private JButton start_btn = new JButton("���� ����");
	private JButton stop_btn = new JButton("���� ����");
	
	//NetWork �ڿ�
	 
	private ServerSocket server_socket ;
	private Socket socket;
	private int port;
	private Vector	 user_vc = new Vector();
	
	Server(){
		init();
		start(); //������ ���� �޼ҵ�
	}
	
	
	
	private void start() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
	}



	private void init() { //ȭ�鱸��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		textArea.setBounds(12, 10, 327, 265);
		contentPane.add(textArea);
		

		JLabel lblNewLabel = new JLabel("��Ʈ ��ȣ");
		lblNewLabel.setBounds(12, 303, 57, 15);
		contentPane.add(lblNewLabel);
		
		port_tf = new JTextField();
		port_tf.setBounds(81, 300, 258, 18);
		contentPane.add(port_tf);
		port_tf.setColumns(10);
		
		
		stop_btn.setBounds(181, 361, 158, 23);
		contentPane.add(stop_btn);
		
		
		start_btn.setBounds(12, 361, 158, 23);
		contentPane.add(start_btn);
		
		this.setVisible(true); // ȭ�鿡 ���̰�
	}
	
	private void Server_start() {
		try {
			server_socket = new ServerSocket(port); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(server_socket !=null) { //���������� ��Ʈ�� ������ ���
			Connection();
		}
		
	}
	
	private void Connection() {
		

		
		
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() { //�����忡�� ó���� ���� �����Ѵ�.
				
				
				while(true) {
					
					
					try { 
					
						//1���� �����忡���� �� ���� �ϸ� ó���� �� �ִ�.
						//�Ѱ��� �ϸ� ó���ϰ�������: accept���� ����߿��� ������ GUI�� �׾����
						//������� ó�������
						
						
						textArea.append("����� ���� �����\n");
						//����� ���� ���. ���� ���
						socket = server_socket.accept(); //����� ������ �� ������ �߻��� �� �ֱ� ������ try catch
						textArea.append("����� ����\n");
						
						UserInfo user = new UserInfo(socket);
						
						user.start();
					
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				}
			
			}
		});
		
		th.start();
	}
	
	public static void main(String[] args) {
		
		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == start_btn) {
			System.out.println("��ŸƮ ��ư Ŭ��");
			
			port = Integer.parseInt(port_tf.getText().trim());
			
			Server_start(); //���� ���� �� ����� ���� ���
		}else if(e.getSource() == stop_btn) {
			System.out.println("��ž ��ư Ŭ��");
		}
	}// �׼��̺�Ʈ ��
	
	class UserInfo extends Thread{
		private OutputStream os;
		private InputStream is;
		private DataOutputStream dos;
		private DataInputStream dis;
		
		private Socket user_socket;
		private String nickName="";
		
		
		UserInfo(Socket soc){
			this.user_socket = soc;
			
			UserNetwork();
		}
		
		private void UserNetwork() {//��Ʈ��ũ �ڿ� ����
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);
				
				nickName = dis.readUTF();
				textArea.append(nickName +": ����� ����!\n");
				
				
				//���� ����ڿ��� ���ο� ����� �˸� 
				System.out.println("���� ���ӵ� ����� �� : "+user_vc.size());
				
				for (int i = 0; i < user_vc.size(); i++) { // ���� ���ӵ� ����ڿ��� ���ο� ����� �˸�
					
					UserInfo u = (UserInfo)user_vc.elementAt(i);
					u.send_Messagee("NewUser/"+nickName);
					
				}
				
				//�ڽſ��� ���� ����� �˸�
				
				
				user_vc.add(this); //����ڿ��� �˸� �� Vector�� �ڽ��� �߰�
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		public void run() { // Thread ���� ó���� ����
			
			while(true) {
				try {
					String msg = dis.readUTF();
					textArea.append(nickName+": ����ڷκ��� ���� �޼��� :"+msg+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				} // �޼��� ����
			}
		}
		
		
		private void send_Messagee(String str) { //���ڿ��� �޾Ƽ� ����
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	
}
