import {Fragment} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import BarraLateral from "../../../componentes/barra-lateral.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import FormularioFuncionario from "../../../componentes/formulario-funcionario.jsx";
import {separadorMilhar} from "../../../global/funcoes.js";

export default function AcoesFuncionario() {
    const rota = useRouter();
    const funcionario = {id: 1,nome: "Funcionário 1",cpf: "486.223.000-80",salario: 1200.50,idade: 30,dataDesligamento: undefined,empresa: {id: 1,nome: "Empresa 1"}}; //está hard code

    switch (rota.query.opcao) {
        case "visualizar":
            return (
                <div className="container-fluid">
                    <div className="row flex-nowrap">
                        <BarraLateral />
                        <div class="my-2 mx-2">
                            <h1> {funcionario.nome} </h1>
                            <br/>
                            <p class="font-weight-light"> CPF : {funcionario.cpf} </p>
                            <br/>
                            <p class="font-weight-light"> Salário : R${separadorMilhar(funcionario.salario.toString().replace(".",","))} </p>
                            <br/>
                            <p class="font-weight-light"> Idade : {funcionario.idade} </p>
                            <br/>
                            {
                                funcionario && funcionario.dataDesligamento
                                ?
                                    <Fragment>
                                        <p class="font-weight-light"> Data de desligamento : ${funcionario.dataDesligamento} </p>
                                        <br/>
                                    </Fragment>
                                :
                                    undefined
                            }
                            <p class="font-weight-light"> Empresa : {funcionario.empresa.nome} </p>
                            <br/>
                            <Link href="/funcionario/listar">
                                <a>
                                    <button type="button" className="btn btn-primary"> 
                                        Cancelar 
                                    </button>
                                </a>
                            </Link>
                        </div>
                    </div>
                    <div className="fixed-bottom">
                        <Rodape />
                    </div>
                </div>
            );
        case "editar":
        case "excluir":
            return (
                <div className="container-fluid">
                    <div className="row flex-nowrap">
                        <BarraLateral />
                        <FormularioFuncionario funcionario={funcionario} ehExclusao={rota.query.opcao === "excluir"} />
                    </div>
                    <div className="fixed-bottom">
                        <Rodape />
                    </div>
                </div>
            );
    }
}
