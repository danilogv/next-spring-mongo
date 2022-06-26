import {Fragment,useState} from "react";
import {useRouter} from "next/router";
import Link from "next/link";
import Notiflix from "notiflix";
import BarraNavegacao from "../../../componentes/barra-navegacao.jsx";
import Rodape from "../../../componentes/rodape.jsx";
import Espera from "../../../componentes/espera.jsx";
import {emailValido,obtemMensagemErro} from "../../../global/funcoes.js";
import {URL_USUARIO,cabecalho} from "../../../global/variaveis.js";

export default function CadastrarUsuario() {
    const [usuario,alteraUsuario] = useState({login: "", email: ""});
    const [esperar,alteraEsperar] = useState(false);
    const rota = useRouter();

    async function submeterFormulario() {
        if (!emailValido(usuario.email)) {
            Notiflix.Notify.failure("E-mail inválido.", {timeout: 5000});
            return;
        }
        
        try {
            alteraEsperar(true);
            const opcoes = {method: "POST",body: JSON.stringify(usuario),headers: cabecalho};
            const resposta = await fetch(URL_USUARIO,opcoes);
            const msg = await obtemMensagemErro(resposta);
            if (msg && msg !== "")
                throw new Error(msg);
            Notiflix.Notify.success("Cadastro realizado com sucesso.", {timeout: 5000});
            rota.push("/usuario");
        }
        catch(erro) {
            Notiflix.Notify.failure(erro.message, {timeout: 5000});
        }
        finally {
            alteraEsperar(false);
        }
    }

    return (
        <Fragment>
            <BarraNavegacao />
            <form className="was-validated" style={{marginTop: "12vh"}}>
                {
                    esperar
                    ?
                        <Espera />
                    :
                        undefined
                }
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
                        <button type="button" className="btn btn-primary" onClick={() => submeterFormulario()}> Cadastrar </button>
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
