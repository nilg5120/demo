function getComputerChoice() {
    const choices = ['グー', 'チョキ', 'パー'];
    const randomIndex = Math.floor(Math.random() * choices.length);
    return choices[randomIndex];
}

function determineWinner(userChoice, computerChoice) {
    if (userChoice === computerChoice) {
        return '引き分けです';
    }

    if ((userChoice === 'グー' && computerChoice === 'チョキ') ||
        (userChoice === 'チョキ' && computerChoice === 'パー') ||
        (userChoice === 'パー' && computerChoice === 'グー')) {
        return 'あなたの勝ちです';
    }

    return 'あなたの負けです';
}

function playRockPaperScissors(userChoice) {
    const computerChoice = getComputerChoice();
    const result = determineWinner(userChoice, computerChoice);
    document.getElementById('result').innerText = `あなたの選択: ${userChoice}\nコンピュータの選択: ${computerChoice}\n${result}`;
}

document.getElementById('rock').addEventListener('click', function() {
    playRockPaperScissors('グー');
});

document.getElementById('scissors').addEventListener('click', function() {
    playRockPaperScissors('チョキ');
});

document.getElementById('paper').addEventListener('click', function() {
    playRockPaperScissors('パー');
});