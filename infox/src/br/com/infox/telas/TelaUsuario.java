/*
 * MIT License
 * 
 * Copyright (c) 2025 Douglas Marcelo Monquero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 * 
 * 1. **Attribution Required**: Any use, modification, or distribution of this
 *    software must include credit to the original author, Douglas Marcelo Monquero,
 *    in any derivative works or publications.
 *
 * 2. **Disclaimer**: THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/**
 * Tela de Cadastro de Usuários
 * Permite adicionar, consultar, alterar e remover usuários do sistema.
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 */
package br.com.infox.telas;

import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;

/**
 * Classe TelaUsuario que representa a interface de gerenciamento de usuários.
 */
public class TelaUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	private JTextField txtUserId;
	private JTextField txtUserName;
	private JTextField txtUserPhone;
	private JTextField txtUserLogin;
	private JTextField txtUserPassword;
	private JComboBox<String> cbxPerfil;

	/**
	 * Construtor da TelaUsuario.
	 * 
	 * @param desktopPane JDesktopPane onde a tela será exibida.
	 */
	public TelaUsuario(JDesktopPane desktopPane) {
		setTitle("Usuários");
		setClosable(true); // Permite fechar o JInternalFrame
		setIconifiable(true); // Permite minimizar
		setMaximizable(true); // Permite maximizar
		setResizable(false); // Permite redimensionamento
		setSize(482, 518); // Define o tamanho do frame
		getContentPane().setLayout(null);

		// Chama o modulo de conexao com o banco
		conexao = ModuloConexao.conector();

		JLabel lblId = new JLabel("*Id");
		lblId.setBounds(38, 35, 22, 14);
		getContentPane().add(lblId);

		JLabel lblNome = new JLabel("*Nome");
		lblNome.setBounds(21, 206, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblFone = new JLabel("Fone");
		lblFone.setBounds(21, 255, 46, 14);
		getContentPane().add(lblFone);

		JLabel lblLogin = new JLabel("*Login");
		lblLogin.setBounds(21, 90, 46, 14);
		getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("*Senha");
		lblSenha.setBounds(21, 139, 46, 14);
		getContentPane().add(lblSenha);

		JLabel lblPerfil = new JLabel("*Perfil");
		lblPerfil.setBounds(186, 35, 46, 14);
		getContentPane().add(lblPerfil);

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

		cbxPerfil = new JComboBox<>(new String[] { "admin", "user" });
		cbxPerfil.setBounds(244, 30, 156, 22);
		getContentPane().add(cbxPerfil);

		JButton btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setIcon(new ImageIcon(getClass().getResource("/br/com/infox/icones/adicionar.png")));
		btnAdicionar.setBounds(10, 393, 89, 52);
		btnAdicionar.addActionListener(e -> adicionar());
		getContentPane().add(btnAdicionar);

		JButton btnConsultar = new JButton("");
		btnConsultar.setToolTipText("Procurar");
		btnConsultar.setIcon(new ImageIcon(getClass().getResource("/br/com/infox/icones/find.png")));
		btnConsultar.setBounds(128, 393, 89, 52);
		btnConsultar.addActionListener(e -> consultar());
		getContentPane().add(btnConsultar);

		JButton btnEditar = new JButton("");
		btnEditar.setToolTipText("Editar");
		btnEditar.setIcon(new ImageIcon(getClass().getResource("/br/com/infox/icones/edit.png")));
		btnEditar.setBounds(246, 393, 89, 52);
		btnEditar.addActionListener(e -> alterar());
		getContentPane().add(btnEditar);

		JButton btnRemover = new JButton("");
		btnRemover.setToolTipText("Remover");
		btnRemover.setIcon(new ImageIcon(getClass().getResource("/br/com/infox/icones/remove.png")));
		btnRemover.setBounds(367, 393, 89, 52);
		btnRemover.addActionListener(e -> remover());
		getContentPane().add(btnRemover);

		JLabel lblCamposObrigatorios = new JLabel("*Campos Obrigatórios");
		lblCamposObrigatorios.setBounds(21, 319, 131, 14);
		getContentPane().add(lblCamposObrigatorios);

		// Centralizando no JDesktopPane
		if (desktopPane != null) {
			int x = (desktopPane.getWidth() - getWidth()) / 2;
			int y = (desktopPane.getHeight() - getHeight()) / 2;
			setLocation(x, y);
		}

	}

	/**
	 * Limpa os campos da tela.
	 */
	private void limparTela() {
		txtUserId.setText("");
		txtUserName.setText("");
		txtUserPhone.setText("");
		txtUserLogin.setText("");
		txtUserPassword.setText("");
		// cbxPerfil.setSelectedIndex(-1);
	}

	/**
	 * Consulta um usuário pelo ID.
	 */
	private void consultar() {
		String sql = "SELECT * FROM tbusuarios WHERE iduser = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUserId.getText());
			rs = pst.executeQuery();

			if (rs.next()) {
				txtUserName.setText(rs.getString(2));
				txtUserPhone.setText(rs.getString(3));
				txtUserLogin.setText(rs.getString(4));
				txtUserPassword.setText(rs.getString(5));
				cbxPerfil.setSelectedItem(rs.getString(6));
			} else {
				JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
				limparTela();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao consultar usuário: " + e.getMessage());
		}
	}

	/**
	 * Adiciona um novo usuário ao banco de dados.
	 */
	private void adicionar() {
		String sql = "INSERT INTO tbusuarios(iduser, usuario, fone, login, senha, perfil) VALUES(?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUserId.getText());
			pst.setString(2, txtUserName.getText());
			pst.setString(3, txtUserPhone.getText());
			pst.setString(4, txtUserLogin.getText());
			pst.setString(5, txtUserPassword.getText());
			pst.setString(6, cbxPerfil.getSelectedItem().toString());

			int adicionado = pst.executeUpdate();
			if (adicionado > 0) {
				JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
				limparTela();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao adicionar usuário: " + e.getMessage());
		}
	}

	/**
	 * Atualiza os dados de um usuário existente.
	 */
	private void alterar() {
		String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
		try {
			// preparar a conexao
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUserName.getText());
			pst.setString(2, txtUserPhone.getText());
			pst.setString(3, txtUserLogin.getText());
			pst.setString(4, txtUserPassword.getText());
			pst.setString(5, cbxPerfil.getSelectedItem().toString());
			pst.setString(6, txtUserId.getText());
			if ((txtUserId.getText().isEmpty()) || (txtUserName.getText().isEmpty())
					|| (txtUserLogin.getText().isEmpty()) || (txtUserPassword.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
				limparTela();
			} else {
				// a linha abaixo alterar a tabela de usuario com os dados do formulario
				// Altera a tabela com os dados do formulário
				// pst.executeUpdate();
				// modificado para ao executar exibir uma mensagem caso tudo ocorreu bem
				int adicionado = pst.executeUpdate();
				// maior que zero pq ao adicionar uma (1) linha o programa retorna 1
				// System.out.println(adicionado);
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Dados do usuário alterado com sucesso!");
					txtUserId.setText("");
					limparTela();
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Remove um usuário do banco de dados.
	 */
	private void remover() {
		// antes de remover necessario fazer a confirmacao da remocao
		int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover o usuários", "Atenção",
				JOptionPane.YES_NO_OPTION);

		if (confirma == JOptionPane.YES_OPTION) {
			String sql = "delete from tbusuarios where iduser=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, txtUserId.getText());

				// maior que zero pq ao adicionar uma (1) linha o programa retorna 1
				int apagado = pst.executeUpdate();
				// System.out.println(apagado);
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
					txtUserId.setText("");
					limparTela();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}
	}
}
