package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Cliente;
import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Lancamento;
import repositorio.Repositorio;

public class TelaConta {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_6;
	private JLabel label_2;
	private JTextField textField_1;
	private JButton button_Criar;
	private JLabel label_Limite;
	private JTextField textField_3;
	private JButton button_Apagar;
	private JButton button_Limpar;
	private JButton button_Criar_1;
	private JLabel label_1;
	private JTextField textField;
	private JLabel label_3;
	private JTextField textField_2;
	private JLabel label_saldo;
	private JLabel label_4;
	private JTextField textField_4;
	private JButton button_Apagar_1;
	private JButton button_Criar_2;

	public TelaConta() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
		frame = new JDialog();
		frame.getContentPane().setBackground(SystemColor.inactiveCaption);
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Conta");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 32, 674, 159);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				label.setText("");
				// copiar dados (como string) da linha selecionada para os campos de texto
				if (table.getSelectedRow() >= 0) {
					Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
					String chave = (String) table.getValueAt(table.getSelectedRow(), 1);
					Double saldo = (Double) table.getValueAt(table.getSelectedRow(), 2);
					Double limite = (Double) table.getValueAt(table.getSelectedRow(), 3);
					Integer cpf = (Integer) table.getValueAt(table.getSelectedRow(), 4);
					String nome = (String) table.getValueAt(table.getSelectedRow(), 5);
					textField.setText(id.toString());
					label_saldo.setText("Saldo:" + saldo.toString());
					textField_1.setText(cpf.toString());
					textField_2.setText(nome);
					textField_3.setText(limite.toString());
					textField_4.setText(chave);
					label_6.setText("selecionado=" + id);
				}
			}
		});
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 330, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("Selecione");
		label_6.setBounds(21, 190, 674, 14);
		frame.getContentPane().add(label_6);

		button_Limpar = new JButton("Limpar");
		button_Limpar.setBackground(SystemColor.control);
		button_Limpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//label.setText("");
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_3.setText("");
				textField_4.setText("");
				label_saldo.setText("Saldo:");
				listagem();
			}
		});
		button_Limpar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Limpar.setBounds(336, 246, 116, 20);
		frame.getContentPane().add(button_Limpar);

		label_2 = new JLabel("CPF:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(21, 249, 34, 14);
		frame.getContentPane().add(label_2);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(52, 246, 104, 20);
		frame.getContentPane().add(textField_1);

		button_Criar = new JButton("Criar ");
		button_Criar.setBackground(SystemColor.control);
		button_Criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText("");
					if (textField.getText().isEmpty() || textField_1.getText().isEmpty()
							|| textField_2.getText().isEmpty() || textField_4.getText().isEmpty())
						throw new Exception("id, chave, cpf ou nome vazio!");

					int id = Integer.parseInt(textField.getText());
					String chave = textField_4.getText();
					int cpf = Integer.parseInt(textField_1.getText());
					String nome = textField_2.getText();

					if (!nome.matches("^[a-zA-Z\s]+$"))
						throw new Exception("nome deve ter letras e espaços!");

					if (chave.isEmpty())
						throw new Exception("chave nao pode ser vazia!");

					Conta conta = Repositorio.localizarConta(chave);
					if (conta != null)
						throw new Exception("chave " + chave + " ja existe!");

					Cliente cliente = Repositorio.localizarCliente(cpf);
					if (cliente != null)
						throw new Exception("cpf " + cpf + " ja existe!");

					cliente = new Cliente(cpf, nome);

					if (textField_3.getText().isEmpty()) // limite especial vazio
						conta = new Conta(id, chave);
					else {
						double limite = Double.parseDouble(textField_3.getText());
						conta = new ContaEspecial(id, chave, limite);
					}

					conta.setCliente(cliente);
					cliente.setConta(conta);
					Repositorio.adicionarConta(conta);
					Repositorio.adicionarCliente(cliente);
					Repositorio.gravarObjetos();
					label.setText("Conta criada!");
					listagem();
					button_Limpar.doClick(); // limpa os campos de texto

				} catch (NumberFormatException ex) {
					label.setText("formato numerico invalido");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_Criar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Criar.setBounds(476, 215, 116, 20);
		frame.getContentPane().add(button_Criar);

		label_Limite = new JLabel("Limite especial:");
		label_Limite.setHorizontalAlignment(SwingConstants.LEFT);
		label_Limite.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_Limite.setBounds(21, 280, 116, 14);
		frame.getContentPane().add(label_Limite);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(133, 277, 116, 20);
		frame.getContentPane().add(textField_3);

		button_Apagar = new JButton("Apagar"); // APAGAR CONTA
		button_Apagar.setBackground(SystemColor.control);
		button_Apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText(null);
					String chave = textField_4.getText();
					Conta conta = Repositorio.localizarConta(chave);
					if (conta == null)
						throw new Exception("chave " + chave + " nao existe!");

					if(conta.getSaldo() != 0) 
						throw new Exception("Conta com saldo não pode ser apagada ");

					Cliente cli = conta.getCliente();
					cli.setConta(null); // desvincula cliente
					conta.setCliente(null);
					Repositorio.removerConta(conta);
					Repositorio.removerCliente(cli);
					Repositorio.gravarObjetos();
					label.setText("deletou conta " + conta.getId());
					listagem();
					button_Limpar.doClick(); // limpa os campos de texto

				} catch (NumberFormatException ex) {
					label.setText("formato numerico invalido");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_Apagar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Apagar.setBounds(476, 246, 116, 20);
		frame.getContentPane().add(button_Apagar);

		button_Criar_1 = new JButton("Alterar limite");
		button_Criar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String chave = textField_4.getText();

					Conta conta = Repositorio.localizarConta(chave);
					if (conta == null)
						throw new Exception("Chave inexistente: " + chave);

					if (!(conta instanceof ContaEspecial))
						throw new Exception("Conta não é especial, não tem limite!");

					String resposta = JOptionPane.showInputDialog(frame, "novo limite da conta");
					if (resposta == null || resposta.isEmpty())
						throw new Exception("limite vazio! ");
					
					double novolimite = Double.parseDouble(resposta);

					if (novolimite <= 0)
						throw new Exception("Limite deve ser positivo!");

					ContaEspecial contaEspecial = (ContaEspecial) conta;
					double atual = contaEspecial.getLimite();
					contaEspecial.setLimite(novolimite);
					Repositorio.gravarObjetos();
					label.setText("limite alterado de " + atual + " para " + novolimite);
					listagem();
					button_Limpar.doClick(); // limpa os campos de texto

				} catch (NumberFormatException ex) {
					label.setText("formato numerico invalido");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_Criar_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Criar_1.setBackground(SystemColor.control);
		button_Criar_1.setBounds(336, 277, 116, 20);
		frame.getContentPane().add(button_Criar_1);

		label_1 = new JLabel("Id:");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(21, 221, 34, 14);
		frame.getContentPane().add(label_1);

		textField = new JTextField();
		textField.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField.setColumns(10);
		textField.setBounds(52, 218, 43, 20);
		frame.getContentPane().add(textField);

		label_3 = new JLabel("Nome:");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_3.setBounds(166, 249, 51, 14);
		frame.getContentPane().add(label_3);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(213, 246, 104, 20);
		frame.getContentPane().add(textField_2);

		label_saldo = new JLabel("Saldo:");
		label_saldo.setHorizontalAlignment(SwingConstants.LEFT);
		label_saldo.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_saldo.setBounds(21, 305, 151, 14);
		frame.getContentPane().add(label_saldo);

		label_4 = new JLabel("ChavePIKS:");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_4.setBounds(92, 221, 111, 14);
		frame.getContentPane().add(label_4);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(213, 215, 104, 20);
		frame.getContentPane().add(textField_4);

		button_Apagar_1 = new JButton("Ver lan\u00E7amentos");
		button_Apagar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String chave = textField_4.getText();

					Conta conta = Repositorio.localizarConta(chave);
					if (conta == null)
						throw new Exception("Chave inexistente: " + chave);

					String texto = "";
					if (conta.getLancamentos().isEmpty())
						texto = "Nenhum lancamento ";

					else {
						texto = "Lancamentos:\n";
						for (Lancamento lan : conta.getLancamentos()) {
							texto += lan + "\n";
						}
					}
					JOptionPane.showMessageDialog(frame, texto);

				} catch (NumberFormatException ex) {
					label.setText("formato numerico invalido");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}

			}
		});
		button_Apagar_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Apagar_1.setBackground(SystemColor.control);
		button_Apagar_1.setBounds(477, 277, 170, 20);
		frame.getContentPane().add(button_Apagar_1);
		
		button_Criar_2 = new JButton("Alterar chave");
		button_Criar_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String chave = textField_4.getText();

					Conta conta = Repositorio.localizarConta(chave);
					if (conta == null)
						throw new Exception("chave inexistente: " + chave);


					String novachave = JOptionPane.showInputDialog(frame, "nova chave substituta de "+chave);

					if (novachave == null || novachave.isEmpty())
						throw new Exception("nova chave vazia!");

					Conta contaaux = Repositorio.localizarConta(novachave);
					if (contaaux != null)
						throw new Exception("nova chave ja existe: " + novachave);

					conta.setChavePiks(novachave);
					Repositorio.gravarObjetos();
					label.setText("chave alterada de " + chave + " para " + novachave);
					listagem();
					button_Limpar.doClick(); // limpa os campos de texto
				} catch (NumberFormatException ex) {
					label.setText("formato numerico invalido");
				} catch (Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_Criar_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_Criar_2.setBackground(SystemColor.control);
		button_Criar_2.setBounds(336, 215, 116, 20);
		frame.getContentPane().add(button_Criar_2);
	}

	public void listagem() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// colunas
			model.addColumn("id");
			model.addColumn("chavePiks");
			model.addColumn("saldo");
			model.addColumn("limite");
			model.addColumn("cpf");
			model.addColumn("nome");

			List<Conta> lista = Repositorio.getContas();
			for (Conta c : lista) {
				// linhas
				if (c instanceof ContaEspecial ce)
					model.addRow(new Object[] { c.getId(), c.getChavePiks(), c.getSaldo(), ce.getLimite(),
							c.getCliente().getCpf(), c.getCliente().getNome() });
				else
					model.addRow(new Object[] { c.getId(), c.getChavePiks(), c.getSaldo(), 0.0, c.getCliente().getCpf(),
							c.getCliente().getNome() });
			}

			label_6.setText("resultados: " + lista.size() + " contas   - selecione uma linha");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}
}
