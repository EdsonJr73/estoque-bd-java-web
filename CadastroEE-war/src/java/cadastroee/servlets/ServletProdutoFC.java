/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cadastroee.servlets;

import cadastroee.controller.ProdutoFacadeLocal;
import cadastroee.model.Produto;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author edson-202308892185
 */
public class ServletProdutoFC extends HttpServlet {

    @EJB
    private ProdutoFacadeLocal facade;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        String destino;

        switch (acao) {
            case "listar":
                List<Produto> produtos = facade.findAll();
                request.setAttribute("listagemProdutos", produtos);
                destino = "ProdutoLista.jsp";
                break;

            case "formIncluir":
                destino = "ProdutoDados.jsp";
                break;

            case "incluir":
                Produto novoProduto = new Produto();
                int proximoId = facade.findMaxId() + 1; 
                // implementei isso pois ele não é auto incrementável e como talvez o banco que o senhor utilizará também não seja, preferi fazer assim
                novoProduto.setIdProduto(proximoId);
                novoProduto.setNome(request.getParameter("nome"));
                novoProduto.setPrecoVenda(Float.valueOf(request.getParameter("precoVenda")));
                novoProduto.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
           
                facade.create(novoProduto);

                request.setAttribute("listagemProdutos", facade.findAll());
                destino = "ProdutoLista.jsp";
                break;

            case "formAlterar":
                int idAlterar = Integer.parseInt(request.getParameter("id"));
                Produto produtoAlterar = facade.find(idAlterar);
                request.setAttribute("produto", produtoAlterar);
                destino = "ProdutoDados.jsp";
                break;

            case "alterar":
                int idProduto = Integer.parseInt(request.getParameter("idProduto"));
                Produto produto = facade.find(idProduto);
                produto.setNome(request.getParameter("nome"));
                produto.setPrecoVenda(Float.valueOf(request.getParameter("precoVenda")));
                produto.setQuantidade(Integer.parseInt(request.getParameter("quantidade")));
                
                facade.edit(produto);

                request.setAttribute("listagemProdutos", facade.findAll());
                destino = "ProdutoLista.jsp";
                break;

            case "excluir":
                int idExcluir = Integer.parseInt(request.getParameter("id"));
                Produto produtoExcluir = facade.find(idExcluir);
                facade.remove(produtoExcluir);

                request.setAttribute("listagemProdutos", facade.findAll());
                destino = "ProdutoLista.jsp";
                break;

            default:
                request.setAttribute("listagemProdutos", facade.findAll());
                destino = "ProdutoLista.jsp";
                break;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
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
        return "Servlet Produto Front Controller";
    }// </editor-fold>

}
