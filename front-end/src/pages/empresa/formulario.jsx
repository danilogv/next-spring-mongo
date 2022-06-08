import React from "react";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import FormularioEmpresa from "../../componentes/formulario-empresa.jsx";
import Rodape from "../../componentes/rodape.jsx";

export default function Formulario() {
    return (
        <div className="container-fluid">
            <div className="row flex-nowrap">
                <BarraLateral />
                <FormularioEmpresa />
            </div>
            <Rodape />
        </div>
    );
}
