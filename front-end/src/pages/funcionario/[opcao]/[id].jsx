import {Fragment,useState,useEffect} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../../componentes/barra-lateral.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import Espera from "../../../componentes/espera.jsx";
import FormularioFuncionario from "../../../componentes/formulario-funcionario.jsx";
import {separadorMilhar,obtemMensagemErro} from "../../../global/funcoes.js";
import {URL_EMPRESA,URL_FUNCIONARIO} from "../../../global/variaveis.js";

export default function AcoesFuncionario() {
    const rota = useRouter();
    const [esperar,alteraEsperar] = useState(false);
    const [funcionario,alteraFuncionario] = useState({
        id: rota.query.id,
        nome: "",
        cpf: "",
        salario: "",
        idade: "",
        dataDesligamento: "",
        empresa: undefined
    });

    async function buscarFuncionario() {
        try {
            alteraEsperar(true);
            let resposta = await fetch(URL_FUNCIONARIO + "/" + funcionario.id,{method: "GET"});
            let msg = await obtemMensagemErro(resposta);
            if (msg && msg !== "")
                throw new Error(msg);
            let dado = await resposta.json();
            resposta = await fetch(URL_EMPRESA + "/" + dado.empresa.id,{method: "GET"});
            msg = await obtemMensagemErro(resposta);
            if (msg && msg !== "")
                throw new Error(msg);
            const dadosEmpresa = await resposta.json();
            dado.empresa.nome = dadosEmpresa.nome;
            alteraFuncionario(dado);
        }
        catch (erro) {
            Notiflix.Notify.failure(erro.message, {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        alteraFuncionario({...funcionario, id: rota.query.id});
        buscarFuncionario();
    },[]);

    switch (rota.query.opcao) {
        case "visualizar":
            return (
                <div className="container-fluid">
                    <div className="row flex-nowrap">
                        {
                            esperar
                            ?
                                <Espera />
                            :
                                undefined
                        }
                        <BarraLateral />
                        <div className="my-2 mx-2">
                            <h1> {funcionario.nome} </h1>
                            <br/>
                            <p className="font-weight-light"> CPF : {funcionario.cpf} </p>
                            <br/>
                            <p className="font-weight-light"> Salário : R${separadorMilhar(funcionario.salario.toString().replace(".",","))} </p>
                            <br/>
                            <p className="font-weight-light"> Idade : {funcionario.idade} </p>
                            <br/>
                            {
                                funcionario && funcionario.dataDesligamento
                                ?
                                    <Fragment>
                                        <p className="font-weight-light"> Data de desligamento : {funcionario.dataDesligamento} </p>
                                        <br/>
                                    </Fragment>
                                :
                                    undefined
                            }
                            <p className="font-weight-light"> Empresa : {funcionario.empresa ? funcionario.empresa.nome : ""} </p>
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
