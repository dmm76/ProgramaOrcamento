package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JDesktopPane;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("X - Sistema de Controle de Cadastro");
        setSize(800, 600); // Define um tamanho padrão maior
        setLocationRelativeTo(null); // Centraliza a tela no monitor
		
        
        //Criando a Barra de Menus
                
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu MenCadastro = new JMenu("Cadastro");
		menuBar.add(MenCadastro);
		
		JMenuItem MenCadCli = new JMenuItem("Clientes");
		MenCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadCli);
		
		JMenuItem MenCadOs = new JMenuItem("Os");
		MenCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadOs);
		
		JMenuItem MenCadUsuarios = new JMenuItem("Usuários");
		MenCadUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadUsuarios);
		
		JMenu MenRelatorio = new JMenu("Relatório");
		menuBar.add(MenRelatorio);
		
		JMenuItem MenRelatorioServicos = new JMenuItem("Serviços");
		MenRelatorio.add(MenRelatorioServicos);		
		
		JMenu MenAjuda = new JMenu("Ajuda");
		menuBar.add(MenAjuda);
		
		JMenuItem MenAjudaSobre = new JMenuItem("Sobre");
		MenAjuda.add(MenAjudaSobre);
		
		JMenu MenOpcoes = new JMenu("Opções");
		menuBar.add(MenOpcoes);
		
		JMenuItem MenOpcoesSair = new JMenuItem("Sair");
		MenOpcoes.add(MenOpcoesSair);
		
		getContentPane().setLayout(null);
		
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(null); // Mantendo a flexibilidade
		painelPrincipal.setBounds(0, 0, 784, 600);
		getContentPane().add(painelPrincipal);
		
		// Criando o DesktopPane dentro do painel
		JDesktopPane desktopPanel = new JDesktopPane();
		desktopPanel.setBounds(10, 10, 482, 504); // Espaço ajustável para componentes extras
		painelPrincipal.add(desktopPanel);
		
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblUsuario.setBounds(502, 52, 74, 22);
		painelPrincipal.add(lblUsuario);
		
		JLabel lblData = new JLabel("Data");
		lblData.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblData.setBounds(502, 137, 60, 35);
		painelPrincipal.add(lblData);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/logoX.png")));
		lblLogo.setBounds(495, 316, 281, 186);
		painelPrincipal.add(lblLogo);
		
	}
}
