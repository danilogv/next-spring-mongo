import {useRouter} from "next/router";
import Link from "next/link";
import BarraLateral from "../../../componentes/barra-lateral";
import Rodape from "../../../componentes/rodape";

export default function AcaoEmpresa() {
    const rota = useRouter();
    const empresa = {nome: "Teste", cnpj: "12.345.678/0001-10"}; //está hard-code
    switch (rota.query.opcao) {
        case "visualizar":
            return (
                <div className="container-fluid">
                    <div className="row flex-nowrap">
                        <BarraLateral />
                        <div class="my-2 mx-2">
                            <h1> {empresa.nome} </h1>
                            <br/>
                            <p class="font-weight-light"> CNPJ : ${empresa.cnpj} </p>
                            <br/>
                            <Link href="/empresa/listar">
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
        case "alterar":
            break;
        case "excluir":
            break;
    }
}
