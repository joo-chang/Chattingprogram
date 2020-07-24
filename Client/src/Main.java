import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 683, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uC804 \uCCB4 \uC811 \uC18D \uC790");
		lblNewLabel.setBounds(12, 10, 97, 15);
		contentPane.add(lblNewLabel);
		
		JList list = new JList();
		list.setBounds(12, 30, 97, 129);
		contentPane.add(list);
		
		JButton btnNewButton = new JButton("\uCABD\uC9C0 \uBCF4\uB0B4\uAE30");
		btnNewButton.setBounds(12, 180, 97, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("\uCC44 \uD305 \uBC29 \uBAA9 \uB85D");
		lblNewLabel_1.setBounds(12, 213, 97, 15);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("\uCC44\uD305\uBC29 \uCC38\uC5EC");
		btnNewButton_1.setBounds(12, 377, 97, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
		btnNewButton_2.setBounds(12, 410, 97, 23);
		contentPane.add(btnNewButton_2);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(121, 26, 534, 376);
		contentPane.add(textArea);
		
		textField_1 = new JTextField();
		textField_1.setBounds(121, 411, 430, 22);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("\uC804\uC1A1");
		btnNewButton_3.setBounds(558, 410, 97, 23);
		contentPane.add(btnNewButton_3);
		
		JList list_1 = new JList();
		list_1.setBounds(12, 238, 97, 129);
		contentPane.add(list_1);
	}
}
