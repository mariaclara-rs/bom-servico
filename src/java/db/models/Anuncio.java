package db.models;

public class Anuncio {
    private int id;
    private String titulo, descricao;
    private float preco;
    private Categoria cat;
    private Usuario usu;

    public Anuncio(int id, String titulo, String descricao, float preco, Categoria cat, Usuario usu) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.preco = preco;
        this.cat = cat;
        this.usu = usu;
    }

    public Anuncio(String titulo, String descricao, float preco, Categoria cat, Usuario usu) {
        this(0,titulo,descricao,preco,cat,usu);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public Categoria getCat() {
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }
    
    @Override
    public String toString() {
        return "id: "+ id +"titulo: "+ titulo +"descricao: "+ descricao +"preco: "+ preco +"nome categoria "+ cat.getNome() +"id usuario: "+ usu.getId();
    }
    
}
