import {useState,useEffect} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import {mascaraCnpj,cnpjValido} from "../global/funcoes.js";
import {URL_EMPRESA,URL_CABECALHO} from "../global/variaveis.js";
import Espera from "./espera.jsx";


export default function FormularioEmpresa(props) {
    const [empresa,alteraEmpresa] = useState({nome: "", cnpj: ""});
    const [esperar,alteraEsperar] = useState(false);
    const rota = useRouter();

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
        if (props.empresa) {
            alteraEmpresa(props.empresa);
        }
    }, [props]);

    function confirmarRemocao(id) {
        const msg = "Deseja realmente excluir essa empresa?\nOs funcionários cadastrados também serão excluidos.";
        Notiflix.Confirm.show("Confirmação",msg,"Sim","Não",() => submeterFormulario(id));
    }

    async function submeterFormulario() {
        if (!cnpjValido(empresa.cnpj)) {
            Notiflix.Notify.failure("CNPJ inválido.", {timeout: 5000});
            return;
        }
        try {
            alteraEsperar(true);
            if (empresa.nome !== "" && empresa.cnpj !== "") {
                if (props.empresa) {
                    const id = props.empresa.id;
                    if (props.ehExclusao) {
                        await fetch(URL_EMPRESA + "/" + id, {method: "DELETE",body: JSON.stringify(empresa)});
                    }
                    else {
                        await fetch(URL_EMPRESA + "/" + id, {method: "PUT",body: JSON.stringify(empresa)});
                    }
                }
                else {
                    const r = await fetch(URL_EMPRESA, {method: "POST", body: JSON.stringify(empresa), headers: URL_CABECALHO});
                    if (!r.ok) {
                        const msg = await r.text();
                        throw new Error(msg);
                    }
                }
                Notiflix.Notify.success("Cadastro realizado com sucesso.", {timeout: 5000});
                rota.push("/empresa/listar");
            }
        }
        catch (erro) {
            if (erro.message)
                Notiflix.Notify.failure(erro.message, {timeout: 5000});
            else
                Notiflix.Notify.failure("Erro de servidor.", {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    return (
        <div className="col-sm-10">
            <div className="container-fluid mt-3">
                <div className="row">
                    {
                        esperar
                        ?
                            <Espera />
                        :
                            undefined
                    }
                    <form className="was-validated">
                        <div className="row">
                            <div className="col-12 col-sm-6">
                                <label htmlFor="nome" className="form-label fw-bold"> Nome* </label>
                                <input type="text" id="nome" name="nome" onChange={(event) => alteraEmpresa({...empresa,nome: event.target.value})} value={empresa.nome} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback"> 
                                    Informe o NOME. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-4">
                                <label htmlFor="cnpj" className="form-label fw-bold"> CNPJ* </label>
                                <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" onChange={(event) => alteraEmpresa({...empresa,cnpj: event.target.value})} value={mascaraCnpj(empresa.cnpj)} className="form-control" readOnly={props.ehExclusao} required />
                                <div className="invalid-feedback">
                                    Informe o CNPJ.
                                </div>
                            </div>
                        </div>
                        <br />
                        <button type="button" className="btn btn-primary" onClick={props.ehExclusao ? () => confirmarRemocao(props.empresa.id) : () => submeterFormulario()}> 
                            {
                                props.ehExclusao
                                ?
                                    <span> Excluir </span> 
                                :
                                    <span> Cadastrar </span> 
                            }
                            
                        </button>
                        <Link href="/empresa/listar">
                            <a className="mx-2">
                                <button type="button" className="btn btn-primary"> 
                                    Cancelar 
                                </button>
                            </a>
                        </Link>
                    </form>
                </div>
            </div>
        </div>
    );
}
