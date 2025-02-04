package br.com.infox.telas;

import java.awt.Dimension;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class TelaOs extends JInternalFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	// variavel armazenar um texto dependendo do radio button selecionado
	

	private static final long serialVersionUID = 1L;
	private JTextField textNumeroOs;
	private JTextField textDataOs;
	private JTextField txtCliPesquisar;
	private JTextField txtCliId;
	private JTable tblClientes;
	private JTextField txtEquipamento;
	private JTextField txtDefeito;
	private JTextField txtServico;
	private JTextField txtTecnico;
	private JTextField txtValor;
	
	//Variaveis usadas para a marcação dos radiobuttons
	private JRadioButton rdbOrcamento;
	private JRadioButton rdbOrdem;
	private String tipo;

	public TelaOs() {
		
		// Inicializando os RadioButtons antes de adicioná-los ao frame
        rdbOrcamento = new JRadioButton("Orçamento");
        rdbOrdem = new JRadioButton("Ordem de Serviço");

        // Adicionando os RadioButtons a um grupo para garantir seleção única
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rdbOrcamento);
        grupo.add(rdbOrdem);
		
		setTitle("Ordem de Serviços");
		setClosable(true); // Permite fechar o JInternalFrame
		setIconifiable(true); // Permite minimizar
		setMaximizable(true);
		setSize(482, 518);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblNewLabel = new JLabel("Nº OS");
		JLabel lblNewLabel_1 = new JLabel("Data");

		textNumeroOs = new JTextField();
		textNumeroOs.setPreferredSize(new Dimension(100, 20));

		textDataOs = new JTextField();
		textNumeroOs.setPreferredSize(new Dimension(120, 20));

		JRadioButton rdbOrcamento = new JRadioButton("Orçamento");
		rdbOrcamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atribuindo um texto a variavel tipo se selecionada
				tipo = "Orçamento";
			}
		});
		JRadioButton rdbOrdem = new JRadioButton("Ordem de Serviço");
		rdbOrdem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// atribuindo um texto a variavel tipo se selecionada
				tipo = "OS";
			}
		});

		// Configurando o GroupLayout para o painel
		GroupLayout panelLayout = new GroupLayout(panel);
		panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGroup(panelLayout
				.createSequentialGroup()
				.addGroup(panelLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(panelLayout.createSequentialGroup().addContainerGap().addComponent(textNumeroOs, 0, 0,
								Short.MAX_VALUE))
						.addComponent(lblNewLabel)
						.addGroup(panelLayout.createSequentialGroup().addContainerGap().addComponent(rdbOrcamento,
								GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(panelLayout.createParallelGroup(Alignment.LEADING, false).addComponent(lblNewLabel_1)
						.addComponent(rdbOrdem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(textDataOs))
				.addContainerGap(99, Short.MAX_VALUE)));
		panelLayout.setVerticalGroup(panelLayout.createParallelGroup(Alignment.LEADING).addGroup(panelLayout
				.createSequentialGroup()
				.addGroup(panelLayout
						.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(lblNewLabel_1))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(panelLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textNumeroOs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(textDataOs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE))
				.addGap(23).addGroup(panelLayout.createParallelGroup(Alignment.BASELINE).addComponent(rdbOrdem)
						.addComponent(rdbOrcamento))
				.addContainerGap(28, Short.MAX_VALUE)));
		panel.setLayout(panelLayout);
		panelLayout.setAutoCreateGaps(true);
		panelLayout.setAutoCreateContainerGaps(true);

		JLabel lblNewLabel_2 = new JLabel("Situação");

		JComboBox cbxSituacao = new JComboBox();
		cbxSituacao.setModel(new DefaultComboBoxModel(new String[] {"Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguradando Peças", "Abandonado pelo Cliente", "Retornou"}));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Cliente",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JButton btnOsAdicionar = new JButton("");
		btnOsAdicionar.setToolTipText("Adicionar OS");
		btnOsAdicionar.setPreferredSize(new Dimension(75, 47));
		btnOsAdicionar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/adicionar.png")));

		JButton btnOsProcurar = new JButton("");
		btnOsProcurar.setToolTipText("Procurar OS");
		btnOsProcurar.setPreferredSize(new Dimension(75, 47));
		btnOsProcurar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/find.png")));

		JButton btnOsEditar = new JButton("");
		btnOsEditar.setToolTipText("Editar OS");
		btnOsEditar.setPreferredSize(new Dimension(75, 47));
		btnOsEditar.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/edit.png")));

		JButton btnOsRemover = new JButton("");
		btnOsRemover.setToolTipText("Remover OS");
		btnOsRemover.setPreferredSize(new Dimension(75, 47));
		btnOsRemover.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/remove.png")));

		JButton btnOsImprimir = new JButton("");
		btnOsImprimir.setToolTipText("Imprimir OS");
		btnOsImprimir.setPreferredSize(new Dimension(75, 47));
		btnOsImprimir.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/print.png")));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		// Aplicando o GroupLayout ao conteúdo do JInternalFrame
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(cbxSituacao, 0, 129, Short.MAX_VALUE))
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 447, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnOsAdicionar, GroupLayout.PREFERRED_SIZE, 75,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnOsProcurar, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnOsEditar, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnOsRemover, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(btnOsImprimir, GroupLayout.PREFERRED_SIZE, 75, Short.MAX_VALUE)))
				.addGap(51)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(lblNewLabel_2).addComponent(cbxSituacao,
																GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
												.addGap(95))
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 117,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE).addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnOsAdicionar, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnOsProcurar, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(btnOsEditar, GroupLayout.PREFERRED_SIZE, 47,
												GroupLayout.PREFERRED_SIZE))
								.addComponent(btnOsRemover, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnOsImprimir, GroupLayout.PREFERRED_SIZE, 47,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panel_2.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("*Equipamento");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_5.setBounds(0, 14, 68, 14);
		panel_2.add(lblNewLabel_5);

		txtEquipamento = new JTextField();
		txtEquipamento.setColumns(10);
		txtEquipamento.setBounds(86, 11, 340, 20);
		panel_2.add(txtEquipamento);

		JLabel lblNewLabel_5_1 = new JLabel("*Defeito");
		lblNewLabel_5_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_5_1.setBounds(27, 40, 41, 14);
		panel_2.add(lblNewLabel_5_1);

		txtDefeito = new JTextField();
		txtDefeito.setColumns(10);
		txtDefeito.setBounds(86, 37, 340, 20);
		panel_2.add(txtDefeito);

		JLabel lblNewLabel_5_1_1 = new JLabel("Serviço");
		lblNewLabel_5_1_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_5_1_1.setBounds(33, 66, 35, 14);
		panel_2.add(lblNewLabel_5_1_1);

		txtServico = new JTextField();
		txtServico.setColumns(10);
		txtServico.setBounds(86, 63, 340, 20);
		panel_2.add(txtServico);

		JLabel lblNewLabel_5_1_2 = new JLabel("Técnico");
		lblNewLabel_5_1_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_5_1_2.setBounds(32, 92, 36, 14);
		panel_2.add(lblNewLabel_5_1_2);

		txtTecnico = new JTextField();
		txtTecnico.setColumns(10);
		txtTecnico.setBounds(86, 89, 129, 20);
		panel_2.add(txtTecnico);

		JLabel lblNewLabel_5_1_3 = new JLabel("Valor Total");
		lblNewLabel_5_1_3.setBounds(233, 92, 61, 14);
		panel_2.add(lblNewLabel_5_1_3);

		txtValor = new JTextField();
		txtValor.setColumns(10);
		txtValor.setBounds(304, 89, 122, 20);
		panel_2.add(txtValor);

		txtCliPesquisar = new JTextField();
		txtCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisar_cliente();
			}
		});
		txtCliPesquisar.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(TelaOs.class.getResource("/br/com/infox/icones/findmenor.png")));

		JLabel lblNewLabel_4 = new JLabel("*ID");

		txtCliId = new JTextField();
		txtCliId.setEditable(false);
		txtCliId.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1.createSequentialGroup()
						.addGap(10)
						.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(lblNewLabel_3).addGap(18).addComponent(lblNewLabel_4).addGap(10)
						.addComponent(txtCliId, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup().addContainerGap().addComponent(scrollPane,
								GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
				.createSequentialGroup()
				.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCliPesquisar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3).addComponent(lblNewLabel_4).addComponent(txtCliId,
								GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE).addGap(8)));

		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setar_campos();
			}
		});
		tblClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblClientes.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, { null, null, null },
				{ null, null, null }, { null, null, null }, }, new String[] { "Id", "Nome", "Telefone" }));

		// Ajuste da largura da coluna "Id" para 30 pixels
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(30);

		// Adicionando a tabela corretamente ao JScrollPane
		scrollPane.setViewportView(tblClientes);

		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		groupLayout.setAutoCreateGaps(true);
		groupLayout.setAutoCreateContainerGaps(true);

		conexao = ModuloConexao.conector();		
		
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent e) {
				// Ao abrir o form, marcar o radio button "Orçamento"
				rdbOrcamento.setSelected(true);
				tipo = "Orçamento"; 				
			}
		});

	}

	private void pesquisar_cliente() {
		String sql = "select idCli as Id, nomeCli as Nome, fonecli as Telefone from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliPesquisar.getText() + "%"); // "jo%" pesquisa o restante ao digitar
			rs = pst.executeQuery();
			// preencher a tabela
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void setar_campos() {
		int setar = tblClientes.getSelectedRow();
		txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString()); // 0 é o campo id do banco

	}
	
	//metodo para cadastrar um OS
	private void emitir_os() {
		String sql = "insert into tbos (tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli) values (?,?,?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
