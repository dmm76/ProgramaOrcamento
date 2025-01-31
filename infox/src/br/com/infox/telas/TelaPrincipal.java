package br.com.infox.telas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	//private JLabel lblData;
	private JDesktopPane desktopPanel;
	
	public static JMenuItem menCadUsuarios; // Agora pode ser acessado por outras classes
	public static JMenu menRelatorio;
	public static JLabel lblUsuario;
	
	
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
		
		JMenu menCadastro = new JMenu("Cadastro");
		menuBar.add(menCadastro);
		
		JMenuItem menCadCli = new JMenuItem("Clientes");
		menCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		menCadastro.add(menCadCli);
		
		JMenuItem menCadOs = new JMenuItem("Os");
		menCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		menCadastro.add(menCadOs);
		
		menCadUsuarios = new JMenuItem("Usuários");
		menCadUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//as linha abaixo abrem o form TelaUsuario dentro do desktopPane
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
		
		JMenuItem menRelatorioServicos = new JMenuItem("Serviços");
		menRelatorioServicos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menRelatorio.add(menRelatorioServicos);		
		
		JMenu menAjuda = new JMenu("Ajuda");
		menuBar.add(menAjuda);
		
		JMenuItem menAjudaSobre = new JMenuItem("Sobre");
		menAjudaSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menAjudaSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chamando a tela sobre
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		menAjuda.add(menAjudaSobre);
		
		JMenu menOpcoes = new JMenu("Opções");
		menuBar.add(menOpcoes);
		
		JMenuItem menOpcoesSair = new JMenuItem("Sair");
		menOpcoesSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
		menOpcoesSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exibe uma caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
				if(sair == JOptionPane.YES_OPTION) {
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
		lblUsuario.setBounds(510, 36, 272, 45);
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
