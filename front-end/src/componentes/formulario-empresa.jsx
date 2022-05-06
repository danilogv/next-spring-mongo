import Link from "next/link";
import {useState} from "react";
import {mascaraCnpj} from "../global/funcoes.js";

export default function FormularioEmpresa() {
    const [nome,alteraNome] = useState("");
    const [cnpj,alteraCnpj] = useState("");

    return (
        <div className="col-sm-10">
            <div className="container-fluid mt-3">
                <div className="row">
                    <form action="/empresa/salvar" className="was-validated">
                        <div className="row">
                            <div className="col-12 col-sm-6">
                                <label htmlFor="nome" className="form-label fw-bold"> Nome* </label>
                                <input type="text" id="nome" name="nome" onChange={(event) => alteraNome(event.target.value)} value={nome} className="form-control" required />
                                <div className="invalid-feedback"> 
                                    Informe o NOME. 
                                </div>
                            </div>
                            <div className="col-12 col-sm-4">
                                <label htmlFor="cnpj" className="form-label fw-bold"> CNPJ* </label>
                                <input type="text" id="cnpj" name="cnpj" placeholder="00.000.000/0000-00" onChange={(event) => alteraCnpj(event.target.value)} value={mascaraCnpj(cnpj)} className="form-control" required />
                                <div className="invalid-feedback">
                                    Informe o CNPJ.
                                </div>
                            </div>
                        </div>
                        <br />
                        <button type="submit" className="btn btn-primary"> Cadastrar </button>
                        <Link href="/empresa/listar">
                            <a className="mx-2">
                                <button type="button" className="btn btn-primary"> Cancelar </button>
                            </a>
                        </Link>
                    </form>
                </div>
            </div>
        </div>
    );
}
