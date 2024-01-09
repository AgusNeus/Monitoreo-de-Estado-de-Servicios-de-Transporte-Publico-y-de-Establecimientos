document.addEventListener('DOMContentLoaded', function() {
    // Manejar clic en el botón "Cerrar Seleccionados"
    document.getElementById('cerrarSeleccionados').addEventListener('click', function() {
        const selectedIncidents = [];
        document.querySelectorAll('input[type="checkbox"]:checked').forEach(function(checkbox) {
            const incidentId = checkbox.getAttribute('data-id');
            selectedIncidents.push(incidentId);
        });

        if (selectedIncidents.length > 0) {
            // Enviar una solicitud al servidor para cambiar el estado de los incidentes a "Cerrado"
            // Ejemplo con fetch:
            fetch('/cerrar-incidentes', {
                method: 'POST',
                body: JSON.stringify(selectedIncidents),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        // Actualizar el estado de las filas en la tabla a "Cerrado"
                        selectedIncidents.forEach(incidentId => {
                            const row = document.querySelector(`tr[data-id="${incidentId}"`);
                            if (row) {
                                row.querySelector('input[type="checkbox"]').setAttribute('disabled', true);
                                row.querySelector('input[type="checkbox"]').remove();
                                row.querySelector('span').textContent = 'Cerrado';
                            }
                        });
                    }
                });
        }
    });

    // Manejar cambio en el elemento <select> con ID "filtro"
    const filtroSelect = document.getElementById('filtro');
    const busquedaInput = document.getElementById('busqueda');

    filtroSelect.addEventListener('change', function() {
        filtrarIncidentes();
    });

    busquedaInput.addEventListener('input', function() {
        filtrarIncidentes();
    });

    function filtrarIncidentes() {
        const filtroValor = filtroSelect.value;
        const busquedaValor = busquedaInput.value.toLowerCase();

        document.querySelectorAll('tbody tr').forEach(function(row) {
            const celda = row.querySelector(`td:nth-child(${getIndiceColumna(filtroValor)})`);
            if (filtroValor === '' || celda.textContent.toLowerCase().includes(busquedaValor)) {
                row.style.display = 'table-row';
            } else {
                row.style.display = 'none';
            }
        });
    }

    function getIndiceColumna(filtro) {
        switch (filtro) {
            case 'nombre':
                return 1;
            case 'servicio':
                return 2;
            case 'establecimiento':
                return 3;
            case 'comunidad':
                return 4;
            case 'abierto':
                return 5;
            case 'cerrado':
                return 5;
            default:
                return 0;
        }
    }
});
