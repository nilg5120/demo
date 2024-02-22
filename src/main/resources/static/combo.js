document.addEventListener('DOMContentLoaded', function () {

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

});
