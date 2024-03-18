window.addEventListener('DOMContentLoaded', (event) => {
    // クラス 'editable' を持つすべての要素を取得します。
    convertTextToImage();
});

//削除ボタンを押したときの処理
function deleteCombo(comboId) {
    if (confirm('このコンボを削除してもよろしいですか？')) {
        fetch('/combos/' + comboId + '/delete', {
            method: 'DELETE',
        })
        .then(response => {
            if (response.ok) {
                window.location.reload(); // 成功したらページをリロード
            } else {
                alert('削除に失敗しました。');
            }
        })
        .catch(error => console.error('Error:', error));
    }
}

function clickButton(row, button) {
    if (button.textContent === '編集') {
        console.log(row+'clickButtonが押されました');

        const nameSpan = row.querySelector('[data-name="name"]');
        const damageSpan = row.querySelector('[data-name="damage"]');
        const inputSpan = row.querySelector('[data-name="input"]');
        const startupSpan = row.querySelector('[data-name="startup"]');
        const usagedgSpan = row.querySelector('[data-name="usagedg"]');
        const usagesaSpan = row.querySelector('[data-name="usagesa"]');
        const explainSpan = row.querySelector('[data-name="explain"]');

        nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent.trim()}">`;
        damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent.trim()}">`;
        inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent.trim()}">`;
        startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent.trim()}">`;
        usagedgSpan.innerHTML = `<input type="text" value="${usagedgSpan.textContent.trim()}">`;
        usagesaSpan.innerHTML = `<input type="text" value="${usagesaSpan.textContent.trim()}">`;
        explainSpan.innerHTML = `<input type="text" value="${explainSpan.textContent.trim()}">`;


        button.textContent = '保存';
        button.classList.replace('edit-btn', 'save-btn');
    }else{
        console.log(row+'保存clickButtonが押されました');
        const comboId = button.getAttribute('data-id');
        const nameInput = row.querySelector('[data-name="name"] input');
        const damageInput = row.querySelector('[data-name="damage"] input');
        const inputInput = row.querySelector('[data-name="input"] input');
        const startupInput = row.querySelector('[data-name="startup"] input');
        const usagedgInput = row.querySelector('[data-name="usagedg"] input');
        const usagesaInput = row.querySelector('[data-name="usagesa"] input');
        const situationSpan = row.querySelector('[data-name="situation"]');
        const explainInput = row.querySelector('[data-name="explain"] input');
    
        // 入力値を変数に格納
        const id = comboId;
        const name = nameInput.value;
        const damage = damageInput.value;
        const input = inputInput.value;
        const startup = startupInput.value;
        const usagedg = usagedgInput.value;
        const usagesa = usagesaInput.value;
        const explain = explainInput.value;
    
        // サーバーに送信
        fetch(`/combos/${comboId}/edit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id, name, damage, input, usagedg, usagesa, startup })
        })
        .then(response => {
            if (response.ok) {
                alert('コンボが更新されました');
    
                // ここでテキストを表示モードに戻す
                nameInput.parentNode.textContent = name;
                damageInput.parentNode.textContent = damage;
                inputInput.parentNode.textContent = input;
                startupInput.parentNode.textContent = startup;
                usagedgInput.parentNode.textContent = usagedg;
                usagesaInput.parentNode.textContent = usagesa;
                explainInput.parentNode.textContent = explain;
    
                // その他のコード
            } else {
                // エラーハンドリング
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('更新中にエラーが発生しました');
        });
        button.textContent = '編集';
        button.classList.replace('save-btn', 'edit-btn');
    }
    
}

//テキストを画像に変換する
function convertTextToImage() {
    document.querySelectorAll('.editable').forEach((element) => {
        const patterns = [
            { key: /HK/gi, value: '<img src="image/HK.png" alt="HK" class="icon"/>' },
            { key: /HP/gi, value: '<img src="image/HP.png" alt="HP" class="icon"/>' },
            { key: /LK/gi, value: '<img src="image/LK.png" alt="LK" class="icon"/>' },
            { key: /LP/gi, value: '<img src="image/LP.png" alt="LP" class="icon"/>' },
            { key: /MK/gi, value: '<img src="image/MK.png" alt="MK" class="icon"/>' },
            { key: /MP/gi, value: '<img src="image/MP.png" alt="MP" class="icon"/>' },
            // 他のパターンも同様に追加
        ];

        let updatedHTML = element.innerHTML;
        patterns.forEach(pattern => {
            updatedHTML = updatedHTML.replace(pattern.key, pattern.value);
        });
        element.innerHTML = updatedHTML;
    });
}


//画像をテキストに変換する
function convrtimagetotext() {
    
}
