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
    const container = document.getElementById('buttons-container'); // ボタンが含まれるコンテナのID

    container.addEventListener('click', function(event) {
        const target = event.target; // クリックされた要素
        if (!target.closest('tr')) return; // trがない場合は処理をしない
        const comboId = target.getAttribute('data-id'); // 正しくcomboIdを取得

        //編集ボタンの処理

        if (target.classList.contains('edit-btn')) {
            console.log('編集ボタンが押されました'+comboId);
            const row = target.closest('tr');
            const nameSpan = row.querySelector('[data-name="name"]');
            const damageSpan = row.querySelector('[data-name="damage"]');
            const inputSpan = row.querySelector('[data-name="input"]');
            const startupSpan = row.querySelector('[data-name="startup"]');

            // テキストを編集可能なフィールドに変更
            nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent}">`;
            damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent}">`;
            inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent}">`;
            startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent}">`;

            target.textContent = '保存';
            target.classList.replace('edit-btn', 'save-btn');
        }
        else if (target.classList.contains('save-btn')) {
            console.log('保存ボタンが押されました'+comboId);
            const row = target.closest('tr');
            const name = row.querySelector('[data-name="name"] input').value;
            const damage = row.querySelector('[data-name="damage"] input').value;
            const input = row.querySelector('[data-name="input"] input').value;
            const startup = row.querySelector('[data-name="startup"] input').value;

            console.log(name+damage+input+startup);

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
                } else {
                    alert('更新に失敗しました');
                }
            })
            .catch(error => console.error('Error:', error));

            // テキストを表示モードに戻す
            row.querySelector('[data-name="name"]').textContent = name;
            row.querySelector('[data-name="damage"]').textContent = damage;
            row.querySelector('[data-name="input"]').textContent = input;
            row.querySelector('[data-name="startup"]').textContent = startup;

            target.textContent = '編集';
            target.classList.replace('save-btn', 'edit-btn');
        }
    });

});
