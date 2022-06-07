import {Fragment,useState,useEffect} from "react";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import Rodape from "../../componentes/rodape.jsx";
import Espera from "../../componentes/espera.jsx";
import {URL_EMPRESA,QTD_PAGINAS_INTERMEDIARIAS} from "../../global/variaveis.js";
import {obtemMensagemErro} from "../../global/funcoes.js";

export default function ListarEmpresa() {
    const [nome,alteraNome] = useState("");
    const [empresas,alteraEmpresas] = useState([]);
    const [dados,alteraDados] = useState({});
    const [esperar,alteraEsperar] = useState(false);
    const [paginas,alteraPaginas] = useState([]);

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        buscarEmpresas();
    },[]);

    useEffect(() => {
        buscarEmpresas(nome);
    },[nome]);

    useEffect(() => {
        let pg = [];
        for (let i = 1; i <= QTD_PAGINAS_INTERMEDIARIAS; i++)
            if (i <= dados.numeroPaginas)
                pg.push(i);
        
        alteraPaginas(pg);
    },[dados]);

    async function buscarEmpresas(nome = undefined,pagina = 0) {
        try {
            alteraEsperar(true);
            let url = URL_EMPRESA;

            if (nome && pagina)
                url += "?nome=" + nome + "&pagina=" + pagina;
            else if (!nome && pagina)
                url += "?pagina=" + pagina;
            
            const resposta = await fetch(url,{method: "GET"});
            const msg = await obtemMensagemErro(resposta);

            if (msg && msg !== "")
                throw new Error(msg);

            const dados = await resposta.json();
            alteraEmpresas(dados.empresas);
            alteraDados(dados);
        }
        catch (erro) {
            Notiflix.Notify.failure(erro.message, {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    function cliqueAnterior() {
        let pg = [];

        for (let i = 0; i < paginas.length;i++)
            pg[i] = paginas[i] - 1;
        
        alteraPaginas(pg);
        buscarEmpresas(undefined,paginas[0]);
    }

    function cliqueProximo() {
        let pg = [];

        for (let i = 0; i < paginas.length;i++)
            pg[i] = paginas[i] + 1;
        
        alteraPaginas(pg);
        buscarEmpresas(undefined,paginas[0] + 1);
    }

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

    function mostrarPaginas() {
        return paginas.map(pagina => (
            <Fragment>
                {
                    pagina === dados.paginaAtual + 1
                    ?
                        <li key={pagina} className="page-item active">
                            <a href="#" onClick={() => buscarEmpresas(undefined,pagina - 1)} className="page-link"> {pagina} </a>
                        </li>
                    :
                        <li key={pagina} className="page-item">
                            <a href="#" onClick={() => buscarEmpresas(undefined,pagina - 1)} className="page-link"> {pagina} </a>
                        </li>
                }
            </Fragment>
        ));
    }

    function paginacao() {
        let urlAnterior = URL_EMPRESA + "?pagina=";
        let urlProximo = URL_EMPRESA + "?pagina=";
        let desabilitaAnterior = "";
        let desabilitaProximo = "";
        
        if (paginas[0] === 1)
            desabilitaAnterior = "disabled";
        
        if (dados.numeroPaginas === paginas[paginas.length - 1])
            desabilitaProximo = "disabled";
    
        return (
            <ul className="pagination mx-4">
                <li className={`page-item ${desabilitaAnterior}`}>
                    <Link href={urlAnterior}>
                        <a href="#" onClick={() => cliqueAnterior()} className="page-link">
                            Anterior
                        </a>
                    </Link>
                </li>
                {mostrarPaginas()}
                <li className={`page-item ${desabilitaProximo}`}>
                    <Link href={urlProximo}>
                        <a href="#" onClick={() => cliqueProximo()} className="page-link">
                            Próximo
                        </a>
                    </Link>
                </li>
            </ul>
        );
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
                            <h3> Empresas </h3>
                            <div className="row" style={{overflowY: "scroll",height: "45vh"}}>
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
                    {paginacao()}
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
