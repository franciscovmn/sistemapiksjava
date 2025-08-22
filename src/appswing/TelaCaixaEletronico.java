package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Conta;
import modelo.ContaEspecial;
import repositorio.Repositorio;

public class TelaCaixaEletronico{
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_1;
	private JButton btnDepositar;
	private JTextField textField;
	private JLabel label_2;
	private JButton btnCreditar;
	private JButton btnTransferir;
	private JLabel label_3;
	private JTable table_1;
	private JScrollPane scrollPane_1;

	public TelaCaixaEletronico() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Caixa Eletronico");
		frame.setBounds(100, 100, 691, 386);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		
		btnDepositar = new JButton("Debitar");
		btnDepositar.setBackground(SystemColor.control);
		btnDepositar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText("");
					if (table.getSelectedRow() < 0) {
						throw new Exception ("Selecione uma conta");
					}
					String chaveOrigem = (String) table.getValueAt(table.getSelectedRow(), 1);
					Conta contaOrigem = Repositorio.localizarConta(chaveOrigem);
					Double valor = Double.parseDouble(textField.getText());
					
					if (valor <= 0) {
						throw new Exception ("valor deve ser positivo!");
					}
					
					contaOrigem.debitar(valor);
					Repositorio.gravarObjetos();
					label.setText("Debito realizado na conta id=" + contaOrigem.getId() );
					listagem();
				} catch (NumberFormatException ex) {
					label.setText("valor vazio");
				} catch (Exception e1) {
					label.setText(e1.getMessage());
				}
				
			}
		});
		
		btnDepositar.setBounds(467, 8, 99, 20);
		frame.getContentPane().add(btnDepositar);

		

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 620, 103);
		scrollPane.setViewportView(table);
		frame.getContentPane().add(scrollPane);
		
		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 310, 620, 14);
		frame.getContentPane().add(label);

		label_1 = new JLabel("Selecione a conta origem");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(22, 27, 207, 14);
		frame.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(243, 8, 105, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		label_2 = new JLabel("Valor:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_2.setBounds(203, 11, 46, 14);
		frame.getContentPane().add(label_2);
		
		btnCreditar = new JButton("Creditar");
		btnCreditar.setBackground(SystemColor.control);
		btnCreditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText("");
					if (table.getSelectedRow() < 0) {
						throw new Exception ("Selecione uma conta");
					}
					String chaveOrigem = (String) table.getValueAt(table.getSelectedRow(), 1);
					Conta contaOrigem = Repositorio.localizarConta(chaveOrigem);
					Double valor = Double.parseDouble(textField.getText());
					
					if (valor <= 0) {
						throw new Exception ("valor deve ser positivo!");
					}
					
					contaOrigem.creditar(valor);
					Repositorio.gravarObjetos();
					label.setText("Credito realizado na conta id=" + contaOrigem.getId() );
					listagem();
				} catch (NumberFormatException ex) {
					label.setText("valor vazio");
			} catch (Exception e1) {
					label.setText(e1.getMessage());
				}
			}
		});
		btnCreditar.setBounds(358, 8, 99, 20);
		frame.getContentPane().add(btnCreditar);
		
		btnTransferir = new JButton("Transferir");
		btnTransferir.setBackground(SystemColor.control);
		btnTransferir.setBounds(358, 165, 99, 20);
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText("");
					if (table.getSelectedRow() < 0 || table_1.getSelectedRow() < 0) {
						throw new Exception ("Selecione as contas origem e destino");
					}
					String chaveOrigem = (String) table.getValueAt(table.getSelectedRow(), 1);
					Conta contaOrigem = Repositorio.localizarConta(chaveOrigem);
					
					String chaveDestino = (String) table_1.getValueAt(table_1.getSelectedRow(), 1);
					Conta contaDestino = Repositorio.localizarConta(chaveDestino);
					Double valor = Double.parseDouble(textField.getText());
					
					if (valor <= 0) {
						throw new Exception ("valor deve ser positivo!");
					}
					
					contaOrigem.transferir(contaDestino, valor);
					Repositorio.gravarObjetos();
					label.setText("PIKS realizado da conta id=" + contaOrigem.getId()+ " para conta id=" + contaDestino.getId()+ " data:"+LocalDateTime.now());
					listagem();
				} catch (NumberFormatException ex) {
					label.setText("valor vazio");
				} catch (Exception e1) {
					label.setText(e1.getMessage());
				}
				
			}
		});
		frame.getContentPane().add(btnTransferir);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(21, 196, 620, 103);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setShowGrid(true);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table_1.setRowSelectionAllowed(true);
		table_1.setRequestFocusEnabled(false);
		table_1.setGridColor(Color.BLACK);
		table_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table_1.setFocusable(false);
		table_1.setFillsViewportHeight(true);
		table_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		table_1.setBackground(Color.WHITE);
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		label_3 = new JLabel("Selecione a conta destino");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_3.setBounds(22, 180, 207, 14);
		frame.getContentPane().add(label_3);
		
		frame.setVisible(true);

	}

	public void listagem() {
		try{
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			table_1.setModel(model);

			//colunas
			model.addColumn("id");
			model.addColumn("chave");
			model.addColumn("saldo");
			model.addColumn("cpf");
			model.addColumn("nome");
			model.addColumn("especial");

			List<Conta> lista = Repositorio.getContas();
			for(Conta c : lista) {
				//linhas
				if(c instanceof ContaEspecial)
					model.addRow(new Object[]{c.getId(),c.getChavePiks(),c.getSaldo(), c.getCliente().getCpf(), c.getCliente().getNome(), "sim"});
				else
					model.addRow(new Object[]{c.getId(),c.getChavePiks(),c.getSaldo(), c.getCliente().getCpf(), c.getCliente().getNome(), "nao"});
			}

		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}
}
