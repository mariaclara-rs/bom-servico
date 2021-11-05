
function salvar(pagina) {
    event.preventDefault();
    document.getElementById("mensagem").innerHTML = "";
    var fdados = document.getElementById("fdados");
    var emailValido = validarEmail(fdados.email.value);
    var senhaValida = validarSenha(fdados.senha.value);
    var nome = fdados.nome.value;
    var cpf = fdados.cpf.value;
    var fone = fdados.telefone.value;
    var camposPreenchidos = validarCampos(nome, cpf, fone);

    if (camposPreenchidos && emailValido && senhaValida) {
        var fdados = document.getElementById("fdados");
        var jsontext = JSON.stringify(Object.fromEntries(new FormData(fdados)));
        const data = new URLSearchParams();
        data.append("acao", "confirmar");
        data.append("dados", jsontext);
        fetch("ControllerCadUsu", {method: 'POST', body: data})
                .then(function (response) {
                    return response.text();
                })
                .then(function (retorno) {
                    if (retorno.startsWith('Erro')) {
                        document.getElementById("mensagem").innerHTML = retorno;
                    } else {
                        if(pagina === "cadastro")
                            window.location.href = "http://localhost:8084/JosePauloMariaClara/index.jsp";
                        else{
                            document.getElementById("mensagem").classList.add("sucesso");
                            document.getElementById("mensagem").innerHTML = "Alterado com sucesso!";
                            setTimeout(() => { 
                                document.getElementById("mensagem").innerHTML = "";
                            }, 5000);
                        }
                    }
                }).catch(function (error) {
            console.error(error);
        });
    }
}

function logout() {
    fetch("ControllerCadUsu?acao=logout", {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            window.location.href = "http://localhost:8084/JosePauloMariaClara/index.jsp";
        });
    }).catch(function (err) {
        console.error(err);
    });

}

function validarCampos(nome, cpf, fone) {
    var erroNome = document.getElementById("erroNome");
    var erroCpf = document.getElementById("erroCpf");
    var erroFone = document.getElementById("erroFone");
    
    erroNome.innerHTML = "";
    erroCpf.innerHTML = "";
    erroFone.innerHTML = "";
    
    var valido = true;
    
    if (nome === "") {
        erroNome.innerHTML = "Nome não pode estar vazio";
        valido = false;
    } 
    
    if (cpf === "" || !(/^\d{3}\.\d{3}\.\d{3}\-\d{2}$/).test(cpf)) {
        erroCpf.innerHTML = "CPF deve estar no fomato XXX.XXX.XXX-XX";
        valido = false;
    }
    
    if(fone === "") {
        erroFone.innerHTML = "Telefone não pode estar vazio";
        valido = false;
    }
    
    return valido;
}
var vet = [];
var i;

function carregarAnunciantes() {
    event.preventDefault();
    var filtro=document.getElementById("filtro").value;
    const URL_TO_FETCH = 'ControllerAnuncio?acao=recuperar&filtro='+filtro;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            document.getElementById("anunciantes").innerHTML="";
            obj = JSON.parse(result);

            for (i = 0; i < obj.length; i++) {
                document.getElementById("anunciantes").innerHTML +=
                    "<div class='col-lg-4 col-xl-3' style='padding: 15px;'>" +
                    "<div class='mx-auto features-icons-item mb-5 mb-lg-0 mb-lg-3'>" +
                    "<div class='d-flex justify-content-xl-center features-icons-icon'>" +
                    "<img id='"+obj[i].usu.id+"'class='rounded-circle' src='assets/img/anuncios/' style='width: 100px;height: 100px; object-fit: cover;'>" +
                    "</div>" +
                    "<h3>" + obj[i].usu.nome + "</h3><strong id='categoria'>"+ obj[i].cat.nome+"</strong>" +
                    "<p class='lead mb-0'>"+obj[i].descricao.toString().substring(0,50)+"[...]<br></p>" +
                    "</div><a class='btn btn-primary' role='button' onclick='vizualizarAnuncio(" + obj[i].usu.id + ")' >Ver mais</a>" +
                    "</div>";
                vet[i]=obj[i].usu.id;
                //carregarImagem(obj[i].usu.id);
            }
            for(var k=0; k<i; k++){
                carregarImagem(vet[k]);
            }
        });
    }).catch(function (err) {
        console.error(err);
    });

}


function carregarImagem(usu_id) {
    const URL_TO_FETCH = 'ControllerImagens?acao=carregar&usu_id=' + usu_id;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            obj = JSON.parse(result);
            document.getElementById(usu_id).src="assets/img/anuncios/profile.png";
            if(obj[0]!=undefined)
                document.getElementById(usu_id).src="assets/img/anuncios/"+obj[0]+"";
        });
    }).catch(function (err) {
        console.error(err);
    });
}


