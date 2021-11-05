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

    if (!usu_nivel.equals("A")) {
        response.sendRedirect("./");
    }
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Painel Administrador</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="assets/fonts/fontawesome5-overrides.min.css">
        <link rel="stylesheet" href="assets/css/Login-Form-Clean.css">        
        <link rel="icon" type="image/png" href="https://img.icons8.com/color/48/000000/small-business.png"/>
        <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
    </head>

    <body onload="painelAdm()">
        <div class="container pt-4">
            <div class="row justify-content-between">
                <div class="col">
                    <a class="btn btn-sm" role="button" style="box-shadow: 0;color: var(--gray);background: transparent;border-style: none;border-radius: 0px;padding-left: 27px;" href="index.jsp">
                        <i class="fa fa-chevron-left" style="color: var(--gray);"></i>
                        &nbsp;Voltar
                    </a>        
                </div>
                <div class="row justify-content-end">
                    <a class="badge badge-dark align-self-center p-3 mr-2" type="button" target="_blank" href="Relatorios">
                        Gerar Relatorio
                    </a>
                    <a class="badge badge-info align-self-center p-3" type="button" target="_blank" href="./webresources/services/query?categoria=informática">
                        Gerar JSON
                    </a>
                </div>
            </div>
        </div>
        <div class="container mt-4">
            <nav>
                <div class="nav nav-tabs" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="false">Gerenciar categorias</a>
                    <a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Gerenciar anúncios</a>
                </div>
            </nav>
            <div class="tab-content p-4" id="nav-tabContent">
                <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">

                    <div class="row d-xl-flex justify-content-xl-center">
                        <div class="col d-flex d-xl-flex flex-column align-items-lg-center align-items-xl-center">
                            <form id="fdados" class="align-items-center align-content-center" method="post" style="max-width: 500px;text-align: center;">                        
                                <h1 class="text-center" style="padding-bottom: 10px;">Gerenciar Categoria</h1>
                                <div class="form-group">
                                    <input class="form-control" type="text" name="id" id="id" value="0" placeholder="Identificador" readonly>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" type="text" name="nome" id="nome" placeholder="Nome">
                                </div>
                                <div class="form-group">
                                    <div class="row justify-content-between">
                                        <div class="col-5">
                                            <button class="btn btn-dark btn-block bg-dark" type="button" onclick="limparForm()">
                                                Limpar
                                            </button>
                                        </div>
                                        <div class="col-7">
                                            <button class="btn btn-primary btn-block bg-primary" type="submit" onclick="salvarCategoria()">
                                                Confirmar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="table-responsive" style="margin-top: 0;max-width: 600px;">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th style="width: 10%;max-width: 10%;">#</th>
                                            <th style="width: 80%;max-width: 80%;">Nome</th>
                                            <th style="text-align: center;max-width: 70px;width: 10%;">Ação</th>
                                        </tr>
                                    </thead>
                                    <tbody id="preview">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <!--fim da categoria aqui-->
                </div>
                <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">

                    <div class="row d-xl-flex justify-content-xl-center">
                        <div class="col offset-xl-0 d-flex d-xl-flex flex-column align-items-lg-center align-items-xl-center" style="max-width: auto;width: auto;"> 
                            <form id="fanuncio" method="post" enctype="multipart/form-data" style="max-width: 500px;">
                                <h1 class="text-center" style="padding-bottom: 10px;">Gerenciar Anuncio</h1>
                                <div class="form-group">
                                    <input class="form-control" type="text" name="id" id="id" value="0" placeholder="Identificador" readonly>
                                </div>
                                <input type="text" id="usuario" name="usuario" hidden>
                                <div class="form-group">
                                    <input class="form-control" type="text" name="titulo" id="titulo" placeholder="Titulo do anuncio">
                                </div>
                                <div class="form-group" style="height: 131px;">
                                    <label style="padding-left: 19px;color: rgb(137,145,150);">Descrição do anuncio</label>
                                    <textarea class="form-control" style="height: 100px;" id="descricao" name="descricao"></textarea>
                                </div>
                                <div class="form-group">
                                    <div class="container" style="padding: 0px;">
                                        <div class="form-row">
                                            <div class="col-md-6">
                                                <select class="form-control" name="categoria" id="categoria">
                                                    <optgroup label="Selecionar categoria...">

                                                    </optgroup>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <input class="form-control" type="text" name="preco" id="preco" placeholder="Valor">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="container" style="padding-right: 0px;padding-left: 0px;">
                                        <div class="form-row">
                                            <div class="col-md-6 col-xl-9">
                                                <input class="form-control-file" type="file" accept=".png, .jpg, .jpeg" name="foto" id="foto">
                                            </div>
                                            <div class="col-3 col-md-6 col-xl-3 d-xl-flex justify-content-xl-end">
                                                <button onclick="inserirImagem(-1)" class="btn btn-success" type="button" style="margin: 0px;padding: 0px;padding-right: 10px;padding-left: 10px;background: var(--success);">
                                                    Enviar
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row d-xl-flex" name="imagens" id="imagens" style="padding-bottom: 0px;padding-top: 10px;">

                                    </div>
                                </div>
                                <span id="msgAnuncio"></span>
                                <div class="form-group">
                                    <div class="row justify-content-between">
                                        <div class="col-5">
                                            <button class="btn btn-dark btn-block bg-dark" type="button" onclick="limparForm()">
                                                Limpar
                                            </button>
                                        </div>
                                        <div class="col-7">
                                            <button class="btn btn-primary btn-block bg-primary" type="submit" onclick="salvarAnuncio(-1)">
                                                Confirmar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <div class="table-responsive" style="margin-top: 0px;max-width: 600px;padding-top: 0px;">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th style="width: 10%;max-width: 10%;">#</th>
                                            <th style="width: 80%;max-width: 80%;">Título</th>
                                            <th style="width: 80%;max-width: 80%;">Categoria</th>
                                            <th style="text-align: center;max-width: 70px;width: 10%;">Ação</th>
                                        </tr>
                                    </thead>
                                    <tbody id="anuncios">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>

    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/script.js" type="text/javascript"></script>
</body>

</html>