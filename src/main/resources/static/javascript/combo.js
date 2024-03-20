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

        convertImageToText();

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
        const explainInput = row.querySelector('[data-name="explain"] input');
        
        if (!nameInput || !damageInput || !inputInput || !startupInput || !usagedgInput || !usagesaInput || !explainInput) {
            console.error('One or more input elements could not be found');
            return;
        }
        
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
            body: JSON.stringify({ id, name, damage, input, usagedg, usagesa, startup , explain})
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

                convertTextToImage();
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
    console.log('convertTextToImage was called');
    document.querySelectorAll('.editable[data-name="input"]').forEach((element) => {
        const patterns = [
            { key: /HK/gi, value: '<img src="image/HK.png" alt="HK" class="icon"/>' },
            { key: /HP/gi, value: '<img src="image/HP.png" alt="HP" class="icon"/>' },
            { key: /LK/gi, value: '<img src="image/LK.png" alt="LK" class="icon"/>' },
            { key: /LP/gi, value: '<img src="image/LP.png" alt="LP" class="icon"/>' },
            { key: /MK/gi, value: '<img src="image/MK.png" alt="MK" class="icon"/>' },
            { key: /MP/gi, value: '<img src="image/MP.png" alt="MP" class="icon"/>' },
            { key: /1/gi, value: '<img src="image/1.png" alt="1" class="icon"/>' },
            { key: /2/gi, value: '<img src="image/2.png" alt="2" class="icon"/>' },
            { key: /3/gi, value: '<img src="image/3.png" alt="3" class="icon"/>' },
            { key: /4/gi, value: '<img src="image/4.png" alt="4" class="icon"/>' },
            { key: /6/gi, value: '<img src="image/6.png" alt="6" class="icon"/>' },
            { key: /7/gi, value: '<img src="image/7.png" alt="7" class="icon"/>' },
            { key: /8/gi, value: '<img src="image/8.png" alt="8" class="icon"/>' },
            { key: /9/gi, value: '<img src="image/9.png" alt="9" class="icon"/>' },
        ];

        let updatedHTML = element.textContent;
        patterns.forEach(pattern => {
            updatedHTML = updatedHTML.replace(pattern.key, pattern.value);
        });
        element.innerHTML = updatedHTML;
    });
}


//画像をテキストに変換する
function convertImageToText() {
    document.querySelectorAll('.editable').forEach((element) => {
        element.querySelectorAll('img').forEach((img) => {
        // 'alt'属性からテキストを取得し、画像をそのテキストで置き換える
        const altText = img.getAttribute('alt');
            if (altText) {
                const textNode = document.createTextNode(altText);
                img.parentNode.replaceChild(textNode, img);
            }
        });
    });
}

//ソート機能
function sortTable(n, isNumeric = false) {
    const table = document.querySelector('table');
    let rows = Array.from(table.querySelectorAll('tbody tr'));
    const headerCell = table.querySelector('th:nth-child(' + (n + 1) + ')');
    const isSortedAsc = headerCell.classList.contains('asc');
  
    rows.sort((a, b) => {
      const aCellValue = isNumeric
        ? parseFloat(a.querySelectorAll('td')[n].textContent)
        : a.querySelectorAll('td')[n].textContent.toLowerCase();
      const bCellValue = isNumeric
        ? parseFloat(b.querySelectorAll('td')[n].textContent)
        : b.querySelectorAll('td')[n].textContent.toLowerCase();
  
      if (aCellValue < bCellValue) return isSortedAsc ? -1 : 1;
      if (aCellValue > bCellValue) return isSortedAsc ? 1 : -1;
      return 0;
    });
  
    Array.from(table.querySelectorAll('th')).forEach(th => th.classList.remove('asc', 'desc'));
    headerCell.classList.add(isSortedAsc ? 'desc' : 'asc');
  
    rows.forEach(row => table.querySelector('tbody').appendChild(row));
  }