function carregarImagensAnuncio(usu_id) {
    const URL_TO_FETCH = 'ControllerImagens?acao=carregar&usu_id=' + usu_id;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            var img = "";
            obj = JSON.parse(result);
            if(obj.length !== 0)
                for (var i = 0; i < obj.length; i++) {
                    if(i === 0)
                        img += "<div class='carousel-item active'>";
                    else
                        img += "<div class='carousel-item'>";
                        img+="<img style='max-width: 500px;min-width: 500px; min-height: 350px; max-height: 350px' class='d-block w-100 img-fluid' src='assets/img/anuncios/"+obj[i]+"' alt=''>"+
                        "</div>";
                }
            else {
                img += "<div class='carousel-item active'>";
                img+="<img style='max-width: 500px;min-width: 500px; min-height: 350px; max-height: 350px' class='d-block w-100 img-fluid' src='assets/img/noimg.jpg' alt=''>"+
                "</div>";
            }
            
            document.getElementById("imagens").innerHTML = img;
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function vizualizarAnuncio(id) {
    const data = new URLSearchParams();
    data.append("acao", "anuncio");
    fetch("ControllerCadUsu?id=" + id, {method: 'POST', body: data})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                window.location.href = "http://localhost:8084/JosePauloMariaClara/ver-anuncio.jsp";

            }).catch(function (error) {
    });

}

function gerarRelatorio() {
    const data = new URLSearchParams();
    data.append("acao", "relatorio");
    fetch("ControllerAnuncio?id=", {method: 'POST', body: data})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                console.log(text);
            }).catch(function (error) {
    });
}

function salvarAnuncio(usu_id) {
    var fdados = document.getElementById("fanuncio");
    if (usu_id === -1)
        usu_id = fdados.usuario.value;
    inserirImagem(usu_id);
    var jsontext = JSON.stringify(Object.fromEntries(new FormData(fdados)));
    const data = new URLSearchParams();
    data.append("acao", "confirmar");
    data.append("dados", jsontext);
    data.append("categoria", fdados.categoria.value);
    data.append("usuario", usu_id);
    fetch("ControllerAnuncio", {method: 'POST', body: data})
            .then(function (response) {
                return response.text();
            })
            .then(function (retorno) {
                 if (retorno.startsWith('Erro')) {
                        document.getElementById("msgAnuncio").class.add("erro");
                        document.getElementById("msgAnuncio").innerHTML = retorno;
                    } else {
                        document.getElementById("msgAnuncio").classList.add("sucesso");
                        document.getElementById("msgAnuncio").innerHTML = "Alterado com sucesso!";
                        setTimeout(() => { 
                            document.getElementById("msgAnuncio").innerHTML = "";
                        }, 5000);
                    }
                if (usu_id !== -1)
                    carregarAnuncio(usu_id);
                carregarDadosAnuncio();
            }).catch(function (error) {
    });
}

function validarEmail(usu_email) {
    var erroEmail = document.getElementById("erroEmail");

    if (usu_email === "") {
        erroEmail.innerHTML = "Email não pode estar vazio";
        return false;
    } else if (!(/^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i).test(usu_email)) {
        erroEmail.innerHTML = "Email inválido";
        return false;
    }
    erroEmail.innerHTML = "";
    return true;
}

function validarSenha(usu_senha) {
    var erroSenha = document.getElementById("erroSenha");

    if (usu_senha === "") {
        erroSenha.innerHTML = "Senha nao pode estar vazia";
        return false;
    } else if (!(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/i).test(usu_senha)) {
        erroSenha.innerHTML = "Senha invalida";
        return false;
    }
    erroSenha.innerHTML = "";
    return true;
}

function validarLogin() {
    document.getElementById("mensagem").innerHTML = "";
    var fdados = document.getElementById("fdados");
    var emailValido = validarEmail(fdados.email.value);
    var senhaValida = validarSenha(fdados.senha.value);
    if (emailValido && senhaValida) {
        const data = new URLSearchParams();
        data.append("email", fdados.email.value);
        data.append("senha", fdados.senha.value);
        fetch("ControllerCadUsu?acao=logar", {method: 'POST', body: data})
                .then(function (response) {
                    return response.text();
                })
                .then(function (retorno) {
                    if (retorno.startsWith('Erro')) {
                        document.getElementById("mensagem").innerHTML = retorno;
                    } else {
                        fdados.reset();
                        window.location.href = "http://localhost:8084/JosePauloMariaClara/meu-perfil.jsp";
                    }
                }).catch(function (error) {
            console.error(error);
        });
    }
}
function carregarDados(usu_id) {
    carregarCategorias();
    carregarPerfil(usu_id);
    carregarAnuncio(usu_id);
}

