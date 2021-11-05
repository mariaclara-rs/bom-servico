<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String usu_id = "";
    String usu_nivel = "";
    try {
        usu_id = session.getAttribute("usu_id").toString();
        usu_nivel = session.getAttribute("usu_nivel").toString();
    } catch (Exception e) { }
%>
<html style="background: rgb(241,247,252);">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>Meu Perfil</title>
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/fonts/fontawesome-all.min.css">
        <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
        <link rel="stylesheet" href="assets/css/Login-Form-Clean.css">
        <link href="assets/css/style.css" rel="stylesheet" type="text/css"/>
        <link rel="icon" type="image/png" href="https://img.icons8.com/color/48/000000/small-business.png"/>
    </head>

    <body style="background: rgb(241,247,252);" onload="carregarDados(<%=usu_id%>)">
        <div class="container">
            <div class="col-xl-12 offset-xl-0" style="margin-top: 37px;padding: 0px;background: rgb(241,247,252);">
                <a class="btn btn-sm" role="button" style="box-shadow: 0;color: var(--gray);background: transparent;border-style: none;border-radius: 0px;padding-left: 27px;" href="index.jsp"><i class="fa fa-chevron-left" style="color: var(--gray);"></i>&nbsp;Voltar</a></div>
            <div class="row d-xl-flex justify-content-around" style="margin-right: 0;margin-left: 0;">
                <div class="col-md-6 d-flex d-xl-flex flex-column align-items-xl-center" style="background: var(--white);width: 577px;max-width: 45%;padding: 15px;padding-right: 50px;padding-left: 50px;">
                    <form id="fdados" method="post" style="max-width: 100%;width: 100%;">
                        <h2 class="sr-only">Login Form</h2>
                        <h1 class="text-center" style="padding-bottom: 10px;">
                            Meus Dados
                        </h1>
                        <div class="form-group">
                            <input 
                                class="form-control" 
                                type="text" name="id"
                                readonly>
                        </div>
                        <div class="form-group">
                            <input 
                                class="form-control" 
                                type="text" name="cpf" 
                                readonly>
                            <span id="erroCpf" class="erro"></span>
                        </div>
                        <div class="form-group">
                            <input 
                                class="form-control" 
                                type="text" name="nome" 
                                placeholder="Nome">
                            <span id="erroNome" class="erro"></span>
                        </div>
                        <div class="form-group">
                            <input 
                                class="form-control" type="text" 
                                name="telefone" placeholder="Telefone">
                            <span id="erroFone" class="erro"></span>
                        </div>
                        <div class="form-group">
                            <input 
                                class="form-control" type="email" 
                                name="email" placeholder="Email">
                            <span id="erroEmail" class="erro"></span>
                        </div>
                        <div class="form-group">
                            <input 
                                class="form-control" type="password" 
                                name="senha" placeholder="Senha">
                            <span id="erroSenha" class="erro"></span>
                        </div>
                        <span id="mensagem"></span>
                        <div class="form-group">
                            <button 
                                class="btn btn-primary btn-block" 
                                onclick="salvar('perfil')"                            
                                >
                                Confirmar
                            </button>
                        </div>
                    </form>
                </div>
                <%  
                    if(!usu_nivel.equals("A")) {
                %>               
                <div class="col-md-6 d-xl-flex justify-content-xl-center" style="width: 576px;background: var(--white);max-width: 45%;padding-top: 15px;padding-bottom: 15px;">
                    <form onsubmit="salvarAnuncio(<%=usu_id%>)" id="fanuncio" name="fanuncio" method="post" enctype="multipart/form-data" style="max-width: 440px;">
                        <h1 class="text-center" style="padding-bottom: 10px;">Meu Anuncio</h1>
                        <div class="form-group">
                            <input 
                                class="form-control" 
                                type="text" name="id" value="0"
                                readonly>
                        </div>
                        <div class="form-group">
                            <input class="form-control" type="text" value="" name="titulo" id="titulo" placeholder="Titulo do anuncio">
                        </div>
                        <div class="form-group" style="height: 131px;">
                            <label style="padding-left: 19px;color: rgb(137,145,150);">
                                Descrição do anuncio
                            </label>
                            <textarea name="descricao" id="descricao" value="" class="form-control" style="height: 100px;">
                            </textarea>
                        </div>
                        <div class="form-group">
                            <div class="container" style="padding: 0px;">
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <select class="form-control" id="categoria" name="categoria" value="null">
                                            <optgroup label="Selecionar categoria...">
                                            </optgroup>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <input class="form-control" type="text" id="preco" name="preco" value="" placeholder="Valor">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="container" style="padding-right: 0px;padding-left: 0px;">
                                <div class="form-row">
                                    <div class="col-md-6 col-xl-9">
                                        <input class="form-control-file" accept="image/png, image/jpg, image/jpeg" type="file" name="foto" id="foto"></div>
                                    <div class="col-3 col-md-6 col-xl-3 d-xl-flex justify-content-xl-end">
                                        <button onclick="inserirImagem(<%=usu_id%>)" class="btn btn-success" style="margin: 0px;padding: 0px;padding-right: 10px;padding-left: 10px;background: var(--success);">
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
                            <button class="btn btn-success btn-block" type="submit" style="background: var(--green);">
                                Concluir
                            </button>
                        </div>
                    </form>
                </div>
                <%}%>
            </div>
        </div>
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/script.js" type="text/javascript"></script>
    </body>

</html>
