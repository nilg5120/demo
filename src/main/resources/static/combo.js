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
/*
    //***編集ボタンが押されたときにコンボを編集

    document.querySelectorAll('.edit-btn').forEach(function(button) {
        button.addEventListener('click', function() {
    
            console.log('edit-btnが押されました');
    
            const comboId = this.getAttribute('data-id');
            const row = this.closest('tr');
            const nameSpan = row.querySelector('[data-name="name"]');
            const damageSpan = row.querySelector('[data-name="damage"]');
            const inputSpan = row.querySelector('[data-name="input"]');
            const startupSpan = row.querySelector('[data-name="startup"]');
    
            // テキストを編集可能なフィールドに変更
            nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent}">`;
            damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent}">`;
            inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent}">`;
            startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent}">`;

            // 編集ボタンを保存ボタンに変更
            console.log('クラスを変更します');
            this.textContent = '保存';
            this.classList.remove('edit-btn');
            this.classList.add('save-btn');
        });
    });

    //***保存ボタンが押されたときにコンボを保存
    document.querySelectorAll('.save-btn').forEach(function(button) {
        // 保存ボタンがクリックされたときのイベントリスナーを追加
        this.addEventListener('click', function() {

            console.log('save-btnが押されました');

            const name = nameSpan.querySelector('input').value;
            const damage = damageSpan.querySelector('input').value;
            const input = inputSpan.querySelector('input').value;
            const startup = startupSpan.querySelector('input').value;

            fetch('/combos/' + comboId + '/edit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // 例: 'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify({ name, damage, input, startup })
            })
            .then(response => {
                if (response.ok) {
                    alert('コンボが更新されました');
                    // 必要に応じてUIを更新
                } else {
                    alert('更新に失敗しました');
                }
            })
            .catch(error => console.error('Error:', error));

            // テキストを編集不可能なフィールドに戻す
            nameSpan.innerHTML = name;
            damageSpan.innerHTML = damage;
            inputSpan.innerHTML = input;
            startupSpan.innerHTML = startup;

            // 保存ボタンを編集ボタンに戻す
            this.textContent = '編集';
            this.classList.remove('save-btn');
            this.classList.add('edit-btn')
        });
    });
    /*
    //edit-btnが押されたときにコンソールに出力
    document.querySelectorAll('.edit-btn').forEach(function(button) {
        button.addEventListener('click', function() {
            console.log('edit-btnが押されました');
        });
    });
    */
    const container = document.getElementById('buttons-container'); // ボタンが含まれるコンテナのID

    container.addEventListener('click', function(event) {
        const target = event.target; // クリックされた要素
        let row = target.closest('tr'); // クリックされた要素が含まれる行

        // 編集ボタンがクリックされた場合
        if (target.classList.contains('edit-btn')) {
            console.log('編集ボタンが押されました');

            // 編集モードの処理をここに記述
            comboId = this.getAttribute('data-id');
            row = this.closest('tr');
            nameSpan = row.querySelector('[data-name="name"]');
            damageSpan = row.querySelector('[data-name="damage"]');
            inputSpan = row.querySelector('[data-name="input"]');
            startupSpan = row.querySelector('[data-name="startup"]');
    
            // テキストを編集可能なフィールドに変更
            nameSpan.innerHTML = `<input type="text" value="${nameSpan.textContent}">`;
            damageSpan.innerHTML = `<input type="text" value="${damageSpan.textContent}">`;
            inputSpan.innerHTML = `<input type="text" value="${inputSpan.textContent}">`;
            startupSpan.innerHTML = `<input type="text" value="${startupSpan.textContent}">`;

            // ボタンのテキストとクラスを変更して「保存」にする
            target.textContent = '保存';
            target.classList.replace('edit-btn', 'save-btn');



            
            console.log(comboId);


        }

        // 保存ボタンがクリックされた場合
        else if (target.classList.contains('save-btn')) {
            console.log('保存ボタンが押されました');

            // 保存処理をここに記述
            const name = nameSpan.querySelector('input').value;
            const damage = damageSpan.querySelector('input').value;
            const input = inputSpan.querySelector('input').value;
            const startup = startupSpan.querySelector('input').value;



            console.log(comboId);





            fetch('/combos/' + comboId + '/edit', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // 例: 'X-CSRF-TOKEN': csrfToken
                },
                body: JSON.stringify({ name, damage, input, startup })
            })
            .then(response => {
                if (response.ok) {
                    alert('コンボが更新されました');
                    // 必要に応じてUIを更新
                } else {
                    alert('更新に失敗しました');
                }
            })
            .catch(error => console.error('Error:', error));

            // テキストを編集不可能なフィールドに戻す
            nameSpan.innerHTML = name;
            damageSpan.innerHTML = damage;
            inputSpan.innerHTML = input;
            startupSpan.innerHTML = startup;

            // 処理後、ボタンのテキストとクラスを「編集」に戻す
            target.textContent = '編集';
            target.classList.replace('save-btn', 'edit-btn');
        }
    });

});
