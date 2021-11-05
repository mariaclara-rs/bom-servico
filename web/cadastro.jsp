<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String usu_id = "";
    try {
        usu_id = session.getAttribute("usu_id").toString();
    } catch (Exception e) {}
    
    if(usu_id != "")
        response.sendRedirect("./");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Cadastro</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/Login-Form-Clean.css">
        <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" type="image/png" href="https://img.icons8.com/color/48/000000/small-business.png"/>
    </head>

    <body style="background: rgb(241,247,252);">
        <section class="login-clean">
            <form method="post" id="fdados" style="max-width: 365px;">
                <h2 class="sr-only">Login Form</h2>
                <h1 class="text-center pb-3">Cadastrar</h1>
                <div class="form-group">
                    <input class="form-control" type="text" id="nome" name="nome" placeholder="Nome">
                    <span id="erroNome" class="erro"></span>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="cpf" id="cpf" placeholder="CPF">
                    <span id="erroCpf" class="erro"></span>
                </div>
                <div class="form-group">
                    <input class="form-control" type="text" name="telefone" id="telefone" placeholder="Telefone">
                    <span id="erroFone" class="erro"></span>
                </div>
                <div class="form-group">
                    <input class="form-control" type="email" name="email" id="email" placeholder="Email">
                    <span id="erroEmail" class="erro"></span>
                </div>
                <div class="form-group">
                    <input class="form-control" type="password" name="senha" id="senha" placeholder="Password">
                    <span id="erroSenha" class="erro"></span>
                </div>
                <span id="mensagem" class="erro"></span>
                <div class="form-group">
                    <button class="btn btn-primary btn-block bg-primary" type="button" onClick="salvar('cadastro')">
                        Cadastrar
                    </button>
                </div>
                <a class="forgot" href="login.jsp">Já possui conta?</a>
            </form>
        </section>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/js/script.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    </body>

</html>
