/*
const log = document.getElementById('log');
const keysPressed = {};  // 現在押されているキーを管理するオブジェクト
const keysHandled = {};  // すでに処理されたキーをトラックするオブジェクト

document.addEventListener('keydown', function(event) {
    keysPressed[event.key] = true;
});

document.addEventListener('keyup', function(event) {
    let message;

    if (!keysHandled[event.key]) {
        if (keysPressed['a'] && keysPressed['d']) {
            message = 'A+D key pressed!';
        } else if (keysPressed['w'] && keysPressed['s']) {
            message = 'W+S key pressed!';
        } else if (keysPressed['a'] && keysPressed['w']) {
            message = 'A+W key pressed!';
        } else if (keysPressed['a'] && keysPressed['s']) {
            message = 'A+S key pressed!';
        } else if (keysPressed['d'] && keysPressed['w']) {
            message = 'D+W key pressed!';
        } else if (keysPressed['d'] && keysPressed['s']) {
            message = 'D+S key pressed!';
        } else {
            switch(event.key) {
                case 'w':
                    message = 'W key pressed!';
                    break;
                case 'a':
                    message = 'A key pressed!';
                    break;
                case 's':
                    message = 'S key pressed!';
                    break;
                case 'd':
                    message = 'D key pressed!';
                    break;
                case ' ':
                    message = 'Space key pressed!';
                    break;
                case 'Enter':
                    log.innerHTML = '';
                    return; // Enterキーでログをクリア
                default:
                    message = `${event.key.toUpperCase()} key pressed!`;
                    break;
            }
        }

        if (message) {
            log.innerHTML += `<div>${message}</div>`; // メッセージを追加
            keysHandled[event.key] = true;
        }
    }

    delete keysPressed[event.key];  // キーを離すと削除
    delete keysHandled[event.key];  // 処理済みキーを削除
});
*/
const logDiv = document.getElementById('log');
const keyActions = [];  // キーの動作（押す/離す）とそのキーを記録
const inputDiv = document.getElementById('input');
const comboDiv = document.getElementById('combo');
const keysSequence = [];  // キー入力の履歴を保存する配列
let currentcommand = 'default';

document.addEventListener('keydown', function(event) {
    addKeyAction('down', event.key);
    if(event.key === 'Backspace') {
        keysSequence.length = 0;  // keysSequenceをクリア
        inputDiv.textContent = '';  // 表示をクリア
    }
    keysSequence.push(event.key);
    inputDiv.textContent = keysSequence.join(', ');  // 現在のキーのシーケンスを表示

    if(event.key === 'Enter') {
        keysSequence.length = 0;  // keysSequenceをクリア
        inputDiv.textContent = '';  // 表示をクリア
        comboDiv.textContent += currentcommand;
    }
    
    if (keyActions.length === 4) {
        checkhadoSequence();
        checktatsumaki();
        checksyoryu();
    }

    if (keyActions.length === 8) {
        checkshinkuhadoSequence()
    }
});

document.addEventListener('keyup', function(event) {
    addKeyAction('up', event.key);
    checkhadoSequence();
    checktatsumaki();
    checksyoryu();
    checkshinkuhadoSequence();
});

function addKeyAction(action, key) {
    // keyActionsが8つのアクションを持っている場合、最も古いアクションを削除
    if (keyActions.length >= 8) {
        keyActions.shift();
    }
    // 新しいアクションを追加
    keyActions.push({ action, key });
}

function checkhadoSequence() {
    if (keyActions.length === 4) {
        const downS = keyActions[0].action === 'down' && keyActions[0].key === 's';
        const downD = keyActions[1].action === 'down' && keyActions[1].key === 'd';
        const upS = keyActions[2].action === 'up' && keyActions[2].key === 's';
        const upD = keyActions[3].action === 'up' && keyActions[3].key === 'd';

        if (downS && downD && upS && upD) {
            logDiv.innerHTML = `<div>波動拳!</div>`;
            currentcommand = '波動拳!';
        }
    }
}

function checktatsumaki(){
    if (keyActions.length === 4) {
        const downS = keyActions[0].action === 'down' && keyActions[0].key === 's';
        const downD = keyActions[1].action === 'down' && keyActions[1].key === 'a';
        const upS = keyActions[2].action === 'up' && keyActions[2].key === 's';
        const upD = keyActions[3].action === 'up' && keyActions[3].key === 'a';

        if (downS && downD && upS && upD) {
            logDiv.innerHTML = `<div>竜巻旋風脚!</div>`;
            currentcommand = '竜巻旋風脚!';
        }
    }
}

function checksyoryu(){
    if (keyActions.length === 4) {
        const downD = keyActions[0].action === 'down' && keyActions[0].key === 'd';
        const upD = keyActions[1].action === 'up' && keyActions[1].key === 'd';
        const downS = keyActions[2].action === 'down' && keyActions[2].key === 's';
        const downD1 = keyActions[3].action === 'down' && keyActions[3].key === 'd';

        if (downD && upD && downS && downD1) {
            logDiv.innerHTML = `<div>昇竜拳!</div>`;
            currentcommand = '昇竜拳!';

        }
    }
}

function checkshinkuhadoSequence() {
    if (keyActions.length === 8) {
        const downS = keyActions[0].action === 'down' && keyActions[0].key === 's';
        const downD = keyActions[1].action === 'down' && keyActions[1].key === 'd';
        const upS = keyActions[2].action === 'up' && keyActions[2].key === 's';
        const upD = keyActions[3].action === 'up' && keyActions[3].key === 'd';
        const downS2 = keyActions[4].action === 'down' && keyActions[4].key === 's';
        const downD2 = keyActions[5].action === 'down' && keyActions[5].key === 'd';
        const upS2 = keyActions[6].action === 'up' && keyActions[6].key === 's';
        const upD2 = keyActions[7].action === 'up' && keyActions[7].key === 'd';

        if (downS && downD && upS && upD && downS2 && downD2 && upS2 && upD2) {
            logDiv.innerHTML = `<div>真空波動拳!</div>`;
            currentcommand = '真空波動拳!';
        }
    }
}