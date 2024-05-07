let currentEditingRow = null; // 現在編集中の行を追跡する変数
// DOMが完全に読み込まれるのを待ってから、以下のコードを実行します。
window.addEventListener('DOMContentLoaded', (event) => {
    // 特定のクラスを持つテキスト要素を画像に変換することを意図しているようです。
    convertTextToImage();
});

// 'deleteCombo' という名前の関数を定義し、パラメータとして 'comboId' を取ります。
// この関数は特定のアイテム（コンボ）を削除するために使用されます。
function deleteCombo(comboId) {
    // 削除する前にユーザーに確認を求めます。
    if (confirm('このコンボを削除してもよろしいですか？')) {
        // 'fetch' APIを使用してサーバーにDELETEリクエストを行います。
        fetch('/combos/' + comboId + '/delete', {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    // サーバーから肯定的な応答があった場合、ページをリロードして変更を反映させます。
                    window.location.reload();
                } else {
                    // 削除操作が失敗した場合、ユーザーに警告します。
                    alert('削除に失敗しました。');
                }
            })
            .catch(error => {
                // fetch操作中に発生したエラーをコンソールに記録します。
                console.error('Error:', error);
                alert('通信エラーが発生しました。');
            });
    }
}

// 編集ボタンを押したときの処理を定義する関数 'clickButton'
function clickButton(row, button) {
    // ボタンのテキストが '編集' の場合、編集モードに入る
    if (button.textContent === '編集') {

        console.log('currentEditingRow  ' + currentEditingRow);
        if (currentEditingRow && currentEditingRow !== row) {
            cancelEdit(currentEditingRow);
        }
        currentEditingRow = row;

        // 編集が始まったことをコンソールにログ出力
        console.log(row + 'clickButtonが押されました');

        // テキストを画像に変換する処理を呼び出す
        convertImageToText();

        // 指定された行から特定のデータ属性を持つ要素を選択して入力フィールドに変換
        const nameSpan = row.querySelector('[data-name="name"]');
        const damageSpan = row.querySelector('[data-name="damage"]');
        const inputSpan = row.querySelector('[data-name="input"]');
        const startupSpan = row.querySelector('[data-name="startup"]');
        const usagedgSpan = row.querySelector('[data-name="usagedg"]');
        const usagesaSpan = row.querySelector('[data-name="usagesa"]');
        const explainSpan = row.querySelector('[data-name="explain"]');
        const hittypeSpan = row.querySelector('[data-name="hittype"]');

        // 各Spanを入力可能なフィールドに変更
        nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent.trim()}">`;
        damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent.trim()}">`;
        inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent.trim()}">`;
        startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent.trim()}">`;
        usagedgSpan.innerHTML = `<input type="text" value="${usagedgSpan.textContent.trim()}">`;
        usagesaSpan.innerHTML = `<input type="text" value="${usagesaSpan.textContent.trim()}">`;
        explainSpan.innerHTML = `<input type="text" value="${explainSpan.textContent.trim()}">`;
        hittypeSpan.innerHTML = `<select type="text" id="hittype" name="hittype" placeholder="カウンター状況"><option value="0">ノーマルヒット</option><option value="1">カウンター</option><option value="2">パニッシュカウンター</option></select>`;

        button.textContent = '保存';
        button.classList.replace('edit-btn', 'save-btn');
    } else {
        // ボタンのテキストが '保存' の場合、保存処理を行う
        console.log(row + '保存clickButtonが押されました');

        // 各入力フィールドから値を取得
        const comboId = button.getAttribute('data-id');
        const nameInput = row.querySelector('[data-name="name"] input');
        const damageInput = row.querySelector('[data-name="damage"] input');
        const inputInput = row.querySelector('[data-name="input"] input');
        const startupInput = row.querySelector('[data-name="startup"] input');
        const usagedgInput = row.querySelector('[data-name="usagedg"] input');
        const usagesaInput = row.querySelector('[data-name="usagesa"] input');
        const explainInput = row.querySelector('[data-name="explain"] input');
        const hittypeSelect = row.querySelector('[data-name="hittype"] select');

        // 入力値を元にサーバーへPOSTリクエストを送信
        fetch(`/combos/${comboId}/edit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                id: comboId, name: nameInput.value, damage: damageInput.value,
                input: inputInput.value, startup: startupInput.value,
                usagedg: usagedgInput.value, usagesa: usagesaInput.value,
                explain: explainInput.value, hittype: hittypeSelect.value
            })
        })
            .then(response => {
                if (response.ok) {
                    alert('コンボが更新されました');
                    // 入力フィールドを通常のテキスト表示に戻す
                    nameInput.parentNode.textContent = nameInput.value;
                    damageInput.parentNode.textContent = damageInput.value;
                    inputInput.parentNode.textContent = inputInput.value;
                    startupInput.parentNode.textContent = startupInput.value;
                    usagedgInput.parentNode.textContent = usagedgInput.value;
                    usagesaInput.parentNode.textContent = usagesaInput.value;
                    explainInput.parentNode.textContent = explainInput.value;
                    hittypeSelect.parentNode.textContent = hittypeSelect.options[hittypeSelect.selectedIndex].text;

                    // 保存後、再度テキストを画像に変換する処理を呼び出す
                    convertTextToImage();
                } else {
                    // 保存失敗時のエラーハンドリング
                    alert('更新中にエラーが発生しました');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('更新中にエラーが発生しました');
            });

        // ボタンのテキストを '編集' に戻し、クラスを 'save-btn' から 'edit-btn' に変更
        button.textContent = '編集';
        button.classList.replace('save-btn', 'edit-btn');
    }
}


//テキストを画像に変換する
function convertTextToImage() {
    //console.log('convertTextToImage was called');
    document.querySelectorAll('.editable[data-name="input"]').forEach((element) => {
        const patterns = [

            { key: /LP/gi, value: '<img src="image/LP.png" alt="LP" class="icon"/>' },
            { key: /LK/gi, value: '<img src="image/LK.png" alt="LK" class="icon"/>' },
            { key: /MP/gi, value: '<img src="image/MP.png" alt="MP" class="icon"/>' },
            { key: /MK/gi, value: '<img src="image/MK.png" alt="MK" class="icon"/>' },
            { key: /HP/gi, value: '<img src="image/HP.png" alt="HP" class="icon"/>' },
            { key: /HK/gi, value: '<img src="image/HK.png" alt="HK" class="icon"/>' },
            { key: /1/gi, value: '<img src="image/1.png" alt="1" class="icon"/>' },
            { key: /2/gi, value: '<img src="image/2.png" alt="2" class="icon"/>' },
            { key: /3/gi, value: '<img src="image/3.png" alt="3" class="icon"/>' },
            { key: /4/gi, value: '<img src="image/4.png" alt="4" class="icon"/>' },
            { key: /6/gi, value: '<img src="image/6.png" alt="6" class="icon"/>' },
            { key: /7/gi, value: '<img src="image/7.png" alt="7" class="icon"/>' },
            { key: /8/gi, value: '<img src="image/8.png" alt="8" class="icon"/>' },
            { key: /9/gi, value: '<img src="image/9.png" alt="9" class="icon"/>' },
            { key: /throw/gi, value: '<img src="image/throw.png" alt="throw" class="icon"/>' },
            { key: /PP/g, value: '<img src="image/P.png" alt="P" class="icon"/><img src="image/P.png" alt="P" class="icon"/>' },


        ];

        let updatedHTML = element.textContent;
        patterns.forEach(pattern => {
            updatedHTML = updatedHTML.replace(pattern.key, pattern.value);
        });
        element.innerHTML = updatedHTML;
    });
}

let sortDirections = []; // 各列のソート方向を追跡するための配列

function sortTable(columnIndex) {
    console.log('sortTableが押されました'); // デバッグ用のコンソールログ
    let table, rows, isSortNecessary;
    table = document.getElementById("myTable"); // テーブルを取得
    isSortNecessary = true;

    // 現在の列のソート方向を確認または初期化
    if (sortDirections[columnIndex] === undefined) {
        sortDirections[columnIndex] = true; // 初めてのソートは昇順
    } else {
        sortDirections[columnIndex] = !sortDirections[columnIndex]; // ソート方向を逆にする
    }

    while (isSortNecessary) {
        isSortNecessary = false; // ソートが必要な状態を初期化
        rows = table.rows; // テーブルの全行を取得

        // 行を比較してソート
        for (let i = 1; i < (rows.length - 1); i++) {
            //変数の宣言　x:現在のセル y:次のセル shouldSwitch:入れ替えるかどうか
            let x, y, shouldSwitch;
            shouldSwitch = false; // 初期値としてスイッチしない設定
            x = rows[i].getElementsByTagName("td")[columnIndex]; // 現在のセルの値
            y = rows[i + 1].getElementsByTagName("td")[columnIndex]; // 次のセルの値

            if (sortDirections[columnIndex]) {
                // 昇順の場合　現在のセルの値が次のセルの値より大きい場合
                if (parseInt(x.innerText) > parseInt(y.innerText)) {
                    shouldSwitch = true;
                }
            } else {
                // 降順の場合　現在のセルの値が次のセルの値より小さい場合
                if (parseInt(x.innerText) < parseInt(y.innerText)) {
                    shouldSwitch = true;
                }
            }

            //console.log('rows.length' + rows.length + ' switching:' + switching + ' i:' + i + ' x:' + x.innerText + ' y:' + y.innerText + ' shouldSwitch:' + shouldSwitch); // デバッグ用のログ

            if (shouldSwitch) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]); // 行を交換
                isSortNecessary = true; // ソートが続くことを示す
                break; // 交換が発生した場合はループを終了
            }
        }
    }
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


//検索機能
function searchCombos() {
    const searchInput = document.getElementById('search');
    const searchTerm = searchInput.value.toLowerCase();
    const tableBody = document.querySelector('table > tbody');
    const comboRows = Array.from(tableBody.getElementsByTagName('tr'));

    for (let i = 0; i < comboRows.length; i++) {
        const row = comboRows[i];
        const name = row.querySelector('[data-name="name"]').textContent.toLowerCase();
        const input = row.querySelector('[data-name="input"]').textContent.toLowerCase();
        const explain = row.querySelector('[data-name="explain"]').textContent.toLowerCase();

        if (name.includes(searchTerm) || input.includes(searchTerm) || explain.includes(searchTerm)) {
            row.style.display = '';
        } else {
            row.style.display = 'none';
        }
    }
}

//追加フォームを表示
function showForm() {
    // "addFormRow" というIDを持つ tbody 要素を取得
    var formRow = document.getElementById('addFormRow');
    // この tbody 要素の display スタイルを変更してフォームを表示
    formRow.style.display = 'table-row-group';
}

//追加フォームの保存ボタンを押したときの処理
function addCombo() {

    console.log('addComboが押されました');
    // 入力値を取得
    const name = document.getElementById('name').value;
    const damage = document.getElementById('damage').value;
    const input = document.getElementById('input').value;
    const startup = document.getElementById('startup').value;
    const usagedg = document.getElementById('usagedg').value;
    const usagesa = document.getElementById('usagesa').value;
    const explain = document.getElementById('explain').value;
    const hittype = document.getElementById('hittype').value;

    console.log(name);
    console.log(damage);
    console.log(input);
    console.log(startup);
    console.log(usagedg);
    console.log(usagesa);
    console.log(explain);
    console.log(hittype);

    // サーバーに送信
    fetch('/combos/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, damage, input, usagedg, usagesa, startup, explain, hittype })
    })
        .then(response => {
            if (response.ok) {
                alert('コンボが追加されました');
                //window.location.reload(); // 成功したらページをリロード
            } else {
                // エラーハンドリング
                response.json().then(data => {
                    console.error('Server Error:', data.message);
                    alert('エラー: ' + data.message); // サーバーからのエラーメッセージを表示
                });
                throw new Error('Server response was not ok.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('追加中にエラーが発生しました');
        });
    // ここでテキストを表示モードに戻す
    nameInput.parentNode.textContent = name;
    damageInput.parentNode.textContent = damage;
    inputInput.parentNode.textContent = input;
    startupInput.parentNode.textContent = startup;
    usagedgInput.parentNode.textContent = usagedg;
    usagesaInput.parentNode.textContent = usagesa;
    explainInput.parentNode.textContent = explain;
    hittypeInput.parentNode.textContent = hittype;
}

function cancelEdit(row) {
    // 編集をキャンセルするロジック
    //入力フィールドを元のテキストに戻す
    const nameInput = row.querySelector('[data-name="name"] input');
    const damageInput = row.querySelector('[data-name="damage"] input');
    const inputInput = row.querySelector('[data-name="input"] input');
    const startupInput = row.querySelector('[data-name="startup"] input');
    const usagedgInput = row.querySelector('[data-name="usagedg"] input');
    const usagesaInput = row.querySelector('[data-name="usagesa"] input');
    const explainInput = row.querySelector('[data-name="explain"] input');
    const hittypeInput = row.querySelector('[data-name="hittype"] select');

    nameInput.parentNode.textContent = nameInput.value;
    damageInput.parentNode.textContent = damageInput.value;
    inputInput.parentNode.textContent = inputInput.value;
    startupInput.parentNode.textContent = startupInput.value;
    usagedgInput.parentNode.textContent = usagedgInput.value;
    usagesaInput.parentNode.textContent = usagesaInput.value;
    explainInput.parentNode.textContent = explainInput.value;
    hittypeInput.parentNode.textContent = hittypeInput.options[hittypeInput.selectedIndex].text;

    const editButton = row.querySelector('.save-btn');
    editButton.textContent = '編集';
    editButton.classList.replace('save-btn', 'edit-btn');
}