package db.dal;

import db.models.Categoria;
import db.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DALCategoria {
    public Categoria buscar(String filtro) {
        Categoria c = null;
        String sql = "select * from categoria";
        if(!filtro.isEmpty())
            sql+=" where " + filtro;
        
        Conexao con;
        try {
            con = new Conexao();
            ResultSet rs = con.consultar(sql);
            if (rs.next()) {
                c = new Categoria(rs.getInt("cat_id"),
                                    rs.getString("cat_nome"));
            }
            con.fecharConexao();
        } catch (Exception e) {
            System.out.println("Erro: "+e);
        }

        return c;
    }
    
    public ArrayList<Categoria> getCategorias(String filtro) {
        ArrayList<Categoria> lista = new ArrayList();

        String sql = "select * from categoria";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        Conexao con = new Conexao();
        ResultSet rs = con.consultar(sql);
        try {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("cat_id"), rs.getString("cat_nome")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.fecharConexao();
        return lista;
    }
    
    public boolean salvar(Categoria c) {
        String sql = "insert into categoria (cat_nome) values ('$1')";
        sql = sql.replace("$1", c.getNome());
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }
    
    public boolean alterar(Categoria c) {
        String sql = "update categoria set cat_nome='$1' where cat_id='"+c.getId()+"'";
        sql = sql.replace("$1", c.getNome());
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }
    
    public boolean apagar(int id) {
        Conexao con = new Conexao();
        boolean flag = con.manipular("delete from categoria where cat_id=" + id);
        con.fecharConexao();
        return flag;
    }
}
