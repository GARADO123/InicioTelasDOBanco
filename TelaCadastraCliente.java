import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TelaCadastraCliente extends  JFrame{
    private JButton btnCriarUsuario;
    private JTextField txtProfissao;
    private JTextField txtCPF;
    private JTextField txtRenda;
    private JTextField txtNome;
    private JPanel pnlTelaCadastraCliente;
    private JTextField txtEndereço;
    private JTextField txtValor;
    private JButton btnLinpar;
    private JButton btnLista;

    final String URL = "jdbc:mysql://localhost:3306/bigbanco";
    final String USER = "root";
    final String PASSAWORD = "root";

    final String INSERIR = "INSERT INTO ContaUsuario(CPF,nome,Endereco,Renda,Profissao,DepositaValor) values (?,?,?,?,?,?)";
    final String CONSULTA="select * from ContaUsuario";

    public TelaCadastraCliente(){
        AddListeners();
        IniciarComponente();
        Conecta();
    }
    public void AddListeners(){
        btnLinpar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                txtNome.setText("");
                txtEndereço.setText("");
                txtRenda.setText("");
                txtCPF.setText("");
                txtProfissao.setText("");
                txtValor.setText("");

            }

        });
        btnLista.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                TelaListaCadastra TelaLista = new TelaListaCadastra();
                TelaLista.setVisible(true);
                dispose();
            }

        });


    }





    public  void Conecta(){

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection= DriverManager.getConnection(URL,USER,PASSAWORD);
            System.out.println("Connedtado");

            final PreparedStatement stmInserir;

            stmInserir = connection.prepareStatement(INSERIR);

            btnCriarUsuario.addActionListener(new ActionListener(){
                @Override

                public void actionPerformed(ActionEvent e ){
                    String CPF = txtCPF.getText();
                    String nome= txtNome.getText();
                    String endereço = txtEndereço.getText();
                    String renda = txtRenda.getText();
                    String profissao = txtProfissao.getText();
                    String valor = txtValor.getText();

                    try{
                        int cpfInt = Integer.parseInt(CPF);
                        stmInserir.setInt(1,cpfInt);
                        stmInserir.setString(2,nome);
                        stmInserir.setString(3,endereço);
                        stmInserir.setString(4,renda);
                        stmInserir.setString(5,profissao);
                        stmInserir.setString(6,valor);
                        stmInserir.executeUpdate();

                        System.out.println("Dados inseridos!");
                        JOptionPane.showMessageDialog(btnCriarUsuario,"Dados inseridos ");
                        txtNome.setText("");
                        txtEndereço.setText("");
                        txtRenda.setText("");
                        txtCPF.setText("");
                        txtProfissao.setText("");
                        txtValor.setText("");

                    }catch (NumberFormatException ex){
                        System.out.println("O CPf formada de numeros inteiros!");
                    }catch (Exception ex){
                        System.out.println("Erro ao inserir dados no banco!");
                    }
                }
            });
        }catch (Exception ex){

            System.out.println("Erro ao conectar o banco de dados ");

        }


    }

    public void IniciarComponente(){
        setTitle("Cadastra de aluno");
        setSize(500,300);
        setContentPane(pnlTelaCadastraCliente);
        setVisible(true);
    }


    public static void main(String[] args){

       TelaCadastraCliente  TelaCadastra = new TelaCadastraCliente();
    }


}










