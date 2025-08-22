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
import repositorio.Repositorio;

public class TelaCliente {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JTextField textField_Nome;
	private JLabel label;
	private JLabel label_cpf;
	private JLabel label_Nome;
	private JLabel label_8;
	private JTextField textField_CPF;
	private JButton button_1;

	public TelaCliente() {
		initialize();
		frame.setVisible(true);
	}

	private void initialize() {
	    frame = new JDialog();
	    frame.getContentPane().setBackground(SystemColor.inactiveCaption);
	    frame.setModal(true);
	    frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));

	    frame.addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowOpened(WindowEvent e) {
	            listagem();
	        }
	    });
	    frame.setTitle("Cliente");
	    frame.setBounds(100, 100, 680, 340);
	    frame.setResizable(false);
	    frame.setLocationRelativeTo(null);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.getContentPane().setLayout(null);

	    scrollPane = new JScrollPane();
	    scrollPane.setBounds(26, 27, 600, 135);
	    frame.getContentPane().add(scrollPane);

	    table = new JTable();
	    table.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            label.setText("");
	            if (table.getSelectedRow() >= 0) {
	                // CORREÇÃO: O CPF na tabela é uma String, não Integer
	                String cpf = (String) table.getValueAt(table.getSelectedRow(), 0);
	                String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
	                textField_CPF.setText(cpf);
	                textField_Nome.setText(nome);
	                label.setText("selecionado=" + cpf);
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

	 // Dentro do método initialize() da classe TelaCliente

	    button = new JButton("Alterar cpf");
	    button.setBackground(SystemColor.control);
	    button.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                if (textField_CPF.getText().isEmpty()) {
	                    throw new Exception("Selecione um cliente ou digite um CPF");
	                }

	                // Guarda o CPF antigo que será usado para localizar o cliente
	                int cpfAntigo = Integer.parseInt(textField_CPF.getText());
	                Cliente cli = Repositorio.localizarCliente(cpfAntigo);
	                
	                if (cli == null) {
	                    throw new Exception("cpf " + cpfAntigo + " inexistente");
	                }
	                String nome = cli.getNome();

	                // Pede o novo CPF
	                String resposta = JOptionPane.showInputDialog(frame, "Digite o novo CPF para " + nome);
	                if (resposta == null || resposta.isEmpty()) {
	                    label.setText("Alteração cancelada.");
	                    return; // Encerra a operação se o usuário cancelar
	                }
	                int novocpf = Integer.parseInt(resposta);

	                // Verifica se o novo CPF já está em uso por outro cliente
	                Cliente cliaux = Repositorio.localizarCliente(novocpf);
	                if (cliaux != null) {
	                    throw new Exception("CPF " + novocpf + " já cadastrado - alteração cancelada");
	                }
	                
	                // --- LÓGICA DA CORREÇÃO ---
	                // 1. Remove o cliente do repositório usando a chave antiga
	                Repositorio.removerCliente(cli);
	                
	                // 2. Atualiza o CPF dentro do objeto cliente
	                cli.setCpf(novocpf);
	                
	                // 3. Adiciona o cliente de volta ao repositório, que agora usará a nova chave
	                Repositorio.adicionarCliente(cli);
	                // --- FIM DA CORREÇÃO ---
	                
	                Repositorio.gravarObjetos();
	                label.setText("CPF alterado de " + cpfAntigo + " para " + novocpf);
	                listagem();
	            } catch (NumberFormatException ex) {
	                label.setText("CPF deve ser um número.");
	            } catch (Exception ex) {
	                label.setText(ex.getMessage());
	            }
	        }
	    });
	    button.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    button.setBounds(263, 193, 131, 23);
	    frame.getContentPane().add(button);

	    label = new JLabel("");
	    label.setForeground(Color.BLUE);
	    label.setBackground(Color.RED);
	    label.setBounds(10, 276, 644, 14);
	    frame.getContentPane().add(label);

	    label_cpf = new JLabel("CPF:");
	    label_cpf.setHorizontalAlignment(SwingConstants.LEFT);
	    label_cpf.setFont(new Font("Dialog", Font.BOLD, 12));
	    label_cpf.setBounds(26, 197, 43, 14);
	    frame.getContentPane().add(label_cpf);

	    label_Nome = new JLabel("Nome:");
	    label_Nome.setForeground(new Color(0, 0, 0));
	    label_Nome.setHorizontalAlignment(SwingConstants.LEFT);
	    label_Nome.setFont(new Font("Dialog", Font.BOLD, 12));
	    label_Nome.setBounds(26, 226, 50, 14);
	    frame.getContentPane().add(label_Nome);

	    textField_CPF = new JTextField();
	    // A LINHA ABAIXO FOI REMOVIDA
	    // textField_CPF.setEditable(false);
	    textField_CPF.setFont(new Font("Dialog", Font.PLAIN, 12));
	    textField_CPF.setColumns(10);
	    textField_CPF.setBounds(72, 194, 104, 20);
	    frame.getContentPane().add(textField_CPF);

	    textField_Nome = new JTextField();
	    // A LINHA ABAIXO FOI REMOVIDA
	    // textField_Nome.setEditable(false);
	    textField_Nome.setFont(new Font("Dialog", Font.PLAIN, 12));
	    textField_Nome.setColumns(10);
	    textField_Nome.setBounds(72, 223, 157, 20);
	    frame.getContentPane().add(textField_Nome);

	    label_8 = new JLabel("selecione");
	    label_8.setBounds(26, 163, 600, 14);
	    frame.getContentPane().add(label_8);

	    button_1 = new JButton("Alterar nome");
	    button_1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            try {
	                if(textField_CPF.getText().isEmpty() || textField_Nome.getText().isEmpty()) {
	                    throw new Exception("cpf ou nome vazio");
	                }

	                int cpf = Integer.parseInt(textField_CPF.getText());
	                Cliente cli = Repositorio.localizarCliente(cpf);
	                if (cli == null) {
	                    throw new Exception("cpf " + cpf + " inexistente");
	                }
	                String nome = cli.getNome();

	                String novonome = JOptionPane.showInputDialog(frame, "novo nome para " + nome);
	                if (!novonome.matches("^[a-zA-Z\\s]+$"))
	                    throw new Exception("nome deve ter letras e espaços!");

	                cli.setNome(novonome);
	                Repositorio.gravarObjetos();
	                label.setText("nome alterado de " + nome+ " para " + novonome );
	                listagem();
	            } catch (Exception ex) {
	                label.setText(ex.getMessage());
	            }
	        }
	    });
	    button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
	    button_1.setBackground(SystemColor.control);
	    button_1.setBounds(263, 223, 131, 23);
	    frame.getContentPane().add(button_1);
	}

	// *****************************
	public void listagem() {
		try {
			List<Cliente> lista = Repositorio.getClientes();

			// model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// colunas
			model.addColumn("cpf");
			model.addColumn("nome");
			model.addColumn("id conta");
			// linhas
			for (Cliente cli : lista) {
				model.addRow(new Object[] { cli.getCpf(), cli.getNome(), cli.getConta().getId() });
			}
			label_8.setText("resultados: " + lista.size() + " clientes - selecione uma linha");

		} catch (Exception ex) {
			label.setText(ex.getMessage());
		}

	}
}
