let cabecalho = new Headers();
cabecalho.append("Access-Control-Allow-Origin","*");
cabecalho.append("Content-Type","application/json");
export const URL_CABECALHO = cabecalho;
export const URL_EMPRESA = "http://localhost:8080/empresa";
export const URL_FUNCIONARIO = "http://localhost:8080/funcionario";
export const QTD_PAGINAS_INTERMEDIARIAS = 5;
