import Link from "next/link";

export default function BarraLateral() {

    function limparToken() {
        localStorage.setItem("token","");
    }

    return (
        <div className="col-auto col-md-3 col-xl-2 px-sm-2 px-0 bg-dark">
            <div className="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
                <a href="/" className="d-flex align-items-center pb-3 mb-md-0 me-md-auto text-white text-decoration-none">
                    <span className="fs-5 d-none d-sm-inline">Sys Contábil</span>
                </a>
                <ul className="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <li className="nav-item">
                        <Link href="/empresa/listar">
                            <a className="nav-link align-middle px-0">
                                <i className="fs-4 bi-shop"></i> 
                                <span className="ms-1 d-none d-sm-inline">Empresa</span>
                            </a>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link href="/funcionario/listar">
                            <a className="nav-link align-middle px-0">
                                <i className="fs-4 bi-person"></i> 
                                <span className="ms-1 d-none d-sm-inline">Funcionário</span>
                            </a>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link href="/usuario">
                            <a className="nav-link align-middle px-0" onClick={() => limparToken()}>
                                <i className="fs-4 bi-reply"></i> 
                                <span className="ms-1 d-none d-sm-inline">Sair</span>
                            </a>
                        </Link>
                    </li>
                </ul>
                <hr />
            </div>
        </div>
    );
}
