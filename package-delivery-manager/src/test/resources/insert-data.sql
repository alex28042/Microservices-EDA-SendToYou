insert into package (id, sender_user_id, receipter_user_id, name) values ('test', null, null, null);
insert into package (id, sender_user_id, receipter_user_id, name) values ('test1', null, null, null);
insert into package (id, sender_user_id, receipter_user_id, name) values ('test2', null, null, null);

insert into "user" (id, email, street, floor, number) values ('1', 'emai', 'calle ayerbe', 'E', '22');
insert into "user" (id, email, street, floor, number) values ('2', 'emai', 'calle ayerbe', 'E', '22');
insert into "templates" (id) values ('<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <script src="https://kit.fontawesome.com/32af1672d8.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="src/styles/index.css">
        <link rel="stylesheet" href="src/styles/header.css">
        <link rel="stylesheet" href="src/styles/footer.css">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Alex</title>
    </head>
    <body id="theme">
        <header class="header">
            <div class="container nom-nav-container">
                <a href="#" class="nom" ><i class="fas fa-rocket"></i>  Alejandro Alonso</a>
                <nav class="barra">
                    <ul>
                        <li><a href="#">INICIO</a></li>
                        <li><a href="https://github.com/alex28042">GITHUB PERSONAL</a></li>
                        <li><a href="#">SOBRE MÍ</a></li>
                        <button onclick="alerta()">
                            <li><a href="#" id="alerta">ALERTA</a></li>
                        </button>
                    </ul>
                </nav>
            </div>
        </header>
        <main class="container">
            <div id="main-text">
                <button class="time" id="time">
                    <i class="far fa-clock"></i>
                </button>
                <p id="hora"></p>
                <div class="weather-position">
                    <div class="display">
                        <h1 class="ciudad"></h1>
                        <p class="temperatura"></p>
                    </div>
                </div>
                <div class="caja" id="caja1">
                    <img src="src/assets/367-3676210_rick-and-morty-wallpaper-4k-celular.png">
                    <p class = "texto-caja">Muy buenas, soy estudiante de ingeniería del Software en la Universidad Politécnica de Madrid, me gusta el desarrollo web y software de aplicaciones moviles :)</p>
                </div>
                <div class="caja" id="caja1">
                    <i class="fab fa-html5" id="icon"></i>
                    <i class="fab fa-css3-alt" id="icon"></i>
                    <i class="fab fa-js" id="icon"></i>
                    <p class="texto-caja">Tecnologías utilizadas: js, css y html</p>
                </div>
                <div>
                    <button id="arriba" class="flecha"><i class="fas fa-angle-double-up"></i></button>
                </div>
            </div>
        </main>
        <footer>
            <div class="container" >
                <div class="social">
                    <a href="https://github.com/alex28042"><i class="fab fa-github"></i></a>
                    <a href="https://www.instagram.com/alex28042/?hl=es"><i class="fab fa-instagram"></i></a>
                    <a href="https://twitter.com/Aleex28042"><i class="fab fa-twitter"></i></a>
                </div>
            </div>
        </footer>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="src/js/index.js" type="text/javascript"></script>
        <script src="src/js/app.js" type="text/javascript"></script>
    </body>
