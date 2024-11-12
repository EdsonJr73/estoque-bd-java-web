<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    cadastroee.model.Produto produto = (cadastroee.model.Produto) request.getAttribute("produto");

    String acao = (produto == null) ? "incluir" : "alterar";
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dados do Produto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
    <h1 class="my-4">Dados do Produto</h1>
    
    <form action="ServletProdutoFC" method="post" class="form">
        <input type="hidden" name="acao" value="<%= acao %>">
        
        <% if ("alterar".equals(acao)) { %>
            <input type="hidden" name="idProduto" value="<%= produto.getIdProduto() %>">
        <% } %>
        
        <div class="mb-3">
            <label for="nome" class="form-label">Nome:</label>
            <input type="text" id="nome" name="nome" class="form-control" value="<%= produto != null ? produto.getNome() : "" %>" required>
        </div>
        
        <div class="mb-3">
            <label for="quantidade" class="form-label">Quantidade:</label>
            <input type="number" id="quantidade" name="quantidade" class="form-control" value="<%= produto != null ? produto.getQuantidade() : 0 %>" required>
        </div>
        
        <div class="mb-3">
            <label for="precoVenda" class="form-label">Preço de Venda:</label>
            <input type="number" step="0.01" id="precoVenda" name="precoVenda" class="form-control" value="<%= produto != null ? produto.getPrecoVenda() : 0.0 %>" required>
        </div>
        
        <button type="submit" class="btn btn-primary">
            <%= "incluir".equals(acao) ? "Adicionar Produto" : "Alterar Produto" %>
        </button>
    </form>
</body>\
</html>
