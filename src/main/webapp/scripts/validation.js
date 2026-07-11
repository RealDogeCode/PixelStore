function validateFormElem(elem, errorMessage) {
    if (elem.checkValidity()) {
        errorMessage.textContent = "";
        errorMessage.classList.remove("alert", "alert-danger");
    } else {
        errorMessage.textContent = elem.validationMessage;
        errorMessage.classList.add("alert", "alert-danger");
    }
}