</html>' ||
                                     '<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <script src="https://kit.fontawesome.com/32af1672d8.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="src/styles/index.css">
        <link rel="stylesheet" href="src/styles/header.css">
        <link rel="stylesheet" href="src/styles/footer.css">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Alex</title>
    </head>
    <body id="theme">
        <header class="header">
            <div class="container nom-nav-container">
                <a href="#" class="nom" ><i class="fas fa-rocket"></i>  Alejandro Alonso</a>
                <nav class="barra">
                    <ul>
                        <li><a href="#">INICIO</a></li>
                        <li><a href="https://github.com/alex28042">GITHUB PERSONAL</a></li>
                        <li><a href="#">SOBRE MÍ</a></li>
                        <button onclick="alerta()">
                            <li><a href="#" id="alerta">ALERTA</a></li>
                        </button>
                    </ul>
                </nav>
            </div>
        </header>
        <main class="container">
            <div id="main-text">
                <button class="time" id="time">
                    <i class="far fa-clock"></i>
                </button>
                <p id="hora"></p>
                <div class="weather-position">
                    <div class="display">
                        <h1 class="ciudad"></h1>
                        <p class="temperatura"></p>
                    </div>
                </div>
                <div class="caja" id="caja1">
                    <img src="src/assets/367-3676210_rick-and-morty-wallpaper-4k-celular.png">
                    <p class = "texto-caja">Muy buenas, soy estudiante de ingeniería del Software en la Universidad Politécnica de Madrid, me gusta el desarrollo web y software de aplicaciones moviles :)</p>
                </div>
                <div class="caja" id="caja1">
                    <i class="fab fa-html5" id="icon"></i>
                    <i class="fab fa-css3-alt" id="icon"></i>
                    <i class="fab fa-js" id="icon"></i>
                    <p class="texto-caja">Tecnologías utilizadas: js, css y html</p>
                </div>
                <div>
                    <button id="arriba" class="flecha"><i class="fas fa-angle-double-up"></i></button>
                </div>
            </div>
        </main>
        <footer>
            <div class="container" >
                <div class="social">
                    <a href="https://github.com/alex28042"><i class="fab fa-github"></i></a>
                    <a href="https://www.instagram.com/alex28042/?hl=es"><i class="fab fa-instagram"></i></a>
                    <a href="https://twitter.com/Aleex28042"><i class="fab fa-twitter"></i></a>
                </div>
            </div>
        </footer>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="src/js/index.js" type="text/javascript"></script>
        <script src="src/js/app.js" type="text/javascript"></script>
    </body>
</html> <!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <script src="https://kit.fontawesome.com/32af1672d8.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css2?family=Montserrat&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="src/styles/index.css">
        <link rel="stylesheet" href="src/styles/header.css">
        <link rel="stylesheet" href="src/styles/footer.css">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Alex</title>
    </head>
    <body id="theme">
        <header class="header">
            <div class="container nom-nav-container">
                <a href="#" class="nom" ><i class="fas fa-rocket"></i>  Alejandro Alonso</a>
                <nav class="barra">
                    <ul>
                        <li><a href="#">INICIO</a></li>
                        <li><a href="https://github.com/alex28042">GITHUB PERSONAL</a></li>
                        <li><a href="#">SOBRE MÍ</a></li>
                        <button onclick="alerta()">
                            <li><a href="#" id="alerta">ALERTA</a></li>
                        </button>
                    </ul>
                </nav>
            </div>
        </header>
        <main class="container">
            <div id="main-text">
                <button class="time" id="time">
                    <i class="far fa-clock"></i>
                </button>
                <p id="hora"></p>
                <div class="weather-position">
                    <div class="display">
                        <h1 class="ciudad"></h1>
                        <p class="temperatura"></p>
                    </div>
                </div>
                <div class="caja" id="caja1">
                    <img src="src/assets/367-3676210_rick-and-morty-wallpaper-4k-celular.png">
                    <p class = "texto-caja">Muy buenas, soy estudiante de ingeniería del Software en la Universidad Politécnica de Madrid, me gusta el desarrollo web y software de aplicaciones moviles :)</p>
                </div>
                <div class="caja" id="caja1">
                    <i class="fab fa-html5" id="icon"></i>
                    <i class="fab fa-css3-alt" id="icon"></i>
                    <i class="fab fa-js" id="icon"></i>
                    <p class="texto-caja">Tecnologías utilizadas: js, css y html</p>
                </div>
                <div>
                    <button id="arriba" class="flecha"><i class="fas fa-angle-double-up"></i></button>
                </div>
            </div>
        </main>
        <footer>
            <div class="container" >
                <div class="social">
                    <a href="https://github.com/alex28042"><i class="fab fa-github"></i></a>
                    <a href="https://www.instagram.com/alex28042/?hl=es"><i class="fab fa-instagram"></i></a>
                    <a href="https://twitter.com/Aleex28042"><i class="fab fa-twitter"></i></a>
                </div>
            </div>
        </footer>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="src/js/index.js" type="text/javascript"></script>
        <script src="src/js/app.js" type="text/javascript"></script>
    </body>
</html>');






