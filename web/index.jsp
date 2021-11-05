<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String usu_id = "";
    String usu_nivel = "";
    try {
        usu_id = session.getAttribute("usu_id").toString();
        usu_nivel = session.getAttribute("usu_nivel").toString();
    } catch (Exception e) {
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Bom Negócio</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="icon" type="image/png" href="https://img.icons8.com/color/48/000000/small-business.png"/>
    </head>

    <body onload="carregarAnunciantes()">
        <nav class="navbar navbar-light navbar-expand bg-light navigation-clean">
            <div class="container">
                <a class="navbar-brand" href="./">BomNegócio</a>
                <div class="collapse navbar-collapse d-xl-flex justify-content-xl-end" id="navcol-1">
                    <%
                        if (usu_id.equals("")) {
                    %>
                    <a class="btn btn-primary m-0" role="button" href="cadastro.jsp">
                        Cadastrar
                    </a>
                    <a class="btn btn-primary m-0 ml-1" role="button" href="login.jsp">
                        Login
                    </a>
                    <% } else {
                        if (usu_nivel.equals("A")) {
                    %>
                    <a class="btn btn-primary m-0 ml-1" role="button" href="administrador.jsp">
                        Painel Administrador
                    </a>
                    <% }%>
                    <a class="btn btn-primary ml-1" 
                       role="button" href="meu-perfil.jsp">
                        Perfil
                    </a>
                    <a class="btn btn-primary ml-1" 
                       role="button" onclick="logout()">
                        Sair
                    </a>
                    <%}%>
                </div>
            </div>
        </nav>
        <header class="text-center text-white masthead banner-index">
            <div class="overlay"></div>
            <div class="container">
                <div class="row">
                    <div class="col-xl-9 mx-auto">
                        <h1 class="ml-5 mr-5 banner-title">
                            Encontre os melhores profissionais!
                        </h1>
                    </div>
                    <div class="col-md-10 col-lg-8 col-xl-7 mx-auto mt-5">
                        <form accept-charset="iso-8859-1,utf-8" onsubmit="carregarAnunciantes()">
                            <div class="form-row">
                                <div class="col-12 col-md-9 mb-2 mb-md-0">
                                    <input class="form-control form-control-lg" id="filtro" type="text" placeholder="Procurar um profissinal...">
                                </div>
                                <div class="col-12 col-md-3">
                                    <button class="btn btn-primary btn-block btn-lg" type="submit">
                                        Pesquisar
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </header>
        <section class="text-center bg-light features-icons">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h1 class="mb-5">Profissionais</h1>
                    </div>
                </div>
                <div class="row mt-4" id="anunciantes">
                    
                </div>
                <div class="row mt-2">
                    <div class="col p-2">
                        <div class="btn-group" role="group">
                            <button class="btn btn-primary active" type="button">1</button>
                            <!--<button class="btn btn-primary" type="button">2</button>
                            <button class="btn btn-primary" type="button">3</button>-->
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <section class="text-center text-white call-to-action">
            <div class="d-flex overlay gradiente"></div>
            <div class="container">
                <div class="row">
                    <div class="col-xl-9 mx-auto">
                        <h2 class="mb-4">É o melhor no que faz?</h2>
                    </div>
                    <div class="col-md-10 col-lg-8 col-xl-7 d-xl-flex mx-auto justify-content-xl-center">
                        <a class="btn btn-primary" role="button" href="cadastro.jsp">
                            Anunciar
                        </a>
                    </div>
                </div>
            </div>
        </section>
        <footer class="bg-light footer p-1">
            <p class="text-muted small mb-4 mb-lg-0 text-center">
                © BomNegócio 2021. Todos os direitos reservados
            </p>
        </footer>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/script.js" type="text/javascript"></script>
    </body>

</html>
