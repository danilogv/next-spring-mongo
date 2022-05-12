import React from "react";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import FormularioFuncionario from "../../componentes/formulario-funcionario.jsx";
import Rodape from "../../componentes/rodape.jsx";

export default function Formulario() {
    return (
        <div className="container-fluid">
            <div className="row flex-nowrap">
                <BarraLateral />
                <FormularioFuncionario />
            </div>
            <div className="fixed-bottom">
                <Rodape />
            </div>
        </div>
    );
}
