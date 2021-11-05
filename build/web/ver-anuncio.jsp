<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String id = session.getAttribute("id_anunciante").toString();
    System.out.println(id);
%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Anuncio</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/Article-Dual-Column.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/3.3.1/css/swiper.min.css">
    <link rel="stylesheet" href="assets/css/Simple-Slider.css">
    <link rel="icon" type="image/png" href="https://img.icons8.com/color/48/000000/small-business.png"/>
</head>

<body onload="carregarPaginaAnuncio(<%=id%>)">
    <section class="article-dual-column"></section>
    <div class="container">
        <div class="row">
            <div class="col mt-5">
                <a class="btn btn-sm bg-transparent text-muted" role="button" href="index.jsp">
                    <i class="fa fa-chevron-left" style="color: var(--gray);"></i>
                    &nbsp;Voltar
                </a>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-8 d-flex d-xl-flex flex-column justify-content-xl-center align-items-xl-center">
                <div id="carouselExampleControls" class="carousel slide mb-5" style="max-width: 500px;min-width: 500px; min-height: 350px; max-height: 350px" data-ride="carousel">
                    <div class="carousel-inner" id="imagens" style="max-width: 500px;min-width: 500px; min-height: 350px; max-height: 350px">
                      
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                  </a>
                  <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                  </a>
                </div>  
                <strong style="font-size: 22px;">Descrição</strong>
                <p class="text-center" id="descricao"></p>
            </div>
            <div class="col-md-4 d-xl-flex flex-column justify-content-center align-items-center align-self-start justify-content-xl-center">
                <h1 class="text-left" id="titulo"></h1>
                <div class="row d-flex w-100">
                    <div class="col d-xl-flex justify-content-xl-start align-items-xl-start">
                        <strong id="categoria"></strong>
                    </div>
                    <div class="col d-xl-flex justify-content-xl-end align-items-xl-center">
                        <i class="fa fa-money money-icon"></i>
                        R$<strong id="preco"></strong>
                    </div>
                </div>
                <div class="row d-flex m-0 w-100">
                    <div class="col d-xl-flex flex-column justify-content-xl-center align-items-xl-end pt-4">
                        <a href="#" class="m-0 f-14" id="usuario">Usuário</a>
                        <a href="" class="m-0 f-14" id="email">email@exemplo.com</a>
                        <a href="#" class="m-0 f-14" id="telefone">(DDD)99999-9999</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/3.3.1/js/swiper.jquery.min.js"></script>
    <script src="assets/js/Simple-Slider.js"></script>
    <script src="assets/js/script.js" type="text/javascript"></script>
</body>

</html>