function inserirImagem(usu_id) {
    event.preventDefault(); // evita refresh da tela
    if (document.getElementById("foto").value !== "") {
        if (usu_id === -1)
            usu_id = document.getElementById("usuario").value;

        const URL_TO_FETCH = 'ControllerImagens?acao=gravar&usu_id=' + usu_id;
        var formData = new FormData(document.getElementById("fanuncio"));
        fetch(URL_TO_FETCH, {method: 'post', body: formData
        }).then(function (response) {
            return response.text();
        }).then(function (retorno) {
            carregarImagens(usu_id);
            document.getElementById("foto").value = "";
        }).catch(function (error) {
            console.error(error);
        });
    }

}
function removerImagem(imagem, usu) {
    //event.preventDefault();
    const URL_TO_FETCH = 'ControllerImagens?acao=remover&img=' + imagem;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            carregarImagens(usu);
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function carregarImagens(usu_id) {
    const URL_TO_FETCH = 'ControllerImagens?acao=carregar&usu_id=' + usu_id;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            var img = "";
            obj = JSON.parse(result);
            var str;
            for (var i = 0; i < obj.length; i++) {
                img += "<div class'col-md-4 d-flex justify-content-xl-center'>" +
                        "<img style='max-width: 100px;max-height: none' src='assets/img/anuncios/" + obj[i] + "'>" +
                        "<button class='btn btn-primary' onclick='removerImagem(`" + obj[i] + "`," + usu_id + ")' type='button' style='padding: 6px;margin: 56px 0px 0px;margin-top: 0;margin-bottom: 49px;color: var(--red);background: transparent;padding-bottom: 0px;padding-right: 6px;padding-top: 0px;border-width: 0px;border-radius: 0px;'>" +
                        "<i class='fa fa-remove'></i>" +
                        "</button><div></div></div>";

            }

            document.getElementById("imagens").innerHTML = img;
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function carregarPerfil(usu_id) {
    var fdados = document.getElementById("fdados");
    fetch("ControllerCadUsu?acao=buscar&id=" + usu_id, {method: 'POST'})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                var json = JSON.parse(text);
                fdados.id.value = json.id;
                fdados.cpf.value = json.cpf;
                fdados.nome.value = json.nome;
                fdados.telefone.value = json.telefone;
                fdados.email.value = json.email;
                fdados.senha.value = json.senha;
            }).catch(function (error) {
        console.error(error);
    });
}

function carregarPaginaAnuncio(usu_id) {
    carregarImagensAnuncio(usu_id);

    var titulo = document.getElementById("titulo");
    var descricao = document.getElementById("descricao");
    var preco = document.getElementById("preco");
    var categoria = document.getElementById("categoria");
    var usuario = document.getElementById("usuario");
    var email = document.getElementById("email");
    var telefone = document.getElementById("telefone");

    fetch("ControllerAnuncio?acao=buscar&usu_id=" + usu_id, {method: 'POST'})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                var json = JSON.parse(text);
                if (json !== null) {
                    titulo.innerHTML = json.titulo;
                    descricao.innerHTML = json.descricao;
                    categoria.innerHTML = json.cat.nome;
                    preco.innerHTML = json.preco;
                    usuario.innerHTML = json.usu.nome;
                    email.innerHTML = json.usu.email;
                    telefone.innerHTML = json.usu.telefone;
                    email.href = "mailto:"+json.usu.email;
                }

            }).catch(function (error) {
        console.error(error);
    });
}

function limparForm() {
    var fanuncio = document.getElementById("fanuncio");
    var fdados = document.getElementById("fdados");
    
    fanuncio.reset();
    fdados.reset();
}

function carregarAnuncio(usu_id) {
    carregarImagens(usu_id);
    var fanuncio = document.getElementById("fanuncio");
    fetch("ControllerAnuncio?acao=buscar&usu_id=" + usu_id, {method: 'POST'})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                var json = JSON.parse(text);
                if (json !== null) {
                    fanuncio.id.value = json.id;
                    fanuncio.titulo.value = json.titulo;
                    fanuncio.descricao.value = json.descricao;
                    fanuncio.categoria.value = json.cat.id;
                    fanuncio.preco.value = json.preco;
                    fanuncio.usuario.value = json.usu.id;
                }
            }).catch(function (error) {}
            );
}

