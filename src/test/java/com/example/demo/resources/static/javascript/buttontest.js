function addButton() {
    var button = document.createElement("button");
    button.innerHTML = "表";
    button.className = "front"; // classの代わりにclassNameを使用
    button.onclick = changeButton;
    document.body.appendChild(button);
}

function Buttonclick() {
    alert("Button clicked");
}

//表ボタンが押されたら裏ボタンに変わる
function changeButton() {
    if (document.querySelector(".front")) {
        var button = document.querySelector(".front");
        button.innerHTML = "裏";
        button.className = "back";
    } else {   
        var button = document.querySelector(".back");
        button.innerHTML = "表";
        button.className = "front";
    }
}