function validarContraseña() {
    var contraseña = document.getElementById("contrasena").value;
    var confirmarContraseña = document.getElementById("confirmar-contrasena").value;
    var errorContraseña = document.getElementById("error-contraseña");

    if (contraseña !== confirmarContraseña) {
        errorContraseña.innerText = "Las contraseñas no coinciden";
        return false;
    } else {
        errorContraseña.innerText = "";
        return true;
    }
}
