    var word = document.querySelector('input[type="text"]');
            word.oninvalid = function(e) {
                e.target.setCustomValidity("");
                if (!e.target.validity.valid) {
                    if (e.target.value.length == 0) {
                        e.target.setCustomValidity("You have to provide the city to search.");
                    } else if (e.target.value.length > 50) {
                        e.target.setCustomValidity("The provided city's length must be between 1 and 50.");
                    }
                }
            };