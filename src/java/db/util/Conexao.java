package db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    private Connection connect;
    private String erro = "";

    public Conexao() {
        this("org.postgresql.Driver",
                "jdbc:postgresql://motty.db.elephantsql.com:5432/",
                "drolmbzz",
                "drolmbzz",
                "o9gstVU5iQdSTGt1szz6IvHSCVgK1n4W");
    }

    public Conexao(String driver, String local, String banco, String usuario, String senha) {
        try {
            Class.forName(driver);
            String url = local + banco;
            connect = DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException cnfex) {
            erro = "Falha ao ler o driver JDBC: " + cnfex.toString();
        } catch (SQLException sqlex) {
            erro = "Impossivel conectar com a base de dados: " + sqlex.toString();
        } catch (Exception ex) {
            erro = "Outro erro: " + ex.toString();
        }
    }

    public String getMensagemErro() {
        return erro;
    }

    public boolean getEstadoConexao() {
        if (connect == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean manipular(String sql) // inserir, alterar,excluir
    {
        try {
            Statement statement = connect.createStatement();
            int result = statement.executeUpdate(sql);
            statement.close();
            System.out.println("result: "+result);
            if (result >= 1) {
                return true;
            }
        } catch (SQLException sqlex) {
            System.out.println("sql: "+sql);
            erro = "Erro: " + sqlex.toString();
            return false;
        }
        return false;
    }

    public ResultSet consultar(String sql) {
        ResultSet rs = null;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException sqlex) {
            erro = "Erro: " + sqlex.toString();
            return null;
        }
        return rs;
    }

    public int getMaxPK(String tabela, String chave) {
        String sql = "select max(" + chave + ") from " + tabela;
        int max = 0;
        ResultSet rs = consultar(sql);
        try {
            if (rs.next()) {
                max = rs.getInt(1);
            }
        } catch (SQLException sqlex) {
            erro = "Erro: " + sqlex.toString();
            return -1;
        }
        return max;
    }

    public boolean fecharConexao() {
        boolean flag = true;
        try {
            connect.close();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
