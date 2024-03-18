$(document).ready(function() {
    $.getJSON('/data/resume.json', function(data) {
        $('#header').text(data.header);
        populateSection(data.basicInfo, '基本情報');
        populateSection(data.education, '学歴・職歴');
        populateSection(data.qualifications, '資格');
        populateSection(data.hobbies, '趣味・特技');
    });
});

function populateSection(sectionData, sectionId) {
    const $section = $(`#${sectionId}`);

    if (sectionData.name || sectionData.birthday || sectionData.address || sectionData.phone) {
        const box1  = $('<div class="box1"></div>');
        const dl1 = $('<dl class="container"></dl>');

        const age = calculateAge(sectionData.birthday);
        const formattedDate = formatToJapaneseDate(sectionData.birthday);

        dl1.attr('id', 'personalDetails');
        dl1.append(`<div class="box1-1"><dt>ふりがな</dt><dd>${sectionData.namefurigana}</dd></div>`);
        dl1.append(`<div class="box1-1 boxname"><dt class="name">氏名</dt><dd>${sectionData.name}</dd></div>`);
        
        dl1.append(`<div class="box1-1"><dt>生年月日:</dt><dd>${formattedDate}生　（満 ${age}歳）</dd></div>`);
        box1.append(dl1);
        box1.append(`<div class="photo-container"><img src="${sectionData.photo}" alt="プロフィール写真"></div>`);
        $section.append(box1);

        const box2 = $('<div class="box2"></div>');
        const dl21 = $('<dl class="container"></dl>');
        const dl22 = $('<dl class="container"></dl>');

        dl21.attr('id', 'contactDetails');
        dl21.append(`<div class="box2-1"><dt>ふりがな</dt><dd>${sectionData.addressfurigana}</dd></div>`)
        dl21.append(`<div class="box2-1"><dt class="boxaddress">住所</dt><dd>${sectionData.address}</dd></div>`);
        dl22.append(`<div class="box2-2"><dt>電話番号</dt><dd>${sectionData.phone}</dd></div>`);
        dl22.append(`<div class="box2-2"><dt>e-mail</dt><dd>${sectionData.email}</dd></div>`);
        box2.append(dl21);
        box2.append(dl22);
        $section.append(box2);
    } else if (sectionData.history) {
        box3 = $('<div class="box3"></div>');
        const dl31 = $('<dl id="container3-1"></dl>');
        const dl32 = $('<dl id="container3-2"></dl>');
        const items = sectionData.history;
        dl31.append(`<div class="year">年</div>`);
        dl31.append(`<div class="month">月</div>`);
        dl31.append(`<div class="detail">学歴</div>`);

        items.forEach(item => {
            const [year, month] = item.date.split('-');
            dl32.append(`<div class="year">${year}</div>`);
            dl32.append(`<div class="month">${month}</div>`);
            dl32.append(`<div class="detail">${item.detail}</div>`);
        });
        
        box3.append(dl31);
        box3.append(dl32);
        $section.append(box3);
    } else if (sectionData.items) {
        box4 = $('<div class="box4"></div>');
        const ul = $('<ul class="qualification-list container"></ul>'); // クラスを追加
        ul.append(`<div class="year">年</div>`);
        ul.append(`<div class="month">月</div>`);
        ul.append(`<div class="detail">資格</div>`);
    
        sectionData.items.forEach(item => {
            const [year, month] = item.date.split('-');
            ul.append(`<div class="year">${year}</div>`);
            ul.append(`<div class="month">${month}</div>`);
            ul.append(`<div class="detail">${item.detail}</div>`);
        });
        
        box4.append(ul);
        $section.append(box4);
    } else if (sectionData.items2) {
        box5 = $('<div class="box5"></div>');
        const ul = $('<ul class="hobby-list container"></ul>'); // クラスを追加
        ul.append(`<li class="hobby">趣味・特技</li>`);
        sectionData.items2.forEach(item => {
            ul.append(`<li>${item}</li>`);
        });
    
        box5.append(ul);
        $section.append(box5); // これが欠けているので、box5を$sectionに追加します。
    }
    
}

// 生年月日から年齢を計算する関数
function calculateAge(birthday) {
    const birthDate = new Date(birthday); // 生年月日をDateオブジェクトに変換
    const today = new Date(); // 今日の日付
    let age = today.getFullYear() - birthDate.getFullYear(); // 年を比較して大まかな年齢を取得
    const monthDiff = today.getMonth() - birthDate.getMonth(); // 月を比較

    // 今年の誕生日を迎えていない場合、年齢から1を引く
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age;
}

function formatToJapaneseDate(dateString) {
    const parts = dateString.split('-');
    return `${parts[0]}年${parts[1]}月${parts[2]}日`;
}