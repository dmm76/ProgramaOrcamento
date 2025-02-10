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

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
// a linha abaixo importa recursos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tela de gerenciamento de clientes do sistema. Permite adicionar, editar,
 * excluir e pesquisar clientes.
 * 
 * @author Douglas Marcelo Monquero
 * @version 1.1
 * @since 2025
 */

public class TelaCliente extends JInternalFrame {

	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	JButton btnCliAdicionar;
	JButton btnCliEditar;
	JButton btnCliRemover;

	private static final long serialVersionUID = 1L;
	private JTextField txtCliName;
	private JTextField txtCliEndereco;
	private JTextField txtCliFone;
	private JTextField txtCliEmail;
	private JTextField textCliPesquisar;
	private JTable tblClientes;

	/**
	 * Create the frame.
	 */
	public TelaCliente(JDesktopPane desktopPane) {
		setTitle("Clientes");
		setClosable(true); // Permite fechar o JInternalFrame
		setIconifiable(true); // Permite minimizar
		setMaximizable(true); // Permite maximizar
		setResizable(false); // Permite redimensionamento
		setSize(482, 518); // Define o tamanho do frame
		getContentPane().setLayout(null);

		conexao = ModuloConexao.conector();

		JLabel lblNome = new JLabel("*Nome");
		lblNome.setBounds(22, 221, 63, 14);
		getContentPane().add(lblNome);

		JLabel lblEndereco = new JLabel("Endereço");
		lblEndereco.setBounds(22, 249, 63, 14);
		getContentPane().add(lblEndereco);

		JLabel lblFone = new JLabel("*Telefone");
		lblFone.setBounds(22, 277, 65, 14);
		getContentPane().add(lblFone);

		JLabel lblEmail = new JLabel("*Email");
		lblEmail.setBounds(22, 305, 63, 14);
		getContentPane().add(lblEmail);

		txtCliName = new JTextField();
		txtCliName.setBounds(126, 218, 283, 20);
		getContentPane().add(txtCliName);
		txtCliName.setColumns(10);

		txtCliEndereco = new JTextField();
		txtCliEndereco.setColumns(10);
		txtCliEndereco.setBounds(126, 246, 283, 20);
		getContentPane().add(txtCliEndereco);

		txtCliFone = new JTextField();
		txtCliFone.setColumns(10);
		txtCliFone.setBounds(128, 274, 283, 20);
		getContentPane().add(txtCliFone);

		txtCliEmail = new JTextField();
		txtCliEmail.setColumns(10);
		txtCliEmail.setBounds(126, 302, 283, 20);
		getContentPane().add(txtCliEmail);

		JLabel lblNewLabel = new JLabel("*Campos Obrigatórios");
		lblNewLabel.setBounds(66, 349, 141, 14);
		getContentPane().add(lblNewLabel);

		btnCliAdicionar = new JButton("");
		btnCliAdicionar.setToolTipText("Adicionar");
		btnCliAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Chamar o método adicionar
				adicionar();
			}
		});
		btnCliAdicionar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/adicionar.png")));
		btnCliAdicionar.setBounds(66, 395, 89, 52);
		getContentPane().add(btnCliAdicionar);

		btnCliEditar = new JButton("");
		btnCliEditar.setToolTipText("Editar");
		btnCliEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnCliEditar.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/edit.png")));
		btnCliEditar.setBounds(193, 395, 89, 52);
		getContentPane().add(btnCliEditar);

		btnCliRemover = new JButton("");
		btnCliRemover.setToolTipText("Excluir");
		btnCliRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnCliRemover.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/remove.png")));
		btnCliRemover.setBounds(320, 395, 89, 52);
		getContentPane().add(btnCliRemover);

		textCliPesquisar = new JTextField();
		textCliPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// O evento é do tipo "enquanto for pesquisando"
				pesquisar();
			}
		});
		textCliPesquisar.setBounds(22, 23, 227, 20);
		getContentPane().add(textCliPesquisar);
		textCliPesquisar.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(TelaCliente.class.getResource("/br/com/infox/icones/searchzinho.png")));
		lblNewLabel_1.setBounds(259, 23, 23, 20);
		getContentPane().add(lblNewLabel_1);

		// Configurando a JTable
		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Evento que será usado para setar os campos ao clicar na tabela
				if (tblClientes.getRowCount() > 0) { // Verifica se há dados na tabela
					setar_campos();
				}
			}
		});

		tblClientes.setFillsViewportHeight(true); // Permite que a tabela ocupe todo o espaço do JScrollPane
		tblClientes.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null },
				{ null, null, null, null, null }, { null, null, null, null, null }, { null, null, null, null, null }, },
				new String[] { "ID", "Nome", "Endereço", "Telefone", "Email" }) {
			Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class, String.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Impede a edição das células
			}
		});

		// Configurando o tamanho das colunas
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(30); // Coluna "ID"
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(150); // Coluna "Nome"
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(150); // Coluna "Endereço"
		tblClientes.getColumnModel().getColumn(3).setPreferredWidth(100); // Coluna "Telefone"
		tblClientes.getColumnModel().getColumn(4).setPreferredWidth(100); // Coluna "Email"

		// Adicionando a tabela a um JScrollPane
		JScrollPane scrollPane = new JScrollPane(tblClientes);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Barra de rolagem sempre
																								// visível
		scrollPane.setBounds(22, 65, 415, 108); // Dimensão do JScrollPane
		getContentPane().add(scrollPane);
	}

	/**
	 * Limpa todos os campos do formulário e esvazia a tabela de clientes. Útil após
	 * adicionar, editar ou remover um cliente.
	 */
	private void limparTela() {
		textCliPesquisar.setText(null);
		txtCliName.setText("");
		txtCliEndereco.setText("");
		txtCliFone.setText("");
		txtCliEmail.setText("");
		((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
	}

	/**
	 * Adiciona um novo cliente ao banco de dados. Valida os campos obrigatórios
	 * antes de inserir os dados.
	 * 
	 * @throws SQLException Se ocorrer um erro ao inserir no banco de dados.
	 */
	private void adicionar() {
		String sql = "insert into tbclientes(nomecli, endcli, fonecli, emailcli) values(?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtCliName.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());

			// Validação dos campos obrigatórios
			if (txtCliName.getText().isEmpty() || txtCliFone.getText().isEmpty() || txtCliEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
			} else {
				int adicionado = pst.executeUpdate();
				// Se adicionado > 0, significa que uma linha foi inserida
				if (adicionado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
					limparTela();
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Pesquisa clientes no banco de dados pelo nome. Atualiza a tabela com os
	 * resultados encontrados.
	 * 
	 * @throws SQLException Se ocorrer um erro na consulta ao banco.
	 */
	private void pesquisar() {
		String sql = "select * from tbclientes where nomecli like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, textCliPesquisar.getText() + "%");
			rs = pst.executeQuery();

			// Usa o DbUtils para preencher a tabela
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

			// Reaplica o modelo com a restrição de edição
			DefaultTableModel model = new DefaultTableModel(new Object[][] {},
					new String[] { "ID", "Nome", "Endereço", "Telefone", "E-mail" }) {
				Class[] columnTypes = new Class[] { Integer.class, String.class, String.class, String.class,
						String.class };

				@Override
				public boolean isCellEditable(int row, int column) {
					return false; // Impede edição
				}

				@Override
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			};

			// Copia os dados preenchidos pelo DbUtils para o novo modelo
			for (int i = 0; i < tblClientes.getRowCount(); i++) {
				Object[] rowData = new Object[tblClientes.getColumnCount()];
				for (int j = 0; j < tblClientes.getColumnCount(); j++) {
					rowData[j] = tblClientes.getValueAt(i, j);
				}
				model.addRow(rowData);
			}

			// Define o novo modelo na tabela
			tblClientes.setModel(model);

			// Ajusta os cabeçalhos novamente
			tblClientes.getColumnModel().getColumn(0).setHeaderValue("ID");
			tblClientes.getColumnModel().getColumn(1).setHeaderValue("Nome");
			tblClientes.getColumnModel().getColumn(2).setHeaderValue("Endereço");
			tblClientes.getColumnModel().getColumn(3).setHeaderValue("Telefone");
			tblClientes.getColumnModel().getColumn(4).setHeaderValue("E-mail");
			tblClientes.getTableHeader().repaint();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	/**
	 * Preenche os campos do formulário com os dados do cliente selecionado na
	 * tabela. Se nenhuma linha for selecionada, não realiza nenhuma ação.
	 */
	public void setar_campos() {
		int setar = tblClientes.getSelectedRow();
		if (setar >= 0) { // Verifica se há uma linha selecionada
			// Obtém os valores da tabela com verificação de null
			Object nome = tblClientes.getModel().getValueAt(setar, 1);
			Object endereco = tblClientes.getModel().getValueAt(setar, 2);
			Object telefone = tblClientes.getModel().getValueAt(setar, 3);
			Object email = tblClientes.getModel().getValueAt(setar, 4);

			// Preenche os campos garantindo que valores nulos não causem erros
			txtCliName.setText(nome != null ? nome.toString() : "");
			txtCliEndereco.setText(endereco != null ? endereco.toString() : "");
			txtCliFone.setText(telefone != null ? telefone.toString() : "");
			txtCliEmail.setText(email != null ? email.toString() : "");

			// a linha abaixo desabilita o botao adicionar
			btnCliAdicionar.setEnabled(false);
//            btnCliEditar.setEnabled(true);
//            btnCliRemover.setEnabled(false);
		}
	}

	/**
	 * Atualiza os dados do cliente selecionado no banco de dados. Valida os campos
	 * obrigatórios antes de realizar a atualização.
	 *
	 * @throws SQLException Se ocorrer um erro ao atualizar os dados.
	 */
	private void alterar() {
		String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=? where idcli=?";
		try {
			// Preparar a conexão
			pst = conexao.prepareStatement(sql);

			pst.setString(1, txtCliName.getText());
			pst.setString(2, txtCliEndereco.getText());
			pst.setString(3, txtCliFone.getText());
			pst.setString(4, txtCliEmail.getText());

			// O ID deve ser usado como critério para evitar conflitos
			int linhaSelecionada = tblClientes.getSelectedRow();
			if (linhaSelecionada >= 0) {
				String idCliente = tblClientes.getModel().getValueAt(linhaSelecionada, 0).toString();
				pst.setString(5, idCliente); // Define o ID do cliente como critério
			} else {
				JOptionPane.showMessageDialog(null, "Selecione um cliente na tabela para alterar.");
				return;
			}

			// Valida os campos obrigatórios
			if (txtCliName.getText().isEmpty() || txtCliEndereco.getText().isEmpty()
					|| txtCliEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios!");
				return;
			}

			// Executa a atualização
			int alterado = pst.executeUpdate();
			System.out.println(alterado);
			if (alterado > 0) {
				JOptionPane.showMessageDialog(null, "Dados do usuário alterado com sucesso!");
				limparTela();
				btnCliAdicionar.setEnabled(true);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					"Erro ao alterar cliente! Verifique a conexão com o banco.\n" + e.getMessage(),
					"Erro de Banco de Dados", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(); // Exibe detalhes no console para depuração
		}
	}

	/**
	 * Remove o cliente selecionado da base de dados. Exibe uma mensagem de
	 * confirmação antes de excluir.
	 *
	 * @throws SQLException Se ocorrer um erro ao excluir o cliente.
	 */
	private void remover() {
		// antes de remover necessario fazer a confirmacao da remocao
		int confirma = JOptionPane.showConfirmDialog(null,
				"Tem certeza que deseja remover este cliente? Esta ação não pode ser desfeita.",
				"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (confirma == JOptionPane.YES_OPTION) {
			int linhaSelecionada = tblClientes.getSelectedRow();
			if (linhaSelecionada < 0) {
				JOptionPane.showMessageDialog(null, "Selecione um cliente para remover!");
				return;
			}
			// Recupera o ID do cliente da linha selecionada (supondo que o ID esteja na
			// coluna 0)
			String idCliente = tblClientes.getModel().getValueAt(linhaSelecionada, 0).toString();
			System.out.println(idCliente);
			String sql = "delete from tbclientes where idcli=?";
			try {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, idCliente);
				int apagado = pst.executeUpdate();
				if (apagado > 0) {
					JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!");
					limparTela();
					btnCliAdicionar.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum cliente foi removido.");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao remover cliente: " + e.getMessage());
			}
		}
	}
}