function carregarCategorias() {
    event.preventDefault();
    const URL_TO_FETCH = 'ControllerCategoria?acao=recuperar';
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            var cat;
            obj = JSON.parse(result);
            for (var i = 0; i < obj.length; i++)
                cat += "<option value='" + obj[i].id + "' id='" + obj[i].id + "'>" + obj[i].nome + "</option>"
            document.getElementById("categoria").innerHTML += cat;
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function carregarDadosCategoria() {
    //event.preventDefault();
    const URL_TO_FETCH = 'ControllerCategoria?acao=recuperar';
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            var cat = "";
            obj = JSON.parse(result);
            for (var i = 0; i < obj.length; i++)
                cat += "<tr class='justify-content-center align-items-center align-content-center' style='text-align: left;'>" +
                        "<td>" + obj[i].id + "</td>" +
                        "<td>" + obj[i].nome + "</td>" +
                        "<td class='d-flex d-lg-flex justify-content-lg-start align-items-lg-end'>" +
                        "<a href='#' onclick='gerenciarCategoria(\"alterar\"," + obj[i].id + ")' class='btn btn-danger' role='button' style='margin: 2px;background: var(--warning);color: var(--light);border-color: var(--yellow);font-size: 15px;'>" +
                        "<i class='fas fa-edit'></i>" +
                        "</a>" +
                        "<a href='#' onclick='gerenciarCategoria(\"apagar\"," + obj[i].id + ")' class='btn btn-danger' role='button' style='margin: 2px;font-size: 15px;'>" +
                        "<i class='fas fa-trash'></i>" +
                        "</a>" +
                        "</td></tr>";
            document.getElementById("preview").innerHTML = cat;
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function carregarDadosAnuncio() {
    const URL_TO_FETCH = 'ControllerAnuncio?acao=recuperar&filtro=';
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            var anu = "";
            obj = JSON.parse(result);
            for (var i = 0; i < obj.length; i++)
                anu += " <tr class='justify-content-center align-items-center align-content-center' style='text-align: left;'> " +
                        " <td>" + obj[i].id + "</td> " +
                        " <td style='text-align: justify;padding: 12px;padding-top: 12px;padding-bottom: 12px;margin-bottom: 0px;width: 121.531px;'>" + obj[i].titulo + "</td> " +
                        " <td style='text-align: justify;padding: 12px;padding-top: 12px;padding-bottom: 12px;margin-bottom: 0px;width: 121.531px;'>" + obj[i].cat.nome + "</td>" +
                        " <td class='d-flex d-lg-flex justify-content-lg-start align-items-lg-end'>" +
                        " <a href='#' onclick='carregarAnuncio(" + obj[i].usu.id + ")' class='btn btn-danger' role='button' style='margin: 2px;background: var(--warning);color: var(--light);border-color: var(--yellow);font-size: 15px;'> " +
                        " <i class='fas fa-edit'></i>" +
                        "</a>" +
                        "<a class='btn btn-danger' onclick='removerAnuncio(" + obj[i].id + ")' role='button' style='margin: 2px;font-size: 15px;'>" +
                        "<i class='fas fa-trash'></i>" +
                        "</a></td>" +
                        "</tr>";
            document.getElementById("anuncios").innerHTML = anu;
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function removerAnuncio(id) {
    const URL_TO_FETCH = 'ControllerAnuncio?acao=remover&id=' + id;
    fetch(URL_TO_FETCH, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            carregarDadosAnuncio();
        });
    }).catch(function (err) {
        console.error(err);
    });
}

function salvarCategoria() {
    event.preventDefault();
    var fdados = document.getElementById("fdados");
    var jsontext = JSON.stringify(Object.fromEntries(new FormData(fdados)));
    const data = new URLSearchParams();
    data.append("acao", "salvar");
    data.append("dados", jsontext);
    fetch("ControllerCategoria", {method: 'POST', body: data})
            .then(function (response) {
                return response.text();
            })
            .then(function (text) {
                carregarDadosCategoria();
                carregarCategorias();
                fdados.reset();
            }).catch(function (error) {
        console.error(error);
    });
}

function painelAdm() {
    carregarDadosCategoria();
    carregarCategorias();
    carregarDadosAnuncio();
}

function gerenciarCategoria(acao, id) {
    var url = "";
    switch (acao) {
        case "apagar":
            url = "ControllerCategoria?acao=apagar&id=" + id;
            break;
        case "alterar":
            url = "ControllerCategoria?acao=alterar&id=" + id;
            break;
    }

    fetch(url, {method: 'get'}).then(function (response) {
        response.text().then(function (result) {
            if (acao === 'apagar') {
                document.getElementById('fdados').reset();
                //document.getElementById("erromsg").innerHTML = result;
                carregarDadosCategoria();
            } else {
                var json = JSON.parse(result);
                var form = document.getElementById('fdados');
                form.id.value = json.id;
                form.nome.value = json.nome;
            }
        });
    }).catch(function (err) {
        console.error(err);
    });
}