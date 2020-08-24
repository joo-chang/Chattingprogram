package 채팅클라이언트;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame implements ActionListener{
	
	//login GUI 변수
	private JFrame Login_GUI = new JFrame();
	private JPanel Login_Pane;
	private JTextField ip_tf;
	private JTextField port_tf;
	private JTextField id_tf;
	private JButton login_btn = new JButton("접 속");
	
	private JPanel contentPane;
	private JTextField textField_1;
	private JButton notesend_btn = new JButton("쪽지 보내기");
	private JLabel lblNewLabel_1 = new JLabel("채팅방 목록");
	private JButton join_btn = new JButton("채팅방 참여");
	private JButton createroom_btn = new JButton("방 만들기");
	private JButton send_btn = new JButton("전송");
	private JList User_list = new JList(); //전체 접속자 List
	private JList Room_list = new JList(); // 전체 방 목록 List
	private JTextArea Chat_area = new JTextArea();
	
	//네트워크를 위한 자원 변수
	private Socket socket;
	private String ip="" ;
	private int port ;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	
	Client(){
		
		Login_init();
		Main_init();
		start();
		
	}
	
	private void start() {
		login_btn.addActionListener(this);
		notesend_btn.addActionListener(this);
		join_btn.addActionListener(this);
		createroom_btn.addActionListener(this);
		send_btn.addActionListener(this);
	}

	private void Login_init() {
		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login_GUI.setBounds(100, 100, 357, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Login_GUI.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server IP");
		lblNewLabel.setBounds(32, 201, 69, 15);
		contentPane.add(lblNewLabel);
		
		ip_tf = new JTextField();
		ip_tf.setBounds(101, 198, 164, 21);
		contentPane.add(ip_tf);
		ip_tf.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Server Port");
		lblNewLabel_1.setBounds(32, 245, 68, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("ID");
		lblNewLabel_2.setBounds(32, 289, 69, 15);
		contentPane.add(lblNewLabel_2);
		
		port_tf = new JTextField();
		port_tf.setBounds(101, 242, 164, 21);
		contentPane.add(port_tf);
		port_tf.setColumns(10);
		
		id_tf = new JTextField();
		id_tf.setBounds(101, 286, 164, 21);
		contentPane.add(id_tf);
		id_tf.setColumns(10);
		
		login_btn.setBounds(32, 343, 261, 23);
		contentPane.add(login_btn);		
		
		Login_GUI.setVisible(true); // true=화면에 보임
	}

	private void Main_init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("전체 접속자");
		lblNewLabel.setBounds(12, 10, 97, 15);
		contentPane.add(lblNewLabel);
		
		
		User_list.setBounds(12, 30, 97, 129);
		contentPane.add(User_list);
		
		
		notesend_btn.setBounds(12, 180, 97, 23);
		contentPane.add(notesend_btn);
		
		
		lblNewLabel_1.setBounds(12, 213, 97, 15);
		contentPane.add(lblNewLabel_1);
		
		
		join_btn.setBounds(12, 377, 97, 23);
		contentPane.add(join_btn);
		
		
		createroom_btn.setBounds(12, 410, 97, 23);
		contentPane.add(createroom_btn);
		
		
		Chat_area.setBounds(121, 26, 534, 376);
		contentPane.add(Chat_area);
		
		textField_1 = new JTextField();
		textField_1.setBounds(121, 411, 430, 22);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		
		send_btn.setBounds(558, 410, 97, 23);
		contentPane.add(send_btn);
		
		
		Room_list.setBounds(12, 238, 97, 129);
		contentPane.add(Room_list);
		
		this.setVisible(true);
	}
	
	private void Network() {
		try {
			
			socket = new Socket(ip,port);
			
			if(socket != null) { //정상저긍로 소켓이 연결되었을경우
				Connection();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private void Connection() {
		
		try {
			
		is = socket.getInputStream();
		dis = new DataInputStream(is);
		
		os = socket.getOutputStream();
		dos = new DataOutputStream(os);
		}catch(IOException e){//에러 처리부분
			
		}
		
		send_message("클라이언트 접속합니다");
		
		String msg = "";
		try {
			msg = dis.readUTF();
			System.out.println("서버로부터 들어온 메세지: "+msg);
			
			send_message("~~~~~~~~");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void send_message(String str) {
		
		try {
			dos.writeUTF(str);
		} catch (IOException e) { //에러처리부분
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()== login_btn) {
			System.out.println("로그인 버튼 클릭");
			//클릭시 ip, port 가져오기
			
			ip = ip_tf.getText().trim();
			port = Integer.parseInt(port_tf.getText().trim());
			
			Network();
		}else if(e.getSource()==notesend_btn) {
			System.out.println("쪽지 보내기 버튼");
		}else if(e.getSource()==join_btn) {
			System.out.println("채팅방 참여 버튼");
		}else if(e.getSource()==createroom_btn) {
			System.out.println("채팅방 만들기 버튼");
		}else if(e.getSource()==send_btn) {
			System.out.println("채팅 전송 버튼");
		}
		
	}
}
