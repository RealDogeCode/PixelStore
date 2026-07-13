function validateFormElem(elem, errorMessage) {
    const valid = elem.checkValidity();

    if (errorMessage) {
        if (valid) {
            errorMessage.textContent = "";
            errorMessage.classList.remove("alert", "alert-danger");
        } else {
            errorMessage.textContent = elem.validationMessage;
            errorMessage.classList.add("alert", "alert-danger");
        }
    }

    return valid;
}

function validateForm(form) {
    let isValid = true;

    const elements = form.querySelectorAll("input, select, textarea");

    elements.forEach(elem => {
        // ignora checkbox
        if (elem.type === "checkbox") {
            return;
        }

        const errorMessage = document.getElementById(elem.id + "Error");

        if (!validateFormElem(elem, errorMessage)) {
            isValid = false;
        }
    });

    return isValid;
}