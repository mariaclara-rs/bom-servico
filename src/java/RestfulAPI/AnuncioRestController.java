package RestfulAPI;

import com.google.gson.Gson;
import db.dal.DALAnuncio;
import db.dal.DALCategoria;
import db.models.Anuncio;
import db.models.Categoria;
import java.util.ArrayList;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("services")
public class AnuncioRestController {

    @Context
    private UriInfo context;

    public AnuncioRestController() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/query/")
    public String getAnuncios(@QueryParam("categoria") String categoria) {
        String json = "[";
        try {
            Categoria c;
            c = new DALCategoria().buscar("UPPER(cat_nome) = '" + categoria.toUpperCase()+"'");

            ArrayList<Anuncio> anuncios = new ArrayList();
            anuncios = new DALAnuncio().getAnuncios("cat_id = " + c.getId());

            
            for(int i = 0; i<anuncios.size();i++) {
                Anuncio a = anuncios.get(i);
                json += "{\"ID\": "+a.getId()+",\"Titulo\": "+"\""+a.getTitulo()+"\""+",\"Descrição\": "+"\""+a.getDescricao()+"\""+",\"Preço\": "+a.getPreco()+",\"Usuario\": "+"\""+a.getUsu().getNome()+"\""+"}";  

                if(i != anuncios.size()-1)
                    json += ", ";
            }
        } catch(Exception e) {
            return json+"]";
        }
        
        return json+"]";
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
