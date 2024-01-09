document.addEventListener("DOMContentLoaded", function() {
    const provinciaSelect = document.getElementById("provincia");
    const municipioSelect = document.getElementById("municipio");
    const localidadSelect = document.getElementById("localidad");

    // Función para actualizar las opciones de municipio y localidad
    function actualizarOpciones() {
        const provinciaSeleccionada = provinciaSelect.value;
        municipioSelect.innerHTML = ""; // Limpiar opciones de municipio
        localidadSelect.innerHTML = ""; // Limpiar opciones de localidad

        var xhrMunicipios = new XMLHttpRequest();
        xhrMunicipios.open("GET", `/obtener-municipios?provincia=${provinciaSeleccionada}`, true);
        xhrMunicipios.onreadystatechange = function() {
            if (xhrMunicipios.readyState === 4 && xhrMunicipios.status === 200) {
                var municipios = JSON.parse(xhrMunicipios.responseText);

                // Llenar el select de municipio con las opciones obtenidas
                municipios.forEach(function(municipio) {
                    var option = document.createElement("option");
                    option.value = municipio.id;
                    option.textContent = municipio.nombre;
                    municipioSelect.appendChild(option);
                });
            }
        };
        xhrMunicipios.send();
    }

    // Escuchar cambios en la provincia
    provinciaSelect.addEventListener("change", actualizarOpciones);

    // Escuchar cambios en el municipio
    municipioSelect.addEventListener("change", function() {
        const municipioSeleccionado = municipioSelect.value;
        localidadSelect.innerHTML = ""; // Limpiar opciones de localidad

        var xhrLocalidades = new XMLHttpRequest();
        xhrLocalidades.open("GET", `/obtener-localidades?municipio=${municipioSeleccionado}`, true);
        xhrLocalidades.onreadystatechange = function() {
            if (xhrLocalidades.readyState === 4 && xhrLocalidades.status === 200) {
                var localidades = JSON.parse(xhrLocalidades.responseText);

                // Llenar el select de localidad con las opciones obtenidas
                localidades.forEach(function(localidad) {
                    var option = document.createElement("option");
                    option.value = localidad.nombre;
                    option.textContent = localidad.nombre;
                    localidadSelect.appendChild(option);
                });
            }
        };
        xhrLocalidades.send();
    });

    // Inicialmente, actualizar opciones basadas en la provincia seleccionada
    actualizarOpciones();
});