package servlets;

import com.google.gson.Gson;
import db.dal.DALUsuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.models.Usuario;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ControllerCadUsu", urlPatterns = {"/ControllerCadUsu"})
public class ControllerCadUsu extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String email, senha, acao;
        String erro = "", retorno = "";
        HttpSession session;
        
        acao = request.getParameter("acao");
        email = request.getParameter("email");
        
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            id = 0;
        }
        Usuario u;
        Gson gson = new Gson();
        DALUsuario ctr = new DALUsuario();
        try (PrintWriter out = response.getWriter()){
            switch (acao) {
                case "confirmar":
                    u = gson.fromJson(request.getParameter("dados"), Usuario.class);
                    if (u.getId() == 0) {
                        if (!ctr.salvar(u))
                            erro = "Erro ao tentar salvar o usuário!";
                    }else {
                        if (!ctr.alterar(u)) {
                            erro = "Erro ao alterar o usuário";
                        }
                    }
                    response.getWriter().print(erro);
                    break;
                case "buscar":
                    u = ctr.buscarUsuario("usu_id='" + id + "'");
                    retorno = gson.toJson(u);
                    break;
                case "logar":
                    senha = request.getParameter("senha");
                    u = ctr.buscarUsuario("usu_email='" + email + "' AND usu_senha='" + senha + "'");
                    if (u == null) 
                        erro="Erro. Usuário não encontrado!";
                    else{
                        session = request.getSession(true);   
                        if(session!=null){
                            session.setAttribute("usu_id", u.getId());
                            session.setAttribute("usu_nivel", u.getNivel());
                        }
                    }
                    response.getWriter().print(erro);
                    break;
                case "alterar":
                    u = gson.fromJson(request.getParameter("dados"), Usuario.class);
                    if (u != null) {
                        if (!ctr.alterar(u)) {
                            erro = "Erro ao tentar alterar o usuário!";
                        }
                    }
                    response.getWriter().print(erro);
                    break;
                case "recuperar":
                    retorno = gson.toJson(ctr.getUsuarios("usu_nivel='U'"));
                    break;
                case "anuncio":
                    session = request.getSession(true);
                    if(session != null){
                        session.setAttribute("id_anunciante", id);
                    }
                    break;
                case "logout":
                    session = request.getSession();
                    session.removeAttribute("usu_id");
                    session.removeAttribute("usu_nivel");
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
