import {useState,useEffect} from "react";
import Link from "next/link";
import Notiflix from 'notiflix';
import {mascaraCnpj} from "../global/funcoes.js";
import {URL} from "../global/variaveis.js";
import Espera from "./espera.jsx";

export default function FormularioEmpresa() {
    const [empresa,alteraEmpresa] = useState({nome: "", cnpj: ""});
    const [esperar,alteraEsperar] = useState(false);

    useEffect(() => {
        Notiflix.Notify.init({showOnlyTheLastOne: true});
    });

    async function submeterFormulario() {
        try {
            alteraEsperar(true);
            if (empresa.nome !== "" && empresa.cnpj !== "") {
                await fetch(URL + "/empresa", {method: "POST",body: JSON.stringify(empresa)});
            }
        }
        catch (erro) {
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
                                <input type="text" id="nome" name="nome" onChange={(event) => alteraEmpresa({...empresa,nome: event.target.value})} value={empresa.nome} className="form-control" required />
                                <div className="invalid-feedback"> 
                                    Informe o NOME. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-4">
                                <label htmlFor="cnpj" className="form-label fw-bold"> CNPJ* </label>
                                <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" onChange={(event) => alteraEmpresa({...empresa,cnpj: event.target.value})} value={mascaraCnpj(empresa.cnpj)} className="form-control" required />
                                <div className="invalid-feedback">
                                    Informe o CNPJ.
                                </div>
                            </div>
                        </div>
                        <br />
                        <button type="button" className="btn btn-primary" onClick={() => submeterFormulario()}> 
                            Cadastrar 
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
