package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaUsuario extends JInternalFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private static final long serialVersionUID = 1L;
	private JTextField txtUserId;
	private JTextField txtUserName;
	private JTextField txtUserPhone;
	private JTextField txtUserLogin;
	private JTextField txtUserPassword;
	private JComboBox<String> cbxPerfil;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public TelaUsuario(JDesktopPane desktopPane) {
		setTitle("Usuários");
		setClosable(true); // Permite fechar o JInternalFrame
		setIconifiable(true); // Permite minimizar
		setMaximizable(true); // Permite maximizar
		setResizable(false); // Permite redimensionamento
		setSize(482, 518); // Define o tamanho do frame
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(38, 35, 22, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(21, 206, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(21, 255, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Login");
		lblNewLabel_3.setBounds(21, 90, 46, 14);
		getContentPane().add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Senha");
		lblNewLabel_4.setBounds(21, 139, 46, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Perfil");
		lblNewLabel_5.setBounds(176, 34, 46, 14);
		getContentPane().add(lblNewLabel_5);

		txtUserId = new JTextField();
		txtUserId.setBounds(89, 32, 63, 20);
		getContentPane().add(txtUserId);
		txtUserId.setColumns(10);

		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBounds(89, 203, 311, 20);
		getContentPane().add(txtUserName);

		txtUserPhone = new JTextField();
		txtUserPhone.setColumns(10);
		txtUserPhone.setBounds(89, 252, 156, 20);
		getContentPane().add(txtUserPhone);

		txtUserLogin = new JTextField();
		txtUserLogin.setColumns(10);
		txtUserLogin.setBounds(89, 87, 156, 20);
		getContentPane().add(txtUserLogin);

		txtUserPassword = new JTextField();
		txtUserPassword.setColumns(10);
		txtUserPassword.setBounds(89, 136, 156, 20);
		getContentPane().add(txtUserPassword);

		cbxPerfil = new JComboBox<String>();
		cbxPerfil.setModel(new DefaultComboBoxModel<String>(new String[] { "admin", "user" }));
		cbxPerfil.setBounds(244, 30, 156, 22);
		getContentPane().add(cbxPerfil);

		JButton btnUserAdd = new JButton("");
		btnUserAdd.setToolTipText("Adicionar");
		btnUserAdd.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/adicionar.png")));
		btnUserAdd.setBounds(10, 393, 89, 52);
		getContentPane().add(btnUserAdd);

		JButton btnUserFind = new JButton("");
		btnUserFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamando o metodo consultar
				consultar();
			}
		});
		btnUserFind.setToolTipText("Procurar");
		btnUserFind.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/find.png")));
		btnUserFind.setBounds(128, 393, 89, 52);
		getContentPane().add(btnUserFind);

		JButton btnUserUpdate = new JButton("");
		btnUserUpdate.setToolTipText("Editar");
		btnUserUpdate.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/edit.png")));
		btnUserUpdate.setBounds(246, 393, 89, 52);
		getContentPane().add(btnUserUpdate);

		JButton btnUserDelete = new JButton("");
		btnUserDelete.setToolTipText("Remover");
		btnUserDelete.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/remove.png")));
		btnUserDelete.setBounds(367, 393, 89, 52);
		getContentPane().add(btnUserDelete);

		// Centralizando no JDesktopPane
		if (desktopPane != null) {
			int x = (desktopPane.getWidth() - getWidth()) / 2;
			int y = (desktopPane.getHeight() - getHeight()) / 2;
			setLocation(x, y);
		}
		// Chama o modulo de conexao com o banco
		conexao = ModuloConexao.conector();
	}

	private void consultar() {
		String sql = "select * from tbusuarios where iduser=?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUserId.getText());
			rs = pst.executeQuery();

			if (rs.next()) {
				txtUserName.setText(rs.getString(2));
				txtUserPhone.setText(rs.getString(3));
				txtUserLogin.setText(rs.getString(4));
				txtUserPassword.setText(rs.getString(5));
				//a linha refere-se ao combobox
				cbxPerfil.setSelectedItem(rs.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
				txtUserName.setText("");
		        txtUserPhone.setText("");
		        txtUserLogin.setText("");
		        txtUserPassword.setText("");
		        cbxPerfil.setSelectedIndex(-1); // Define como nenhum item selecionado
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
