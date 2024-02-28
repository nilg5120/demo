document.addEventListener('DOMContentLoaded', function () {

    let comboId;
    let nameSpan;
    let damageSpan;
    let inputSpan;
    let startupSpan;

    

    //***削除ボタンが押されたときにコンボを削除

    const deleteButtons = document.querySelectorAll('.delete-btn');
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function (event) {

            console.log('delete-btnが押されました');
            event.preventDefault();
            const comboId = this.getAttribute('data-id');

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
        });
    });


    //***編集、保存ボタンが押されたときにコンボを編集
    const container = document.getElementById('buttons-container');

    container.addEventListener('click', function(event) {
        const target = event.target;
        const row = target.closest('tr');

        if (!row || !target.matches('button')) return;

        const comboId = target.getAttribute('data-id');

        if (target.classList.contains('edit-btn')) {
            // 編集ボタンのロジック
            setupEdit(row, target);
        } else if (target.classList.contains('save-btn')) {
            // 保存ボタンのロジック
            saveChanges(row, target);
        }
    });

    function setupEdit(row, button) {
        const nameSpan = row.querySelector('[data-name="name"]');
        const damageSpan = row.querySelector('[data-name="damage"]');
        const inputSpan = row.querySelector('[data-name="input"]');
        const startupSpan = row.querySelector('[data-name="startup"]');

        nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent.trim()}">`;
        damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent.trim()}">`;
        inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent.trim()}">`;
        startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent.trim()}">`;

        button.textContent = '保存';
        button.classList.replace('edit-btn', 'save-btn');

        button.removeEventListener('click', setupEdit);
        button.addEventListener('click', function() {
            saveChanges(row, button);
        });
    }

    function saveChanges(row, button) {
        // comboId と入力フィールドの値を取得
        const comboId = button.getAttribute('data-id');
        const nameInput = row.querySelector('[data-name="name"] input');
        const damageInput = row.querySelector('[data-name="damage"] input');
        const inputInput = row.querySelector('[data-name="input"] input');
        const startupInput = row.querySelector('[data-name="startup"] input');
    
        // 入力値を変数に格納
        const name = nameInput.value;
        const damage = damageInput.value;
        const input = inputInput.value;
        const startup = startupInput.value;
    
        // サーバーに送信
        fetch(`/combos/${comboId}/edit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ name, damage, input, startup })
        })
        .then(response => {
            if (response.ok) {
                alert('コンボが更新されました');
    
                // ここでテキストを表示モードに戻す
                nameInput.parentNode.textContent = name;
                damageInput.parentNode.textContent = damage;
                inputInput.parentNode.textContent = input;
                startupInput.parentNode.textContent = startup;
    
                // その他のコード
            } else {
                // エラーハンドリング
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('更新中にエラーが発生しました');
        });
        target.textContent = '編集';
        target.classList.replace('save-btn', 'edit-btn');
    }

});
