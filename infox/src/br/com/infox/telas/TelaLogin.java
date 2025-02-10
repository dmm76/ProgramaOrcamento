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

package br.com.infox.telas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Tela de Login do sistema. Permite que os usuários façam login com
 * autenticação no banco de dados.
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 * @since 2025
 */

public class TelaLogin extends JFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * Realiza a autenticação do usuário com o banco de dados. Se o login for
	 * bem-sucedido, abre a tela principal e define permissões.
	 * 
	 * @throws SQLException         Se houver erro ao acessar o banco de dados.
	 * @throws NullPointerException Se a conexão com o banco não estiver ativa. *
	 */

	public void logar() {
		String sql = "select * from tbusuarios where login=? and senha=?";
		try {
			// as linhas abaixo preparam a consulta ao banco em funcao do que foi digitado
			// no fields
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());
			// aumentando a seguranca recuperando um digito por vez na senha
			String captura = new String(pswSenha.getPassword());
			pst.setString(2, captura);
			// a linha abaixo executa a query/consulta/select
			rs = pst.executeQuery();

			// se existir usuario e senha correspondente
			if (rs.next()) {

				// alinha abaixo obtem o conteudo do campo perfil da tbusuario do banco
				String perfil = rs.getString(6);
				// System.out.println(perfil);

				// a estrutura abaixo faz o tratamento do pefil do usuario
				if (perfil.equals("admin")) {
					// alinha abaixo exibe o conteudo do campo da tabela
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.menRelatorio.setEnabled(true);
					TelaPrincipal.menCadUsuarios.setEnabled(true);
					TelaPrincipal.lblUsuario.setText("<html>Seja bem-vindo(a)!<br>" + rs.getString(2) + "</html>");
					// fecha a tela de login e a conexao ao bd
					conexao.close();
					this.dispose();
				} else {
					TelaPrincipal principal = new TelaPrincipal();
					principal.setVisible(true);
					TelaPrincipal.lblUsuario.setText("<html>Bem-vindo(a)!<br>" + rs.getString(2) + "</html>");
					conexao.close();
					this.dispose();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos. Verifique e tente novamente.",
						"Erro de Login", JOptionPane.ERROR_MESSAGE);
			}
			// Limpar os campos após a tentativa de login
			txtUsuario.setText("");
			pswSenha.setText("");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static final long serialVersionUID = 1L;
	private JTextField txtUsuario;
	private JPasswordField pswSenha;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 297, 192);
		getContentPane().setLayout(null);

		// Centralizar a janela
		setLocationRelativeTo(null);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamando o metodo logar
				logar();
			}
		});
		btnLogin.setBounds(159, 115, 98, 20);
		getContentPane().add(btnLogin);

		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(12, 26, 55, 16);
		getContentPane().add(lblUsuario);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(12, 71, 55, 16);
		getContentPane().add(lblSenha);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(85, 24, 172, 20);
		getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);

		pswSenha = new JPasswordField();
		pswSenha.setBounds(85, 69, 172, 20);
		getContentPane().add(pswSenha);

		JLabel lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon("C:\\Users\\Extreme\\Pictures\\DBImagens\\dbstage.png"));
		lblStatus.setBounds(22, 99, 34, 44);
		getContentPane().add(lblStatus);

		JLabel lblStatus2 = new JLabel("");
		lblStatus2.setIcon(new ImageIcon("C:\\Users\\Extreme\\Pictures\\DBImagens\\dberror.png"));
		lblStatus2.setBounds(22, 99, 34, 46);
		getContentPane().add(lblStatus2);

		conexao = ModuloConexao.conector();
		// a linha abaixo serve de apoio ao status da conexao
		// System.out.println(conexao);
		if (conexao != null) {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dbstage.png")));
		} else {
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/dberror.png")));
		}

		// Configurando o botão Login como padrão para o Enter
		getRootPane().setDefaultButton(btnLogin);

	}
}
