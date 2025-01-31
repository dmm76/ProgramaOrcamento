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
	public static JMenuItem MenCadUsuarios; // Agora pode ser acessado por outras classes
	public static JMenu MenRelatorio;
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
		
		JMenu MenCadastro = new JMenu("Cadastro");
		menuBar.add(MenCadastro);
		
		JMenuItem MenCadCli = new JMenuItem("Clientes");
		MenCadCli.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadCli);
		
		JMenuItem MenCadOs = new JMenuItem("Os");
		MenCadOs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadOs);
		
		MenCadUsuarios = new JMenuItem("Usuários");
		MenCadUsuarios.setEnabled(false);
		MenCadUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.ALT_DOWN_MASK));
		MenCadastro.add(MenCadUsuarios);
		
		MenRelatorio = new JMenu("Relatório");
		MenRelatorio.setEnabled(false);
		menuBar.add(MenRelatorio);
		
		JMenuItem MenRelatorioServicos = new JMenuItem("Serviços");
		MenRelatorio.add(MenRelatorioServicos);		
		
		JMenu MenAjuda = new JMenu("Ajuda");
		menuBar.add(MenAjuda);
		
		JMenuItem MenAjudaSobre = new JMenuItem("Sobre");
		MenAjudaSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//chamando a tela sobre
				TelaSobre sobre = new TelaSobre();
				sobre.setVisible(true);
			}
		});
		MenAjuda.add(MenAjudaSobre);
		
		JMenu MenOpcoes = new JMenu("Opções");
		menuBar.add(MenOpcoes);
		
		JMenuItem MenOpcoesSair = new JMenuItem("Sair");
		MenOpcoesSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//exibe uma caixa de dialogo
				int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Atenção", JOptionPane.YES_NO_OPTION);
				if(sair == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		MenOpcoes.add(MenOpcoesSair);
		
		getContentPane().setLayout(null);
		
		JDesktopPane desktopPanel = new JDesktopPane();
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
