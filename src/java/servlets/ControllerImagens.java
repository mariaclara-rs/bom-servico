package servlets;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ControllerImagens", urlPatterns = {"/ControllerImagens"})
public class ControllerImagens extends HttpServlet {

    List<String> imagens = new ArrayList();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String retorno = "", caminho="";
        String usu_id;
        String acao = request.getParameter("acao");
        try (PrintWriter out = response.getWriter()) {
            switch (acao) {
                case "carregar":
                    usu_id = request.getParameter("usu_id");
                    carregar(usu_id);
                    Gson gson = new Gson();
                    retorno = gson.toJson(imagens);
                    break;

                case "remover":
                    caminho=getServletContext().getRealPath("/") + "assets\\img\\anuncios\\";
                    String img = request.getParameter("img");
                    File arq = new File(caminho+img);
                    arq.delete();
                    break;
                    
                case "gravar":
                    if(imagens.size() < 3) {
                        caminho=getServletContext().getRealPath("/") + "assets\\img\\anuncios\\";
                        usu_id = request.getParameter("usu_id");
                        Part foto = request.getPart("foto");
                        byte[] imagem=new byte[(int)foto.getSize()];
                        foto.getInputStream().read(imagem);
                        long millis = System.currentTimeMillis();
                        
                        FileOutputStream arquivo = new FileOutputStream(
                                new File(caminho + (usu_id) + millis + "." + foto.getContentType().split("/")[1])
                        );
                        arquivo.write(imagem);
                        arquivo.close();
                    }
                    break;
            }
            out.print(retorno);
        }
    }

    public void carregar(String usu_id) {
        imagens.clear();
        String caminho = getServletContext().getRealPath("/") + "assets\\img\\anuncios\\";
        Gson gson = new Gson();

        File pasta = new File(caminho);
        for (File f : pasta.listFiles()) {
            if (f.getName().startsWith(usu_id)) {
                imagens.add(f.getName());
            }
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

    private void ArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
