	package 채팅서버;

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
	private JButton start_btn = new JButton("서버 실행");
	private JButton stop_btn = new JButton("서버 종료");
	
	//NetWork 자원
	 
	private ServerSocket server_socket ;
	private Socket socket;
	private int port;
	private Vector	 user_vc = new Vector();
	
	Server(){
		init();
		start(); //리스너 설정 메소드
	}
	
	
	
	private void start() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
	}



	private void init() { //화면구성
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 367, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		textArea.setBounds(12, 10, 327, 265);
		contentPane.add(textArea);
		

		JLabel lblNewLabel = new JLabel("포트 번호");
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
		
		this.setVisible(true); // 화면에 보이게
	}
	
	private void Server_start() {
		try {
			server_socket = new ServerSocket(port); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(server_socket !=null) { //정상적으로 포트가 열렸을 경우
			Connection();
		}
		
	}
	
	private void Connection() {
		

		
		
		Thread th = new Thread(new Runnable() {

			@Override
			public void run() { //스레드에서 처리할 일을 기재한다.
				
				
				while(true) {
					
					
					try { 
					
						//1가지 스레드에서는 한 가지 일만 처리할 수 있다.
						//한가지 일만 처리하고있을때: accept에서 대기중에는 나머지 GUI가 죽어버림
						//스레드로 처리해줘야
						
						
						textArea.append("사용자 접속 대기중\n");
						//사용자 접속 대기. 무한 대기
						socket = server_socket.accept(); //사용자 접속할 때 에러가 발생할 수 있기 때문에 try catch
						textArea.append("사용자 접속\n");
						
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
			System.out.println("스타트 버튼 클릭");
			
			port = Integer.parseInt(port_tf.getText().trim());
			
			Server_start(); //소켓 생성 및 사용자 접속 대기
		}else if(e.getSource() == stop_btn) {
			System.out.println("스탑 버튼 클릭");
		}
	}// 액션이벤트 끝
	
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
		
		private void UserNetwork() {//네트워크 자원 설정
			try {
				is = user_socket.getInputStream();
				dis = new DataInputStream(is);
				
				os = user_socket.getOutputStream();
				dos = new DataOutputStream(os);
				
				nickName = dis.readUTF();
				textArea.append(nickName +": 사용자 접속!\n");
				
				
				//기존 사용자에게 새로운 사용자 알림 
				System.out.println("현재 접속된 사용자 수 : "+user_vc.size());
				
				for (int i = 0; i < user_vc.size(); i++) { // 현재 접속된 사용자에게 새로운 사용자 알림
					
					UserInfo u = (UserInfo)user_vc.elementAt(i);
					u.send_Messagee("NewUser/"+nickName);
					
				}
				
				//자신에게 기존 사용자 알림
				
				
				user_vc.add(this); //사용자에게 알린 후 Vector에 자신을 추가
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		public void run() { // Thread 에서 처리할 내용
			
			while(true) {
				try {
					String msg = dis.readUTF();
					textArea.append(nickName+": 사용자로부터 들어온 메세지 :"+msg+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				} // 메세지 수신
			}
		}
		
		
		private void send_Messagee(String str) { //문자열을 받아서 전송
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	
	
	
	
}
