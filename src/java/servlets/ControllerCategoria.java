package servlets;

import com.google.gson.Gson;
import db.dal.DALCategoria;
import db.models.Categoria;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ControllerCategoria", urlPatterns = {"/ControllerCategoria"})
public class ControllerCategoria extends HttpServlet {

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        String retorno = null, erro="";
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        
        Categoria c;
        Gson gson = new Gson();
        DALCategoria ctr = new DALCategoria();
        
        try (PrintWriter out = response.getWriter()) {
            switch(acao){
                case "recuperar":
                    retorno = gson.toJson(ctr.getCategorias(""));
                    break;
                case "salvar":
                    c = gson.fromJson(request.getParameter("dados"), Categoria.class);
                    if (c.getId() == 0) {
                        if (!ctr.salvar(c)) {
                            erro = "Erro ao tentar salvar a categoria";
                        }
                    }else {
                        if (!ctr.alterar(c)) {
                            erro = "Erro ao alterar a categoria";
                        }
                    }
                    retorno=erro;
                    break;
                case "apagar":
                    if (!ctr.apagar(id)) {
                        erro = "Erro ao apagar a categoria";
                    }
                    response.getWriter().print(erro);
                    break;
                case "alterar":
                    c = ctr.buscar("cat_id="+id);
                    retorno = gson.toJson(c);
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
