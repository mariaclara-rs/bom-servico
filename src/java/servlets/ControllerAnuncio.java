package servlets;

import com.google.gson.Gson;
import db.dal.DALAnuncio;
import db.dal.DALCategoria;
import db.dal.DALUsuario;
import db.models.Anuncio;
import db.models.Categoria;
import db.models.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ControllerAnuncio", urlPatterns = {"/ControllerAnuncio"})
public class ControllerAnuncio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        String erro = "", retorno = "";
        int usu_id;
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        Anuncio a;
        Categoria c;
        Usuario u;
        Gson gson = new Gson();
        DALAnuncio ctr = new DALAnuncio();
        DALCategoria cat = new DALCategoria();
        DALUsuario usu = new DALUsuario();
        try (PrintWriter out = response.getWriter()) {
            switch (acao){
                case "remover":
                    if (!ctr.apagar(id)) 
                        erro = "Erro ao apagar o anuncio";
                    
                    response.getWriter().print(erro);
                    break;
                case "recuperar":
                    String filtro = request.getParameter("filtro");
                    if(!filtro.equals("")) 
                        retorno = gson.toJson(ctr.filtrarAnuncios(filtro));
                    else
                        retorno = gson.toJson(ctr.getAnuncios(""));
                    
                    break;
                case "buscar":
                    usu_id=Integer.parseInt(request.getParameter("usu_id"));
                    a = ctr.buscar("usu_id='" + usu_id + "'");
                    System.out.println("buscaAnun:"+ a.getCat().getNome());
                    retorno = gson.toJson(a);
                    break;
               case "confirmar":
                    a = gson.fromJson(request.getParameter("dados"), Anuncio.class);
                    c = cat.buscar("cat_id = '"+request.getParameter("categoria")+"'");
                    u = usu.buscarUsuario("usu_id = '"+request.getParameter("usuario")+"'");
                    a.setCat(c);
                    a.setUsu(u);
                    
                    if (a.getId() == 0) {
                        if (!ctr.salvar(a)) {
                            erro = "Erro ao tentar salvar o anuncio!";
                        }
                    }else {
                        if (!ctr.alterar(a)) {
                            erro = "Erro ao alterar o anuncio";
                        }
                    }
                    response.getWriter().print(erro);
                    break;
            }
            out.print(retorno);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
