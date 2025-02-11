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
import java.sql.*;
import javax.swing.*;
import br.com.infox.dal.ModuloConexao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Classe Principal do Sistema - Interface gráfica principal com menus e
 * controle de usuários.
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 */
public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private Connection conexao = null;
	private JDesktopPane desktopPanel;
	public static JMenuItem menCadUsuarios;
	public static JMenu menRelatorio;
	public static JMenuItem menCadCli;
	public static JLabel lblUsuario;

	/**
	 * Método principal que inicializa a aplicação e exibe a Tela Principal.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TelaPrincipal frame = new TelaPrincipal();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Construtor da Tela Principal - Inicializa menus, atalhos e componentes
	 * gráficos.
	 */
	public TelaPrincipal() {
		conexao = ModuloConexao.conector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("X - Sistema de Controle de Cadastro");
		setSize(800, 600);
		setLocationRelativeTo(null);

		// Criar Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		criarMenuCadastro(menuBar);
		criarMenuRelatorio(menuBar);
		criarMenuAjuda(menuBar);
		criarMenuOpcoes(menuBar);

		// Área principal
		getContentPane().setLayout(null);
		desktopPanel = new JDesktopPane();
		desktopPanel.setBounds(10, 11, 482, 518);
		getContentPane().add(desktopPanel);

		// Informações do Usuário
		lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(510, 36, 272, 128);
		lblUsuario.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
		getContentPane().add(lblUsuario);

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(542, 283, 209, 35);
		lblData.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
		getContentPane().add(lblData);

		// Adicionando a IMAGEM DO LOGO
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(493, 329, 281, 186);
		lblLogo.setIcon(new ImageIcon(getClass().getResource("/br/com/infox/icones/logoX.png")));
		getContentPane().add(lblLogo);

		// Atualizar Data na Tela
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				lblData.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(new Date()));
			}
		});
	}

	/**
	 * Cria o menu de Cadastro.
	 */
	private void criarMenuCadastro(JMenuBar menuBar) {
		JMenu menCadastro = new JMenu("Cadastro");
		menuBar.add(menCadastro);

		menCadCli = new JMenuItem("Clientes");
		menCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menCadCli.addActionListener(e -> abrirTela(new TelaCliente(desktopPanel)));
		menCadastro.add(menCadCli);

		JMenuItem menCadOs = new JMenuItem("Ordem de Serviço");
		menCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menCadOs.addActionListener(e -> abrirTela(new TelaOs(desktopPanel)));
		menCadastro.add(menCadOs);

		menCadUsuarios = new JMenuItem("Usuários");
		menCadUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		menCadUsuarios.setEnabled(false);
		menCadUsuarios.addActionListener(e -> abrirTela(new TelaUsuario(desktopPanel)));
		menCadastro.add(menCadUsuarios);
	}

	/**
	 * Cria o menu de Relatórios.
	 */
	private void criarMenuRelatorio(JMenuBar menuBar) {
		menRelatorio = new JMenu("Relatório");
		menRelatorio.setEnabled(false);
		menuBar.add(menRelatorio);

		JMenuItem menRelCli = new JMenuItem("Clientes");
		menRelCli.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menRelCli.addActionListener(e -> confirmarRelatorio("clientes.jasper"));
		menRelatorio.add(menRelCli);

		JMenuItem menRelServ = new JMenuItem("Serviços");
		menRelServ.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menRelServ.addActionListener(e -> confirmarRelatorio("servicos.jasper"));
		menRelatorio.add(menRelServ);
	}

	/**
	 * Cria o menu de Ajuda.
	 */
	private void criarMenuAjuda(JMenuBar menuBar) {
		JMenu menAjuda = new JMenu("Ajuda");
		menuBar.add(menAjuda);

		JMenuItem menAjudaSobre = new JMenuItem("Sobre");
		menAjudaSobre.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menAjudaSobre.addActionListener(e -> new TelaSobre().setVisible(true));
		menAjuda.add(menAjudaSobre);
	}

	/**
	 * Cria o menu de Opções.
	 */
	private void criarMenuOpcoes(JMenuBar menuBar) {
		JMenu menOpcoes = new JMenu("Opções");
		menuBar.add(menOpcoes);

		JMenuItem menOpcoesSair = new JMenuItem("Sair");
		menOpcoesSair.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menOpcoesSair.addActionListener(e -> {
			int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (sair == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});
		menOpcoes.add(menOpcoesSair);
	}

	/**
	 * Método para abrir janelas dentro do JDesktopPane.
	 */
	private void abrirTela(JInternalFrame tela) {
		desktopPanel.add(tela);
		tela.setVisible(true);
		try {
			tela.setSelected(true);
		} catch (PropertyVetoException ex) {
			ex.printStackTrace();
		}
		tela.toFront();
	}

	/**
	 * Confirmação antes de gerar um relatório.
	 */
	private void confirmarRelatorio(String caminhoRelatorio) {
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão desse relatório?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			gerarRelatorio(caminhoRelatorio);
		}
	}

	/**
	 * Método para gerar e exibir relatórios.
	 */
	private void gerarRelatorio(String caminhoRelatorio) {
		try {
			InputStream relatorio = getClass().getResourceAsStream("/reports/" + caminhoRelatorio);
			if (relatorio == null) {
				JOptionPane.showMessageDialog(null, "Erro: Arquivo do relatório não encontrado!");
				return;
			}
			JasperPrint print = JasperFillManager.fillReport(relatorio, new HashMap<>(), conexao);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
		}
	}
}