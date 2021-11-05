package db.dal;

import db.models.Anuncio;
import db.models.Categoria;
import db.util.Conexao;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DALAnuncio {

    public boolean salvar(Anuncio a) {
        String sql = "insert into anuncio (anu_titulo, anu_descricao, anu_preco, cat_id, usu_id) values ('$1','$2',$3,$4,$5)";
        sql = sql.replace("$1", a.getTitulo());
        sql = sql.replace("$2", a.getDescricao());
        sql = sql.replace("$3", a.getPreco() + "");
        sql = sql.replace("$4", a.getCat().getId() + "");
        sql = sql.replace("$5", a.getUsu().getId() + "");
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }

    public boolean alterar(Anuncio a) {
        String sql = "update anuncio set anu_titulo='$1', anu_descricao='$2', anu_preco='$3', cat_id='$4' where anu_id='" + a.getId() + "'";
        sql = sql.replace("$1", a.getTitulo());
        sql = sql.replace("$2", a.getDescricao());
        sql = sql.replace("$3", a.getPreco() + "");
        sql = sql.replace("$4", a.getCat().getId() + "");
        Conexao con = new Conexao();
        boolean flag = con.manipular(sql);
        con.fecharConexao();
        return flag;
    }

    public Anuncio buscar(String filtro) {
        Anuncio a = null;
        
        String sql = "select * from anuncio";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }
        Conexao con;
        try {
            con = new Conexao();
            ResultSet rs = con.consultar(sql);
            if (rs.next()) {
                a = new Anuncio(rs.getInt("anu_id"),
                        rs.getString("anu_titulo"),
                        rs.getString("anu_descricao"),
                        rs.getFloat("anu_preco"),
                        new DALCategoria().buscar("cat_id='" + rs.getInt("cat_id") + "'"),
                        new DALUsuario().buscarUsuario("usu_id='" + rs.getInt("usu_id") + "'"));
            }
            con.fecharConexao();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }

        return a;
    }

    public ArrayList<Anuncio> getAnuncios(String filtro) {
        ArrayList<Anuncio> lista = new ArrayList();

        String sql = "select * from anuncio";
        if (!filtro.isEmpty()) {
            sql += " where " + filtro;
        }

        Conexao con = new Conexao();
        ResultSet rs = con.consultar(sql);
        try {
            while (rs.next()) {
                lista.add(new Anuncio(rs.getInt("anu_id"),
                        rs.getString("anu_titulo"),
                        rs.getString("anu_descricao"),
                        rs.getFloat("anu_preco"),
                        new DALCategoria().buscar("cat_id='" + rs.getInt("cat_id") + "'"),
                        new DALUsuario().buscarUsuario("usu_id='" + rs.getInt("usu_id") + "'"))
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.fecharConexao();
        System.out.println("GetAnu: "+lista);
        return lista;
    }
    
    public ArrayList<Anuncio> filtrarAnuncios(String filtro) {
        ArrayList<Anuncio> lista = new ArrayList();

        String sql = "select * from anuncio, categoria where anuncio.cat_id=categoria.cat_id "
                + "AND (upper(cat_nome) like '%"+filtro.toUpperCase()+"%' OR upper(anu_titulo) like '%"+filtro.toUpperCase()+"%')";
        System.out.println("sql: "+sql);
        Conexao con = new Conexao();
        ResultSet rs = con.consultar(sql);
        try {
            while (rs.next()) {
                lista.add(new Anuncio(rs.getInt("anu_id"),
                        rs.getString("anu_titulo"),
                        rs.getString("anu_descricao"),
                        rs.getFloat("anu_preco"),
                        new Categoria(rs.getInt("cat_id"),rs.getString("cat_nome")),
                        new DALUsuario().buscarUsuario("usu_id='" + rs.getInt("usu_id") + "'"))
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        con.fecharConexao();
        return lista;
    }
    
    public boolean apagar(int id) {
        Conexao con = new Conexao();
        boolean flag = con.manipular("delete from anuncio where anu_id=" + id);
        con.fecharConexao();
        return flag;
    }
}
