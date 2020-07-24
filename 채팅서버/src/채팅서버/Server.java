package 채팅서버;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	Server(){
		init();
		start();
	}
	
	
	
	private void start() {
		start_btn.addActionListener(this);
		stop_btn.addActionListener(this);
	}



	private void init() {
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
	
	public static void main(String[] args) {
		
		new Server();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == start_btn) {
			System.out.println("스타트 버튼 클릭");
		}else if(e.getSource() == stop_btn) {
			System.out.println("스탑 버튼 클릭");
		}
	}
}
