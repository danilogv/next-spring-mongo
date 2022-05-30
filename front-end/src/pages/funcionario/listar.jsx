import {useState,useEffect} from "react";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import Rodape from "../../componentes/rodape.jsx";
import Espera from "../../componentes/espera.jsx";
import {URL_FUNCIONARIO} from "../../global/variaveis.js";
import {obtemMensagemErro} from "../../global/funcoes.js";

export default function ListarFuncionario() {
    const [nome,alteraNome] = useState("");
    const [funcionarios,alteraFuncionarios] = useState([]);
    const [esperar,alteraEsperar] = useState(false);

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        buscarFuncionarios();
    },[]);

    useEffect(() => {
        buscarFuncionarios(nome);
    },[nome]);


    async function buscarFuncionarios(nome) {
        try {
            alteraEsperar(true);
            let url = URL_FUNCIONARIO;

            if (nome) {
                url += "?nome=" + nome;
            }

            const resposta = await fetch(url,{method: "GET"});
            const msg = await obtemMensagemErro(resposta);

            if (msg && msg !== "")
                throw new Error(msg);
            
            const dados = await resposta.json();
            alteraFuncionarios(dados);
        }
        catch (erro) {
            Notiflix.Notify.failure(erro.message, {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    function imprimirLinhasTabela() {
        return funcionarios.map(funcionario => (
            <tr key={funcionario.id} className="d-flex">
                <td className="col-9">
                    <span> {funcionario.nome} </span>
                </td>
                <td className="col-1">
                    <Link href={"/funcionario/visualizar/" + funcionario.id} passHref={true}>
                        <a>
                            <i className="fs-4 bi-eye"></i>
                        </a>
                    </Link>
                </td>
                <td className="col-1">
                    <Link href={"/funcionario/editar/" + funcionario.id} passHref={true}>
                        <a>
                            <i className="fs-4 bi-pencil"></i>
                        </a>
                    </Link>
                </td>
                <td className="col-1">
                    <Link href={"/funcionario/excluir/" + funcionario.id}>
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
                {
                    esperar
                    ?
                        <Espera />
                    :
                        undefined
                }
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
                            <h3> Funcionários </h3>
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
                    <Link href="/funcionario/formulario">
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
