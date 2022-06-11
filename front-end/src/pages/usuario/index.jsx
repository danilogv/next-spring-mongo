import {Fragment,useState} from "react";
import Link from "next/link";
import Notiflix from "notiflix";
import Rodape from "../../componentes/rodape.jsx";
import BarraNavegacao from "../../componentes/barra-navegacao.jsx";
import Espera from "../../componentes/espera.jsx";
import {URL_CABECALHO} from "../../global/variaveis.js";
import {obtemMensagemErro} from "../../global/funcoes.js";

export default function Login() {
    const [usuario,alteraUsuario] = useState({email: "",senha: ""});
    const [esperar,alteraEsperar] = useState(false);

    async function submeterFormulario() {
        try {
            alteraEsperar(true);
            const opcoes = {method: "POST",body: JSON.stringify(usuario),headers: URL_CABECALHO};
            const resposta = await fetch("http://127.0.0.1/login",opcoes);
            console.log(resposta);
            /*const msg = await obtemMensagemErro(resposta);
            if (msg && msg !== "")
                throw new Error(msg);
            Notiflix.Notify.success("Cadastro realizado com sucesso.", {timeout: 5000});*/
        }
        catch (erro) {
            Notiflix.Notify.failure(erro.message, {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    return (
        <Fragment>
            <BarraNavegacao />
            <div className="d-flex align-items-center justify-content-center vh-100">
                <form className="was-validated">
                    {
                        esperar
                        ?
                            <Espera />
                        :
                            undefined
                    }
                    <div className="mb-3 form-control-sm">
                        <label htmlFor="email" className="form-label"> E-mail </label>
                        <input type="text" id="email" name="email" className="form-control" value={usuario.email} onChange={(event) => alteraUsuario({...usuario,email: event.target.value})} required />
                        <div className="invalid-feedback"> 
                            Informe o E-MAIL. 
                        </div>
                    </div>
                    <div className="mb-3 form-control-sm">
                        <label htmlFor="senha" className="form-label"> Senha </label>
                        <input type="password" id="senha" name="senha" className="form-control" value={usuario.senha} onChange={(event) => alteraUsuario({...usuario,senha: event.target.value})} required />
                        <div className="invalid-feedback"> 
                            Informe a SENHA. 
                        </div>
                    </div>
                    <div className="mb-3 form-control-sm">
                        <button type="button" className="btn btn-primary" onClick={() => submeterFormulario()}> Entrar </button>
                    </div>
                    <div className="row form-control-sm">
                        <div className="col-sm-2">
                            <Link href="/usuario/cadastro">
                                <a> Cadastro </a>
                            </Link>
                        </div>
                        <div className="px-5 col-sm-9">
                            <Link href="/usuario/senha">
                                <a> Esqueceu a senha? </a>
                            </Link>
                        </div>
                    </div>
                </form>
            </div>
            <Rodape />
        </Fragment>
    );

}
