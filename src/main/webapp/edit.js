function getCities() {
    $.ajax({
        type: 'GET',
        crossdomain: false,
        url: "http://localhost:8080/dreamjob/cities.do",
        dataType: 'json'
    }).done(function(incomeData) {
        addCities(incomeData);
    }).fail(function(err){
        alert(err);
    });
}


function addCities(cities) {
    let select = document.getElementById('cities');
    cities.forEach(x => {
        let opt = document.createElement('option');
        opt.value = x.id;
        opt.text = x.name;
        let cityID = Number.parseInt(document.getElementById('xID').value)
        if (x.id === cityID) {
            opt.selected = true;
        }
        select.add(opt);
    })
}

function validateInput() {
    let els = document.getElementsByTagName('input');
    let rslString = '';
    for (let i = 0; i < els.length; i++) {
        let element = els[i];
        if (element.getAttribute('type') === 'text'
           && element.value === '') {
            rslString += `Заполните поле ${element.getAttribute('name')}`;
        }
    }
    if (rslString !== '') {
        alert(rslString);
        return false;
    }
    return true;
}
getCities();