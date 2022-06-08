import {Fragment,useState} from "react";
import Link from "next/link";
import BarraNavegacao from "../../../componentes/barra-navegacao.jsx";
import Rodape from "../../../componentes/rodape.jsx";

export default function CadastrarUsuario() {
    const [usuario,alteraUsuario] = useState({login: "", email: ""});

    return (
        <Fragment>
            <BarraNavegacao />
            <form className="was-validated" style={{marginTop: "12vh"}}>
                <div className="row mx-2">
                    <div className="col-12 col-sm-4">
                        <label htmlFor="email" className="form-label"> E-mail </label>
                        <input type="text" id="email" name="email" className="form-control" value={usuario.email} onChange={(event) => alteraUsuario({...usuario,email: event.target.value})} required />
                        <div className="invalid-feedback"> 
                            Informe o E-MAIL. 
                        </div>
                    </div>
                    <div className="col-12 col-sm-4">
                        <label htmlFor="senha" className="form-label"> Senha </label>
                        <input type="password" id="senha" name="senha" className="form-control" value={usuario.senha} onChange={(event) => alteraUsuario({...usuario,senha: event.target.value})} required />
                        <div className="invalid-feedback"> 
                            Informe a SENHA.
                        </div>
                    </div>
                </div>
                <br />
                <div className="row mx-2">
                    <div className="col-sm-4">
                        <button type="submit" class="btn btn-primary"> Cadastrar </button>
                        <Link href="/usuario">
                            <a>
                                <button type="button" class="btn btn-primary mx-2"> Voltar </button>
                            </a>
                        </Link>
                    </div>
                </div>
            </form>
            <Rodape />
        </Fragment>
    )
}
