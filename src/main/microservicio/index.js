const express = require ('express')
const app = express()
const port = 3000
const bodyParser = require("body-parser")


app.post("/", bodyParser.json(), (req,res)=>{


    const entidades = {};

    req.body.incidentes.forEach(incidente =>{

        if (!Object.keys(entidades).includes(incidente.entidad)){
            entidades[incidente.entidad] = {}
            entidades[incidente.entidad].incidentes = []
        }
        entidades[incidente.entidad].incidentes.push(incidente);
    })

    Object.keys(entidades).forEach(key=>{
        const entidad = entidades[key];
        let sumatoriaTiempoResolucionIncidente = 0;
        let cantIncidentesNoResueltos = 0;
        let comunidadesAfectadas = [];

        entidad.incidentes.forEach(incidente=>{

            if (!comunidadesAfectadas.includes(incidente.comunidad)) {
                comunidadesAfectadas.push(incidente.comunidad)
            }

            if (!incidente.fechaHoraCierre)
                return cantIncidentesNoResueltos ++

            const fechaApertura = new Date(incidente.fechaHoraApertura);
            const fechaCierre = new Date(incidente.fechaHoraCierre);
            const tiempoResolucionIncidente = fechaCierre - fechaApertura;
            sumatoriaTiempoResolucionIncidente += tiempoResolucionIncidente;
        })

        let miembrosAfectados = 0

        req.body.comunidades.forEach(comunidad=>{

            if (comunidadesAfectadas.includes(comunidad.nombre)){

                miembrosAfectados += comunidad.miembros.reduce((acc,miembro)=>miembro.rol == 1 ? acc + 1 : acc , 0) //el reduce es un sumador que itera a traves de tdo el array y devuelve el accumulador 1 ES EL AFECTADO!!!
            }
        })

        entidad.impacto = (sumatoriaTiempoResolucionIncidente + cantIncidentesNoResueltos * req.body.CNF) * miembrosAfectados;

    })

    function ordenarPorImpacto(jsonData) {
        // Convierte el JSON en un array de objetos con clave y valor
        const entries = Object.entries(jsonData);

        // Ordena el array de objetos por el valor de "impacto" de mayor a menor
        const sortedEntries = entries.sort((a, b) => b[1].impacto - a[1].impacto);

        // Crea un nuevo objeto a partir del array ordenado
        const sortedJSON = Object.fromEntries(sortedEntries);

        return sortedJSON;
    }


    return res.send(ordenarPorImpacto(entidades)).json();
})

app.listen(port, () => console.log("conectado!"));

