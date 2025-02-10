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
import javax.swing.JFrame;
import br.com.infox.dal.ModuloConexao;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Classe Principal do Sistema
 * Gera a interface gráfica principal com menus de navegação e controle de usuários
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 */
public class TelaPrincipal extends JFrame {
	Connection conexao = null;

	private static final long serialVersionUID = 1L;
	// private JLabel lblData;
	private JDesktopPane desktopPanel;

	public static JMenuItem menCadUsuarios; // Agora pode ser acessado por outras classes
	public static JMenu menRelatorio;

	public static JMenuItem menCadCli; // Agora pode ser acessado por outras classes

	public static JLabel lblUsuario;

	/**
	 * Método principal que inicializa a aplicação e exibe a Tela Principal.
	 *
	 * @param args Argumentos da linha de comando (não utilizados).
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor que configura a Tela Principal do sistema.
	 * Inicializa menus, atalhos, componentes gráficos e conecta-se ao banco de dados.
	 */
	public TelaPrincipal() {
		conexao = ModuloConexao.conector();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("X - Sistema de Controle de Cadastro");
		setSize(800, 600); // Define um tamanho padrão maior
		setLocationRelativeTo(null); // Centraliza a tela no monitor

		// Criando a Barra de Menus

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menCadastro = new JMenu("Cadastro");
		menuBar.add(menCadastro);

		menCadCli = new JMenuItem("Clientes");
		/**
		 * Abre a Tela de Cadastro de Clientes dentro do DesktopPane.
		 */
		menCadCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCliente cliente = new TelaCliente(desktopPanel);
				desktopPanel.add(cliente);
				cliente.setVisible(true);
			}
		});
		menCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menCadastro.add(menCadCli);

		JMenuItem menCadOs = new JMenuItem("Os");
		/**
		 * Abre a Tela de Ordem de Serviço (OS) dentro do DesktopPane.
		 * Move a janela para frente para garantir que seja visível.
		 */
		menCadOs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// chamando a telaOs
				TelaOs os = new TelaOs(desktopPanel);
				desktopPanel.add(os);
				os.setVisible(true);
				try {
					os.setSelected(true); // Garante que a janela será ativa
				} catch (PropertyVetoException ex) {
					ex.printStackTrace();
				}
				os.toFront(); // Move a janela para frente
				os.moveToFront();
			}
		});
		menCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menCadastro.add(menCadOs);

		menCadUsuarios = new JMenuItem("Usuários");
		/**
		 * Abre a Tela de Cadastro de Usuários dentro do DesktopPane.
		 * Esta opção é habilitada apenas para usuários administradores.
		 */
		menCadUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// as linha abaixo abrem o form TelaUsuario dentro do desktopPane
				TelaUsuario usuario = new TelaUsuario(desktopPanel);
				desktopPanel.add(usuario);
				usuario.setVisible(true);

			}
		});
		menCadUsuarios.setEnabled(false);
		menCadUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		menCadastro.add(menCadUsuarios);

		menRelatorio = new JMenu("Relatório");
		menRelatorio.setEnabled(false);
		menuBar.add(menRelatorio);

		JMenuItem menRelCli = new JMenuItem("Clientes");
		menRelCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desse relatório?", "Atenção",
						JOptionPane.YES_NO_OPTION);

				if (confirma == JOptionPane.YES_OPTION) {
					try {
						// Certifique-se de que a conexão com o banco está ativa
						if (conexao == null || conexao.isClosed()) {
							JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados está fechada!");
							return;
						}

						// Criar um HashMap para os parâmetros do relatório (mesmo se estiver vazio)
						Map<String, Object> parametros = new HashMap<>();

						// Caminho do relatório (verifique se está correto)
						String caminhoRelatorio = "C:\\dbxrelatorios\\clientes.jasper";

						// Verificar se o arquivo existe antes de tentar abrir
						File relatorio = new File(caminhoRelatorio);
						if (!relatorio.exists()) {
							JOptionPane.showMessageDialog(null, "Erro: Arquivo do relatório não encontrado!");
							return;
						}

						// Preparar e exibir o relatório
						JasperPrint print = JasperFillManager.fillReport(caminhoRelatorio, parametros, conexao);
						JasperViewer.viewReport(print, false);

					} catch (Exception e2) {
						e2.printStackTrace(); // Exibir erro detalhado no console
						JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e2.getMessage());
					}
				}
			}
		});

		// Configuração do atalho de teclado
		menRelCli.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menRelatorio.add(menRelCli);

		JMenuItem menRelServ = new JMenuItem("Serviços");
		/**
		 * Gera e exibe um relatório de clientes usando JasperReports.
		 * 
		 * @throws SQLException Se ocorrer um erro ao acessar o banco de dados.
		 * @throws Exception Se houver falha ao gerar o relatório.
		 */
		menRelServ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerando um relatorio de serviços
				int confirma = JOptionPane.showConfirmDialog(null, "Confirma a emissão desse relatório?", "Atenção",
						JOptionPane.YES_NO_OPTION);

				if (confirma == JOptionPane.YES_OPTION) {
					try {
						// Certifique-se de que a conexão com o banco está ativa
						if (conexao == null || conexao.isClosed()) {
							JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados está fechada!");
							return;
						}

						// Criar um HashMap para os parâmetros do relatório (mesmo se estiver vazio)
						Map<String, Object> parametros = new HashMap<>();

						// Caminho do relatório (verifique se está correto)
						String caminhoRelatorio = "C:\\dbxrelatorios\\servicos.jasper";

						// Verificar se o arquivo existe antes de tentar abrir
						File relatorio = new File(caminhoRelatorio);
						if (!relatorio.exists()) {
							JOptionPane.showMessageDialog(null, "Erro: Arquivo do relatório não encontrado!");
							return;
						}

						// Preparar e exibir o relatório
						JasperPrint print = JasperFillManager.fillReport(caminhoRelatorio, parametros, conexao);
						JasperViewer.viewReport(print, false);

					} catch (Exception e2) {
						e2.printStackTrace(); // Exibir erro detalhado no console
						JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e2.getMessage());
					}
				}
			}
		});
		menRelServ.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menRelatorio.add(menRelServ);

		JMenu menAjuda = new JMenu("Ajuda");
		menuBar.add(menAjuda);

		JMenuItem menAjudaSobre = new JMenuItem("Sobre");
		/**
		 * Exibe a tela "Sobre" com informações do sistema.
		 */
		menAjudaSobre.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));		
		menAjudaSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		menAjuda.add(menAjudaSobre);

		JMenu menOpcoes = new JMenu("Opções");
		menuBar.add(menOpcoes);

		JMenuItem menOpcoesSair = new JMenuItem("Sair");
		/**
		 * Fecha o sistema após confirmar a intenção do usuário.
		 */
		menOpcoesSair.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menOpcoesSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// exibe uma caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção",
						JOptionPane.YES_NO_OPTION);
				if (sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		menOpcoes.add(menOpcoesSair);

		getContentPane().setLayout(null);

		desktopPanel = new JDesktopPane();
		desktopPanel.setBounds(10, 11, 482, 518);
		getContentPane().add(desktopPanel);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(493, 329, 281, 186);
		getContentPane().add(lblLogo);
		lblLogo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/logoX.png")));

		lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(510, 36, 272, 128);
		getContentPane().add(lblUsuario);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));

		JLabel lblData = new JLabel("Data");
		lblData.setBounds(542, 283, 209, 35);
		getContentPane().add(lblData);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 18));

		/**
		 * Atualiza automaticamente a data exibida na tela principal.
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				Date data = new Date();
				DateFormat formatador = DateFormat.getDateInstance(DateFormat.MEDIUM);
				lblData.setText(formatador.format(data));
			}
		});

	}
}
