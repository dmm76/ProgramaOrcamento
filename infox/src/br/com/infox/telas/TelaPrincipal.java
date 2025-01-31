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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblData;

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
		
		JDesktopPane desktopPanel = new JDesktopPane();
		desktopPanel.setBounds(10, 11, 482, 518);
		getContentPane().add(desktopPanel);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(493, 329, 281, 186);
		getContentPane().add(lblLogo);
		lblLogo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/br/com/infox/icones/logoX.png")));
		
		JLabel lblUsuario = new JLabel("Usuário");
		lblUsuario.setBounds(502, 37, 272, 22);
		getContentPane().add(lblUsuario);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(542, 283, 209, 35);
		getContentPane().add(lblData);
		lblData.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		//as linhas abaixo substituem a lbldata pela data atual do sistema
		//ao inicializar o form
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
