import {useState,useEffect} from "react";
import Link from "next/link";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import Rodape from "../../componentes/rodape.jsx";
import Notiflix from "notiflix";
import {URL_EMPRESA} from "../../global/variaveis.js";

export default function ListarEmpresa() {
    const [nome,alteraNome] = useState("");
    const [empresas,alteraEmpresas] = useState([]);

    async function buscarEmpresas() {
        try {
            const resposta = await fetch(URL_EMPRESA,{method: "GET"});
            if (resposta.ok) {
                const dados = await resposta.json();
                alteraEmpresas(dados);
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
        buscarEmpresas();
    },[]);

    function imprimirLinhasTabela() {
        return empresas.map(empresa => (
            <tr key={empresa.id} className="d-flex">
                <td className="col-9">
                    <span> {empresa.nome} </span>
                </td>
                <td className="col-1">
                    <Link href={`/empresa/visualizar/${empresa.id}`} passHref={true}>
                        <a>
                            <i className="fs-4 bi-eye"></i>
                        </a>
                    </Link>
                </td>
                <td className="col-1">
                    <Link href={`/empresa/editar/${empresa.id}`} passHref={true}>
                        <a>
                            <i className="fs-4 bi-pencil"></i>
                        </a>
                    </Link>
                </td>
                <td className="col-1">
                    <Link href={"/empresa/excluir/" + empresa.id}>
                        <a>
                            <i className="fs-4 bi-trash"></i>
                        </a>
                    </Link>
                </td>
            </tr>
        ));
    }

    return (
        <div className="container-fluid">
            <div className="row flex-nowrap">
                <BarraLateral />
                <div className="col-sm-10">
                    <div className="container-fluid mt-3">
                        <div className="mx-3">
                            <span> Pesquisar por nome : </span>
                            <div className="row">
                                <div className="col-12 col-sm-5">
                                    <input type="text" id="nome" name="nome" onChange={(event) => alteraNome(event.target.value)} value={nome} className="form-control" />
                                </div>
                            </div>
                            <br/>
                            <h3> Empresas </h3>
                            <div className="row" style={{overflowY: "scroll",height: "50vh"}}>
                                <div className="col-12 col-sm-9">
                                    <table className="table">
                                        <tbody>
                                            {imprimirLinhasTabela()}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br />
                    <Link href="/empresa/formulario">
                        <a className="mx-4">
                            <button type="button" className="btn btn-primary"> Cadastrar </button>
                        </a>
                    </Link>
                    
                </div>
            </div>
            <div className="fixed-bottom">
                <Rodape />
            </div>
        </div>
    );
}
