import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class TelaListaCadastra extends JFrame {
    private JPanel pnlTelaListaCadastra;
    private JTable tbListaUsuario;
    private JButton btnExcluir;
    private JButton btnVoltar;

    final String URL = "jdbc:mysql://localhost:3306/bigbanco";
    final String USER = "root";
    final String PASSAWORD = "root";

    final String EXCLUIR = "delete from contausuario where CPF=? ";

    final String CONSULTAR = "select * from contausuario";

    public TelaListaCadastra(){

        addListener();
        iniciarComponente();
    }

    public void iniciarComponente(){
        setTitle("Cadastra de aluno");
        setSize(600,400);
        setContentPane(pnlTelaListaCadastra);
        setVisible(true);
    }
    public  void addListener(){

        btnVoltar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TelaCadastraCliente telaCadastraCliente = new TelaCadastraCliente();
                telaCadastraCliente.setVisible(true);
                dispose();
            }

        });

        btnExcluir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                Connection connection = null;

                try{
                    int linhaSelecionada = tbListaUsuario.getSelectedRow();
                    if (linhaSelecionada < 0 ){
                        JOptionPane.showMessageDialog(null,"Selecione uma linha para excluir");
                        return;
                    }
                    int cpf = (int) tbListaUsuario.getValueAt(linhaSelecionada,1);

                    connection = DriverManager.getConnection(URL,USER,PASSAWORD);
                    PreparedStatement stmt = connection.prepareStatement(EXCLUIR);
                    stmt.setInt(1,cpf);
                    int resultado= stmt.executeUpdate();
                    if(resultado==1){
                        JOptionPane.showMessageDialog(null,"Registro excluido com sucesso");
                    }else{
                        JOptionPane.showMessageDialog(null,"Erro ap excluir registro");
                    }
                    DefaultTableModel aluno = (DefaultTableModel) tbListaUsuario.getModel();
                    aluno.removeRow(linhaSelecionada);
                }catch (SQLException ex){
                    System.out.println("Erro ao excluir registro"+ ex.getMessage());
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Selecione uma linha para excluir");
                }
            }

        });
        DefaultTableModel ListaUsuario = new DefaultTableModel();
        ListaUsuario.addColumn("CPF");
        ListaUsuario.addColumn("Nome");
        ListaUsuario.addColumn("Endereco");
        ListaUsuario.addColumn("Renda");
        ListaUsuario.addColumn("Profissao");
        ListaUsuario.addColumn("DepositarValor");
        tbListaUsuario.setModel(ListaUsuario);

        Connection connection = null;


        try{
            connection = DriverManager.getConnection(URL,USER,PASSAWORD);
            Statement stmt = null;
            stmt = connection.createStatement();

            ResultSet rs = null;

            rs = stmt.executeQuery(CONSULTAR);

            while (rs.next()){
                Object[] row = new Object[6];

                row[0]= rs.getObject(1);
                row[1]= rs.getObject(2);
                row[2]= rs.getObject(3);
                row[3]= rs.getObject(4);
                row[4]= rs.getObject(5);
                row[5]= rs.getObject(6);

                ListaUsuario.addRow(row);


            }

        }catch (SQLException ex){
            throw  new RuntimeException(ex);
        }

    }

    public static void main(String[] args) {
        TelaListaCadastra telaListaCadastra = new TelaListaCadastra();
    }
}



