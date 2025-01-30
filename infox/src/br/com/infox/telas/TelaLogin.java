package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.Box;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.ImageIcon;

public class TelaLogin extends JFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private static final long serialVersionUID = 1L;
	private JTextField txtField;
	private JPasswordField pswField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("X System - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 297, 192);
		getContentPane().setLayout(null);

		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(12, 26, 55, 16);
		getContentPane().add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(12, 71, 55, 16);
		getContentPane().add(lblSenha);

		txtField = new JTextField();
		txtField.setBounds(85, 24, 172, 20);
		getContentPane().add(txtField);
		txtField.setColumns(10);

		pswField = new JPasswordField();
		pswField.setBounds(85, 69, 172, 20);
		getContentPane().add(pswField);

		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(159, 115, 98, 20);
		getContentPane().add(btnLogin);
		
		JLabel lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon("C:\\Users\\Extreme\\Pictures\\DBImagens\\dbstage.png"));
		lblStatus.setBounds(22, 99, 34, 44);
		getContentPane().add(lblStatus);
		
		JLabel lblStatus2 = new JLabel("");
		lblStatus2.setIcon(new ImageIcon("C:\\Users\\Extreme\\Pictures\\DBImagens\\dberror.png"));
		lblStatus2.setBounds(22, 99, 34, 46);
		getContentPane().add(lblStatus2);

		conexao = ModuloConexao.conector();
		//a linha abaixo serve de apoio ao status da conexao
		//System.out.println(conexao);
		if(conexao!=null) {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dbstage.png")));
		}else {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dberror.png")));
		}
		
	}
}
