
package db.dal;

import db.models.Usuario;
import db.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DALUsuario {
     public boolean salvar(Usuario u) {
        String sql = "insert into usuario (usu_nome, usu_nivel, usu_email, usu_senha, usu_telefone, usu_cpf) values ('$1','U','$3','$4','$5','$6')";
        sql = sql.replace("$1", u.getNome());
        sql = sql.replace("$3", u.getEmail());
        sql = sql.replace("$4", u.getSenha());
        sql = sql.replace("$5", u.getTelefone());
        sql = sql.replace("$6", u.getCpf());
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }
     
    public boolean alterar(Usuario u) {
        String sql = "update usuario set usu_nome='$1', usu_email='$2', usu_senha='$3', usu_telefone='$4' where usu_id='"+u.getId()+"'";
        sql = sql.replace("$1", u.getNome());
        sql = sql.replace("$2", u.getEmail());
        sql = sql.replace("$3", u.getSenha());
        sql = sql.replace("$4", u.getTelefone());
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }

    public Usuario buscarUsuario(String filtro) {
        Usuario u = null;
        String sql = "select * from usuario";
        if(!filtro.isEmpty())
            sql+=" where " + filtro;
        
        Conexao con;
        try {
            con = new Conexao();
            ResultSet rs = con.consultar(sql);
            if (rs.next()) {
                u = new Usuario(rs.getInt("usu_id"),
                                rs.getString("usu_nome"), 
                                rs.getString("usu_email"),
                                rs.getString("usu_senha"),
                                rs.getString("usu_telefone"),
                                rs.getString("usu_cpf"), 
                                rs.getString("usu_nivel").charAt(0));
            }
            con.fecharConexao();
        } catch (Exception e) {
            System.out.println("Erro: "+e);
        }

        return u;
    }
    
    public ArrayList<Usuario> getUsuarios(String filtro) {
        ArrayList<Usuario> lista = new ArrayList();

        String sql = "select * from usuario";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        Conexao con = new Conexao();
        ResultSet rs = con.consultar(sql);
        try {
            while (rs.next()) {
                lista.add(new Usuario(rs.getInt("usu_id"),
                        rs.getString("usu_nome"),
                        rs.getString("usu_email"), 
                        rs.getString("usu_senha"), 
                        rs.getString("usu_telefone"), 
                        rs.getString("usu_cpf"),  
                        rs.getString("usu_nivel").charAt(0)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.fecharConexao();
        return lista;
    }
  
}
