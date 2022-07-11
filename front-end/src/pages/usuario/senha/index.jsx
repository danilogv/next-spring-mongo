import {Fragment,useState} from "react";
import Link from "next/link";
import BarraNavegacao from "../../../componentes/barra-navegacao.jsx";
import Rodape from "../../../componentes/rodape.jsx";

export default function EsqueceuSenha() {
    const [email,alteraEmail] = useState("");

    return (
        <Fragment>
            <BarraNavegacao />
            <form className="was-validated" style={{marginTop: "12vh"}}>
                <div className="row mx-2">
                    <div className="col-12 col-sm-4">
                        <label htmlFor="email" className="form-label"> E-mail </label>
                        <input type="text" id="email" name="email" className="form-control" value={email} onChange={(event) => alteraEmail(event.target.value)} required />
                        <div className="invalid-feedback"> 
                            Informe o E-MAIL. 
                        </div>
                    </div>
                </div>
                <br />
                <div className="row mx-2">
                    <div className="col-sm-4">
                        <button type="submit" className="btn btn-primary"> Enviar </button>
                        <Link href="/usuario">
                            <a>
                                <button type="button" className="btn btn-primary mx-2"> Voltar </button>
                            </a>
                        </Link>
                    </div>
                </div>
            </form>
            <Rodape />
        </Fragment>
    )
}
