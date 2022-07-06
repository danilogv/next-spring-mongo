import {Fragment,useState} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import Rodape from "../../componentes/rodape.jsx";
import BarraNavegacao from "../../componentes/barra-navegacao.jsx";
import Espera from "../../componentes/espera.jsx";
import {URL_USUARIO,configPagina} from "../../global/variaveis.js";

export default function Login() {
    const [usuario,alteraUsuario] = useState({email: "",senha: ""});
    const [esperar,alteraEsperar] = useState(false);
    const [token,alteraToken] = useState(undefined);
    const rota = useRouter();

    async function submeterFormulario() {
        try {
            alteraEsperar(true);
            const opcoes = {method: "POST",body: JSON.stringify(usuario),headers: configPagina};
            const resposta = await fetch(URL_USUARIO + "/login",opcoes);

            if (resposta.status === 200) {
                const token = await resposta.text();
                alteraToken(token);
            }
            else {
                const msg = "Usuário ou senha inválidos.";
                throw new Error(msg);
            }
        }
        catch (erro) {
            Notiflix.Notify.failure(erro.message,{timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    if (token) {
        if (typeof window !== "undefined")
            localStorage.setItem("token",token);
        
        rota.push("/empresa/listar");
    }
    else {
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
                            <button type="button" className="btn btn-primary" onClick={() => submeterFormulario({type: "salvarToken"})}> Entrar </button>
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

}
