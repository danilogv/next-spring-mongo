import BarraLateral from "../../componentes/barra-lateral";
import Rodape from "../../componentes/rodape";

export default function ListarFuncionario() {
    return (
        <div className="container-fluid">
            <div className="row flex-nowrap">
                <BarraLateral />
                <div className="col-sm-10">
                    Content area funcionário...
                    <div className="position-absolute bottom-0" style={{width: "81.5vw"}}>
                        <Rodape />
                    </div>
                </div>
            </div>
        </div>
    );
}
