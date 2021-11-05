
package servlets;

import db.util.Conexao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@WebServlet(name = "Relatorios", urlPatterns = {"/Relatorios"})
public class Relatorios extends HttpServlet {
     private byte[] gerarRelatorioPDF(String sql, String relat){   
        byte[] pdf;
        System.out.println("sql: "+sql+" relat: "+relat);
        try { //sql para obter os dados para o relatorio
            JasperPrint jasperprint=null;
            ResultSet rs = new Conexao().consultar(sql);
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            System.out.println("jrRS: "+jrRS);
            jasperprint = JasperFillManager.fillReport(relat, null, jrRS);
            pdf=JasperExportManager.exportReportToPdf(jasperprint);

        } catch (JRException erro) {
            pdf=null;
        }
        return pdf;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/pdf");
        byte[] pdf=gerarRelatorioPDF("SELECT c.cat_nome, a.anu_titulo, u.usu_nome, "
                                    + "a.anu_preco FROM anuncio a, categoria c, "
                                    + "usuario u WHERE a.cat_id = c.cat_id " +
                                    "AND a.usu_id = u.usu_id " +
                                    "ORDER BY c.cat_nome ASC",
                                 getServletContext().getRealPath("")+"Relatorios\\RelAnuncio.jasper");
        System.out.println("pdf: "+pdf.length);
        response.getOutputStream().write(pdf,0,pdf.length);
        response.getOutputStream().flush();
        response.getOutputStream().close();
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
