function addButton() {
    var button = document.createElement("button");
    button.innerHTML = "表";
    button.className = "front"; // classの代わりにclassNameを使用
    button.onclick = function () {
        alert("front Button clicked");
    };
    document.body.appendChild(button);
}

// ボタンが追加された後に、そのボタンに対して処理を行う
window.onload = function() {
    addButton(); // ボタンを追加

    // すべてのボタンに対してループ処理を行う
    var buttons = document.querySelectorAll("button");
    buttons.forEach(function(button) {
        if (button.classList.contains('front')) {
            button.innerHTML = "Click me again";
        } else {
            button.innerHTML = "Click me";
        }
    });
};
