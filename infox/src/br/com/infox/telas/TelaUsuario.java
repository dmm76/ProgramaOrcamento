package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import java.awt.Container;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class TelaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtUserId;
	private JTextField txtUserName;
	private JTextField txtUserPhone;
	private JTextField txtUserLogin;
	private JTextField txtUserPassword;

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
	    lblNewLabel_1.setBounds(21, 105, 46, 14);
	    getContentPane().add(lblNewLabel_1);
	    
	    JLabel lblNewLabel_2 = new JLabel("Fone");
	    lblNewLabel_2.setBounds(21, 179, 46, 14);
	    getContentPane().add(lblNewLabel_2);
	    
	    JLabel lblNewLabel_3 = new JLabel("Login");
	    lblNewLabel_3.setBounds(21, 243, 46, 14);
	    getContentPane().add(lblNewLabel_3);
	    
	    JLabel lblNewLabel_4 = new JLabel("Senha");
	    lblNewLabel_4.setBounds(21, 280, 46, 14);
	    getContentPane().add(lblNewLabel_4);
	    
	    JLabel lblNewLabel_5 = new JLabel("Perfil");
	    lblNewLabel_5.setBounds(21, 347, 46, 14);
	    getContentPane().add(lblNewLabel_5);
	    
	    txtUserId = new JTextField();
	    txtUserId.setEnabled(false);
	    txtUserId.setEditable(false);
	    txtUserId.setBounds(89, 32, 115, 20);
	    getContentPane().add(txtUserId);
	    txtUserId.setColumns(10);
	    
	    txtUserName = new JTextField();
	    txtUserName.setColumns(10);
	    txtUserName.setBounds(89, 102, 311, 20);
	    getContentPane().add(txtUserName);
	    
	    txtUserPhone = new JTextField();
	    txtUserPhone.setColumns(10);
	    txtUserPhone.setBounds(89, 176, 311, 20);
	    getContentPane().add(txtUserPhone);
	    
	    txtUserLogin = new JTextField();
	    txtUserLogin.setColumns(10);
	    txtUserLogin.setBounds(89, 240, 156, 20);
	    getContentPane().add(txtUserLogin);
	    
	    txtUserPassword = new JTextField();
	    txtUserPassword.setColumns(10);
	    txtUserPassword.setBounds(89, 277, 156, 20);
	    getContentPane().add(txtUserPassword);
	    
	    JComboBox cbxPerfil = new JComboBox();
	    cbxPerfil.setModel(new DefaultComboBoxModel(new String[] {"admin", "user"}));
	    cbxPerfil.setBounds(89, 343, 156, 22);
	    getContentPane().add(cbxPerfil);
	    
	    JButton btnUserAdd = new JButton("");
	    btnUserAdd.setToolTipText("Adicionar");
	    btnUserAdd.setIcon(new ImageIcon(TelaUsuario.class.getResource("/br/com/infox/icones/adicionar.png")));
	    btnUserAdd.setBounds(10, 393, 89, 52);
	    getContentPane().add(btnUserAdd);
	    
	    JButton btnUserFind = new JButton("");
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
    
	}
}
