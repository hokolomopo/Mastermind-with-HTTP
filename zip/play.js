var win = 0;
        var loose = 0;

        function sendAjax() {

            if (loose == 1 || win == 1) {
                resetPage();
                loose = 0;
                win = 0;
            }

            var toSend = "colors=";
            for (i = 0; i < 4; i++) {
                if (i != 0)
                    toSend += "+";
                toSend += document.getElementById("usrImg" + i).alt;
            }
            var xhttp = new XMLHttpRequest();
            console.log("Send");
            xhttp.onreadystatechange = function () {
                console.log(this.readyState + " " + this.status);

                if (this.readyState == 4 && this.status == 200) {
                    var tokens = this.responseText.split("+");
                    console.log("Answer received : " + tokens[0] + " " + tokens[1] + " " + tokens[2]);

                    var currentLine = tokens[0];

                    var color;
                    for (i = 0; i < 4; i++) {
                        color = document.getElementById("usrImg" + i).alt;
                        document.getElementById("game" + currentLine + i).alt = color;
                        document.getElementById("game" + currentLine + i).src = color + ".png";
                    }

                    for (i = 0; i < tokens[1]; i++) {
                        document.getElementById("results" + currentLine + i).alt = "red";
                        document.getElementById("results" + currentLine + i).src = "red.png";
                    }
                    for (i = tokens[1]; i < tokens[1] * 1 + tokens[2] * 1; i++) {
                        document.getElementById("results" + currentLine + i).alt = "white";
                        document.getElementById("results" + currentLine + i).src = "white.png";
                    }

                    if (tokens[1] == 4) {
                        window.alert("Congratulation ! You Won !");
                        win = 1;
                    }
                    else if (tokens[0] == 11) {
                        window.alert("Too bad, you lost :'(");
                        loose = 1;
                    }


                }
            };
            xhttp.open("GET", "/request?" + toSend);
            xhttp.send();

        }

        function resetPage() {
            console.log("resetting...");
            var color = "empty";

            for (var j = 0; j < 12; j++) {
                for (var i = 0; i < 4; i++) {
                    document.getElementById("game" + j + i).alt = color;
                    document.getElementById("game" + j + i).src = color + ".png";
                    document.getElementById("results" + j + i).alt = color;
                    document.getElementById("results" + j + i).src = color + ".png";
                }
            }
        }

        function changeColor(x) {
            var element = document.getElementById("usrImg" + x);
            var color = element.getAttribute("alt");

            switch (color) {
                case "white":
                    element.src = "black.png";
                    element.alt = "black";
                    break;
                case "black":
                    element.src = "red.png";
                    element.alt = "red";
                    break;
                case "red":
                    element.src = "blue.png";
                    element.alt = "blue";
                    break;
                case "blue":
                    element.src = "yellow.png";
                    element.alt = "yellow";
                    break;
                case "yellow":
                    element.src = "green.png";
                    element.alt = "green";
                    break
                case "green":
                    element.src = "white.png";
                    element.alt = "white";
                    break;
                default:
                    element.src = "white.png";
                    element.alt = "white";
                    break;
            }


        }