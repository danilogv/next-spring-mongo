import {useState} from "react";
import Link from "next/link";
import BarraLateral from "../../componentes/barra-lateral.jsx";
import Rodape from "../../componentes/rodape.jsx";

export default function ListarFuncionario() {
    const [nome,alteraNome] = useState("");
    const funcionarios = [
        {id: 1,nome: "Funcionário 1",cpf: "000.000.000-00",salario: 1200.00,idade: 30,dataDesligamento: undefined,empresa: {id: 1,nome: "Empresa 1"}},
        {id: 2,nome: "Funcionário 2",cpf: "111.111.111-11",salario: 3000.00,idade: 25,dataDesligamento: "2022-02-05",empresa: {id: 2,nome: "Empresa 2"}}
    ]; //está hard code

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
