import {Fragment} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraLateral from "../../../componentes/barra-lateral.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import FormularioFuncionario from "../../../componentes/formulario-funcionario.jsx";
import {separadorMilhar} from "../../../global/funcoes.js";
import {URL_FUNCIONARIO} from "../../../global/variaveis.js";

export default function AcoesFuncionario() {
    const rota = useRouter();
    const [funcionario,alteraFuncionario] = useState({id: 1,nome: "",cpf: "",salario: "",idade: "",dataDesligamento: "",empresa: undefined});
    const [esperar,alteraEsperar] = useState(false);

    async function buscarFuncionario() {
        try {
            alteraEsperar(true);
            const resposta = await fetch(URL_FUNCIONARIO + "/" + funcionario.id,{method: "GET"});
            const msg = await obtemMensagemErro(resposta);
            if (msg && msg !== "")
                throw new Error(msg);
            const dado = await resposta.json();
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
