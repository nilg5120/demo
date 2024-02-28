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

function clickButton() {
    console.log('clickButtonが押されました');
}