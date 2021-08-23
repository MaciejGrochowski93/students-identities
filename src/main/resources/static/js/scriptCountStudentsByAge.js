    var number = document.querySelector('input[type="number"]');
            number.oninvalid = function(e) {
                e.target.setCustomValidity("");
                if (!e.target.validity.valid) {
                    if (e.target.value.length == 0) {
                        e.target.setCustomValidity("You have to provide Student's age.");
                    } else if (e.target.value > 100) {
                        e.target.setCustomValidity("You have to provide age between 18 and 100.");
                    } else if (e.target.value < 1) {
                        e.target.setCustomValidity("You have to provide age between 18 and 100.");
                    }
                }
            };