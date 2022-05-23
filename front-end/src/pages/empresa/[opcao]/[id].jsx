import {useState,useEffect} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../../componentes/barra-lateral.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import FormularioEmpresa from "../../../componentes/formulario-empresa.jsx";
import {URL_EMPRESA} from "../../../global/variaveis.js";

export default function AcoesEmpresa() {
    const rota = useRouter();
    const [empresa,alteraEmpresa] = useState({id: rota.query.id,nome: "",cnpj: ""});

    async function buscarEmpresa() {
        try {
            const resposta = await fetch(URL_EMPRESA + "/" + empresa.id,{method: "GET"});
            if (resposta.ok) {
                const dado = await resposta.json();
                alteraEmpresa(dado);
            }
            else
                throw new Error("Erro ao buscar dados das empresas.");
        }
        catch (erro) {
            if (erro.message)
                Notiflix.Notify.failure(erro.message, {timeout: 5000});
            else
                Notiflix.Notify.failure("Erro de servidor.", {timeout: 5000});
        }
    }

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        buscarEmpresa();
    },[]);

    switch (rota.query.opcao) {
        case "visualizar":
            return (
                <div className="container-fluid">
                    <div className="row flex-nowrap">
                        <BarraLateral />
                        <div className="my-2 mx-2">
                            <h1> {empresa.nome} </h1>
                            <br/>
                            <p className="font-weight-light"> CNPJ : {empresa.cnpj} </p>
                            <br/>
                            <Link href="/empresa/listar">
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
                        <FormularioEmpresa empresa={empresa} ehExclusao={rota.query.opcao === "excluir"} />
                    </div>
                    <div className="fixed-bottom">
                        <Rodape />
                    </div>
                </div>
            );
    }
}
