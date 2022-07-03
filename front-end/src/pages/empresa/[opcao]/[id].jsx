import {useState,useEffect,useContext} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../../componentes/barra-lateral.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import Espera from "../../../componentes/espera.jsx";
import FormularioEmpresa from "../../../componentes/formulario-empresa.jsx";
import {URL_EMPRESA,configPagina} from "../../../global/variaveis.js";
import {obtemMensagemErro} from "../../../global/funcoes.js";

export default function AcoesEmpresa() {
    const rota = useRouter();
    const [empresa,alteraEmpresa] = useState({id: rota.query.id,nome: "",cnpj: ""});
    const [esperar,alteraEsperar] = useState(false);
    let token = "";

    if (typeof window !== 'undefined')
        token = localStorage.getItem("token");

    async function buscarEmpresa() {
        try {
            alteraEsperar(true);
            const cabecalho = {...configPagina,"Authorization": "Bearer " + token};
            const resposta = await fetch(URL_EMPRESA + "/" + empresa.id,{method: "GET",headers: cabecalho});
            const msg = await obtemMensagemErro(resposta);
            
            if (msg && msg !== "")
                throw new Error(msg);

            const dado = await resposta.json();
            alteraEmpresa(dado);
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
        buscarEmpresa();
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
                        {
                            esperar
                            ?
                                <Espera />
                            :
                                undefined
                        }
                        <BarraLateral />
                        <FormularioEmpresa empresa={empresa} ehExclusao={rota.query.opcao === "excluir"} />
                    </div>
                    <Rodape />
                </div>
            );
    }
}
